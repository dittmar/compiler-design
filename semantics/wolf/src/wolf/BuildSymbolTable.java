package wolf;

import wolf.interfaces.*;
import wolf.enums.*;

/**
 * A visitor responsible for building symbol tables for the
 * parser.
 * @author William Ezekiel
 * @author Kevin Dittmar
 * @author Joseph Alacqua
 * @version Apr 3, 2016
 */
public class BuildSymbolTable implements Visitor {

    SymbolTable program_table;

    /**
     * Visit a program
     * @param n a program
     */
    @Override
    public void visit(Program n) {
        program_table = new SymbolTable();
        for (Def def : n.def_list) {
            def.accept(this);
        }
        n.function.accept(this);
        System.out.println("A Winner is You!");
    }
    
    /**
     * Visit a definition
     * @param n a definition object (Def)
     */
    @Override
    public void visit(Def n) {
        n.def_name.accept(this);
        n.sig.accept(this);
        n.function.accept(this);
    }
    
    /**
     * Visit a function signature
     * @param n a signature (Sig)
     */
    @Override
    public void visit(Sig n) {
        for(Identifier id: n.sig_args) {
            id.accept(this);
        }
    }
    
    /**
     * Visit a function in the WOLF language.
     * @param n a function
     */
    @Override
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
    
    /**
     * Visit a user function.
     * @param n a user function
     */
    @Override
    public void visit(UserFunc n) {
        n.user_func_name.accept(this);
        n.arg_list.accept(this);
    }
    
    /**
     * Visit a native unary
     * @param n a native unary
     */
    @Override
    public void visit(NativeUnary n) {
        n.unary_op.accept(this);
        n.arg.accept(this);
    }
    
    /**
     * Visit a branch
     * @param n a branch
     */
    @Override
    public void visit(Branch n) {
        n.condition.accept(this);
        n.true_branch.accept(this);
        n.false_branch.accept(this);
    }
    
    /**
     * Visit a mapping function in the WOLF language
     * @param n a mapping function object (WolfMap)
     */
    @Override
    public void visit(WolfMap n) {
        n.unary_op.accept(this);
        n.list_argument.accept(this);
    }
    
    /**
     * Visit a fold function
     * @param n a fold function
     */
    @Override
    public void visit(Fold n) {
        n.fold_symbol.accept(this);
        n.fold_body.accept(this);
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
    
    /**
     * Visit a list argument
     * @param n a list argument
     */
    @Override
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
    
    /**
     * Visit a lambda in the WOLF language
     * @param n a lambda object (WolfLambda)
     */
    @Override
    public void visit(WolfLambda n) {
        n.sig.accept(this);
        n.function.accept(this);
    }
    
    /**
     * Visit a fold body
     * @param n a fold body
     */
    @Override
    public void visit(FoldBody n) {
        n.bin_op.accept(this);
        n.list_argument.accept(this);
    }
    
    /**
     * Visit a binary operation
     * @param n a binary operation
     */
    @Override
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
    
    /**
     * Visit a list in the WOLF language
     * @param n a list object (WolfList)
     */
    @Override
    public void visit(WolfList n) {
         n.arg_list.accept(this);
    }
    
    /**
     * Visit an identifier
     * @param n an identifier
     */
    @Override
    public void visit(Identifier n) {
        n.accept(this);
    }
    
    /**
     * Visit an argument
     * @param n an argument
     */
    @Override
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
    
    /**
     * Visit a user function name
     * @param n a user functio name
     */
    @Override
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
    
    /**
     * Visit a list of arguments
     * @param n a list of arguments object (ArgList)
     */
    @Override
    public void visit(ArgList n) {
        for(Arg arg:n.arg_list) {
            arg.accept(this);
        }
    }
    
    /**
     * Visit an integer literal
     * @param n an integer literal
     */
    @Override
    public void visit(IntLiteral n) {
        n.accept(this);
    }
    
    /**
     * Visit a float literal
     * @param n a float literal
     */
    @Override
    public void visit(FloatLiteral n) {
        n.accept(this);
    }
    /**
     * Visit a string in the WOLF language
     * @param n a string object (WolfString)
     */
    @Override
    public void visit(WolfString n) {
        for(StringMiddle sm:n.string_middle) {
            sm.accept(this);
        }
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
        n.escape_char.accept(this);
    }
    
    /**
     * Visit an escape character
     * @param n an escape character
     */
    public void visit(EscapeChar n) {
        n.accept(this);
    } 
}