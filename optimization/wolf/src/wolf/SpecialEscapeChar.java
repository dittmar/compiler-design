package wolf;

import wolf.interfaces.EscapeChar;
import wolf.interfaces.Visitor;

/**
 *
 * @author Kevin Dittmar
 * @version Apr 25, 2016
 */
public class SpecialEscapeChar implements EscapeChar {

    String escape_char_text;
    
    public SpecialEscapeChar(String escape_char_text) {
        this.escape_char_text = escape_char_text;
    }
    
    @Override
    public String toString() {
        return escape_char_text;
    }
}
