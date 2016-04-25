package wolf;

import java.util.ArrayList;
import java.util.List;
import wolf.interfaces.EscapeChar;
import wolf.enums.FoldSymbol;
import wolf.enums.NativeBinOp;
import wolf.enums.NativeUnaryOp;
import wolf.interfaces.Arg;
import wolf.interfaces.BinOp;
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
          .append(stringifyType(n.type))
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
        String full_lambda_signature = stringifyType(return_type) + " " +
            lambda_name + lambda_sig;
        
        helper.append("private static ")
              .append(full_lambda_signature)
              .append(lambda_function)
              .append("\n@FunctionalInterface\npublic static interface Lambda_")
              .append(n.getId())
              .append("{").append(full_lambda_signature).append("}");
        
        return "Lambda_" + n.getId() + " " + lambda_name + " = " +
               this.class_name + "::" + lambda_name + ";\n" +
               stringifyType(return_type) + " " + lambda_name + "_result = " +
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
        fold_body[1] = buildFoldCode(n.bin_op);
        return fold_body;
    }

    @Override
    public String visit(WolfMap n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String visit(ListArgsList n) {
        StringBuilder sb = new StringBuilder();
        String list_name = "list" + list_counter++;
        sb.append("ArrayList<")
          .append(stringifyType(n.getArgList().get(0).getType()))
          .append("> ").append(list_name)
          .append(" = new ArrayList<>();\n");
        for (Arg arg : n.getArgList()) {
            sb.append(list_name)
              .append(".add(").append(arg.accept(this)).append(");\n");
        }

        return sb.toString();
    }

    @Override
    public String visit(ArgsList n) {
        return "";
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
        return "";
    }

    @Override
    public String visit(EscapeChar n) {
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
        return "";
    }

    @Override
    public String visit(InputArg n) {
        return "";
    }
    
    private String buildFoldCode(BinOp bin_op) {
        return "";
    }
    
    private String buildBinOpCode(BinOp bin_op) {
        String bin_op_code = (String) bin_op.accept(this);
        return "";
    }
    
    private String buildMapCode(UnaryOp unary_op) {
        return "";
    }
    
    private String buildUnaryOpCode(UnaryOp unary_op) {
        return "";
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
}
