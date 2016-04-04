package wolf;
import wolf.node.*;
import java.util.Map;
import java.util.HashMap;
/**
 * A symbol table.
 * @author Kevin Dittmar
 * @author William Ezekiel
 */
public class SymbolTable {
  Map<String, Binding> symbol_table;
  SymbolTable parent_table;
  
  /**
   * Create a symbol table
   */
  public SymbolTable() {
    symbol_table = new HashMap();
  }
  
  public SymbolTable(SymbolTable parent_table) {
    this();
    this.parent_table = parent_table;
  }

  /**
   * Put an item into the symbol table.
   * @param i an identifier
   * @param b a binding
   * @return the updated symbol table or null if the identifier already exists
   *    in symbol table.
   */
  public SymbolTable put(Identifier i, Binding b) {
    if(!symbol_table.containsKey(i.identifier.getText())) {
      symbol_table.put(i.identifier.getText(),b);
      return this;
    }
    System.err.println("Identifier " + i + " already defined in this context");
    return null;
  }
  
  public Binding lookup(Identifier i) {
      return symbol_table.get(i.identifier.getText());
  }
}
