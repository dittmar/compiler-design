/**
 * The visitor interface. Visitors define an operation to separate a class
 * from said operation.
 * @author (Kevin Dittmar)
 * @author (William Ezekiel)
 * @author (Joseph Alacqua)
 * @version Mar 29 2016
 */
public interface Visitor {
  // visit a Sum
  Object visit(Sum n);
  // visit a Difference
  Object visit(Difference n);
  // visit a Constant
  Object visit(Constant n);
  // visit a Product
  Object visit(Product n);
  // visit a Quotient
  Object visit(Quotient n);
  // visit a Mod
  Object visit(Mod n);
  // visit a Variable
  Object visit(Variable n);
  // visit an Assign
  Object visit(Assign n);
}
