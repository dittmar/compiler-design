package wolf.enums;

import wolf.interfaces.EscapeChar;
import wolf.interfaces.Visitor;
import wolf.node.TEscapeAlarm;
import wolf.node.TEscapeBackslash;
import wolf.node.TEscapeBackspace;
import wolf.node.TEscapeCarriageReturn;
import wolf.node.TEscapeDoubleQuote;
import wolf.node.TEscapeFormfeed;
import wolf.node.TEscapeNewline;
import wolf.node.TEscapeQuestionMark;
import wolf.node.TEscapeSingleQuote;
import wolf.node.TEscapeTab;
import wolf.node.TEscapeVerticalTab;

/**
 *
 * @author Kevin Dittmar
 * @version Apr 25, 2016
 */
public enum StandardEscapeChar implements EscapeChar{
    ESCAPE_ALARM(TEscapeAlarm.class, "\\a"),
    ESCAPE_BACKSPACE(TEscapeBackspace.class, "\\b"),
    ESCAPE_FORMFEED(TEscapeFormfeed.class, "\\f"),
    ESCAPE_CARRIAGE_RETURN(TEscapeCarriageReturn.class, "\\r"),
    ESCAPE_NEWLINE(TEscapeNewline.class, "\\n"),
    ESCAPE_TAB(TEscapeTab.class, "\\t"),
    ESCAPE_VERTICAL_TAB(TEscapeVerticalTab.class, "\\v"),
    ESCAPE_BACKSLASH(TEscapeBackslash.class, "\\\\"),
    ESCAPE_SINGLE_QUOTE(TEscapeSingleQuote.class, "\\'"),
    ESCAPE_DOUBLE_QUOTE(TEscapeDoubleQuote.class, "\\\""),
    ESCAPE_QUESTION_MARK(TEscapeQuestionMark.class, "\\?");
    
    private final Class token_class;
    private final String text;
    /**
     * Create an EscapeChar
     * @param token_class the token class of the escape character
     *      to be represented.
     */
    StandardEscapeChar(Class token_class, String text) {
        this.token_class = token_class;
        this.text = text;
    }
    
    /**
     * Accept a visitor
     * @param v a visitor
     * @return the result of visiting a StandardEscapeChar
     */
    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
    
    @Override
    public String toString() {
        return text;
    }
}
