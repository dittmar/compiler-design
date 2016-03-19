package parse_table_generator;

import java.util.ArrayList;
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
    
    public ParseTable(ArrayList<LinkedHashMap<Symbol,TableCell>> table,
            Set<Symbol> symbols) {
        this.table = table;
        allSymbols = symbols;
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int counter = 0;
        sb.append("\t");
        for(Symbol s : allSymbols) {
            sb.append("|\t").append(s).append("\t|");
        }
        sb.append("\n");
        for(LinkedHashMap<Symbol,TableCell> row : table) {
            sb.append(++counter).append("\t");
            for(Symbol s: allSymbols) {
                TableCell tc = row.get(s);
                if(tc == null) {
                    sb.append("|\t  \t|");
                }
                else {
                    sb.append("|\t").append(tc).append("\t|");
                }

            }  
            sb.append("\n");
        }
        return sb.toString();
    }
}
