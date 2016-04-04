package wolf;

import wolf.enums.EscapeChar;
import wolf.interfaces.StringMiddle;
import wolf.interfaces.Visitor;

/**
 *
 * @author Kevin Dittmar
 * @version Apr 3, 2016
 */
public class StringEscapeSeq implements StringMiddle {
    EscapeChar escape_char;
    
    public void accept(Visitor v) {
        v.visit(this);
    }
}
