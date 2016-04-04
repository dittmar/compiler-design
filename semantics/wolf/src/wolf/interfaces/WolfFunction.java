package wolf.interfaces;

/**
 * An interface for each type of function in WOLF
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @author Joseph Alacqua
 * @version Apr 3, 2016
 */
public interface WolfFunction extends Arg, ListArgument {
    @Override
    public Object accept(Visitor v);
}
