package wolf.enums;

import wolf.interfaces.Visitor;
import wolf.node.TEscapeAlarm;
import wolf.node.TEscapeBackslash;
import wolf.node.TEscapeBackspace;
import wolf.node.TEscapeCarriageReturn;
import wolf.node.TEscapeDefault;
import wolf.node.TEscapeDoubleQuote;
import wolf.node.TEscapeFormfeed;
import wolf.node.TEscapeHexChar;
import wolf.node.TEscapeNewline;
import wolf.node.TEscapeOctalChar;
import wolf.node.TEscapeQuestionMark;
import wolf.node.TEscapeSingleQuote;
import wolf.node.TEscapeTab;
import wolf.node.TEscapeUnicodeChar;
import wolf.node.TEscapeVerticalTab;

/**
 *
 * @author Kevin Dittmar
 * @version Apr 3, 2016
 */
public enum EscapeChar {
    ESCAPE_ALARM(TEscapeAlarm.class),
    ESCAPE_BACKSPACE(TEscapeBackspace.class),
    ESCAPE_FORMFEED(TEscapeFormfeed.class),
    ESCAPE_CARRIAGE_RETURN(TEscapeCarriageReturn.class),
    ESCAPE_NEWLINE(TEscapeNewline.class),
    ESCAPE_TAB(TEscapeTab.class),
    ESCAPE_VERTICAL_TAB(TEscapeVerticalTab.class),
    ESCAPE_BACKSLASH(TEscapeBackslash.class),
    ESCAPE_SINGLE_QUOTE(TEscapeSingleQuote.class),
    ESCAPE_DOUBLE_QUOTE(TEscapeDoubleQuote.class),
    ESCAPE_QUESTION_MARK(TEscapeQuestionMark.class),
    ESCAPE_OCTAL_CHAR(TEscapeOctalChar.class),
    ESCAPE_HEX_CHAR(TEscapeHexChar.class),
    ESCAPE_UNICODE_CHAR(TEscapeUnicodeChar.class),
    ESCAPE_DEFAULT(TEscapeDefault.class);
    
    Class token_class;
    
    EscapeChar(Class token_class) {
        this.token_class = token_class;
    }
    
    public void accept(Visitor v) {
        //magic
    }
}
