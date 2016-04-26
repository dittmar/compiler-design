package wolf.enums;

import wolf.interfaces.BinOp;
import wolf.interfaces.Visitor;
import wolf.node.TAnd;
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
import wolf.node.TXor;

/**
 * Enumerated type representing WOLF's native binary operations.
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @author Joseph Alacqua
 * @version Apr 3, 2016
 */
public enum NativeBinOp implements BinOp {
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
    XOR(TXor.class);
    
    private final Class token_class;
    
    /**
     * Create a NativeBinOp
     * @param token_class the class of the token being represented
     */
    NativeBinOp(Class token_class) {
        this.token_class = token_class;
    }
    
    @Override
    public String toString() {
        return EnumReporter.report(token_class);
    }

    public Class getTokenClass() {
        return token_class;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
