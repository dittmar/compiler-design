package parse_table_generator;

/**
 *
 * @author Kevin Dittmar
 * @version Mar 19, 2016
 */
public class EndSymbol implements Symbol {
    private final String name;

    public EndSymbol(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof EndSymbol)) {
            return false;
        } else {
            EndSymbol es = (EndSymbol) o;
            return this.name.equals(es.getName());
        }
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return quietToString();
        //return verboseToString();
    }

    public String quietToString() {
        return name;
    }

    public String verboseToString() {
        return "EndSymbol[name=\"" + name + "\"]";
    }
}
