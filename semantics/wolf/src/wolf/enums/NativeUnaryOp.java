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
 *
 * @author Kevin Dittmar
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
    
    NativeUnaryOp(Class token_class) {
        this.token_class = token_class;
    }
    
    public void accept(Visitor v) {
        // do nothing
    }
}
