import java.util.Map;
import java.util.HashMap;

public class SymbolTable {
  Map <String, Binding> symbol_table;

  public SymbolTable() {
    symbol_table = new HashMap<>();
  }

  public SymbolTable put(Identifier i, Binding b) {
    if (!symbol_table.containsKey(i.getName())) {
      symbol_table.put(i.getName(), b);
      return this;
    }
    System.err.println(i.getName() + " already defined in this context.");
    return null;
  }
}
