package wolf;

import wolf.enums.FoldSymbol;
import wolf.interfaces.Visitor;
import wolf.interfaces.WolfFunction;

/**
 *
 * @author Kevin Dittmar
 * @version Apr 3, 2016
 */
public class Fold implements WolfFunction {
    FoldSymbol fold_symbol;
    FoldBody fold_body;
    
    public void accept(Visitor v) {
        v.visit(this);
    }
}
