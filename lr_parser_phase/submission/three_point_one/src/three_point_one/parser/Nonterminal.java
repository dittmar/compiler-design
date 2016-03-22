package three_point_one.parser;

/**
 * A nonterminal is a symbol in the grammar that is used to generate other 
 * symbols in the grammar.
 * @author Joseph Alacqua
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @version Mar 14, 2016
 */
public class Nonterminal implements Symbol {
    private final String name;

    /**
     * Create a nonterminal
     * @param name the name of this nonterminal.
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
     * @return the name of this terminal
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Check if this nonterminal equals another object. Two nonterminals are
     * equal if they have the same name.
     * @param o an object.
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
     * @return the integer hash code.
     */
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + name.hashCode();
        return result;
    }

    /**
     * @return the string representation of this nonterminal
     */
    @Override
    public String toString() {
        return name;
    }
}
