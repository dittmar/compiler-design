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
public class Fold extends WolfFunction {
    FoldSymbol fold_symbol;
    FoldBody fold_body;
    
    public Fold(FoldSymbol fold_symbol, FoldBody fold_body) {
        this.fold_symbol = fold_symbol;
        this.fold_body = fold_body;
    }
    
    /**
     * Accept a visitor
     * @param v a visitor
     * @return the return type of the fold
     */
    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
