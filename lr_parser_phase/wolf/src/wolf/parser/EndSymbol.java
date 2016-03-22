package wolf.parser;

/**
 * A special terminal symbol representing the EndSymbol (usually $) 
 * of a language.
 * @author Joseph Alacqua
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @version Mar 19, 2016
 */
public class EndSymbol extends Terminal {
    
    /**
     * Create a new EndSymbol.
     * @param name the text used for this EndSymbol.
     */
    public EndSymbol(String name) {
        super(name);
    }
    
    /**
     * Check if this EndSymbol is equal to another object. Two EndSymbols
     * are equal if they have the same name.
     * @param o the object being compared.
     * @return true if this EndSymbol is equal to the object.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof EndSymbol)) {
            return false;
        } else {
            EndSymbol es = (EndSymbol) o;
            return this.getName().equals(es.getName());
        }
    }

    /**
     * @return the hash code for EndSymbol, which is the same as a
     * Terminal's hash code function.
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
