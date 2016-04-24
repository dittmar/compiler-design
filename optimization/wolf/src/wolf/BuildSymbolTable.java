package wolf;

import java.util.ArrayList;
import java.util.List;

import wolf.enums.*;
import wolf.interfaces.*;
import wolf.node.TIdentifier;

/**
 * A visitor responsible for building symbol tables for the parser.
 *
 * @author William Ezekiel
 * @author Kevin Dittmar
 * @author Joseph Alacqua
 * @version Apr 3, 2016
 */
public class BuildSymbolTable implements Visitor {

    List<SymbolTable> tables;
    SymbolTable program_table;
    SymbolTable current_def_table;
    List<SymbolTable> lambda_table_list;
    int lambda_count = 0;

  /**
   * @return the list of all symbol tables
   */
  public List<SymbolTable> getTables() {
        return tables;
    }

  /**
   * @return the program table
   */
  public SymbolTable getProgramTable() {
        return program_table;
    }

  /**
   * @return the list of lambda tables.
   */
  public List<SymbolTable> getLambdaTables() {
        return lambda_table_list;
    }

    /**
     * Print the list of symbol tables.
     */
    public void displayTables() {
        StringBuilder sb = new StringBuilder();
        for (SymbolTable st : tables) {
            sb.append(st);
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }

    /**
     * Visit a program
     *
     * @param n a program
     * @return the list of all generated symbol tables.
     */
    @Override
    public Object visit(Program n) {
        tables = new ArrayList<>();
        program_table = current_def_table =
            new SymbolTable("Program Environment");
        lambda_table_list = new ArrayList<>();
        tables.add(program_table);
        for (Def def : n.def_list) {
            current_def_table = new SymbolTable(
                program_table,
                def.def_name.toString()
            );
            program_table.put(
                def.def_name,
                new Binding(
                    def.def_name,
                    new TableValue(def.type, current_def_table)
                )
            );
            def.accept(this);
            tables.add(current_def_table);
        }
        current_def_table = program_table;
        n.function.accept(this);
        displayTables();
        return tables;
    }

    /**
     * Visit a definition
     *
     * @param n a definition object (Def)
     * @return the return type of the function definition
     */
    @Override
    public Type visit(Def n) {
        if (n.sig != null) { // sig has no args if n.sig is null
            n.sig.accept(this);
        }
        return (Type) n.function.accept(this);
    }

    /**
     * Visit a function signature
     *
     * @param n a signature (Sig)
     * @return the type of the last identifier in the signature or
     * null if the signature is empty.
     */
    @Override
    public Type visit(Sig n) {
        Type last = null;
        for (SigArg sig_arg : n.sig_args) {
            last = (Type) sig_arg.accept(this);
        }
        return last;
    }

    /**
     * Visit a signature argument.
     * @param n is the SigArg to visit
     * @return the type of the SigArg
     */
    @Override
    public Type visit(SigArg n) {
        current_def_table.put(
            n.identifier,
            new Binding(n.identifier, new TableValue(n.type))
        );
        return n.type;
    }

    /**
     * Visit a user function.
     *
     * @param n a user function
     * @return the return type of the user function
     */
    @Override
    public Type visit(UserFunc n) {
        n.arg_list.accept(this);
        return (Type) n.user_func_name.accept(this);
    }

    /**
     * Visit a branch
     *
     * @param n a branch
     * @return the return type of the branch
     */
    @Override
    public Type visit(Branch n) {
        n.condition.accept(this);
        Type trueType = (Type) n.true_branch.accept(this);
        n.false_branch.accept(this);
        return trueType;
       /*if (!condType.equals(new Type(FlatType.INTEGER))) {
           throw new UnsupportedOperationException(
               n.condition + " of type " + condType +
               ".  Condition functions must return an integer."
           );
       }*/

       /*if (!(trueType.equals(falseType))) {
           throw new UnsupportedOperationException(
               "Ambiguous return types for branch: " +
               "\ntrue branch: " + trueType +
               "\nfalse branch: " + falseType +
               "\ntrue branch: " + n.true_branch +
               "\nfalse branch: " + n.false_branch
           );
       }*/
    }

    /**
     * Visit a lambda in the WOLF language
     *
     * @param n a lambda object (WolfLambda)
     * @return type of lambda
     */
    @Override
    public Type visit(WolfLambda n) {
        String lambda_table_name = n.toString();
        n.setId(lambda_count);

        // Make a symbol table for the lambda
        SymbolTable lambda_table = new SymbolTable(lambda_table_name);

        // Set the parent table of the lambda to be the current table scope
        lambda_table.parent_table = current_def_table;

        // Register lambda in the list to keep track of it
        lambda_table_list.add(lambda_count, lambda_table);
        lambda_count++;

        // Set the current table scope to be the lambda in preparation to
        // examine the lambda function and its parameters
        current_def_table = lambda_table;

        // Parse the lambda for types
        n.sig.accept(this);
        Type type = (Type) n.function.accept(this);

        // Make an identifier for the lambda in the symbol tables
        Identifier lambda_id = new Identifier(
            new TIdentifier(lambda_table_name)
        );

        // Add the lambda to the symbol tables list
        tables.add(lambda_table);
        // Bind the lambda's table information to its identifier
        Binding binding = new Binding(
            lambda_id,
            new TableValue(type, lambda_table)
        );
        // if the lambda has no parent, put it in the global environment table
        // otherwise, put it in its parent's table
        if (lambda_table.parent_table == null) {
            program_table.put(lambda_id, binding);
        } else {
            lambda_table.parent_table.put(lambda_id, binding);
        }
        // Reset the scope now that the lambda has been processed
        current_def_table = lambda_table.parent_table;
        return type;
    }

    /**
     * Visit a fold function
     *
     * @param n a fold function
     * @return the return type of the fold
     */
    @Override
    public Type visit(Fold n) {
        n.fold_symbol.accept(this);
        return (Type) n.fold_body.accept(this);
    }

    /**
     * Visit a fold symbol
     *
     * @param n a fold symbol
     * @return null, never
     */
    @Override
    public Type visit(FoldSymbol n) {
        // do nothing, return null
        return null;
    }

    /**
     * Visit a fold body
     *
     * @param n a fold body
     * @return the return type of the fold
     */
    @Override
    public Type visit(FoldBody n) {
        n.bin_op.accept(this);
        return (Type) n.list_argument.accept(this);
    }

    /**
     * Visit a mapping function in the WOLF language
     *
     * @param n a mapping function object (WolfMap)
     * @return the return type of the map
     */
    @Override
    public Type visit(WolfMap n) {
        n.unary_op.accept(this);
        Type list_type = (Type) n.list_argument.accept(this);
        return new Type(list_type.flat_type, true);
    }

    /**
     * Visit a list of arguments for a list.
     *
     * @param n a list of arguments for a list (ListArgs)
     * @return the type of the list.
     */
    @Override
    public Type visit(ListArgsList n) {
        Type type = null;
        for (Arg arg : n.getArgList()) {
            type = (Type) arg.accept(this);
        }
        return type;
    }

    /**
     * Visit a list of arguments.
     *
     * @param n a list of arguments (Args)
     * @return the type of the last argument in the list, or null if the list is empty.
     */
    @Override
    public Type visit(ArgsList n) {
        Type last_arg_type = null;
        for (Arg arg : n.getArgList()) {
            last_arg_type = (Type) arg.accept(this);
        }
        return last_arg_type;
    }

    /**
     * Visit a native unary operator that can have either list
     * or non-list arguments depending on the operation.
     *
     * @param n a native unary
     * @return the type the native unary should return
     */
    @Override
    public Type visit(NativeUnary n) {
        Type arg_type = (Type) n.arg.accept(this);
        return arg_type;
    }

    /**
     * Visit a native unary operator that has a list argument.
     *
     * @param n a native list unary
     * @return the type the native list unary should return
     */
    @Override
    public Type visit(NativeListUnary n) {
        Type list_type = (Type) n.list_argument.accept(this);
        return list_type;
    }

    /**
     * Visit a native binary operator that can have either list
     * or non-list arguments depending on the operation.
     *
     * @param n a native binary
     * @return the type the native binary should return
     */
    @Override
    public Type visit(NativeBinary n) {
        Type left_type = (Type) n.arg_left.accept(this);
        n.arg_right.accept(this);

        switch (n.binary_op) {
            case PLUS:
            case MINUS:
            case MULT:
            case DIV:
            case MOD:
                return left_type;
            case LT:
            case GT:
            case LTE:
            case GTE:
            case AND:
            case OR:
            case XOR:
            case EQUAL:
            case NOT_EQUAL:
                return new Type(FlatType.INTEGER);
            default:
                System.err.println("Invalid Binary Operator!");
                return null;
        }
    }

    /**
     * Visit a native binary that one non-list and one list argument
     *
     * @param n a native list binary
     * @return the type the native list binary should return
     */
    @Override
    public Type visit(NativeListBinary n) {
        n.arg.accept(this);
        Type list_type = (Type) n.list_argument.accept(this);
        return new Type(list_type.flat_type, true);
    }

    /**
     * Visit an identifier
     *
     * @param n an identifier
     * @return the type of the identifier
     * An identifier can be anything, it depends on the context of the
     * function. Identifiers are only used for user-defined functions and
     * their parameters, which have user-specified types
     */
    @Override
    public Type visit(Identifier n) {
        TableValue tv = null;
        Binding binding = current_def_table.lookup(n);
        if (binding == null) {
            SymbolTable parent = current_def_table.parent_table;
            while (parent != null && tv == null) {
                binding = parent.lookup(n);
                if (binding == null) {
                    parent = parent.parent_table;
                } else {
                    tv = binding.table_value;
                }
            }
        } else {
            tv = binding.table_value;
        }
        if (tv == null) {
            return null;
        }
        return tv.type;

    }

    /**
     * Visit a float literal
     *
     * @param n a float literal
     * @return the FLOAT type
     */
    @Override
    public Type visit(FloatLiteral n) {
        return new Type(FlatType.FLOAT);
    }

    /**
     * Visit an integer literal
     *
     * @param n an integer literal
     * @return the INTEGER type
     */
    @Override
    public Type visit(IntLiteral n) {
        return new Type(FlatType.INTEGER);
    }

    /**
     * Visit a list in the WOLF language
     *
     * @param n a list object (WolfList)
     * @return the X_LIST type where X is either FLOAT, INTEGER, or STRING, or
     * null if the list is empty.
     */
    @Override
    public Type visit(WolfList n) {
        Type list_type = null;
        for (ListElement element : n.list_elements) {
            list_type = (Type) element.accept(this);
        }
        return (list_type == null) ? null : new Type(list_type.flat_type, true);
       /*
       Type type = (Type) this.arg_list.get(0).accept(v);
       return new Type(type.flat_type, true);
       */
    }

    /**
     * Visit a string in the WOLF language
     *
     * @param n a string object (WolfString)
     * @return the STRING type
     */
    @Override
    public Type visit(WolfString n) {
        for (StringMiddle sm : n.string) {
            sm.accept(this);
        }
        return new Type(FlatType.STRING);
    }

    /**
     * Visit a string body
     *
     * @param n a string body
     * @return null, no use in BuildSymbolTable
     */
    @Override
    public Type visit(StringBody n) {
        // do nothing, return null
        return null;
    }

    /**
     * Visit a string escape sequence
     *
     * @param n a string escape sequence
     * @return null, no use in BuildSymbolTable
     */
    @Override
    public Type visit(StringEscapeSeq n) {
        // do nothing, return null
        return null;
    }

  /**
   * Visit a string escape character.
   * @param n the escape character
   * @return null, no use in BuildSymbolTable
   */
    @Override
    public Type visit(EscapeChar n) {
        // do nothing, return null
        return null;
    }

  /**
   * Visit a native binary operator.
   * @param n the native binary operator
   * @return null, no use in BuildSymbolTable
   */
    @Override
    public Type visit(NativeBinOp n) {
        // do nothing, return null
        return null;
    }

    /**
     * Visit a native unary operator.
     * @param n the native unary operator
     * @return null, no use in BuildSymbolTable
     */
    @Override
    public Type visit(NativeUnaryOp n) {
        // do nothing, return null
        return null;
    }
    
    /**
     * @param n is the type to visit
     * @return null because Type has no purpose here.
     */
    @Override
    public Type visit(Type n) {
        return null;
    }
}