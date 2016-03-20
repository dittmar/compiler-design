package parse_table_generator;

/**
 * Special Terminal used as a look-ahead symbol.
 * @author William Ezekiel
 * @version Mar 20, 2016
 */
public class AnySymbol extends Terminal {
    public AnySymbol() {
        super("?");
    }
    
    @Override
    public boolean equals(Object o) {
        return o instanceof AnySymbol;
    }

    @Override
    public String verboseToString() {
        return "AnySymbol[name=\"" + this.getName() + "\"]";
    }
}
