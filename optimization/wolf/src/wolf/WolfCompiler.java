package wolf;

import java.util.ArrayList;
import java.util.List;
import wolf.enums.EscapeChar;
import wolf.enums.FoldSymbol;
import wolf.enums.NativeBinOp;
import wolf.enums.NativeUnaryOp;
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
            return "ArrayDeque<" + flat_type + ">";
        } else {
            return flat_type;
        }
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
        
    }

    @Override
    public String visit(FoldSymbol n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String visit(FoldBody n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String visit(WolfMap n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String visit(ListArgsList n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String visit(ArgsList n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String visit(NativeUnary n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String visit(NativeListUnary n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String visit(NativeBinary n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String visit(NativeListBinary n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String visit(FloatLiteral n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String visit(IntLiteral n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String visit(WolfList n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String visit(WolfString n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String visit(StringBody n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String visit(StringEscapeSeq n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String visit(EscapeChar n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String visit(NativeBinOp n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String visit(NativeUnaryOp n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String visit(Branch n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String visit(InputArg n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
