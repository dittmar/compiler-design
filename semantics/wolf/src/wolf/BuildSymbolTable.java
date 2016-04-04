package wolf;

import wolf.interfaces.*;
import wolf.enums.*;

/**
 *
 * @author Kevin Dittmar
 * @version Apr 3, 2016
 */
public class BuildSymbolTable implements Visitor {

    SymbolTable program_table;

    public BuildSymbolTable() {

    }

    public void visit(Program n) {
        program_table = new SymbolTable();
        for (Def def : n.def_list) {
            def.accept(this);
        }
        n.function.accept(this);
        System.out.println("A Winner is You!");
    }
    
    public void visit(Def n) {
        n.def_name.accept(this);
        n.sig.accept(this);
        n.function.accept(this);
    }
    
    public void visit(Sig n) {
        for(Identifier id: n.sig_args) {
            id.accept(this);
        }
    }
    
    public void visit(WolfFunction n) {
        if(n instanceof UserFunc) {
            visit((UserFunc) n);
        } else if(n instanceof NativeUnary) {
            visit((NativeUnary) n);
        } else if(n instanceof NativeBinary) {
            visit((NativeBinary) n);
        } else if (n instanceof Branch) {
            visit((Branch) n);
        } else if(n instanceof Fold) {
            visit((Fold) n);
        } else if(n instanceof WolfMap) {
            visit((WolfMap) n);
        } else {
            System.err.println("Invalid Function");
            System.exit(1);
        }
    }
    
    public void visit(UserFunc n) {
        n.user_func_name.accept(this);
        n.arg_list.accept(this);
    }
    
    public void visit(NativeUnary n) {
        n.unary_op.accept(this);
        n.arg.accept(this);
    }
    
    public void visit(Branch n) {
        n.condition.accept(this);
        n.true_branch.accept(this);
        n.false_branch.accept(this);
    }
    
    public void visit(WolfMap n) {
        n.unary_op.accept(this);
        n.list_argument.accept(this);
    }
    
    public void visit(Fold n) {
        n.fold_symbol.accept(this);
        n.fold_body.accept(this);
    }
    
    public void visit(FoldSymbol n) {
        n.accept(this);
    }
    
    public void visit(UnaryOp n) {
        if(n instanceof NativeUnary) {
            visit((NativeUnary) n);
        } else if(n instanceof Identifier) {
            visit((Identifier) n);
        } else if(n instanceof WolfLambda) {
            visit((WolfLambda) n);
        } else {
            System.err.println("Invalid UnaryOp");
            System.exit(1);
        }
    }
    
    public void visit(ListArgument n) {
        if(n instanceof WolfList) {
            visit((WolfList) n);
        } else if(n instanceof Identifier) {
            visit((Identifier) n);
        } else if(n instanceof WolfFunction) {
            visit((WolfFunction) n);
        }
        else {
            System.err.println("Invalid List Argument");
            System.exit(1);
        }
    }
    
    public void visit(WolfLambda n) {
        n.sig.accept(this);
        n.function.accept(this);
    }
    
    public void visit(FoldBody n) {
        n.bin_op.accept(this);
        n.list_argument.accept(this);
    }
    
    public void visit(BinOp n) {
        if(n instanceof NativeBinary) {
            visit((NativeBinary) n);
        } else if(n instanceof Identifier) {
            visit((Identifier) n);
        } else if(n instanceof WolfLambda) {
            visit((WolfLambda) n);
        }
        else {
            System.err.println("Invalid BinOp");
            System.exit(1);
        }
    }
    
    public void visit(WolfList n) {
         n.arg_list.accept(this);
    }
    
    public void visit(Identifier n) {
        n.accept(this);
    }
    
    public void visit(Arg n) {
        if(n instanceof WolfFunction) {
            visit((WolfFunction) n);
        } else if(n instanceof WolfList) {
            visit((WolfList) n);
        } else if(n instanceof IntLiteral) {
            visit((IntLiteral) n);
        } else if(n instanceof FloatLiteral) {
            visit((FloatLiteral) n);
        } else if(n instanceof Identifier) {
            visit((Identifier) n);
        } else if(n instanceof WolfString) {
            visit((WolfString) n);
        } else {
            System.err.println("Invalid Arg");
            System.exit(1);
        }
    }
    
    public void visit(UserFuncName n) {
        if(n instanceof Identifier) {
            visit((Identifier) n);
        } else if(n instanceof WolfLambda) {
            visit((WolfLambda) n);
        } else {
            System.err.println("Invalid UserFuncName");
            System.exit(1);
        }
    }
    
    public void visit(ArgList n) {
        for(Arg arg:n.arg_list) {
            arg.accept(this);
        }
    }
    
    public void visit(IntLiteral n) {
        n.accept(this);
    }
    
    public void visit(FloatLiteral n) {
        n.accept(this);
    }
    
    public void visit(WolfString n) {
        for(StringMiddle sm:n.string_middle) {
            sm.accept(this);
        }
    }
    
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
    
    public void visit(StringBody n) {
        n.accept(this);
    }
    
    public void visit(StringEscapeSeq n) {
        n.escape_char.accept(this);
    }
    
    public void visit(EscapeChar n) {
        n.accept(this);
    }
    
    
}