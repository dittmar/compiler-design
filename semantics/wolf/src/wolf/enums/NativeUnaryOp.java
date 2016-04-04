package wolf.enums;

import wolf.interfaces.Visitor;
import wolf.node.TFlatten;
import wolf.node.THead;
import wolf.node.TIdentity;
import wolf.node.TLength;
import wolf.node.TLogicalNot;
import wolf.node.TNeg;
import wolf.node.TPrint;
import wolf.node.TReverse;
import wolf.node.TTail;

/**
 * Enumerated type representing WOLF's native unary operations
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @author Joseph Alacqua
 * @version Apr 3, 2016
 */
public enum NativeUnaryOp {
    NEG(TNeg.class),
    LOGICAL_NOT(TLogicalNot.class),
    HEAD(THead.class),
    TAIL(TTail.class),
    REVERSE(TReverse.class),
    FLATTEN(TFlatten.class),
    IDENTITY(TIdentity.class),
    PRINT(TPrint.class),
    LENGTH(TLength.class);
    
    Class token_class;
    
    /**
     * Create a NativeUnaryOp
     * @param token_class the name of the token representing the
     *      native unary operation
     */
    NativeUnaryOp(Class token_class) {
        this.token_class = token_class;
    }
    
    /**
     * Accept a visitor
     * @param v a visitor
     */
    public void accept(Visitor v) {
        // do nothing
    }
}
