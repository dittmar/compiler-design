package wolf.parser;

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
    public String verboseToString() {
        return "StartSymbol[name=\"" + this.getName() + "\"]";
    }
}
