package parse_table_generator;

/**
 *
 * @author Kevin Dittmar
 * @version Mar 19, 2016
 */
public class StartSymbol extends Nonterminal {
    public StartSymbol(String name) {
        super(name);
    }

    @Override
    public String getName() {
        return super.getName();
    }

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

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + this.getName().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return quietToString();
        //return verboseToString();
    }

    public String quietToString() {
        return this.getName();
    }

    public String verboseToString() {
        return "StartSymbol[name=\"" + this.getName() + "\"]";
    }
}
