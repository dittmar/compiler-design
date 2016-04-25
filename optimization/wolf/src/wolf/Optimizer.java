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

  public Optimizer() {
    equal = new Equal();

    one = new IntLiteral(
        new TIntNumber("1")
    );
    zero = new IntLiteral(
        new TIntNumber("0")
    );
    identity_one = new NativeUnary(NativeUnaryOp.IDENTITY,one);
    identity_zero = new NativeUnary(NativeUnaryOp.IDENTITY,zero);

    ArrayList<StringMiddle> middle = new ArrayList<>();
    middle.add(new StringBody(new TStringBody("")));
    empty_string = new WolfString(middle);
  }

  @Override
  public Program visit(Program n) {
    WolfFunction op_function = (WolfFunction) n.function.accept(this);
    ArrayList<Def> op_def_list = new ArrayList<>();
    for(Def def:n.def_list) {
      ArrayList<Def> clone_def_list = new ArrayList<>(n.def_list);
      clone_def_list.remove(def); // remove current definition

      Def op_def = (Def) def.accept(this);
      id_use = new IdentifierUsage(op_def.def_name);

      boolean found = (Boolean) op_function.accept(id_use);
      if(!found) {
        for(Def d : clone_def_list) {
          found = (Boolean) d.accept(id_use);
          if(found) {
            break;
          }
        }
      }
      if(found) {
        op_def_list.add(op_def);
      }
    }
    return new Program(op_def_list,op_function);
  }

  /**
   * Optimize a function definition
   * @param n a function definition
   * @return an optimized function definition.
   */
  @Override
  public Def visit(Def n) {
    Identifier op_def_name = (Identifier) n.def_name.accept(this);
    Sig op_sig = (Sig) n.sig.accept(this);
    WolfFunction op_function = (WolfFunction) n.function.accept(this);

    return new Def(n.type,op_def_name,op_sig,op_function);
  }

  /**
   * Optimize a function signature
   * @param n a function signature
   * @return an optimized function signature.
   */
  @Override
  public Sig visit(Sig n) {
    List<SigArg> op_sig_args = new ArrayList<>();
    for(SigArg sig_arg : n.sig_args) {
      SigArg op_sig_arg = (SigArg) sig_arg.accept(this);
      if(op_sig_arg != null) {
        op_sig_args.add(op_sig_arg);
      }
    }
    return new Sig(op_sig_args);
  }

  /**
   * Optimize a function signature argument
   * @param n a signature argument
   * @return the given signature argument, it's already optimized.
   */
  @Override
  public SigArg visit(SigArg n) {
    return n;
  }

  /**
   * Optimize a user function
   * @param n a user function
   * @return an optimized user function.
   */
  @Override
  public UserFunc visit(UserFunc n) {
    UserFuncName op_user_func_name = (UserFuncName) n.user_func_name.accept(this);
    Args op_args = (Args) n.arg_list.accept(this);
    return new UserFunc(op_user_func_name,op_args);
  }

  /**
   * Optimize a branch
   * @param n a branch function
   * @return the optimize branch or a native unary function.
   */
  @Override
  public Object visit(Branch n) {
    WolfFunction op_condition = (WolfFunction) n.condition.accept(this);
    WolfFunction op_true_branch = (WolfFunction) n.true_branch.accept(this);
    WolfFunction op_false_branch = (WolfFunction) n.false_branch.accept(this);

    if (equal.visit(op_condition, identity_one)) {
      return new NativeUnary(NativeUnaryOp.IDENTITY,op_true_branch);
    }
    if(equal.visit(op_condition,identity_zero)) {
      return new NativeUnary(NativeUnaryOp.IDENTITY,op_false_branch);
    }
    if(equal.visit(op_true_branch,op_false_branch)) {
      return new NativeUnary(NativeUnaryOp.IDENTITY,op_true_branch);
    }
    return new Branch(op_condition,op_true_branch,op_false_branch);
  }

  @Override
  public WolfLambda visit(WolfLambda n) {
    WolfFunction op_function = (WolfFunction) n.function.accept(this);
    Sig op_sig = (Sig) n.sig.accept(this);
    return new WolfLambda(op_sig,op_function);
  }

  /**
   * Optimize a fold
   * @param n a fold
   * @return an optimized fold
   */
  @Override
  public Fold visit(Fold n) {
    FoldSymbol op_symbol = (FoldSymbol) n.fold_symbol.accept(this);
    FoldBody op_body = (FoldBody) n.fold_body.accept(this);
    return new Fold(op_symbol, op_body);
  }

  /**
   * Optimize a fold symbol
   * @param n a fold symbol
   * @return the given fold symbol, it's already optimized.
   */
  @Override
  public FoldSymbol visit(FoldSymbol n) {
    return n;
  }

  /**
   * Optimize a fold body
   * @param n a fold body
   * @return an optimized fold body
   */
  @Override
  public FoldBody visit(FoldBody n) {
    BinOp op_operator = (BinOp) n.bin_op.accept(this);
    ListArgument op_list_argument = (ListArgument) n.list_argument.accept(this);
    return new FoldBody(op_operator,op_list_argument);
  }

  /**
   * Optimize a map function
   * @param n a map function
   * @return an optimized map function.
   */
  @Override
  public WolfMap visit(WolfMap n) {
    UnaryOp op_operator = (UnaryOp) n.unary_op.accept(this);
    ListArgument op_list_argument = (ListArgument) n.list_argument.accept(this);
    return new WolfMap(op_operator,op_list_argument);
  }

  /**
   * Optimize a list argument list
   * @param n a list argument list
   * @return the given list argument list, it's already optimized.
   */
  @Override
  public ListArgsList visit(ListArgsList n) {
    List<Arg> op_arg_list = new ArrayList<>();
    for(Arg arg : n.getArgList()) {
      op_arg_list.add((Arg) arg.accept(this));
    }
    return new ListArgsList(op_arg_list);
  }

  /**
   * Optimize an argument list
   * @param n an argument list
   * @return the given argument list, it's already optimized.
   */
  @Override
  public ArgsList visit(ArgsList n) {
    List<Arg> op_arg_list = new ArrayList<>();
    for(Arg arg : n.getArgList()) {
      op_arg_list.add((Arg) arg.accept(this));
    }
    return new ArgsList(op_arg_list);
  }

  /**
   * Optimize a native unary.
   * @param n a native unary
   * @return an optimized native unary.
   */
  @Override
  public Object visit(NativeUnary n) {
    NativeUnaryOp op_operator = (NativeUnaryOp) n.unary_op.accept(this);
    Arg op_arg = (Arg) n.arg.accept(this);
    switch (op_operator) {
      case NEG:
        // negation in a negation
        if(op_arg instanceof NativeUnary) {
          NativeUnary nu = (NativeUnary) op_arg;
          if(nu.unary_op.equals(NativeUnaryOp.NEG)) {
            return new NativeUnary(NativeUnaryOp.IDENTITY,nu.arg);
          }
        }
        break;
      case LOGICAL_NOT:
        // Logical not in a logical not
        if(op_arg instanceof NativeUnary) {
          NativeUnary nu = (NativeUnary) op_arg;
          if(nu.unary_op.equals(NativeUnaryOp.LOGICAL_NOT)) {
            return new NativeUnary(NativeUnaryOp.IDENTITY,nu.arg);
          }
        }
        break;
      case IDENTITY:
        // identity in an identity
        if(op_arg instanceof NativeUnary) {
          NativeUnary nu = (NativeUnary) op_arg;
          if(nu.unary_op.equals(NativeUnaryOp.IDENTITY)) {
            if(!(nu.arg instanceof IntLiteral) &&
                !(nu.arg instanceof FloatLiteral) &&
                !(nu.arg instanceof WolfList) &&
                !(nu.arg instanceof WolfString) &&
                !(nu.arg instanceof Identifier)) {
              return nu.arg;
            }
            return new NativeUnary(NativeUnaryOp.IDENTITY,nu.arg);
          }
        }
        // Remove an identity around a function, it's redundant.
        if(!(op_arg instanceof IntLiteral) &&
            !(op_arg instanceof FloatLiteral) &&
            !(op_arg instanceof WolfList) &&
            !(op_arg instanceof WolfString) &&
            !(op_arg instanceof Identifier)) {
          return op_arg;
        }
        break;
      case PRINT:
        break;
      default:
        System.err.println("Invalid Native Unary Operation");
        return null;
    }
    return new NativeUnary(op_operator,op_arg);
  }

  /**
   * Optimize a native list unary
   * @param n a native list unary
   * @return the given native list unary, it's already optimized.
   */
  @Override
  public NativeListUnary visit(NativeListUnary n) {
    return n;
  }

  /**
   * Optimize a native binary
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
        if(equal.visit(op_left,zero) || equal.visit(op_left,empty_string) ||
            equal.visit(op_left,identity_zero)) {
          return new NativeUnary(NativeUnaryOp.IDENTITY,op_right);
        }
        if(equal.visit(op_right,zero) || equal.visit(op_right,empty_string) ||
            equal.visit(op_right,identity_zero)) {
          return new NativeUnary(NativeUnaryOp.IDENTITY,op_left);
        }
        break;
      case MINUS:
        // a-a = 0
        if(equal.visit(op_left,op_right)) {
          return identity_zero;
        }
        break;
      case MULT:
        // a*0 or 0*a = 0
        if(equal.visit(op_left,zero) || equal.visit(op_right,zero) ||
            equal.visit(op_left,identity_zero) || equal.visit(op_right,identity_zero)) {
          return identity_zero;
        }
        // Identity (*1)
        if(equal.visit(op_left,one) || equal.visit(op_left,identity_one)) {
          return new NativeUnary(NativeUnaryOp.IDENTITY,op_left);
        }
        if(equal.visit(op_right,one) || equal.visit(op_right,identity_one)) {
          return new NativeUnary(NativeUnaryOp.IDENTITY,op_right);
        }
        break;
      case DIV:
        // 0/a = 0
        if(equal.visit(op_left,zero) || equal.visit(op_left,identity_zero)) {
          return identity_zero;
        }
        // a/0 = undefined
        if(equal.visit(op_right,zero) || equal.visit(op_right,identity_zero)) {
          throw new ArithmeticException("Cannot divide by zero!");
        }

        // a/1 = a
        if(equal.visit(op_right,one) || equal.visit(op_right,identity_one)) {
          return identity_one;
        }

        // a/a = 1
        if(equal.visit(op_left,op_right)) {
          return identity_one;
        }
        break;
      case MOD:
        // 1 % a = 1
        if(equal.visit(op_left,one) || equal.visit(op_left,identity_one)) {
          return identity_one;
        }

        // a % 1 = 0
        if(equal.visit(op_right,one) || equal.visit(op_right,identity_one)) {
          return identity_zero;
        }

        // a % a = 0
        if(equal.visit(op_left,op_right)) {
          return identity_zero;
        }

        // a % 0 = undefined
        if(equal.visit(op_right,zero)) {
          throw new ArithmeticException("Cannot divide by 0.");
        }

        // 0 % a = 0 || @(0) % a = 0
        if(equal.visit(op_left,zero) || equal.visit(op_left,identity_zero)) {
          return identity_zero;
        }
        break;
      case LT:
      case GT:
        // a < a (false)
        // a > a (false)
        if(equal.visit(op_left,op_right)) {
          return identity_zero;
        }
        break;
      case LTE:
      case GTE:
        // a >= a (true)
        // a <= a (true)
        if(equal.visit(op_left,op_right)) {
          return identity_one;
        }
        break;
      case AND:
        // true && true = true
        if(!equal.visit(op_left,zero) && !equal.visit(op_right,zero) ||
            !equal.visit(op_left,identity_zero) && !equal.visit(op_right,identity_zero)) {
          return identity_one;
        }

        // false in an and is false
        if(equal.visit(op_left,zero) || equal.visit(op_right,zero) ||
            equal.visit(op_left,identity_zero) || equal.visit(op_right,identity_zero)) {
          return identity_zero;
        }

        // a && a = a
        if(equal.visit(op_left,op_right)) {
          return new NativeUnary(NativeUnaryOp.IDENTITY,op_left);
        }
        break;
      case OR:
        // true || anything = true
        if(!equal.visit(op_left,one) || !equal.visit(op_right,one) ||
            !equal.visit(op_left,identity_one) || !equal.visit(op_right,identity_one)) {
          return identity_one;
        }

        // false || false = false
        if(equal.visit(op_left,zero) && equal.visit(op_right,zero) ||
            equal.visit(op_left,identity_zero) && equal.visit(op_right,identity_zero)) {
          return identity_zero;
        }

        // a || a = a
        if(equal.visit(op_left,op_right)) {
          return new NativeUnary(NativeUnaryOp.IDENTITY,op_left);
        }

        // false || a = a
        if(equal.visit(op_left,zero) || equal.visit(op_left,identity_zero)) {
          return new NativeUnary(NativeUnaryOp.IDENTITY,op_right);
        }

        // a || false = a
        if(equal.visit(op_right,zero) || equal.visit(op_right, identity_zero)) {
          return new NativeUnary(NativeUnaryOp.IDENTITY,op_left);
        }
        break;
      case XOR:
        // 0 xor a = a
        if(equal.visit(op_left,zero) || equal.visit(op_left,identity_zero)) {
          return new NativeUnary(NativeUnaryOp.IDENTITY,op_right);
        }

        // a xor 0 = a
        if(equal.visit(op_right,zero) || equal.visit(op_right,identity_zero)) {
          return new NativeUnary(NativeUnaryOp.IDENTITY,op_left);
        }

        // 1 xor a = !a
        if(equal.visit(op_left,one) || equal.visit(op_left,identity_one)) {
          return new NativeUnary(NativeUnaryOp.LOGICAL_NOT,op_right);
        }

        // a xor 1 = !a
        if(equal.visit(op_right,one) || equal.visit(op_right,identity_one)) {
          return new NativeUnary(NativeUnaryOp.LOGICAL_NOT,op_left);
        }
        break;
      case EQUAL:
        if(equal.visit(op_left,op_right)) {
          return identity_one;
        }
        // same type
        if(op_left.getClass().equals(op_right.getClass()) && !(op_left instanceof Identifier)) {
          if(!equal.visit(op_left,op_right)) {
            return identity_zero;
          }
        }
        break;
      case NOT_EQUAL:
        if(equal.visit(op_left,op_right)) {
          return identity_zero;
        }
        // same type
        if(op_left.getClass().equals(op_right.getClass())) {
          if(!equal.visit(op_left,op_right)) {
            return identity_one;
          }
        }
        break;
      default:
        System.err.println("Invalid Binary Operator!");
        return null;
    }
    return new NativeBinary(op_operator,op_left,op_right);
  }

  /**
   * Optimize a native list binary
   * @param n a native list binary
   * @return the given native list binary, it's already optimized
   */
  @Override
  public NativeListBinary visit(NativeListBinary n) {
    return n;
  }

  /**
   * Optimize an identifier
   * @param n an identifier
   * @return the given identifier, it's already optimized.
   */
  @Override
  public Identifier visit(Identifier n) {
    return n;
  }

  /**
   * Optimize a float literal
   * @param n the float literal
   * @return the given float literal, it's already optimized.
   */
  @Override
  public FloatLiteral visit(FloatLiteral n) {
    return n;
  }

  /**
   * Optimize an integer literal
   * @param n the integer literal
   * @return the given integer literal, it's already optimized.
   */
  @Override
  public IntLiteral visit(IntLiteral n) {
    return n;
  }

  /**
   * Optimize a WolfList
   * @param n a WolfList
   * @return the given WolfList, it's already optimized.
   */
  @Override
  public WolfList visit(WolfList n) {
    return n;
  }

  /**
   * Optimize a WolfString
   * @param n a WolfString
   * @return the given WolfString, it's already optimized.
   */
  @Override
  public WolfString visit(WolfString n) {
    return n;
  }

  /**
   * Optimize a string body
   * @param n a string body
   * @return the given string body, it's already optimized.
   */
  @Override
  public StringBody visit(StringBody n) {
    return n;
  }

  /**
   * Optimize a given string escape sequence.
   * @param n a string escape sequence.
   * @return the given string escape sequence, it's already optimized.
   */
  @Override
  public StringEscapeSeq visit(StringEscapeSeq n) {
    return n;
  }

  /**
   * Optimize a given escape character
   * @param n an escape character.
   * @return the given escape character, it's already optimized.
   */
  @Override
  public EscapeChar visit(EscapeChar n) {
    return n;
  }

  /**
   * Optimize a native binary operator.
   * @param n a native binary operator.
   * @return the given native binary operator, it's already optimized.
   */
  @Override
  public NativeBinOp visit(NativeBinOp n) {
    return n;
  }

  /**
   * Optimize a native unary operator
   * @param n a native unary operator.
   * @return the given native unary operator, it's already optimized.
   */
  @Override
  public NativeUnaryOp visit(NativeUnaryOp n) {
    return n;
  }

    @Override
    public Object visit(Type n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object visit(InputArg n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
