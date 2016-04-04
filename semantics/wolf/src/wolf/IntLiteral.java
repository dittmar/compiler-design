package wolf;

import wolf.interfaces.Arg;
import wolf.interfaces.Visitor;
import wolf.node.TIntNumber;

/**
 * An integer literal, represents an integer
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @author Joseph Alacqua
 * @version Apr 3, 2016
 */
public class IntLiteral implements Arg {
    TIntNumber int_literal;
    
    /**
     * Accepts a visitor
     * @param v a visitor
     */
    @Override
    public void accept(Visitor v) {
        
    }
}
