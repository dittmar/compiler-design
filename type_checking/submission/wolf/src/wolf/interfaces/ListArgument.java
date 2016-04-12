package wolf.interfaces;

import wolf.Type;

/**
 * Interface for list arguments in WOLF. List Arguments are either a list
 * or a function that returns a list.
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @author Joseph Alacqua
 * @version Apr 3, 2016
 */
public interface ListArgument {
    Type accept(Visitor v);
}
