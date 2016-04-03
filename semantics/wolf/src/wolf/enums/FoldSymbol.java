package wolf.enums;

import wolf.node.TFoldl;
import wolf.node.TFoldr;

/**
 *
 * @author Kevin Dittmar
 * @version Apr 3, 2016
 */
public enum FoldSymbol {
    FOLD_LEFT (TFoldl.class), 
    FOLD_RIGHT (TFoldr.class);
    
    final Class token_class;
    FoldSymbol(Class token_class) {
        this.token_class = token_class;
    }
}
