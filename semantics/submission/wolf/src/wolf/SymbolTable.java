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
  int index;
  
  /**
   * Create a symbol table
   */
  public SymbolTable(int index) {
    symbol_table = new HashMap();
    this.index = index;
  }
  
  public SymbolTable(SymbolTable parent_table, int index) {
    this(index);
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
  
  public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append(paddedTableString("-----Table " + index + "-----"))
        .append("\n")
        .append(paddedTableString("Identifier"))
        .append(paddedTableString("Binding"))
        .append(paddedTableString("Table"))
        .append("\n");
      for(String key: symbol_table.keySet()) {
          Binding b = symbol_table.get(key);
          Identifier id = b.identifier;
          Type type = b.table_value.type;
          SymbolTable table = b.table_value.table;
          sb.append(paddedTableString(id.toString()))
            .append(paddedTableString(type.toString()));
          if (table != null) {
              sb.append(paddedTableString("Table " + table.index));
          }
          sb.append("\n");
      }
      return sb.toString();
  }
  
      /**
     * @param string a string
     * @return a padded string for the string representation of this parse table.
     */
    private String paddedTableString(String string) {
        int left_num_spaces =
            (32 - string.length()) / 2 + 1;
        int right_num_spaces = left_num_spaces + (string.length() % 2);
        return String.format(
            "%" + left_num_spaces + "s" +   // left spaces
            "%s" +                          // table contents
            "%" + right_num_spaces + "s",   // right spaces
            " ", string, " "
        );
    }
}
