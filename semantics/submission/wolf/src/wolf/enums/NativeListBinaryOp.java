package wolf.enums;

import wolf.node.TAppend;
import wolf.node.TPrepend;

/**
 *
 * @author Kevin Dittmar
 * @version Apr 5, 2016
 */
public enum NativeListBinaryOp {
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
}
