package three_point_one.parser;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * A parse table. Consists or rows representing states and columns representing
 * Symbols. Used to determine actions of an LRParser.
 * @author Joseph Alacqua
 * @author Kevines Dittmar
 * @author William Ezekiel
 * @version Mar 14, 2016
 */
public class ParseTable
{
    ArrayList<LinkedHashMap<Symbol,TableCell>> table;
    Set<Symbol> allSymbols; // all symbols in the grammar
    private Map<String, Terminal> terminal_lookup_table;
    private int longest_symbol_length = 0;
    
    /**
     * Create a parse table
     * @param table the list of maps representing the table.
     * @param symbols a set of symbols
     * @param terminal_lookup_table the terminal lookup table used by the 
     *      LRParser.
     */
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
    
    /**
     * @return the terminal lookup table.
     */
    public Map<String, Terminal> getTerminalLookupTable()
    {
        return terminal_lookup_table;
    }
    
    /**
     * @return the string representation of this parse table.
     */
    @Override
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
    
    /**
     * @param symbol a symbol.
     * @param state_id an id. 
     * Note: state_id - 1 is used because the list starts at 0, but state
     *       numbering starts at 1.
     * @return the table cell for the given state at the given symbol.
     */
    public TableCell getTableCellAt(Symbol symbol, int state_id) {
        return table.get(state_id-1).get(symbol);
    }
    
    /**
     * @param string a string
     * @return a padded string for the string representation of this parse table.
     */
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
