package wolf.enums;

import java.util.ArrayList;
import java.util.List;
import wolf.FlatType;
import wolf.Type;
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
    
    final Class token_class;
    
    /**
     * Create a NativeBinOp
     * @param token_class the class of the token being represented
     */
    NativeBinOp(Class token_class) {
        this.token_class = token_class;
    }
    
    public List<Type> accept(Visitor n) {
        List<Type> valid_types = new ArrayList();
        valid_types.add(new Type(FlatType.INTEGER));
        if(!token_class.equals(TXor.class) && !token_class.equals(TOr.class) &&
                !token_class.equals(TAnd.class) && !token_class.equals(TNotEqual.class) &&
                !token_class.equals(TEqual.class) && !token_class.equals(TGte.class) &&
                !token_class.equals(TLte.class) && !token_class.equals(TGt.class) &&
                !token_class.equals(TLt.class)) {
            valid_types.add(new Type(FlatType.FLOAT));
            if(!token_class.equals(TMod.class) && !token_class.equals(TDiv.class) &&
                    !token_class.equals(TMult.class)) {
                valid_types.add(new Type(FlatType.STRING));
                if(!token_class.equals(TPlus.class) & !token_class.equals(TMinus.class)) {
                    return null;
                }
            }
        }
        return valid_types;
    }
    
    @Override
    public String toString() {
        return EnumReporter.report(token_class);
    }
}
