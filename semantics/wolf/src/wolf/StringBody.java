package wolf;

import wolf.interfaces.StringMiddle;
import wolf.interfaces.Visitor;
import wolf.node.TStringBody;

/**
 * The body of a string
 * @author William Ezekiel
 * @author Kevin Dittmar
 * @author Joseph Alacqua
 * @version Apr 3, 2016
 */
public class StringBody implements StringMiddle {
    TStringBody string_body;
    
    public StringBody(TStringBody string_body) {
        this.string_body = string_body;
    }
    
    /**
     * Accept a visitor
     * @param v a visitor
     */
    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
