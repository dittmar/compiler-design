package parsetablegenerator;

/**
 *
 * @author Joseph Alacqua
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @version Mar 14, 2016
 */
public class Nonterminal implements Symbol {
    private final String name;

    public Nonterminal(String name) {
        if (name.isEmpty() || !name.matches("[A-Z].*")) {
            throw new IllegalArgumentException("Nonterminal: Nonterminal "
                    + "names must begin with a capital letter");
        } else {
            this.name = name;
        }
    }

    @Override
    public String getName() {
        return name;
    }

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
        return "Nonterminal[name=\"" + name + "\"]";
    }

    public static void main(String[] args) {
        Nonterminal S = new Nonterminal("S");
        Nonterminal X = new Nonterminal("X");
        Nonterminal S2 = new Nonterminal("S");

        System.out.println("S = " + S + " = " + S.verboseToString());
        System.out.println("X = " + X + " = " + X.verboseToString());
        System.out.println("S2 = " + S2 + " = " + S2.verboseToString());

        System.out.println("S = X? " + S.equals(X));
        System.out.println("S = S2? " + S.equals(S2));

        assert !S.equals(X);
        assert S.equals(S2);
    }
}
