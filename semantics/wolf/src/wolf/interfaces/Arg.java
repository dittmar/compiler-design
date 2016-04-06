package wolf.interfaces;

import wolf.Type;

/**
 * Interface for an Arg in WOLF
 * @author William Ezekiel
 * @author Kevin Dittmar
 * @author Joseph Alacqua
 * @version Apr 3, 2016
 */
public interface Arg {
    public Type accept(Visitor v);
}
