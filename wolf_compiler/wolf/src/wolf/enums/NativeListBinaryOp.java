package wolf.enums;

import wolf.interfaces.BinOp;
import wolf.interfaces.Visitor;
import wolf.node.TAppend;
import wolf.node.TPrepend;

/**
 *
 * @author Kevin Dittmar
 * @version Apr 5, 2016
 */
public enum NativeListBinaryOp implements BinOp {
    APPEND(TAppend.class),
    PREPEND(TPrepend.class);
            
    final Class token_class;
    
    /**
     * Create a NativeListBinaryOp
     * @param token_class the class of the token being represented
     */
    NativeListBinaryOp(Class token_class) {
        this.token_class = token_class;
    }
    
    @Override
    public String toString() {
        return EnumReporter.report(token_class);
    }

    @Override
    public Object accept(Visitor v) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
