package wolf.enums;

import wolf.interfaces.Visitor;
import wolf.node.TFoldl;
import wolf.node.TFoldr;

/**
 * A fold symbol designated the start of either a fold left
 * or fold right operation.
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @author Joseph Alacqua
 * @version Apr 3, 2016
 */
public enum FoldSymbol {
    FOLD_LEFT (TFoldl.class), 
    FOLD_RIGHT (TFoldr.class);
    
    final Class token_class;
    
    /**
     * Create a fold symbol
     * @param token_class the token class this fold symbol represents
     */
    FoldSymbol(Class token_class) {
        this.token_class = token_class;
    }
    
    /**
     * Accept a visitor
     * @param v a visitor
     */ 
    public Object accept(Visitor v) {
        return v.visit(this);
    }
    
    @Override
    public String toString() {
        return EnumReporter.report(token_class);
    }
}
