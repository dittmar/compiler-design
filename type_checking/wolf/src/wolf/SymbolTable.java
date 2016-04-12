package wolf;
import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.List;
/**
 * A symbol table.
 * @author (Kevin Dittmar)
 * @author (William Ezekiel)
 * @author (Joseph Alacqua)
 * @version Apr 3, 2016
 */
public class SymbolTable {
  Map<String, Binding> symbol_table;
  SymbolTable parent_table;
  String table_name;
  
  /**
   * Create a symbol table
   * @param table_name it the name of the table
   */
  public SymbolTable(String table_name) {
    symbol_table = new LinkedHashMap();
    this.table_name = table_name;
  }
  
  /**
   * @param parent_table is the parent table of this symbol table
   * @param table_name is the name of this symbol table
   */
  public SymbolTable(SymbolTable parent_table, String table_name) {
    this(table_name);
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
  
  /**
   * @return string representation of a symbol table
   */
  @Override
  public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append(paddedTableString("-----Table " + table_name + "-----"))
        .append("\n")
        .append(paddedTableString("Identifier"))
        .append(paddedTableString("Binding"))
        .append(paddedTableString("Table"))
        .append("\n");
      for(String key: symbol_table.keySet()) {
          Binding b = symbol_table.get(key);
          Identifier id = b.identifier;
          Type type = b.table_value.type;
          SymbolTable sym_table = b.table_value.table;
          sb.append(paddedTableString(id.toString()))
            .append(paddedTableString(type.toString()));
          if (sym_table != null) {
              sb.append(paddedTableString("Table " + sym_table.table_name));
          }
          sb.append("\n");
      }
      return sb.toString();
  }
  
      /**
     * @param string a string
     * @return a padded string for the string representation
     * of this parse table.
     */
    private String paddedTableString(String string) {
        int left_num_spaces =
            (Math.max(32, string.length()) - string.length()) / 2 + 1;
        int right_num_spaces = left_num_spaces + (string.length() % 2);
        return String.format(
            "%" + left_num_spaces + "s" +   // left spaces
            "%s" +                          // table contents
            "%" + right_num_spaces + "s",   // right spaces
            " ", string, " "
        );
    }
    
    
    public int getNumberOfArguments() {
        int count = 0;
        for(Binding binding:symbol_table.values()) {
            if(!binding.hasTable()) {
                count++;
            }
        }
        return count;
    }
    
    public List<Type> getArgFormat() {
        List<Type> argTypes = new ArrayList();
        for(Binding binding:symbol_table.values()) {
            if(!binding.hasTable()) {
                argTypes.add(binding.table_value.type); 
            }
        }
        return argTypes;
    }
}
