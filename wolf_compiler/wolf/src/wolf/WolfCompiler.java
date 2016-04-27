package wolf;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
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
    private final String IMPORTS = 
        "import java.util.List;\n" +
        "import java.util.ArrayList;\n" +
        "import java.util.Collections;\n" +
        "import java.util.function.BiFunction;\n" +
        "import java.util.function.Function;\n";
    private final String PRINT_METHOD =
      "private static void print(List list) {\n" +
        "StringBuilder sb = new StringBuilder();\n" +
        "sb.append(\"[ \");\n" +
        "list.forEach(element -> sb.append(element).append(\" \"));\n" +
        "sb.append(\"]\");\n" +
        "System.out.println(sb.toString());\n" +
      "}\n";

    private final StringBuilder user_function_builder;
    private final StringBuilder main_vars_builder;
    private StringBuilder scope_side_effects;
    private final StringBuilder post_main_helpers;
    private final SemanticTypeCheck stc;
    private final String class_name;
    private int list_counter = 0;
    private int temp_counter = 0;
    
    public WolfCompiler(SemanticTypeCheck stc, String filename) {
        user_function_builder = new StringBuilder();
        main_vars_builder = new StringBuilder();
        post_main_helpers = new StringBuilder();
        this.stc = stc;
        this.class_name = ((filename.contains("_")) ?
            Arrays.asList(filename.split("_"))
                .stream()
                .map(string -> titlecase(string))
                .reduce("", String::concat) : 
            titlecase(filename)).split("\\.")[0];
    }
    
    private String titlecase(String string) {
        return string.substring(0, 1).toUpperCase() +
               string.substring(1, string.length()).toLowerCase();
    }
    
    public void compile(Program program) {
        Writer writer = null;

        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(class_name + ".java"), "utf-8"));
            writer.write(visit(program));
        } catch (IOException ex) {
            System.err.println("Could not write compiled program");
        } finally {
            try {
                writer.close();
            } catch (Exception ex) {
                System.err.println("Error closing writer");
            }
        }

        Runtime rt = Runtime.getRuntime();
        try {
            Process pr = rt.exec("javac " + class_name + ".java");
        } catch (IOException e) {
            System.err.println("Could not run javac");
        }
    }
    
    @Override
    public String visit(Program n) {
        for (Def def : n.def_list) {
            scope_side_effects = new StringBuilder();
            user_function_builder.append(def.accept(this));
        }
        String main = buildMain(n.function);
        return IMPORTS + 
               "public class " + class_name + " {\n" +
               user_function_builder.toString() +
               main + "}";
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
        sb.append(scope_side_effects.toString())
          .append("return ")
          .append(function_body)
          .append(";\n}\n");
        
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
        String type = (String) n.type.accept(this);
        if (n.type.is_list) {
            type = "List<" + type + ">";
        }
        return type + " " +
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
        return func_name + arg_list;
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
              .append(" {\nreturn ")
              .append(lambda_function)
              .append(";\n}\n")
              .append("\n@FunctionalInterface\npublic static interface Lambda_")
              .append(n.getId())
              .append("{\n").append(full_lambda_signature).append(";\n}");
        
        post_main_helpers.append(helper).append("\n");
        String func_name = lambda_name + "_func";
        scope_side_effects.append("Lambda_").append(n.getId()).append(" ").append(lambda_name)
            .append(" = ").append(this.class_name).append("::").append(lambda_name).append(";\n")
            .append(return_type.accept(this)).append(" ").append(func_name)
            .append(" = ").append(lambda_name).append(".").append(lambda_name).append(";\n")
            .toString();
        return func_name;
    }

    @Override
    public String visit(Fold n) {
        String[] fold = (String[]) n.fold_body.accept(this);
        int saved_list_counter = list_counter;
        scope_side_effects.append(fold[0]);
        if (n.fold_symbol.equals(FoldSymbol.FOLD_RIGHT)) {
            // Reverse the list to emulate foldr
            // The list we want to reverse is the next one.
            scope_side_effects.append("Collections.reverse(list")
              .append(saved_list_counter + 1).append(");\n");
        }
        scope_side_effects.append(fold[1]);
        return "";
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
        scope_side_effects.append(arrayListDeclare(
            temp_name, (String) n.getType().accept(this)
        ));
        String unary_op_string = (String) n.unary_op.accept(this);
        String list_argument_string = (String) n.list_argument.accept(this);
        scope_side_effects.append("for (")
          .append(n.list_argument.getType().accept(this))
          .append(" arg : ").append(list_argument_string)
          .append(") {\n")
          .append(temp_name).append(".add(").append(unary_op_string)
          .append("(arg));\n}\n");
        return temp_name;
    }

    @Override
    public String visit(ListArgsList n) {
        String list_name = "list" + list_counter++;
        main_vars_builder.append(arrayListDeclare(
            list_name, (String) n.getArgList().get(0).getType().accept(this)
        ));
        for (Arg arg : n.getArgList()) {
            String arg_string = (String) arg.accept(this);
            main_vars_builder.append(list_name)
              .append(".add(").append(arg_string).append(");\n");
        }

        return list_name;
    }

    @Override
    public String visit(ArgsList n) {
        StringBuilder sb = new StringBuilder();
        List<String> arg_list_strings = new ArrayList<>();
        sb.append("(");
        for (Arg arg : n.getArgList()) {
            String arg_string = (String) arg.accept(this);
            arg_list_strings.add(arg_string);
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
        return buildNativeListUnaryOperation(
            n,
            (String) n.list_argument.accept(this)
        );
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
        return buildNativeListBinaryOperation(
            n,
            (String) n.arg.accept(this),
            (String) n.list_argument.accept(this)
        );
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
        main_vars_builder.append(arrayListDeclare(
            list_name, (String) n.getType().accept(this)
        ));
        for (ListElement le : n.list_elements) {
            String le_string = (String) le.accept(this);
            main_vars_builder.append(list_name)
              .append(".add(").append(le_string).append(");\n");
        }
        return list_name;
    }

    @Override
    public String visit(WolfString n) {
        return "\"" + n.toString() + "\"";
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
              .append(")\n? ").append(n.true_branch.accept(this))
              .append("\n: ").append(n.false_branch.accept(this))
              .toString();
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
        String function_string = (String) lambda.function.accept(this);
        return sb.append("BiFunction<")
          .append(lambda.getType().accept(this)).append(",")
          .append(lambda.sig.sig_args.get(0).type.accept(this)).append(",")
          .append(lambda.sig.sig_args.get(1).type.accept(this)).append("> ")
          .append(lambda_temp).append(" = (")
          .append(lambda.sig.sig_args.get(0).identifier.accept(this))
          .append(", ")
          .append(lambda.sig.sig_args.get(1).identifier.accept(this))
          .append(") -> {\nreturn ").append(function_string)
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
        String list_argument_string = (String) list_argument.accept(this);
        return new StringBuilder().append(type.accept(this))
          .append(temp_name).append(";\n")
          .append("for (").append(type.accept(this)).append(" arg : ")
          .append(list_argument_string)
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
                return buildArithmeticOrRelationalBinaryCode(
                    (String) bin.binary_op.accept(this), left, right
                );
            case EQUAL:
                return buildArithmeticOrRelationalBinaryCode(
                    "==" , left, right
                );
            case NOT_EQUAL:
                return buildArithmeticOrRelationalBinaryCode(
                    "!=", left, right
                );
            case AND:
                return buildLogicalBinaryCode(
                    "&&", left, right
                );
            case OR:
                return buildLogicalBinaryCode(
                    "||", left, right
                );
            case XOR:
                return buildLogicalBinaryCode(
                    "^", left, right
                );
            default:
                throw new UnsupportedOperationException(
                    "Unknown binary operator " + bin.binary_op.toString()
                );
        }
    }
    
    private String buildNativeListBinaryOperation(
        NativeListBinary bin, String arg, String list) {
        switch (bin.binary_op) {
            case APPEND:
                scope_side_effects.append(list)
                    .append(".add(").append(arg).append(")");
                return list;
            case PREPEND:
                scope_side_effects.append(list)
                    .append(".add(0, ").append(arg).append(")");
                return list;
            default:
                throw new UnsupportedOperationException(
                    "Unknown list binary operator " + bin.binary_op.toString()
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
                String print_method = "System.out.println";
                if (unary.arg.getType().is_list) {
                    print_method = "print";
                }
                scope_side_effects.append(print_method)
                    .append("(")
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
    
    private String buildNativeListUnaryOperation(
        NativeListUnary unary, String list) {
        String temp_list = "";
        switch (unary.list_unary_op) {
            case HEAD:
                return list + ".get(0)";
            case TAIL:
                temp_list = "temp" + temp_counter++;
                scope_side_effects.append(
                    makeListTemp(
                        temp_list,
                        (String) unary.list_argument.getType().accept(this),
                        list)
                );
                return temp_list + ".subList(1, " + temp_list + ".size())";
            case LAST:
                return list + ".get(" + list + ".size() - 1)";
            case REVERSE:
                temp_list = "temp" + temp_counter++;
                scope_side_effects.append(
                    makeListTemp(
                        temp_list,
                        (String) unary.list_argument.getType().accept(this),
                        list))
                .append("Collections.reverse(").append(temp_list).append(");");
                return temp_list;
            case LENGTH:
                return list + ".size()";
            default:
                throw new UnsupportedOperationException(
                    "Unknown list unary operator " +
                    unary.list_unary_op.toString()
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
        return flat_type;
    }
    
    private String makeListTemp(String temp, String type, String existing) {
        return "ArrayList<" + type + ">" + temp +
               " = new ArrayList<>(" + existing + ");\n";
    }
    
    private String buildMain(WolfFunction function) {
        StringBuilder sb = new StringBuilder();
        String function_body = (String) function.accept(this);
        String print_method = "System.out.println";
        if (function.getType().is_list) {
            print_method = "print";
        }
        return sb.append("public static void main(String[] args) {\n")
          .append(main_vars_builder.toString())
          .append(print_method)
          .append("(")
          .append(function_body)
          .append(");")
          .append("\n}\n")
          .append(post_main_helpers.toString())
          .append(PRINT_METHOD).toString();
    }
}
