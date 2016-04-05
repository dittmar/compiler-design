/**
 * An expression. Superclass for several arithmetic expressions. Expressions
 * consist of a left and right side which are also expressions.
 * @author (Kevin Dittmar)
 * @author (William Ezekiel)
 * @author (Joseph Alacqua)
 * @version Mar 29 2016
 */
public abstract class Exp {
  Exp left;
  Exp right;

  public abstract Object accept(Visitor v);
}
