package wolf.parser;

/**
 * A grammar rule.
 * @author Joseph Alacqua
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @version Mar 14, 2016
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Rule {

    Nonterminal lhs;
    List<Symbol> rhs;
    
    /**
     * Create a rule
     * @param lhs a nonterminal left hand side.
     * @param symbols a list of symbols representing the right hand side.
     */
    public Rule(Nonterminal lhs, List<Symbol> symbols) {
        rhs = new ArrayList<>(symbols);
        this.lhs = lhs;
    }

    /**
     * @return the left hand side of this rule.
     */
    public Nonterminal getLhs() {
        return lhs;
    }

    /**
     * @return the right hand side of the rule.
     */
    public List<Symbol> getRhs() {
        return rhs;
    }
    
    /**
     * @param index an integer index
     * @return get the symbol on the right hand side with the given index.
     */
    public Symbol getSymbolOnRight(int index) {
        return rhs.get(index);
    }

    /**
     * Check if this rule equals another object. Two rules are equal if their
     * left and right hand sides are equal.
     * @param o an object.
     * @return true if this rule equals the given object.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof Rule)) {
            return false;
        } else {
            Rule r = (Rule) o;
            return r.lhs.equals(lhs)
                    && r.rhs.equals(rhs);
        }
    }

    /**
     * @return the integer hash code.
     */
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + lhs.hashCode();
        for(Symbol s: rhs) {
            result *= 31;
            result += s.hashCode();
        }
        return result;
    }

    /**
     * @return the string version of this rule.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(lhs)
          .append(" -> ");
        sb.append(getRhsString());
        
        return sb.toString();
    }
    
    /**
     * @return the string representation of the right hand side of this rule.
     */
    public String getRhsString() {
        StringBuilder sb = new StringBuilder();
        for (Symbol s : rhs)
        {
            sb.append(s.getName())
              .append(" ");
        }
        return sb.toString().trim();
    }
}
