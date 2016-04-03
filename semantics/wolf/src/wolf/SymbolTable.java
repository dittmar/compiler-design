package wolf;
import wolf.node.*;
import java.util.Map;
import java.util.HashMap;
/**
 * A symbol table...more on this later.
 * @author sdb
 */
public class SymbolTable {
  Map<String,Binding> symTab;

  /**
   * Create a symbol table
   */
  public SymbolTable() {
    symTab = new HashMap();
  }

  /**
   * Put an item into the symbol table.
   * @param i an identifier
   * @param b a binding
   * @return the updated symbol table or null if the identifier already exists
   *    in symbol table.
   */
  public SymbolTable put(TIdentifier i, Binding b) {
    if(!symTab.containsKey(i.getText())) {
      symTab.put(i.getText(),b);
      return this;
    }
    System.err.println("Identifier " + i + " already defined in this context");
    return null;
  }
}
