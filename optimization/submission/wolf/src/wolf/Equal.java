package wolf;

import wolf.interfaces.EscapeChar;
import wolf.interfaces.*;
import wolf.enums.*;

/**
 * Check for equality among the classes.
 */
public class Equal {

  /**
   * Check for equality among all WOL(F) classes.
   * @param obj1
   * @param obj2
   * @return
   */
    public Boolean visit(Object obj1, Object obj2) {
      if(!obj1.getClass().equals(obj2.getClass())) {
        return false;
      }

      if(obj1 instanceof Args) {

        ArgsList args_list1 = (ArgsList) obj1;
        ArgsList args_list2 = (ArgsList) obj2;
        if(args_list1.getArgList().size() != args_list2.getArgList().size()) {
          return false;
        }
        for(int i = 0; i < args_list1.getArgList().size();i++) {
          if(!visit(args_list1.getArgList().get(i),args_list2.getArgList().get(i))) {
            return false;
          }
        }
        return true;

      } else if (obj1 instanceof Branch) {

        Branch b1 = (Branch) obj1;
        Branch b2 = (Branch) obj2;

        return  visit(b1.condition,b2.condition) &&
                visit(b1.true_branch,b2.true_branch) &&
                visit(b1.false_branch,b2.false_branch);

      } else if (obj1 instanceof Def) {

        Def d1 = (Def) obj1;
        Def d2 = (Def) obj2;

        return  d1.type.equals(d2.type) &&
                visit(d1.def_name,d2.def_name) &&
                visit(d1.sig, d2.sig) &&
                visit(d1.function,d2.function);

      } else if (obj1 instanceof FloatLiteral) {

        FloatLiteral fl1 = (FloatLiteral) obj1;
        FloatLiteral fl2 = (FloatLiteral) obj2;

        float f1 = Float.parseFloat(fl1.float_literal.getText());
        float f2 = Float.parseFloat(fl2.float_literal.getText());

        return f1 == f2;

      } else if (obj1 instanceof Fold) {

        Fold f1 = (Fold) obj1;
        Fold f2 = (Fold) obj2;

        return  visit(f1.fold_symbol,f2.fold_symbol) &&
                visit(f1.fold_body,f2.fold_body);


      } else if (obj1 instanceof FoldSymbol) {

        FoldSymbol fs1 = (FoldSymbol) obj1;
        FoldSymbol fs2 = (FoldSymbol) obj2;

        return fs1.equals(fs2);

      } else if (obj1 instanceof FoldBody) {

        FoldBody fb1 = (FoldBody) obj1;
        FoldBody fb2 = (FoldBody) obj2;

        return  visit(fb1.bin_op,fb2.bin_op) &&
                visit(fb1.list_argument, fb2.list_argument);

      } else if (obj1 instanceof Identifier) {

        Identifier id1 = (Identifier) obj1;
        Identifier id2 = (Identifier) obj2;

        return id1.identifier.getText().equals(id2.identifier.getText());

      } else if (obj1 instanceof IntLiteral) {

        IntLiteral il1 = (IntLiteral) obj1;
        IntLiteral il2 = (IntLiteral) obj2;

        int i1 = Integer.parseInt(il1.int_literal.getText());
        int i2 = Integer.parseInt(il2.int_literal.getText());

        return i1 == i2;

      } else if(obj1 instanceof NativeBinary) {

        NativeBinary nb1 = (NativeBinary) obj1;
        NativeBinary nb2 = (NativeBinary) obj2;

        return  visit(nb1.binary_op,nb2.binary_op) &&
                visit(nb1.arg_left,nb2.arg_left) &&
                visit(nb1.arg_right, nb2.arg_right);

      } else if(obj1 instanceof NativeListBinary) {

        NativeListBinary nlb1 = (NativeListBinary) obj1;
        NativeListBinary nlb2 = (NativeListBinary) obj2;

        return  visit(nlb1.binary_op,nlb2.binary_op) &&
                visit(nlb1.arg,nlb2.arg) &&
                visit(nlb1.list_argument,nlb2.list_argument);

      } else if(obj1 instanceof NativeListUnary) {

        NativeListUnary nlu1 = (NativeListUnary) obj1;
        NativeListUnary nlu2 = (NativeListUnary) obj2;

        return  visit(nlu1.list_unary_op, nlu2.list_unary_op) &&
                visit(nlu1.list_argument, nlu2.list_argument);

      } else if(obj1 instanceof NativeUnary) {

        NativeUnary nu1 = (NativeUnary) obj1;
        NativeUnary nu2 = (NativeUnary) obj2;

        return  visit(nu1.unary_op,nu2.unary_op) &&
                visit(nu1.arg, nu2.arg);

      } else if (obj1 instanceof NativeBinOp) {

        NativeBinOp nbo1 = (NativeBinOp) obj1;
        NativeBinOp nbo2 = (NativeBinOp) obj2;

        return nbo1.equals(nbo2);

      } else if(obj1 instanceof NativeUnaryOp) {

        NativeUnaryOp nuo1 = (NativeUnaryOp) obj1;
        NativeUnaryOp nuo2 = (NativeUnaryOp) obj2;

        return nuo1.equals(nuo2);

      } else if(obj1 instanceof NativeListBinaryOp) {
        NativeListBinaryOp nlbo1 = (NativeListBinaryOp) obj1;
        NativeListBinaryOp nlbo2 = (NativeListBinaryOp) obj2;

        return nlbo1.equals(nlbo2);

      } else if(obj1 instanceof NativeListUnaryOp) {
        NativeListUnaryOp nluo1 = (NativeListUnaryOp) obj1;
        NativeListUnaryOp nluo2 = (NativeListUnaryOp) obj2;

        return nluo1.equals(nluo2);

      } else if(obj1 instanceof Program) {

        Program p1 = (Program) obj1;
        Program p2 = (Program) obj2;

        if(p1.def_list.size() != p2.def_list.size()) {
          return false;
        }
        for(int i = 0; i < p1.def_list.size(); i++) {
          if(!visit(p1.def_list.get(i), p2.def_list.get(i))) {
            return false;
          }
        }

        return visit(p1.function, p2.function);

      } else if(obj1 instanceof Sig) {

        Sig s1 = (Sig) obj1;
        Sig s2 = (Sig) obj2;

        if(s1.sig_args.size() != s2.sig_args.size()) {
          return false;
        }

        for(int i = 0; i < s1.sig_args.size(); i++) {
          if(!visit(s1.sig_args.get(i),s2.sig_args.get(i))) {
            return false;
          }
        }

        return true;

      } else if(obj1 instanceof SigArg) {

        SigArg sa1 = (SigArg) obj1;
        SigArg sa2 = (SigArg) obj2;

        return  sa1.type.equals(sa2.type) &&
                visit(sa1.identifier,sa2.identifier);

      } else if(obj1 instanceof StringBody) {

        StringBody sb1 = (StringBody) obj1;
        StringBody sb2 = (StringBody) obj2;

        return sb1.string_body.getText().equals(sb2.string_body.getText());

      } else if(obj1 instanceof StringEscapeSeq) {

        StringEscapeSeq ses1 = (StringEscapeSeq) obj1;
        StringEscapeSeq ses2 = (StringEscapeSeq) obj2;

        return visit(ses1.escape_char, ses2.escape_char);

      } else if(obj1 instanceof EscapeChar) {

        EscapeChar ec1 = (EscapeChar) obj1;
        EscapeChar ec2 = (EscapeChar) obj2;

        return ec1.equals(ec2);

      } else if(obj1 instanceof UserFunc) {

        UserFunc uf1 = (UserFunc) obj1;
        UserFunc uf2 = (UserFunc) obj2;

        return  visit(uf1.user_func_name,uf2.user_func_name) &&
                visit(uf1.arg_list,uf2.arg_list);


      } else if(obj1 instanceof WolfLambda) {

        WolfLambda wl1 = (WolfLambda) obj1;
        WolfLambda wl2 = (WolfLambda) obj2;

        return  visit(wl1.sig,wl2.sig) &&
                visit(wl1.function, wl2.function);

      } else if(obj1 instanceof WolfList) {

        WolfList wl1 = (WolfList) obj1;
        WolfList wl2 = (WolfList) obj2;

        if(wl1.list_elements.size() != wl2.list_elements.size()) {
          return false;
        }
        for(int i = 0; i < wl1.list_elements.size(); i++) {
          if(!visit(wl1.list_elements.get(i),wl2.list_elements.get(i))) {
            return false;
          }
        }

        return true;

      } else if(obj1 instanceof WolfMap) {

        WolfMap wm1 = (WolfMap) obj1;
        WolfMap wm2 = (WolfMap) obj2;

        return  visit(wm1.unary_op,wm2.unary_op) &&
                 visit(wm1.list_argument, wm2.list_argument);

      } else if(obj1 instanceof WolfString) {

        WolfString ws1 = (WolfString) obj1;
        WolfString ws2 = (WolfString) obj2;

        if(ws1.string.size() != ws2.string.size()) {
          return false;
        }

        for(int i = 0; i < ws1.string.size(); i++) {
          if(!visit(ws1.string.get(i), ws2.string.get(i))) {
            return false;
          }
        }
      return true;
      }
      System.out.println("No match");
      return false;
    }
}
