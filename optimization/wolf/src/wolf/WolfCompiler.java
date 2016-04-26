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
import wolf.interfaces.UnaryOp;
import wolf.interfaces.Visitor;

/**
 *
 * @author Kevin Dittmar
 * @version Apr 23, 2016
 */
public class WolfCompiler implements Visitor {
    
    private final StringBuilder java_program_builder;
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
            java_program_builder.append(def.accept(this));
        }
        return java_program_builder.toString();
    }
    
    @Override
    public String visit(Def n) {
        StringBuilder sb = new StringBuilder();
        sb.append("public static ")
          .append(n.type.accept(this))
          .append(" ")
          .append(n.def_name.toString())
          .append(n.sig.accept(this))
          .append(n.function.accept(this));
        
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
        
        helper.append("private static ")
              .append(full_lambda_signature)
              .append(lambda_function)
              .append("\n@FunctionalInterface\npublic static interface Lambda_")
              .append(n.getId())
              .append("{").append(full_lambda_signature).append("}");
        
        return "Lambda_" + n.getId() + " " + lambda_name + " = " +
               this.class_name + "::" + lambda_name + ";\n" +
               return_type.accept(this) + " " + lambda_name + "_result = " +
               lambda_name + "." + lambda_name;
    }

    @Override
    public String visit(Fold n) {
        StringBuilder sb = new StringBuilder();
        String[] fold = (String[]) n.fold_body.accept(this);
        sb.append(fold[0]);
        if (n.fold_symbol.equals(FoldSymbol.FOLD_RIGHT)) {
            // Reverse the list
            sb.append("Collections.reverse(list")
              .append(list_counter - 1).append(");\n");
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
        StringBuilder sb = new StringBuilder();
        String temp_name = "temp" + temp_counter++;
        sb.append(arrayListDeclare(
            temp_name, (String) n.getType().accept(this)
        )).append("for (")
          .append(n.list_argument.getType().accept(this))
          .append("arg : ").append(n.list_argument.accept(this))
          .append(") {\n")
          .append(temp_name).append(".add(").append(n.unary_op.accept(this))
          .append("(arg));\n}\n");
        return sb.toString();
    }

    @Override
    public String visit(ListArgsList n) {
        StringBuilder sb = new StringBuilder();
        String list_name = "list" + list_counter++;
        sb.append(arrayListDeclare(
            list_name, (String) n.getArgList().get(0).getType().accept(this)
        ));
        for (Arg arg : n.getArgList()) {
            sb.append(list_name)
              .append(".add(").append(arg.accept(this)).append(");\n");
        }

        return sb.toString();
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
        return "";
    }

    @Override
    public String visit(NativeListUnary n) {
        return "";
    }

    @Override
    public String visit(NativeBinary n) {
        return "";
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
        
        return "";
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
            sb.append("if (").append(n.condition.accept(this)).append(") {\n")
              .append(n.true_branch.accept(this)).append(";\n")
              .append("} else {\n").append(n.false_branch.accept(this))
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
        sb.append(buildListIteratorCode(type, temp_name, list_argument));
        if (bin_op instanceof NativeBinary) {
            NativeBinary bin = (NativeBinary) bin_op;
            sb.append(buildNativeBinaryCode(bin, temp_name));
        } else if (bin_op instanceof WolfLambda) {
            sb.append(buildBinaryLambdaCall(lambda_temp, temp_name));
        }
        return sb.toString();
    }
    
    private String buildBinOpCode(BinOp bin_op) {
        String bin_op_code = (String) bin_op.accept(this);
        return "";
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
          .append("\n};\n").toString();
    }
    
    private String buildBinaryLambdaCall(String lambda_temp, String temp_name) {
        return new StringBuilder().append(temp_name).append(" = ")
          .append(lambda_temp).append(".apply(").append(temp_name)
          .append(", arg);\n}\n").toString();
    }
    
    private String buildListIteratorCode(Type type, String temp_name,
        ListArgument list_argument) {
        return new StringBuilder().append(type.accept(this))
          .append(temp_name).append(";\n")
          .append("for (").append(type.accept(this)).append(" arg : ")
          .append(list_argument.accept(this))
          .append(") {\n").toString();
    }
    
    private String buildNativeBinaryCode(NativeBinary bin, String temp_name) {
        return new StringBuilder().append(temp_name).append(" = ")
              .append(temp_name)
              .append(bin.binary_op.accept(this))
              .append("arg;\n}\n").toString();
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

    @Override
    public Object visit(NativeListBinaryOp n) {
        return n.toString();
    }

    @Override
    public Object visit(NativeListUnaryOp n) {
        return n.toString();
    }
}
