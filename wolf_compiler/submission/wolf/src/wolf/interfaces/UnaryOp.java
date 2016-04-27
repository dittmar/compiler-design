package wolf.interfaces;

/**
 * Interface for unary operations in WOLF
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @author Joseph Alacqua
 * @version Apr 3, 2016
 */
public interface UnaryOp {
    Object accept(Visitor v);
}
