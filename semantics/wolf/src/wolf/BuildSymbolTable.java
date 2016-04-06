package wolf;

import java.util.ArrayList;
import java.util.List;
import wolf.interfaces.*;
import wolf.enums.*;
import wolf.node.TIdentifier;

/**
 * A visitor responsible for building symbol tables for the
 * parser.
 * @author William Ezekiel
 * @author Kevin Dittmar
 * @author Joseph Alacqua
 * @version Apr 3, 2016
 */
public class BuildSymbolTable implements Visitor {

    List<SymbolTable> tables;
    SymbolTable program_table;
    SymbolTable current_def_table;
    int lambda_count = 0;
    
    /**
     * Visit a program
     * @param n a program
     */
    @Override
    public void visit(Program n) {
        tables = new ArrayList<>();
        program_table = new SymbolTable("Program Environment Table");        
        tables.add(program_table);
        for (Def def : n.def_list) {
            current_def_table = new SymbolTable(program_table, def.def_name.toString() + " Table");
            program_table.put(def.def_name, new Binding(def.def_name, new TableValue(def.type,current_def_table)));
            def.accept(this);
            tables.add(current_def_table);
        }
        n.function.accept(this);
        StringBuilder sb = new StringBuilder();
        for(SymbolTable st : tables) {
            sb.append(st);
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }
    
    /**
     * Visit a definition
     * @param n a definition object (Def)
     */
    @Override
    public Type visit(Def n) {
        // sig has no args
        if(n.sig != null) {
            n.sig.accept(this);
        }
        return n.function.accept(this);
    }
    
    /**
     * Visit a function signature
     * @param n a signature (Sig)
     */
    @Override
    public void visit(Sig n) {
        for(SigArg sig_arg: n.sig_args) {
            current_def_table.put(sig_arg.identifier, 
                    new Binding(sig_arg.identifier,new TableValue(sig_arg.type)));
        }
    }
    
    @Override
    public Type visit(SigArg n) {
        n.identifier.accept(this);
        return n.type.accept(this);
    }
    
    public Type visit(Type n) {
        return n.accept(this);
    }
    
    /**
     * Visit a function in the WOLF language.
     * @param n a function
     */
    @Override
    public Type visit(WolfFunction n) {
        if(n instanceof UserFunc) {
            return visit((UserFunc) n);
        } else if(n instanceof NativeUnary) {
            return visit((NativeUnary) n);
        } else if(n instanceof NativeBinary) {
            return visit((NativeBinary) n);
        } else if (n instanceof Branch) {
            return visit((Branch) n);
        } else if(n instanceof Fold) {
            return visit((Fold) n);
        } else if(n instanceof WolfMap) {
            return visit((WolfMap) n);
        } else if (n instanceof NativeListUnary) {
            return n.accept(this);
        } else if (n instanceof NativeListBinary) {
            return n.accept(this);
        } else {
            System.err.println("Invalid Function");
            System.exit(1);
            return null;
        }
    }
    
    /**
     * Visit a user function.
     * @param n a user function
     */
    @Override
    public Type visit(UserFunc n) {
        n.arg_list.accept(this);
        return n.user_func_name.accept(this);
    }
    
    /**
     * Visit a native unary
     * @param n a native unary
     */
    @Override
    public Type visit(NativeUnary n) {
        return n.accept(this);
    }
    
    /**
     * Visit a native binary
     * @param n a native unary
     */
    @Override
    public Type visit(NativeBinary n) {
        return n.accept(this);
    }
    
    /**
     * Visit a branch
     * @param n a branch
     */
    @Override
    public Type visit(Branch n) {
        Type condType = (Type) n.condition.accept(this);
        if(!condType.equals(new Type(FlatType.INTEGER))) {
            System.err.print("Condition not of integer type!");
            System.exit(1);
        }
        
        Type trueType = (Type)  n.true_branch.accept(this);
        Type falseType = (Type) n.false_branch.accept(this);
        if(!(trueType.equals(falseType))) {
            System.err.print("Ambiguous return types for branch: true branch: " 
                    + trueType + " false branch: " + falseType);
            System.exit(1);
        }
        return trueType;
    }
    
    /**
     * Visit a mapping function in the WOLF language
     * @param n a mapping function object (WolfMap)
     */
    @Override
    public Type visit(WolfMap n) {
        n.unary_op.accept(this);
        return n.list_argument.accept(this);
    }
    
    /**
     * Visit a fold function
     * @param n a fold function
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
     */
    @Override
    public Type visit(UnaryOp n) {
        if(n instanceof NativeUnary) {
            return visit((NativeUnary) n);
        } else if(n instanceof Identifier) {
            return visit((Identifier) n);
        } else if(n instanceof WolfLambda) {
            return visit((WolfLambda) n);
        } else {
            System.err.println("Invalid UnaryOp");
            System.exit(1);
            return null;
        }
    }
    
    /**
     * Visit a list argument
     * @param n a list argument
     */
    @Override
    public Type visit(ListArgument n) {
        if(n instanceof WolfList) {
            return visit((WolfList) n);
        } else if(n instanceof Identifier) {
            return visit((Identifier) n);
        } else if(n instanceof WolfFunction) {
            return visit((WolfFunction) n);
        }
        else {
            System.err.println("Invalid List Argument");
            System.exit(1);
            return null;
        }
    }
    
    /**
     * Visit a lambda in the WOLF language
     * @param n a lambda object (WolfLambda)
     */
    @Override
    public Type visit(WolfLambda n) {
        String lambda_table_name = "Lambda" + ++lambda_count;
        SymbolTable lambda_table = new SymbolTable(lambda_table_name);
        lambda_table.parent_table = current_def_table;
        current_def_table = lambda_table;
        
        
        n.sig.accept(this);
        Type type = n.function.accept(this);
        
        Identifier lambda_id = new Identifier(
            new TIdentifier(lambda_table_name)
        );
        tables.add(lambda_table);
        Binding binding = new Binding(
            lambda_id, 
            new TableValue(type, lambda_table)
        );
        if (lambda_table.parent_table == null) {
            program_table.put(lambda_id, binding);
        } else {
            lambda_table.parent_table.put(lambda_id, binding);
        }
        
        current_def_table = lambda_table.parent_table;
        return type;
    }
    
    /**
     * Visit a fold body
     * @param n a fold body
     */
    @Override
    public Type visit(FoldBody n) {
        n.bin_op.accept(this);
        return n.list_argument.accept(this);
    }
    
    /**
     * Visit a binary operation
     * @param n a binary operation
     */
    @Override
    public Type visit(BinOp n) {
        if(n instanceof NativeBinary) {
            return visit((NativeBinary) n);
        } else if(n instanceof Identifier) {
            return visit((Identifier) n);
        } else if(n instanceof WolfLambda) {
            return visit((WolfLambda) n);
        }
        else {
            System.err.println("Invalid BinOp");
            System.exit(1);
            return null;
        }
    }
    
    /**
     * Visit a list in the WOLF language
     * @param n a list object (WolfList)
     */
    @Override
    public Type visit(WolfList n) {
        for(Arg arg:n.arg_list) {
            arg.accept(this);
        }
        return n.accept(this);
    }
    
    /**
     * Visit an identifier
     * @param n an identifier
     */
    @Override
    public Type visit(Identifier n) {
        // An identifier can be anything, it depends on the context of the
        // fucntion. Identifiers are only used for user-defined functions, and
        // its type cannot be determined until said function has been evaluated.
        // A general type, PARAMETER, is used to encompass all possibilities.
        
        return n.accept(this);
    }
    
    /**
     * Visit an argument
     * @param n an argument
     * @return ???
     */
    @Override
    public Type visit(Arg n) {
        if(n instanceof WolfFunction) {
            return visit((WolfFunction) n);
        } else if(n instanceof WolfList) {
            return visit((WolfList) n);
        } else if(n instanceof IntLiteral) {
            return visit((IntLiteral) n);
        } else if(n instanceof FloatLiteral) {
            return visit((FloatLiteral) n);
        } else if(n instanceof Identifier) {
            return visit((Identifier) n);
        } else if(n instanceof WolfString) {
            return visit((WolfString) n);
        } else {
            System.err.println("Invalid Arg");
            System.exit(1);
            return null;
        }
    }
    
    /**
     * Visit a user function name
     * @param n a user function name
     */
    @Override
    public Type visit(UserFuncName n) {
        if(n instanceof Identifier) {
            return visit((Identifier) n);
        } else if(n instanceof WolfLambda) {
            return visit((WolfLambda) n);
        } else {
            System.err.println("Invalid UserFuncName");
            System.exit(1);
            return null;
        }
    }
    
    /**
     * Visit a list of arguments.
     * @param n a list of arguments (Args)
     */
    @Override
    public void visit(ArgsList n) {
        for(Arg arg:n.getArgList()) {
            arg.accept(this);
        }
    }
    
    /**
     * Visit a list of arguments for a list.  They must be the same type
     * @param n a list of arguments for a list (ListArgs)
     */
    @Override
    public Type visit(ListArgsList n) {
        Type type = null;
        for(Arg arg:n.getArgList()) {
            Type next_type = arg.accept(this);
            if (type != null && !type.equals(next_type)) {
                System.err.println("List has multiple types: " +
                    type.toString() + " and " + next_type.toString());
                System.exit(1);
            }
            type = next_type;
        }
        return type;
    }
    
    /**
     * Visit an integer literal
     * @param n an integer literal
     */
    @Override
    public Type visit(IntLiteral n) {
        return n.accept(this);
    }
    
    /**
     * Visit a float literal
     * @param n a float literal
     */
    @Override
    public Type visit(FloatLiteral n) {
       return n.accept(this);
    }
    /**
     * Visit a string in the WOLF language
     * @param n a string object (WolfString)
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
        if(n instanceof StringBody) {
            visit((StringBody) n);
        } else if(n instanceof StringEscapeSeq) {
            visit((StringEscapeSeq) n);
        } else {
            System.err.println("Invalid StringMiddle");
            System.exit(1);
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
    public void visit(StringEscapeSeq n) {
        n.accept(this);
    }
}