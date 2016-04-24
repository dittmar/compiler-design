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
    
    public WolfCompiler() {
        java_program_builder = new StringBuilder();
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
          .append(n.sig.accept(this));
          //.append(n.function.accept(this));
        
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
    
    /**
     *
     * @param n
     * @return
     */
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
    public Object visit(UserFunc n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object visit(WolfLambda n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object visit(Fold n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object visit(FoldSymbol n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object visit(FoldBody n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object visit(WolfMap n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object visit(ListArgsList n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object visit(ArgsList n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object visit(NativeUnary n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object visit(NativeListUnary n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object visit(NativeBinary n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object visit(NativeListBinary n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object visit(FloatLiteral n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object visit(IntLiteral n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object visit(WolfList n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object visit(WolfString n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object visit(StringBody n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object visit(StringEscapeSeq n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object visit(EscapeChar n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object visit(NativeBinOp n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object visit(NativeUnaryOp n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object visit(Branch n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
