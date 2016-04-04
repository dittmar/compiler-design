package wolf;

import wolf.interfaces.TableValue;

/**
 * A table value containing a symbol table.
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @author Joseph Alacqua
 * @version Apr 3, 2016
 */
public class TableValueTable implements TableValue {
    SymbolTable table;
    
    /**
     * Create a TableValueTable
     * @param table a symbol table
     */
    public TableValueTable(SymbolTable table) {
        this.table = table;
    }
    
    /**
     * @return a string representation of this TableValueTable
     */
    @Override
    public String toString() {
        return table.toString();
    }
}
