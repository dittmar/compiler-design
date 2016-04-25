package wolf.interfaces;

import wolf.interfaces.StringMiddle;
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
 * Enumerated type representing different escape characters
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @author Joseph Alacqua
 * @version Apr 3, 2016
 */
public interface EscapeChar {
    @Override
    public String toString();
}
