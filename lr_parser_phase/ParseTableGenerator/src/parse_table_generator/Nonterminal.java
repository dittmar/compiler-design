package parse_table_generator;

/**
 * A Nonterminal
 * @author Joseph Alacqua
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @version Mar 14, 2016
 */
public class Nonterminal implements Symbol {
    private final String name;

    /**
     * Create a nonterminal. 
     * @param name the text for the nonterminal
     * @throws IllegalArgumentException
     */
    public Nonterminal(String name) {
        if (name.isEmpty() || !name.matches("[A-Z].*")) {
            throw new IllegalArgumentException("Nonterminal: Nonterminal "
                    + "names must begin with a capital letter");
        } else {
            this.name = name;
        }
    }

    /**
     * @return the name of this Nonterminal
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Check if this nonterminal is equal to the given object. Two nonterminals
     * are equal if they have the same name. 
     * @param o the object to compare.
     * @return true if this nonterminal equals the given object.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof Nonterminal)) {
            return false;
        } else {
            Nonterminal nt = (Nonterminal) o;
            return this.name.equals(nt.getName());
        }
    }

    /**
     * @return the hash code of this nonterminal.
     */
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + name.hashCode();
        return result;
    }

    /**
     * @return the string representation of this nonterminal.
     */
    @Override
    public String toString() {
        return name;
    }
}
