package wolf;

import wolf.enums.FoldSymbol;
import wolf.interfaces.Visitor;
import wolf.interfaces.WolfFunction;

/**
 * A fold function
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @author Joseph Alacqua
 * @version Apr 3, 2016
 */
public class Fold implements WolfFunction {
    FoldSymbol fold_symbol;
    FoldBody fold_body;
    
    /**
     * Accept a visitor
     * @param v a visitor
     */
    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
