package wolf.enums;

import wolf.node.TAnd;
import wolf.node.TAppend;
import wolf.node.TDiv;
import wolf.node.TEqual;
import wolf.node.TGt;
import wolf.node.TGte;
import wolf.node.TLt;
import wolf.node.TLte;
import wolf.node.TMinus;
import wolf.node.TMod;
import wolf.node.TMult;
import wolf.node.TNotEqual;
import wolf.node.TOr;
import wolf.node.TPlus;
import wolf.node.TPrepend;
import wolf.node.TXor;

/**
 *
 * @author Kevin Dittmar
 * @version Apr 3, 2016
 */
public enum NativeBinOp {
    PLUS(TPlus.class),
    MINUS(TMinus.class),
    MULT(TMult.class),
    DIV(TDiv.class),
    MOD(TMod.class),
    LT(TLt.class),
    GT(TGt.class),
    LTE(TLte.class),
    GTE(TGte.class),
    EQUAL(TEqual.class),
    NOT_EQUAL(TNotEqual.class),
    AND(TAnd.class),
    OR(TOr.class),
    XOR(TXor.class),
    APPEND(TAppend.class),
    PREPEND(TPrepend.class);
    
    final Class token_class;
    
    NativeBinOp(Class token_class) {
        this.token_class = token_class;
    }
}
