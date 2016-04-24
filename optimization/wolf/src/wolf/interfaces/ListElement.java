package wolf.interfaces;

/**
 * Interface for list elements in WOLF. List Elements are either integers, floats, strings, or
 * a function or identifier that produces one of those types
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @author Joseph Alacqua
 * @version Apr 21, 2016
 */
public interface ListElement {
  Object accept(Visitor v);
}
