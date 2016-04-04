package wolf.interfaces;

/**
 * Interface for list arguments in WOLF
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @author Joseph Alacqua
 * @version Apr 3, 2016
 */
public interface ListArgument {
    void accept(Visitor v);
}
