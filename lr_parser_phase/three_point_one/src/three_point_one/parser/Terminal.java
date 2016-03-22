package three_point_one.parser;

/**
 * A terminal is a symbol in the language.
 * @author Joseph Alacqua
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @version Mar 14, 2016
 */
public class Terminal implements Symbol {

    private final String name;

    public Terminal(String name) {
        name = name.replaceAll("\\s", "");
        if (name.isEmpty() || name.matches("[A-Z].*")) {
            throw new IllegalArgumentException(
                "Terminal: Terminal names cannot begin with a capital letter");
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
     * Check if this terminal is equal to another object. Two terminals are 
     * equal if they have equal names.
     * @param o an object
     * @return true if this terminal equals the given object.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof Terminal)) {
            return false;
        } else {
            Terminal nt = (Terminal) o;
            return this.name.equals(nt.getName());
        }
    }

    /**
     * @return the integer hash code of this terminl.
     */
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + name.hashCode();
        return result;
    }

    /**
     * @return the string representation of this terminal
     */
    @Override
    public String toString() {
        return name;
    }
}
