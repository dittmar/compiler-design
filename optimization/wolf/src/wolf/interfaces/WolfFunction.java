package wolf.interfaces;

import wolf.Type;

/**
 * An interface for each type of function in WOLF
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @author Joseph Alacqua
 * @version Apr 3, 2016
 */
public interface WolfFunction extends Arg, ListArgument, ListElement {
    @Override
    Object accept(Visitor v);
}
