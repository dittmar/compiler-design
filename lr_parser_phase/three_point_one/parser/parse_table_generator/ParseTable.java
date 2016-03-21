package parse_table_generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Joseph Alacqua
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @version Mar 14, 2016
 */
public class ParseTable
{
    ArrayList<LinkedHashMap<Symbol,TableCell>> table;
    Set<Symbol> allSymbols; // all symbols in the grammar
    private Map<String, Terminal> terminal_lookup_table;
    private int longest_symbol_length = 0;
    
    public ParseTable(ArrayList<LinkedHashMap<Symbol, TableCell>> table,
        Set<Symbol> symbols, Map<String, Terminal> terminal_lookup_table)
    {
        this.table = table;
        allSymbols = symbols;
        this.terminal_lookup_table = terminal_lookup_table;
        
        for (Symbol s : allSymbols)
        {
            if (s.getName().length() > longest_symbol_length)
            {
                longest_symbol_length = s.getName().length();
            }
        }
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int counter = 0;
        sb.append("\t");
        // Padding method found here:
        /* http://stackoverflow.com/questions/15635273/\
           can-we-use-string-format-to-pad-prefix-with-a-\
           character-with-desired-length
         */
        for(Symbol s : allSymbols) {
            sb.append("|")
              .append(paddedTableString(s.getName()))
              .append("|");
        }
        sb.append("\n");
        for(LinkedHashMap<Symbol,TableCell> row : table) {
            sb.append(++counter).append("\t");
            for(Symbol s: allSymbols) {
                TableCell tc = row.get(s);
                if(tc == null) {
                    sb.append("|")
                      .append(paddedTableString(" "))
                      .append("|");
                }
                else {
                    sb.append("|")
                      .append(paddedTableString(tc.toString()))
                      .append("|");
                }

            }  
            sb.append("\n");
        }
        return sb.toString();
    }
    
    private String paddedTableString(String string)
    {
        int left_num_spaces =
            (longest_symbol_length - string.length()) / 2 + 1;
        int right_num_spaces = left_num_spaces + (string.length() % 2);
        return String.format(
            "%" + left_num_spaces + "s" +   // left spaces
            "%s" +                          // table contents
            "%" + right_num_spaces + "s",   // right spaces
            " ", string, " "
        );
    }
}
