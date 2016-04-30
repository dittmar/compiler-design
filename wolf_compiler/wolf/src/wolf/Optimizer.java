package wolf;

import wolf.interfaces.*;
import wolf.enums.*;
import wolf.node.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Optimize a WOLF program.
 */
public class Optimizer implements Visitor {

    // 0
    IntLiteral zero;

    // 1
    IntLiteral one;

    // @(1)
    NativeUnary identity_one;

    // @(0)
    NativeUnary identity_zero;

    //Empty String
    WolfString empty_string;

    // Integer type
    Type int_type;

    // String type
    Type string_type;

    // Float type
    Type float_type;

    private Equal equal;

    private IdentifierUsage id_use;

    private boolean started_optimized;

    public Optimizer() {
        equal = new Equal();

        one = new IntLiteral(
                new TIntNumber("1")
        );
        one.setType(new Type(FlatType.INTEGER));
        zero = new IntLiteral(
                new TIntNumber("0")
        );
        zero.setType(new Type(FlatType.INTEGER));
        identity_one = new NativeUnary(NativeUnaryOp.IDENTITY, one);
        identity_one.setType(new Type(FlatType.INTEGER));
        identity_zero = new NativeUnary(NativeUnaryOp.IDENTITY, zero);
        identity_zero.setType(new Type(FlatType.INTEGER));
        ArrayList<StringMiddle> middle = new ArrayList<>();
        middle.add(new StringBody(new TStringBody("")));
        empty_string = new WolfString(middle);
        empty_string.setType(new Type(FlatType.STRING));
    }

    @Override
    public Program visit(Program n) {
        started_optimized = true;
        WolfFunction op_function = (WolfFunction) n.function.accept(this);
        ArrayList<Def> op_def_list = new ArrayList<>();
        for (Def def : n.def_list) {
            ArrayList<Def> clone_def_list = new ArrayList<>(n.def_list);
            clone_def_list.remove(def); // remove current definition

            Def op_def = (Def) def.accept(this);
            id_use = new IdentifierUsage(op_def.def_name);

            boolean found = (Boolean) op_function.accept(id_use);
            if (!found) {
                for (Def d : clone_def_list) {
                    found = (Boolean) d.accept(id_use);
                    if (found) {
                        break;
                    }
                }
            }
            if (found) {
                op_def_list.add(op_def);
            } else {
              started_optimized = false;
            }
        }
        if (!equal.visit(op_def_list, n.def_list) ||
            !equal.visit(op_function, n.function)) {
          started_optimized = false;
        }
        n.def_list = op_def_list;
        n.function = op_function;
        return n;
    }

    /**
     * Optimize a function definition
     *
     * @param n a function definition
     * @return an optimized function definition.
     */
    @Override
    public Def visit(Def n) {
        Identifier op_def_name = (Identifier) n.def_name.accept(this);
        Sig op_sig = (Sig) n.sig.accept(this);
        WolfFunction op_function = (WolfFunction) n.function.accept(this);

        if (!equal.visit(n.def_name, op_def_name) ||
            !equal.visit(n.sig, op_sig) ||
            !equal.visit(n.function, op_function)) {
          started_optimized = false;
        }
        n.def_name = op_def_name;
        n.sig = op_sig;
        n.function = op_function;
        return n;
    }

    /**
     * Optimize a function signature
     *
     * @param n a function signature
     * @return an optimized function signature.
     */
    @Override
    public Sig visit(Sig n) {
        List<SigArg> op_sig_args = new ArrayList<>();
        for (SigArg sig_arg : n.sig_args) {
            SigArg op_sig_arg = (SigArg) sig_arg.accept(this);
            if (op_sig_arg != null) {
                op_sig_args.add(op_sig_arg);
            }

        }
        if (!equal.visit(n.sig_args, op_sig_args)) {
          started_optimized = false;
        }
        n.sig_args = op_sig_args;
        return n;
    }

    /**
     * Optimize a function signature argument
     *
     * @param n a signature argument
     * @return the given signature argument, it's already optimized.
     */
    @Override
    public SigArg visit(SigArg n) {
        return n;
    }

    /**
     * Optimize a user function
     *
     * @param n a user function
     * @return an optimized user function.
     */
    @Override
    public UserFunc visit(UserFunc n) {
        UserFuncName op_user_func_name = (UserFuncName) n.user_func_name.accept(this);
        Args op_args = (Args) n.arg_list.accept(this);
        if (!equal.visit(n.user_func_name, op_user_func_name) ||
            !equal.visit(n.arg_list, op_args)) {
          started_optimized = false;
        }
        n.user_func_name = op_user_func_name;
        n.arg_list = op_args;
        return n;
    }

    /**
     * Optimize a branch
     *
     * @param n a branch function
     * @return the optimize branch or a native unary function.
     */
    @Override
    public Object visit(Branch n) {
        WolfFunction op_condition = (WolfFunction) n.condition.accept(this);
        WolfFunction op_true_branch = (WolfFunction) n.true_branch.accept(this);
        WolfFunction op_false_branch = (WolfFunction) n.false_branch.accept(this);

        if (equal.visit(op_condition, identity_one)) {
            started_optimized = false;
            NativeUnary nativeUnary = new NativeUnary(NativeUnaryOp.IDENTITY, op_true_branch);
            nativeUnary.setType(n.true_branch.getType());
            return nativeUnary;
        }
        if (equal.visit(op_condition, identity_zero)) {
            started_optimized = false;
            NativeUnary nativeUnary = new NativeUnary(NativeUnaryOp.IDENTITY, op_false_branch);
            nativeUnary.setType(n.false_branch.getType());
            return nativeUnary;
        }
        if (equal.visit(op_true_branch, op_false_branch)) {
            started_optimized = false;
            NativeUnary nativeUnary = new NativeUnary(NativeUnaryOp.IDENTITY, op_true_branch);
            nativeUnary.setType(n.true_branch.getType());
            return nativeUnary;
        }
        if (!equal.visit(n.condition, op_condition) ||
            !equal.visit(n.true_branch, op_true_branch) ||
            !equal.visit(n.false_branch, op_false_branch)) {
          started_optimized = false;
        }
        n.condition = op_condition;
        n.true_branch = op_true_branch;
        n.false_branch = op_false_branch;
        return n;
    }

    @Override
    public WolfLambda visit(WolfLambda n) {
        WolfFunction op_function = (WolfFunction) n.function.accept(this);
        Sig op_sig = (Sig) n.sig.accept(this);
        if (!equal.visit(n.function, op_function) ||
            !equal.visit(n.sig, op_sig)) {
          started_optimized = false;
        }
        n.function = op_function;
        n.sig = op_sig;
        return n;
    }

    /**
     * Optimize a fold
     *
     * @param n a fold
     * @return an optimized fold
     */
    @Override
    public Fold visit(Fold n) {
        FoldSymbol op_symbol = (FoldSymbol) n.fold_symbol.accept(this);
        FoldBody op_body = (FoldBody) n.fold_body.accept(this);
        if (!equal.visit(n.fold_symbol, op_symbol) ||
            !equal.visit(n.fold_body, op_body)) {
          started_optimized = false;
        }
        n.fold_symbol = op_symbol;
        n.fold_body = op_body;
        return n;
    }

    /**
     * Optimize a fold symbol
     *
     * @param n a fold symbol
     * @return the given fold symbol, it's already optimized.
     */
    @Override
    public FoldSymbol visit(FoldSymbol n) {
        return n;
    }

    /**
     * Optimize a fold body
     *
     * @param n a fold body
     * @return an optimized fold body
     */
    @Override
    public FoldBody visit(FoldBody n) {
        BinOp op_operator = (BinOp) n.bin_op.accept(this);
        ListArgument op_list_argument = (ListArgument) n.list_argument.accept(this);
        if (!equal.visit(n.bin_op, op_operator) ||
            !equal.visit(n.list_argument, op_list_argument)) {
          started_optimized = false;
        }
        n.bin_op = op_operator;
        n.list_argument = op_list_argument;
        return n;
    }

    /**
     * Optimize a map function
     *
     * @param n a map function
     * @return an optimized map function.
     */
    @Override
    public WolfMap visit(WolfMap n) {
        UnaryOp op_operator = (UnaryOp) n.unary_op.accept(this);
        ListArgument op_list_argument = (ListArgument) n.list_argument.accept(this);
        if (!equal.visit(n.unary_op, op_operator) ||
            !equal.visit(n.list_argument, op_list_argument)) {
          started_optimized = false;
        }
        n.unary_op = op_operator;
        n.list_argument = op_list_argument;
        return n;
    }

    /**
     * Optimize a list argument list
     *
     * @param n a list argument list
     * @return the given list argument list, it's already optimized.
     */
    @Override
    public ListArgsList visit(ListArgsList n) {
        for (int i = 0; i < n.getArgList().size(); i++) {
            Arg op_arg = (Arg) n.getArgList().get(i).accept(this);
            if (!equal.visit(op_arg, n.getArgList().get(i))) {
              started_optimized = false;
            }
            n.getArgList().set(i, op_arg);
        }

        return n;
    }

    /**
     * Optimize an argument list
     *
     * @param n an argument list
     * @return the given argument list, it's already optimized.
     */
    @Override
    public ArgsList visit(ArgsList n) {
        for (int i = 0; i < n.getArgList().size(); i++) {
            Arg op_arg = (Arg) n.getArgList().get(i).accept(this);
            if (!equal.visit(op_arg, n.getArgList().get(i))) {
              started_optimized = false;
            }
            n.getArgList().set(i, op_arg);
        }

        return n;
    }

    /**
     * Optimize a native unary.
     *
     * @param n a native unary
     * @return an optimized native unary.
     */
    @Override
    public Object visit(NativeUnary n) {
        NativeUnaryOp op_operator = (NativeUnaryOp) n.unary_op.accept(this);
        Arg op_arg = (Arg) n.arg.accept(this);
        switch (op_operator) {
            case NEG:
                if (op_arg instanceof NativeUnary) {
                    // negation in negation
                    NativeUnary nu = (NativeUnary) op_arg;
                    if (equal.visit(nu.unary_op, NativeUnaryOp.NEG)) {
                        started_optimized = false;
                        n.unary_op = NativeUnaryOp.IDENTITY;
                        n.arg = nu.arg;
                        return n;
                    }
                    // identity in a negation
                    if (equal.visit(nu.unary_op, NativeUnaryOp.IDENTITY)) {
                        started_optimized = false;
                        n.unary_op = NativeUnaryOp.NEG;
                        n.arg = nu.arg;
                        return n;
                    }
                }
                break;
            case LOGICAL_NOT:
                // Logical not in a logical not
                if (op_arg instanceof NativeUnary) {
                    NativeUnary nu = (NativeUnary) op_arg;
                    if (equal.visit(nu.unary_op, NativeUnaryOp.LOGICAL_NOT)) {
                        started_optimized = false;
                        n.unary_op = NativeUnaryOp.IDENTITY;
                        n.arg = nu.arg;
                        return n;
                    }

                    // identity in a logical not
                    if (equal.visit(nu.unary_op, NativeUnaryOp.IDENTITY)) {
                        started_optimized = false;
                        n.unary_op = NativeUnaryOp.LOGICAL_NOT;
                        n.arg = nu.arg;
                        return n;
                    }
                }
                break;
            case IDENTITY:
                // identity in an identity
                if (op_arg instanceof NativeUnary) {
                    NativeUnary nu = (NativeUnary) op_arg;
                    if (equal.visit(nu.unary_op, NativeUnaryOp.IDENTITY)) {
                        if (!(nu.arg instanceof IntLiteral)
                                && !(nu.arg instanceof FloatLiteral)
                                && !(nu.arg instanceof WolfList)
                                && !(nu.arg instanceof WolfString)
                                && !(nu.arg instanceof Identifier)) {
                            started_optimized = false;
                            return nu.arg;
                        }
                        n.unary_op = NativeUnaryOp.IDENTITY;
                        n.arg = nu.arg;
                        return n;
                    }
                }
                // Remove an identity around a function, it's redundant.
                if (!(op_arg instanceof IntLiteral)
                        && !(op_arg instanceof FloatLiteral)
                        && !(op_arg instanceof WolfList)
                        && !(op_arg instanceof WolfString)
                        && !(op_arg instanceof Identifier)) {
                    started_optimized = false;
                    return op_arg;
                }
                break;
            case PRINT:
                break;
            default:
                System.err.println("Invalid Native Unary Operation");
                return null;
        }
        n.unary_op = op_operator;
        n.arg = op_arg;
        return n;
    }

    /**
     * Optimize a native list unary
     *
     * @param n a native list unary
     * @return the given native list unary, it's already optimized.
     */
    @Override
    public NativeListUnary visit(NativeListUnary n) {
        return n;
    }

    /**
     * Optimize a native binary
     *
     * @param n a native binary
     * @return an optimized native binary or a native unary
     */
    @Override
    public Object visit(NativeBinary n) {
        NativeBinOp op_operator = (NativeBinOp) n.binary_op.accept(this);
        Arg op_left = (Arg) n.arg_left.accept(this);
        Arg op_right = (Arg) n.arg_right.accept(this);

        switch (op_operator) {
            case PLUS:
                // Identity (+0, + "")
                if (equal.visit(op_left, zero) || 
                    equal.visit(op_left, empty_string) ||
                    equal.visit(op_left, identity_zero)) {
                    started_optimized = false;
                    NativeUnary nativeUnary = new NativeUnary(NativeUnaryOp.IDENTITY, op_right);
                    nativeUnary.setType(n.getType());
                    return nativeUnary;
                }
                if (equal.visit(op_right, zero) ||
                    equal.visit(op_right, empty_string) ||
                    equal.visit(op_right, identity_zero)) {
                    started_optimized = false;
                    NativeUnary nativeUnary = new NativeUnary(NativeUnaryOp.IDENTITY, op_left);
                    nativeUnary.setType(n.getType());
                    return nativeUnary;
                }
                break;
            case MINUS:
                // a-a = 0
                if (equal.visit(op_left, op_right)) {
                    started_optimized = false;
                    return identity_zero;
                }
                break;
            case MULT:
                // a*0 or 0*a = 0
                if (equal.visit(op_left, zero) ||
                    equal.visit(op_right, zero) ||
                    equal.visit(op_left, identity_zero) ||
                    equal.visit(op_right, identity_zero)) {
                    started_optimized = false;
                    return identity_zero;
                }
                // Identity (*1)
                if (equal.visit(op_left, one) ||
                    equal.visit(op_left, identity_one)) {
                    started_optimized = false;
                    NativeUnary nativeUnary = new NativeUnary(NativeUnaryOp.IDENTITY, op_left);
                    nativeUnary.setType(n.getType());
                    return nativeUnary;
                }
                if (equal.visit(op_right, one) ||
                    equal.visit(op_right, identity_one)) {
                    started_optimized = false;
                    NativeUnary nativeUnary = new NativeUnary(NativeUnaryOp.IDENTITY, op_right);
                    nativeUnary.setType(n.getType());
                    return nativeUnary;
                }
                break;
            case DIV:
                // 0/a = 0
                if (equal.visit(op_left, zero) ||
                    equal.visit(op_left, identity_zero)) {
                    started_optimized = false;
                    return identity_zero;
                }
                // a/0 = undefined
                if (equal.visit(op_right, zero) ||
                    equal.visit(op_right, identity_zero)) {
                    throw new ArithmeticException("Cannot divide by zero!");
                }

                // a/1 = a
                if (equal.visit(op_right, one) ||
                    equal.visit(op_right, identity_one)) {
                    started_optimized = false;
                    return identity_one;
                }

                // a/a = 1
                if (equal.visit(op_left, op_right)) {
                    started_optimized = false;
                    return identity_one;
                }
                break;
            case MOD:
                // 1 % a = 1
                if (equal.visit(op_left, one) ||
                    equal.visit(op_left, identity_one)) {
                    started_optimized = false;
                    return identity_one;
                }

                // a % 1 = 0
                if (equal.visit(op_right, one) ||
                    equal.visit(op_right, identity_one)) {
                    started_optimized = false;
                    return identity_zero;
                }

                // a % a = 0
                if (equal.visit(op_left, op_right)) {
                    started_optimized = false;
                    return identity_zero;
                }

                // a % 0 = undefined
                if (equal.visit(op_right, zero)) {
                    throw new ArithmeticException("Cannot divide by 0.");
                }

                // 0 % a = 0 || @(0) % a = 0
                if (equal.visit(op_left, zero) ||
                    equal.visit(op_left, identity_zero)) {
                    started_optimized = false;
                    return identity_zero;
                }
                break;
            case LT:
            case GT:
                // a < a (false)
                // a > a (false)
                if (equal.visit(op_left, op_right)) {
                    started_optimized = false;
                    return identity_zero;
                }
                break;
            case LTE:
            case GTE:
                // a >= a (true)
                // a <= a (true)
                if (equal.visit(op_left, op_right)) {
                    started_optimized = false;
                    return identity_one;
                }
                break;
            case AND:
                // true && true = true
                if (!equal.visit(op_left, zero) &&
                    !equal.visit(op_right, zero) &&
                    op_left instanceof IntLiteral ||
                    !equal.visit(op_left, identity_zero) &&
                    !equal.visit(op_right, identity_zero) &&
                    op_right instanceof IntLiteral) {
                    started_optimized = false;
                    return identity_one;
                }

                // false in an and is false
                if (equal.visit(op_left, zero) ||
                    equal.visit(op_right, zero) ||
                    equal.visit(op_left, identity_zero) ||
                    equal.visit(op_right, identity_zero)) {
                    started_optimized = false;
                    return identity_zero;
                }

                // a && a = a
                if (equal.visit(op_left, op_right)) {
                    started_optimized = false;
                    NativeUnary nativeUnary = new NativeUnary(NativeUnaryOp.IDENTITY, op_left);
                    nativeUnary.setType(n.getType());
                    return nativeUnary;
                }
                break;
            case OR:
                // true || anything = true
                if (!equal.visit(op_left, one) ||
                    !equal.visit(op_right, one) ||
                    !equal.visit(op_left, identity_one) ||
                    !equal.visit(op_right, identity_one)) {
                    started_optimized = false;
                    return identity_one;
                }

                // false || false = false
                if (equal.visit(op_left, zero) &&
                    equal.visit(op_right, zero) ||
                    equal.visit(op_left, identity_zero) &&
                    equal.visit(op_right, identity_zero)) {
                    started_optimized = false;
                    return identity_zero;
                }

                // a || a = a
                if (equal.visit(op_left, op_right)) {
                    started_optimized = false;
                    NativeUnary nativeUnary = new NativeUnary(NativeUnaryOp.IDENTITY, op_left);
                    nativeUnary.setType(n.getType());
                    return nativeUnary;
                }

                // false || a = a
                if (equal.visit(op_left, zero) ||
                    equal.visit(op_left, identity_zero)) {
                    started_optimized = false;
                    NativeUnary nativeUnary = new NativeUnary(NativeUnaryOp.IDENTITY, op_right);
                    nativeUnary.setType(n.getType());
                    return nativeUnary;
                }

                // a || false = a
                if (equal.visit(op_right, zero) ||
                    equal.visit(op_right, identity_zero)) {
                    started_optimized = false;
                    NativeUnary nativeUnary = new NativeUnary(NativeUnaryOp.IDENTITY, op_left);
                    nativeUnary.setType(n.getType());
                    return nativeUnary;
                }
                break;
            case XOR:
                // 0 xor a = a
                if (equal.visit(op_left, zero) ||
                    equal.visit(op_left, identity_zero)) {
                    started_optimized = false;
                    NativeUnary nativeUnary = new NativeUnary(NativeUnaryOp.IDENTITY, op_right);
                    nativeUnary.setType(n.getType());
                    return nativeUnary;
                }

                // a xor 0 = a
                if (equal.visit(op_right, zero) ||
                    equal.visit(op_right, identity_zero)) {
                    started_optimized = false;
                    NativeUnary nativeUnary = new NativeUnary(NativeUnaryOp.IDENTITY, op_left);
                    nativeUnary.setType(n.getType());
                    return nativeUnary;
                }

                // 1 xor a = !a
                if (equal.visit(op_left, one) ||
                    equal.visit(op_left, identity_one)) {
                    started_optimized = false;
                    NativeUnary nativeUnary = new NativeUnary(NativeUnaryOp.LOGICAL_NOT, op_right);
                    nativeUnary.setType(n.getType());
                    return nativeUnary;
                }

                // a xor 1 = !a
                if (equal.visit(op_right, one) ||
                    equal.visit(op_right, identity_one)) {
                    started_optimized = false;
                    NativeUnary nativeUnary = new NativeUnary(NativeUnaryOp.LOGICAL_NOT, op_left);
                    nativeUnary.setType(n.getType());
                    return nativeUnary;
                }
                break;
            case EQUAL:
                if (equal.visit(op_left, op_right)) {
                    started_optimized = false;
                    return identity_one;
                }
                // same type
                if (op_left.getClass().equals(op_right.getClass()) &&
                    !(op_left instanceof Identifier)) {
                    if (!equal.visit(op_left, op_right)) {
                        started_optimized = false;
                        return identity_zero;
                    }
                }
                break;
            case NOT_EQUAL:
                if (equal.visit(op_left, op_right)) {
                    started_optimized = false;
                    return identity_zero;
                }
                // same type
                if (op_left.getClass().equals(op_right.getClass())) {
                    if (!equal.visit(op_left, op_right)) {
                        started_optimized = false;
                        return identity_one;
                    }
                }
                break;
            default:
                System.err.println("Invalid Binary Operator!");
                return null;
        }
        n.binary_op = op_operator;
        n.arg_left = op_left;
        n.arg_right = op_right;
        return n;
    }

    /**
     * Optimize a native list binary
     *
     * @param n a native list binary
     * @return the given native list binary, it's already optimized
     */
    @Override
    public NativeListBinary visit(NativeListBinary n) {
        return n;
    }

    /**
     * Optimize an identifier
     *
     * @param n an identifier
     * @return the given identifier, it's already optimized.
     */
    @Override
    public Identifier visit(Identifier n) {
        return n;
    }

    /**
     * Optimize a float literal
     *
     * @param n the float literal
     * @return the given float literal, it's already optimized.
     */
    @Override
    public FloatLiteral visit(FloatLiteral n) {
        return n;
    }

    /**
     * Optimize an integer literal
     *
     * @param n the integer literal
     * @return the given integer literal, it's already optimized.
     */
    @Override
    public IntLiteral visit(IntLiteral n) {
        return n;
    }

    /**
     * Optimize a WolfList
     *
     * @param n a WolfList
     * @return the given WolfList, it's already optimized.
     */
    @Override
    public WolfList visit(WolfList n) {
        return n;
    }

    /**
     * Optimize a WolfString
     *
     * @param n a WolfString
     * @return the given WolfString, it's already optimized.
     */
    @Override
    public WolfString visit(WolfString n) {
        return n;
    }

    /**
     * Optimize a string body
     *
     * @param n a string body
     * @return the given string body, it's already optimized.
     */
    @Override
    public StringBody visit(StringBody n) {
        return n;
    }

    /**
     * Optimize a given string escape sequence.
     *
     * @param n a string escape sequence.
     * @return the given string escape sequence, it's already optimized.
     */
    @Override
    public StringEscapeSeq visit(StringEscapeSeq n) {
        return n;
    }

    /**
     * Optimize a native binary operator.
     *
     * @param n a native binary operator.
     * @return the given native binary operator, it's already optimized.
     */
    @Override
    public NativeBinOp visit(NativeBinOp n) {
        return n;
    }

    /**
     * Optimize a native unary operator
     *
     * @param n a native unary operator.
     * @return the given native unary operator, it's already optimized.
     */
    @Override
    public NativeUnaryOp visit(NativeUnaryOp n) {
        return n;
    }

    @Override
    public Object visit(Type n) {
        return n;
    }

    @Override
    public Object visit(InputArg n) {
        return n;
    }

    @Override
    public Object visit(NativeListBinaryOp n) {
        return n;
    }

    @Override
    public Object visit(NativeListUnaryOp n) {
        return n;
    }

    public boolean startedOptimized() {
      return started_optimized;
    }
}
