package wolf;

import wolf.interfaces.*;
import wolf.enums.*;
import wolf.node.*;

/**
 * Determines if an identifier is used.
 */
public class IdentifierUsage implements Visitor {

  Identifier id;
  Equal equal;

  public IdentifierUsage(Identifier id) {
    this.id = id;
    equal = new Equal();
  }

  @Override
  public Boolean visit(Program n) {
    boolean found;
    for(Def def: n.def_list) {
      found = (Boolean) def.accept(this);
      if(found) {
        return found;
      }
    }
    return (Boolean) n.function.accept(this);
  }

  @Override
  public Boolean visit(Def n) {
    return (Boolean) n.function.accept(this);
  }

  @Override
  public Boolean visit(Sig n) {
    return false;
  }

  @Override
  public Boolean visit(SigArg n) {
    return false;
  }

  @Override
  public Boolean visit(UserFunc n) {
    boolean found = (Boolean) n.user_func_name.accept(this);
    if(found) {
      return found;
    }
    for(Arg arg: n.arg_list.getArgList()) {
      if((Boolean) arg.accept(this)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public Boolean visit(Branch n) {
    boolean found = (Boolean) n.condition.accept(this);
    if(found) {
      return found;
    }
    found = (Boolean) n.true_branch.accept(this);
    if(found) {
      return found;
    }
    return (Boolean) n.false_branch.accept(this);
  }

  @Override
  public Boolean visit(WolfLambda n) {
    return (Boolean) n.function.accept(this);
  }

  @Override
  public Boolean visit(Fold n) {
    return (Boolean) n.fold_body.accept(this);
  }

  @Override
  public Boolean visit(FoldSymbol n) { return false; }

  @Override
  public Boolean visit(FoldBody n) {
    boolean found = (Boolean) n.list_argument.accept(this);
    if(found) {
      return found;
    }
    return (Boolean) n.bin_op.accept(this);
  }

  @Override
  public Boolean visit(WolfMap n) {
    boolean found;
    found = (Boolean) n.list_argument.accept(this);
    if(found) {
      return found;
    }
    found = (Boolean) n.unary_op.accept(this);
    return found;
  }

  @Override
  public Boolean visit(ListArgsList n) {
    for(Arg arg : n.getArgList()) {
      if(arg instanceof Identifier) {
        if((Boolean) arg.accept(this)) {
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public Boolean visit(ArgsList n) {
    for(Arg arg : n.getArgList()) {
      if(arg instanceof Identifier) {
        if((Boolean) arg.accept(this)) {
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public Boolean visit(NativeUnary n) {
    return (Boolean) n.arg.accept(this);
  }

  @Override
  public Boolean visit(NativeListUnary n) {
    return (Boolean) n.list_argument.accept(this);
  }

  @Override
  public Boolean visit(NativeBinary n) {
    boolean found;
    found = (Boolean) n.arg_left.accept(this);
    if(found) {
      return found;
    }
    found = (Boolean) n.arg_right.accept(this);
    return found;
  }

  @Override
  public Boolean visit(NativeListBinary n) {
    boolean found;
    found = (Boolean) n.arg.accept(this);
    if(found) {
      return found;
    }
    found = (Boolean) n.list_argument.accept(this);
    return found;
  }

  // ----
  @Override
  public Boolean visit(Identifier n) {
    return equal.visit(n,id);
  }

  @Override
  public Boolean visit(FloatLiteral n) { return false; }

  @Override
  public Boolean visit(IntLiteral n) { return false; }

  // Needs fixing
  @Override
  public Boolean visit(WolfList n) {
    for(ListElement element: n.list_elements) {
      if((Boolean) element.accept(this)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public Boolean visit(WolfString n) { return false; }

  @Override
  public Boolean visit(StringBody n) { return false; }

  @Override
  public Boolean visit(StringEscapeSeq n) { return false; }

  @Override
  public Boolean visit(EscapeChar n) { return false; }

  @Override
  public Boolean visit(NativeBinOp n) { return false; }

  @Override
  public Boolean visit(NativeUnaryOp n) { return false; }

  @Override
  public Boolean visit(Type n) {
    return false;
  }
  public Boolean visit(InputArg n) {
    return false;
  }
}
