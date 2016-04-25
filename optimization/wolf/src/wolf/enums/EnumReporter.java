package wolf.enums;

import java.lang.reflect.Constructor;
import wolf.node.Token;

/**
 *
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @author Joe Alacqua
 * @version Apr 10, 2016
 */
public final class EnumReporter {    
    public static String report(Class token_class) {
        try {
                return ((Token)token_class.newInstance()).getText();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalArgumentException("Bad class: " + token_class);
        }
    }
}
