package wolf;

import wolf.interfaces.EscapeChar;
import wolf.interfaces.StringMiddle;
import wolf.interfaces.Visitor;

/**
 * A string escape sequence, used for escape characters in strings
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @author Joseph Alacqua
 * @version Apr 3, 2016
 */
public class StringEscapeSeq implements StringMiddle {
    EscapeChar escape_char;
    
    /**
     * Accept a visitor
     * @param v a visitor
     * @return the result of the visit
     */
    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
    
    @Override
    public String toString() {
        return "\\" + escape_char.toString();
    }
}
