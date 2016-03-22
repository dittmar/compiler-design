package three_point_one.parser;

/**
 * A Nonterminal symbol that starts the program.
 * Should be the only symbol left on the stack if the LRParser is successful.
 * @author Joseph Alacqua
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @version Mar 14, 2016
 */
public class StartSymbol extends Nonterminal {
    /**
     * Create a start
     * @param name the name of this symbol.
     */
    public StartSymbol(String name) {
        super(name);
    }

    /**
     * Check if this StartSymbol equals another object. Two start symbols are
     * equal if they have the same name.
     * @param o an object
     * @return true if this start symbol equals the given object.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof StartSymbol)) {
            return false;
        } else {
            StartSymbol ss = (StartSymbol) o;
            return this.getName().equals(ss.getName());
        }
    }
}
