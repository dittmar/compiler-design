package parse_table_generator;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

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
    
    public ParseTable(ArrayList<LinkedHashMap<Symbol,TableCell>> table) {
        this.table = table;
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int counter = 0;
        for(LinkedHashMap<Symbol,TableCell> row : table) {
            sb.append(++counter);
            for(Map.Entry<Symbol,TableCell> element : row.entrySet()) {
                sb.append("< ").append(element.getKey()).append(",")
                        .append(element.getValue()).append(" >").append("  |  ");
            }
            sb.append("\n");
            //for(TableCell element : row.values()) {
            //    sb.append(element);
            //}
        }
        return sb.toString();
    }
}
