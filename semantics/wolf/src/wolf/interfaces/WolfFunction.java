package wolf.interfaces;

/**
 *
 * @author Kevin Dittmar
 * @version Apr 3, 2016
 */
public interface WolfFunction extends Arg, ListArgument {
    public void accept(Visitor v);
}
