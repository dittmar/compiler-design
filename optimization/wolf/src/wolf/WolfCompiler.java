package wolf;

import java.util.ArrayList;
import java.util.List;
import wolf.enums.FoldSymbol;
import wolf.enums.NativeBinOp;
import wolf.enums.NativeListBinaryOp;
import wolf.enums.NativeListUnaryOp;
import wolf.enums.NativeUnaryOp;
import wolf.interfaces.Arg;
import wolf.interfaces.BinOp;
import wolf.interfaces.ListArgument;
import wolf.interfaces.ListElement;
import wolf.interfaces.UnaryOp;
import wolf.interfaces.Visitor;
import wolf.interfaces.WolfFunction;

/**
 *
 * @author Kevin Dittmar
 * @version Apr 23, 2016
 */
public class WolfCompiler implements Visitor {
    
    private final StringBuilder java_program_builder;
    private StringBuilder current_function_initializations;
    private final StringBuilder post_main_helpers;
    private final SemanticTypeCheck stc;
    private final String class_name;
    private int list_counter = 0;
    private int temp_counter = 0;
    
    public WolfCompiler(SemanticTypeCheck stc, String filename) {
        java_program_builder = new StringBuilder();
        post_main_helpers = new StringBuilder();
        this.stc = stc;
        this.class_name = filename;
    }
    
    public void compile(Program program) {
        System.out.println(visit(program));
    }
    
    @Override
    public String visit(Program n) {
        for (Def def : n.def_list) {
            current_function_initializations = new StringBuilder();
            java_program_builder.append(def.accept(this));
        }
        return java_program_builder.append(buildMain(n.function)).toString();
    }
    
    @Override
    public String visit(Def n) {
        StringBuilder sb = new StringBuilder();
        sb.append("public static ")
          .append(n.type.accept(this))
          .append(" ")
          .append(n.def_name.toString())
          .append(n.sig.accept(this))
          .append("{\n");
        String function_body = (String) n.function.accept(this);
        sb.append(current_function_initializations.toString())
          .append(function_body)
          .append("\n}\n");
        
        return sb.toString();
    }
    
    @Override
    public String visit(Sig n) {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        List<String> sig_args_string = new ArrayList<>();
        for (SigArg arg : n.sig_args) {
            sig_args_string.add((String) arg.accept(this));
        }
        sb.append(String.join(", ", sig_args_string)).append(")");
        return sb.toString();
    }
    
    @Override
    public String visit(SigArg n) {
        return (String)n.type.accept(this) + " " +
               (String)n.identifier.accept(this);
    }
    
    @Override
    public String visit(Type n) {
        return stringifyType(n);
    }
    @Override
    public String visit(Identifier n) {
        return n.toString();
    }

    @Override
    public String visit(UserFunc n) {
        String func_name = (String) n.user_func_name.accept(this);
        String arg_list = (String) n.arg_list.accept(this);
        return func_name + arg_list + ";";
    }

    @Override
    public String visit(WolfLambda n) {
        StringBuilder helper = new StringBuilder();

        String lambda_function = (String) n.function.accept(this);
        String lambda_sig = (String) n.sig.accept(this);
        SymbolTable lambda_table = stc.lambda_table_list.get(n.getId());
        SymbolTable parent_table = lambda_table.parent_table;
        Type return_type = parent_table.symbol_table.get(
            lambda_table.table_name
        ).table_value.type;
        
        String lambda_name = "lambda" + n.getId();
        String full_lambda_signature = return_type.accept(this) + " " +
            lambda_name + lambda_sig;
        
        // Add the lambda helper to the list.
        helper.append("private static ")
              .append(full_lambda_signature)
              .append(lambda_function)
              .append("\n@FunctionalInterface\npublic static interface Lambda_")
              .append(n.getId())
              .append("{").append(full_lambda_signature).append("}");
        
        post_main_helpers.append(helper).append("\n");
        
        return "Lambda_" + n.getId() + " " + lambda_name + " = " +
               this.class_name + "::" + lambda_name + ";\n" +
               return_type.accept(this) + " " + lambda_name + "_result = " +
               lambda_name + "." + lambda_name;
    }

    @Override
    public String visit(Fold n) {
        StringBuilder sb = new StringBuilder();
        String[] fold = (String[]) n.fold_body.accept(this);
        int saved_list_counter = list_counter;
        sb.append(fold[0]);
        if (n.fold_symbol.equals(FoldSymbol.FOLD_RIGHT)) {
            // Reverse the list to emulate foldr
            // The list we want to reverse is the next one.
            current_function_initializations.append("Collections.reverse(list")
              .append(saved_list_counter + 1).append(");\n");
        }
        
        return sb.toString();
    }

    @Override
    public String visit(FoldSymbol n) {
        return null;
    }

    @Override
    public String[] visit(FoldBody n) {
        String[] fold_body = new String[2];
        fold_body[0] = (String) n.list_argument.accept(this);
        fold_body[1] = buildFoldCode(n.bin_op, n.list_argument);
        return fold_body;
    }

    @Override
    public String visit(WolfMap n) {
        String temp_name = "temp" + temp_counter++;
        current_function_initializations.append(arrayListDeclare(
            temp_name, (String) n.getType().accept(this)
        ));
        current_function_initializations.append("for (")
          .append(n.list_argument.getType().accept(this))
          .append("arg : ").append(n.list_argument.accept(this))
          .append(") {\n")
          .append(temp_name).append(".add(").append(n.unary_op.accept(this))
          .append("(arg));\n}\n");
        return temp_name;
    }

    @Override
    public String visit(ListArgsList n) {
        String list_name = "list" + list_counter++;
        current_function_initializations.append(arrayListDeclare(
            list_name, (String) n.getArgList().get(0).getType().accept(this)
        ));
        for (Arg arg : n.getArgList()) {
            current_function_initializations.append(list_name)
              .append(".add(").append(arg.accept(this)).append(");\n");
        }

        return list_name;
    }

    @Override
    public String visit(ArgsList n) {
        StringBuilder sb = new StringBuilder();
        List<String> arg_list_strings = new ArrayList<>();
        sb.append("(");
        for (Arg arg : n.getArgList()) {
            arg_list_strings.add((String) arg.accept(this));
        }
        return sb.append(String.join(", ", arg_list_strings))
                 .append(")")
                 .toString();
    }

    @Override
    public String visit(NativeUnary n) {
        return buildNativeUnaryOperation(
            n,
            (String) n.arg.accept(this)
        );
    }

    @Override
    public String visit(NativeListUnary n) {
        return "";
    }

    @Override
    public String visit(NativeBinary n) {
        return buildNativeBinaryOperation(
            n,
            (String) n.arg_left.accept(this),
            (String) n.arg_right.accept(this)
        );
    }

    @Override
    public String visit(NativeListBinary n) {
        return "";
    }

    @Override
    public String visit(FloatLiteral n) {
        return n.toString();
    }

    @Override
    public String visit(IntLiteral n) {
        return n.toString();
    }

    @Override
    public String visit(WolfList n) {
        StringBuilder sb = new StringBuilder();
        String list_name = "list" + list_counter++;
        current_function_initializations.append(arrayListDeclare(
            list_name, (String) n.getType().accept(this)
        ));
        for (ListElement le : n.list_elements) {
            current_function_initializations.append(list_name)
              .append(".add(").append(le.accept(this)).append(");\n");
        }
        return list_name;
    }

    @Override
    public String visit(WolfString n) {
        return n.toString();
    }

    @Override
    public String visit(StringBody n) {
        return n.toString();
    }

    @Override
    public String visit(StringEscapeSeq n) {
        return n.toString();
    }

    @Override
    public String visit(NativeBinOp n) {
        return n.toString();
    }

    @Override
    public String visit(NativeUnaryOp n) {
        return n.toString();
    }

    @Override
    public String visit(Branch n) {
        StringBuilder sb = new StringBuilder();
        return 
            sb.append("(").append(n.condition.accept(this))
              .append(") ? return\n")
              .append(n.true_branch.accept(this)).append(";\n")
              .append(": return\n").append(n.false_branch.accept(this))
              .append(";\n}\n").toString();
    }

    @Override
    public String visit(InputArg n) {
        if (n.type.flat_type.equals(FlatType.INTEGER)) {
            return "Integer.parseInt(argv[" + n.arg_number + "])";
        } else if (n.type.flat_type.equals(FlatType.FLOAT)) {
            return "Float.parseFloat(argv[" + n.arg_number + "])";
        } else {
            return "argv[" + n.arg_number + "]";
        }
    }
    
        @Override
    public Object visit(NativeListBinaryOp n) {
        return n.toString();
    }

    @Override
    public Object visit(NativeListUnaryOp n) {
        return n.toString();
    }
    
    private String buildFoldCode(BinOp bin_op, ListArgument list_argument) {
        if (!(bin_op instanceof WolfLambda || 
                bin_op instanceof NativeBinary)) {
            throw new UnsupportedOperationException(
                "Tried to do NativeListBinary in Fold");
        }
        Type type = list_argument.getType();
        StringBuilder sb = new StringBuilder();
        String temp_name = "temp" + temp_counter++;
        String lambda_temp = "l_temp" + temp_counter++;
        if (bin_op instanceof WolfLambda) {
            WolfLambda lambda = (WolfLambda) bin_op;
            sb.append(buildBinaryLambdaCode(lambda, lambda_temp));
        }
        sb.append(buildListIteratorCode(type, temp_name, list_argument))
          .append(" {\n");
        if (bin_op instanceof NativeBinary) {
            NativeBinary bin = (NativeBinary) bin_op;
            sb.append(buildNativeAccumulator(bin, temp_name))
              .append("");
        } else if (bin_op instanceof WolfLambda) {
            sb.append(buildBinaryLambdaAccumulator(lambda_temp, temp_name));
        }
        return sb.append(";\n}\n").toString();
    }
    
    private String buildBinOpCode(BinOp bin_op) {
        if (bin_op instanceof NativeBinary) {
            NativeBinary nat_bin = (NativeBinary) bin_op;
            return (String) nat_bin.accept(this);
        } else if (bin_op instanceof UserFunc) {
            UserFunc user_func = (UserFunc) bin_op;
            return (String) user_func.accept(this);
        }
        throw new UnsupportedOperationException(
            "Unknown binary operation " + bin_op.toString()
        );
    }
    
    private String buildUnaryOpCode(UnaryOp unary_op) {
        return "";
    }
    
    private String buildBinaryLambdaCode(WolfLambda lambda, String lambda_temp) {
        StringBuilder sb = new StringBuilder();
        return sb.append("BiFunction<")
          .append(lambda.getType().accept(this)).append(",")
          .append(lambda.sig.sig_args.get(0).type.accept(this)).append(",")
          .append(lambda.sig.sig_args.get(1).type.accept(this)).append("> ")
          .append(lambda_temp).append(" = (")
          .append(lambda.sig.sig_args.get(0).identifier.accept(this))
          .append(", ")
          .append(lambda.sig.sig_args.get(1).identifier.accept(this))
          .append(") -> {\nreturn ").append(lambda.function.accept(this))
          .append(";\n};\n").toString();
    }
    
    private String buildBinaryLambdaAccumulator(String lambda_temp, 
        String temp_name) {
        return new StringBuilder().append(temp_name).append(" = ")
          .append(lambda_temp).append(".apply(").append(temp_name)
          .append(", arg);\n").toString();
    }
    
    private String buildListIteratorCode(Type type, String temp_name,
        ListArgument list_argument) {
        return new StringBuilder().append(type.accept(this))
          .append(temp_name).append(";\n")
          .append("for (").append(type.accept(this)).append(" arg : ")
          .append(list_argument.accept(this))
          .append(")").toString();
    }
    
    private String buildNativeAccumulator(NativeBinary bin, String temp_name) {
        return new StringBuilder().append(temp_name).append(" = ")
              .append(buildNativeBinaryOperation(bin, temp_name, "arg"))
              .append(";\n").toString();
    }
    
    private String buildNativeBinaryOperation(NativeBinary bin, String left,
        String right) {
        switch (bin.binary_op) {
            case PLUS:
            case MINUS:
            case MULT:
            case DIV:
            case MOD:
            case LT:
            case GT:
            case GTE:
            case LTE:
            case EQUAL:
            case NOT_EQUAL:
                return buildArithmeticOrRelationalBinaryCode(
                    (String) bin.binary_op.accept(this), left, right
                );
            case AND:
            case OR:
            case XOR:
                return buildLogicalBinaryCode(
                    (String) bin.binary_op.accept(this), left, right
                );
            default:
                throw new UnsupportedOperationException(
                    "Unknown binary operator " + bin.binary_op.toString()
                );
        }
    }
    
    private String buildNativeUnaryOperation(NativeUnary unary, String arg) {
        switch (unary.unary_op) {
            case NEG:
                return "-1 * (" + arg + ")";
            case IDENTITY:
                return arg;
            case PRINT:
                current_function_initializations.append("System.out.println(")
                    .append(arg)
                    .append(");\n");
                return arg;
            case LOGICAL_NOT:
                return "!(" + arg + ")";
            default:
                throw new UnsupportedOperationException(
                    "Unknown unary operator " + unary.unary_op.toString()
                );
        }
    }
    
    private String buildLogicalBinaryCode(String op,
        String left, String right) {
        return "((" + left + " != 0) " + 
                op + " " +
                "(" + right + " != 0)";
    }
    
    private String buildArithmeticOrRelationalBinaryCode(String op,
        String left, String right) {
        return left + " " + op + " " + right;
    }
    
    private String arrayListDeclare(String name, String type) {
        return new StringBuilder().append("ArrayList<")
          .append(type)
          .append("> ").append(name)
          .append(" = new ArrayList<>();\n").toString();
    }
    
    private String stringifyType(Type type) {
        String flat_type = "";
        if (type.flat_type.equals(FlatType.INTEGER)) {
            flat_type = "Integer";
        } else if (type.flat_type.equals(FlatType.FLOAT)) {
            flat_type = "Float";
        } else if (type.flat_type.equals(FlatType.STRING)) {
            flat_type = "String";
        }
        if (type.is_list) {
            return "ArrayList<" + flat_type + ">";
        } else {
            return flat_type;
        }
    }
    
    private String buildMain(WolfFunction function) {
        StringBuilder sb = new StringBuilder();
        return sb.append("public static void main(String[] args) {\n")
          .append(function.accept(this))
          .append("\n}\n").toString();
    }
}
