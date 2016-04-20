package wolf;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import wolf.enums.*;
import wolf.interfaces.*;

/**
 * Type Checking
 *
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @version Apr 10, 2016
 */
public class SemanticTypeCheck implements Visitor {

    List<SymbolTable> tables;
    SymbolTable program_table;
    SymbolTable current_def_table;
    Stack<String> last_table_names;
    List<SymbolTable> lambda_table_list;
    
    public SemanticTypeCheck(List<SymbolTable> tables, 
            SymbolTable program_table, List<SymbolTable> lambda_table_list) {
        this.tables = tables;
        this.program_table = this.current_def_table = program_table;
        last_table_names = new Stack<>();
        this.lambda_table_list = lambda_table_list;
    }
    
    /**
     * Visit a program
     * @param n a program
     */
    @Override
    public void visit(Program n) {
        for(Def def:n.def_list) {
            last_table_names.push(current_def_table.table_name);
            current_def_table = getTableWithName(def.def_name.identifier.getText());
            def.accept(this);
            current_def_table = getTableWithName(last_table_names.pop());
        }
        n.function.accept(this);
        System.out.println("Type Checking Completed Successfully!");
    }

    /**
     * Visit a definition
     * @param n a definition object (Def)
     * @return the return type of the function definition
     */
    @Override
    public Type visit(Def n) {
        Type actual_type = n.function.accept(this);
        Type expected_type = n.type;
        if(!expected_type.equals(actual_type)) {
            String def_name = n.def_name.identifier.getText();
            TypeErrorReporter.mismatchDefType(
                actual_type, expected_type, def_name);
        }
        return expected_type;
    }

    /**
     * Visit a function signature. Never visited in SemanticTypeCheck. 
     * Only here to please the Java compiler.
     * @param n a signature (Sig)
     */
    @Override
    public void visit(Sig n) {
        // nothing to type check in a Sig
    }

    /**
     * Visit a signature argument. Never visited in SemanticTypeCheck. 
     * Only here to please the Java compiler.
     * @param n is the SigArg to visit
     * @return null
     */
    @Override
    public Type visit(SigArg n) {
        // nothing to type check in a SigArg
        return null;
    }

    /**
     * Visit a type
     * @param n is the type to visit
     * @return the type of the Type
     */
    @Override
    public Type visit(Type n) {
        return n.accept(this);
    }

    /**
     * Visit a function in the WOLF language.
     * @param n a function
     * @return the return type of the function
     */
    @Override
    public Type visit(WolfFunction n) {
        if (n instanceof UserFunc) {
            return visit((UserFunc) n);
        } else if (n instanceof NativeUnary) {
            return visit((NativeUnary) n);
        } else if (n instanceof NativeBinary) {
            return visit((NativeBinary) n);
        } else if (n instanceof Branch) {
            return visit((Branch) n);
        } else if (n instanceof Fold) {
            return visit((Fold) n);
        } else if (n instanceof WolfMap) {
            return visit((WolfMap) n);
        } else if (n instanceof NativeListUnary) {
            return n.accept(this);
        } else if (n instanceof NativeListBinary) {
            return n.accept(this);
        } else {
            throw new IllegalArgumentException("Invalid Function");
        }
    }

    /**
     * Visit a user function.
     * @param n a user function
     * @return the return type of the user function
     */
    @Override
    public Type visit(UserFunc n) {
        Type type = null;
        if(n.user_func_name instanceof Identifier) {
            Identifier id = (Identifier) n.user_func_name;
            last_table_names.push(current_def_table.table_name);
            current_def_table = getTableWithName(id.identifier.getText());
            type = n.user_func_name.accept(this);
            n.arg_list.accept(this);
            current_def_table = getTableWithName(last_table_names.pop());
        }
        else if(n.user_func_name instanceof WolfLambda) {
            // Save the current scope.
            last_table_names.push(current_def_table.table_name);
            // Get the lambda and its corresponding table.
            WolfLambda lambda = (WolfLambda) n.user_func_name;
            current_def_table = lambda_table_list.get(lambda.getId());
            // Check the lambda's arguments.
            n.arg_list.accept(this);
            // Reset the scope
            current_def_table = getTableWithName(last_table_names.pop());
            // Get the lambda's return type
            type = visitLambda((WolfLambda) n.user_func_name);
        }
        else {
            throw new IllegalArgumentException("Invalid UserFunc");
        }
        return type;
    }

    /**
     * Visit a native unary
     * @param n a native unary
     * @return the return type of the unary function
     */
    @Override
    public Type visit(NativeUnary n) {
        return n.accept(this);
    }

    /**
     * Visit a native binary
     * @param n a native unary
     * @return the return type of the binary function
     */
    @Override
    public Type visit(NativeBinary n) {
        return n.accept(this);
    }

    /**
     * Visit a branch
     * @param n a branch
     * @return the return type of the branch
     */
    @Override
    public Type visit(Branch n) {
        Type condType = (Type) n.condition.accept(this);
        if (!condType.equals(new Type(FlatType.INTEGER))) {
           TypeErrorReporter.mismatchBranchCondition(condType);
        }

        Type trueType = (Type) n.true_branch.accept(this);
        Type falseType = (Type) n.false_branch.accept(this);
        if (!(trueType.equals(falseType))) {
            TypeErrorReporter.mismatchBranchTrueFalse(trueType, falseType);
        }
        return trueType;
    }

    /**
     * Visit a mapping function in the WOLF language
     * @param n a mapping function object (WolfMap)
     * @return the return type of the map
     */
    @Override
    public Type visit(WolfMap n) {
        Object type_object = n.unary_op.accept(this);
        Type arg_type = new Type(n.list_argument.accept(this).flat_type);
        // There's a single valid type for the operation.
        if (type_object instanceof Type) {
            Type valid_type = (Type) type_object;
            if (valid_type.equals(arg_type)) {
                return arg_type;
            }
        } 
        // The operation supports several different types
        else if (type_object instanceof List) {
            List<Type> valid_types = (List<Type>) type_object;
            if(valid_types.contains(arg_type)) {
                return arg_type;
            }
        }
        throw new UnsupportedOperationException(
            n.unary_op + " does not accept " + arg_type);
        
    }

    /**
     * Visit a fold function
     * @param n a fold function
     * @return the return type of the fold
     */
    @Override
    public Type visit(Fold n) {
        n.fold_symbol.accept(this);
        return n.fold_body.accept(this);
    }

    /**
     * Visit a fold symbol
     * @param n a fold symbol
     */
    @Override
    public void visit(FoldSymbol n) {
        n.accept(this);
    }

    /**
     * Visit a unary operation
     * @param n a unary operation object
     * @return the return type of the unary function
     */
    @Override
    public Type visit(UnaryOp n) {
        if (n instanceof NativeUnary) {
            return visit((NativeUnary) n);
        } else if (n instanceof Identifier) {
            return visit((Identifier) n);
        } else if (n instanceof WolfLambda) {
            return visitLambda((WolfLambda) n);
        } else {
            throw new IllegalArgumentException("Invalid UnaryOp");
        }
    }

    /**
     * Visit a list argument
     * @param n a list argument
     * @return type of list argument
     */
    @Override
    public Type visit(ListArgument n) {
        if (n instanceof WolfList) {
            return visit((WolfList) n);
        } else if (n instanceof Identifier) {
            return visit((Identifier) n);
        } else if (n instanceof WolfFunction) {
            return visit((WolfFunction) n);
        } else {
            throw new IllegalArgumentException("Invalid List Argument");
        }
    }

    /**
     * Visit a lambda in the WOLF language
     * @param n a lambda object (WolfLambda)
     * @return type of lambda
     */
    @Override
    public Type visit(WolfLambda n) {
        n.function.accept(this);
        n.sig.accept(this);
        SymbolTable lambda_table = lambda_table_list.get(n.getId());
        SymbolTable parent_table = lambda_table.parent_table;
        return parent_table.symbol_table.get(
            lambda_table.table_name
        ).table_value.type;
    }

    /**
     * Visit a fold body
     * @param n a fold body
     * @return the return type of the fold
     */
    @Override
    public Type visit(FoldBody n) {
        Object type_object = n.bin_op.accept(this);
        Type arg_type = new Type(n.list_argument.accept(this).flat_type);
        // There's a single valid type for the operation.
        if (type_object instanceof Type) {
            Type valid_type = (Type) type_object;
            if (valid_type.equals(arg_type)) {
                return arg_type;
            }
        } 
        // The operation supports several different types
        else if (type_object instanceof List) {
            List<Type> valid_types = (List<Type>) type_object;
            if(valid_types.contains(arg_type)) {
                return arg_type;
            }
        }
        throw new UnsupportedOperationException(
                n.bin_op + " does not accept " + arg_type);
    }

    /**
     * Visit a binary operation
     * @param n a binary operation
     * @return the return type of the binary function
     */
    @Override
    public Type visit(BinOp n) {
        if (n instanceof NativeBinary) {
            return visit((NativeBinary) n);
        } else if (n instanceof Identifier) {
            return visit((Identifier) n);
        } else if (n instanceof WolfLambda) {
            return visitLambda((WolfLambda) n);
        } else {
            throw new IllegalArgumentException("Invalid BinOp");
        }
    }

    /**
     * Visit a list in the WOLF language
     * @param n a list object (WolfList)
     * @return the type of the list
     */
    @Override
    public Type visit(WolfList n) {
        for (Arg arg : n.arg_list) {
            arg.accept(this);
        }
        return n.accept(this);
    }

    /**
     * Visit an identifier
     * @param n an identifier
     * @return the type of the identifier
     * An identifier can be anything, it depends on the context of the
     * function. Identifiers are only used for user-defined functions and
     * their parameters, which have user-specified types
     */
    @Override
    public Type visit(Identifier n) {
        return n.accept(this);
    }

    /**
     * Visit an argument
     * @param n an argument
     * @return type of argument
     */
    @Override
    public Type visit(Arg n) {
        if (n instanceof WolfFunction) {
            return visit((WolfFunction) n);
        } else if (n instanceof WolfList) {
            return visit((WolfList) n);
        } else if (n instanceof IntLiteral) {
            return visit((IntLiteral) n);
        } else if (n instanceof FloatLiteral) {
            return visit((FloatLiteral) n);
        } else if (n instanceof Identifier) {
            return visit((Identifier) n);
        } else if (n instanceof WolfString) {
            return visit((WolfString) n);
        } else {
            throw new IllegalArgumentException("Invalid Arg");
        }
    }

    /**
     * Visit a user function name
     * @param n a user function name
     * @return the type of the user-defined function
     */
    @Override
    public Type visit(UserFuncName n) {
        if (n instanceof Identifier) {
            return visit((Identifier) n);
        } else if (n instanceof WolfLambda) {
            return visitLambda((WolfLambda) n);
        } else {
            throw new IllegalArgumentException("Invalid UserFuncName");
        }
    }

    /**
     * Visit a list of arguments.
     * @param n a list of arguments (Args)
     */
    @Override
    public void visit(ArgsList n) {
        List<Type> actualArgFormat = new ArrayList();
        for (Arg arg : n.getArgList()) {
            actualArgFormat.add(arg.accept(this));
        }
        List<Type> expectedArgFormat = current_def_table.getArgFormat();
        
        // Must have same number of arguments AND argument formats
        // must be the same
        if(!actualArgFormat.equals(expectedArgFormat)) {
            TypeErrorReporter.mismatchArgumentFormat(actualArgFormat, 
                    expectedArgFormat, current_def_table.table_name);
        }
        
    }

    /**
     * Visit a list of arguments for a list. They must be the same type
     * @param n a list of arguments for a list (ListArgs)
     * @return the type of the list.  If all elements are not the same type,
     * it will die.
     */
    @Override
    public Type visit(ListArgsList n) {
        Type type = null;
        for (Arg arg : n.getArgList()) {
            Type next_type = arg.accept(this);
            if (type != null && !type.equals(next_type)) {
                throw new IllegalArgumentException("List has multiple types: "
                        + type.toString() + " and " + next_type.toString());
            }
            type = next_type;
        }
        return type;
    }

    /**
     * Visit an integer literal
     * @param n an integer literal
     * @return the INTEGER type; n.accept(this) is used by convention
     */
    @Override
    public Type visit(IntLiteral n) {
        return n.accept(this);
    }

    /**
     * Visit a float literal
     * @param n a float literal
     * @return the FLOAT type; n.accept(this) is used by convention
     */
    @Override
    public Type visit(FloatLiteral n) {
        return n.accept(this);
    }

    /**
     * Visit a string in the WOLF language
     * @param n a string object (WolfString)
     * @return the STRING type;  n.accept(this) is used by convention
     */
    @Override
    public Type visit(WolfString n) {
        return n.accept(this);
    }

    /**
     * Visit a string middle
     * @param n a string middle
     */
    @Override
    public void visit(StringMiddle n) {
        if (n instanceof StringBody) {
            visit((StringBody) n);
        } else if (n instanceof StringEscapeSeq) {
            visit((StringEscapeSeq) n);
        } else {
            throw new IllegalArgumentException("Invalid StringMiddle");
        }
    }

    /**
     * Visit a string body
     * @param n a string body
     */
    @Override
    public void visit(StringBody n) {
        n.accept(this);
    }

    /**
     * Visit a string escape sequence
     * @param n a string escape sequence
     */
    @Override
    public void visit(StringEscapeSeq n) {
        n.accept(this);
    }
    
    public SymbolTable getTableWithName(String name) {
        for(SymbolTable table: tables) {
            if(table.table_name.equals(name)) {
                return table;
            }
        }
        return null;
    }
    
    @Override
    public SymbolTable getCurrentDefTable() {
        return current_def_table;
    }
    
    private Type visitLambda(WolfLambda lambda) {
        last_table_names.push(current_def_table.table_name);
        current_def_table = lambda_table_list.get(lambda.getId());
        Type returnType = visit(lambda);
        current_def_table = getTableWithName(last_table_names.pop());
        return returnType;
    }
}
