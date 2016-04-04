package wolf.interfaces;

/**
 * Interface for an Arg in WOLF
 * @author William Ezekiel
 * @author Kevin Dittmar
 * @author Joseph Alacqua
 * @version Apr 3, 2016
 */
public interface Arg {
    public void accept(Visitor v);
}
