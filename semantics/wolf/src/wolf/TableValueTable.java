package wolf;

/**
 *
 * @author Kevin Dittmar
 * @version Apr 3, 2016
 */
public class TableValueTable implements TableValue {
    SymbolTable table;
    
    public TableValueTable(SymbolTable table) {
        this.table = table;
    }
    
    @Override
    public String toString() {
        return table.toString();
    }
}
