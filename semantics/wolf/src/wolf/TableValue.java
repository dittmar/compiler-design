package wolf;

/**
 * An interface used for Symbol Tables. May hold either a 
 * type or another symbol table.
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @author Joseph Alacqua
 * @version Apr 3, 2016
 */
public class TableValue {
    SymbolTable table;
    Type type;
    
    /**
     * Create a TableValueTable
     * @param type a type for the identifier associated with this TableValue
     * @param table a symbol table
     */
    public TableValue(Type type, SymbolTable table) {
        this.type = type;
        this.table = table;
    }
    
    /**
     * Create a TableValueTable
     * @param type a type for the identifier associated with this TableValue
     */
    public TableValue(Type type) {
        this.type = type;
    }
    /**
     * @return a string representation of this TableValueTable
     */
    @Override
    public String toString() {
        return table.toString();
    }
}
