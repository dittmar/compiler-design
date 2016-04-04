package wolf;

import wolf.enums.EscapeChar;
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
     */
    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
