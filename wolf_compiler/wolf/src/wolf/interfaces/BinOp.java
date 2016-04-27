package wolf.interfaces;

/**
 * Interface for a binary operation in WOLF
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @author JosephAalacqua
 * @version Apr 3, 2016
 */
public interface BinOp {
    Object accept(Visitor v);
}
