package parse_table_generator;

/**
 * Special Terminal used as a look-ahead symbol.
 * @author Joseph Alacqua
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @version Mar 20, 2016
 */
public class AnySymbol extends Terminal {
    
    /**
     * Create an AnySymbol object.
     */
    public AnySymbol() {
        super("?AnySymbol?");
    }
    
    /**
     * Check if an object is an AnySymbol.
     * @param o the object being compared.
     * @return true if the object is equal to this AnySymbol.
     */
    @Override
    public boolean equals(Object o) {
        return o instanceof AnySymbol;
    }
}
