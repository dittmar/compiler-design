package wolf;

import wolf.interfaces.Arg;
import wolf.interfaces.ListElement;
import wolf.interfaces.Visitor;
import wolf.node.TFloatNumber;

/**
 * A float literal, represents a float.
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @author Joseph Alacqua
 * @version Apr 3, 2016
 */
public class FloatLiteral implements Arg, ListElement {
    TFloatNumber float_literal;
    
    public FloatLiteral(TFloatNumber float_literal) {
        this.float_literal = float_literal;
    }
    /**
     * Accepts a visitor
     * @param v a visitor
     * @return the type of this FloatLiteral, FLOAT
     */
    @Override
    public Object accept(Visitor v) {
        return v.visit(this); //return new Type(FlatType.FLOAT);
    }
}
