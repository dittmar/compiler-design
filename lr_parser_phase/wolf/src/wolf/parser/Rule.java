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
     * @param lhs
     * @param symbols 
     */
    public Rule(Nonterminal lhs, Symbol... symbols) {
        rhs = Arrays.asList(symbols);
        this.lhs = lhs;
    }
    
    public Rule(Nonterminal lhs, List<Symbol> symbols)
    {
        rhs = new ArrayList<>(symbols);
        this.lhs = lhs;
    }

    public Nonterminal getLhs() {
        return lhs;
    }

    public List<Symbol> getRhs() {
        return rhs;
    }
    
    public Symbol getSymbolOnRight(int index) {
        return rhs.get(index);
    }

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

    @Override
    public String toString() {
        return quietToString();
        //return verboseToString();
    }

    public String quietToString() {
        StringBuilder sb = new StringBuilder();
        sb.append(lhs)
          .append(" -> ");
        sb.append(getRhsString());
        
        return sb.toString();
    }

    public String verboseToString() {
        return "Production[source=\"" + 
                lhs + 
                "\", product=\"" + 
                getRhsString() + 
                "\"]";
    }

    public static void main(String[] args) {
        Nonterminal S = new Nonterminal("S");
        Nonterminal A = new Nonterminal("A");
        Nonterminal B = new Nonterminal("B");
        Terminal a = new Terminal("a");
        Terminal b = new Terminal("b");

        Rule p1 = new Rule(S, A, B, b);
        Rule p2 = new Rule(A, a);
        Rule p3 = new Rule(A, Symbol.Epsilon.EPSILON);
        Rule p4 = new Rule(B, b);
        Rule p5 = new Rule(B, Symbol.Epsilon.EPSILON);
        Rule p1_prime = new Rule(S, A, B, b);
        Rule p3_prime = new Rule(A, Symbol.Epsilon.EPSILON);

        System.out.println("p1 = " + p1 + " = " + p1.verboseToString());
        System.out.println("p2 = " + p2 + " = " + p2.verboseToString());
        System.out.println("p3 = " + p3 + " = " + p3.verboseToString());
        System.out.println("p4 = " + p4 + " = " + p4.verboseToString());
        System.out.println("p5 = " + p5 + " = " + p5.verboseToString());
        System.out.println("p1_prime = " + p1_prime + " = " + p1_prime.verboseToString());
        System.out.println("p3_prime = " + p3_prime + " = " + p3_prime.verboseToString());

        System.out.println("p1 = p2? " + p1.equals(p3));
        System.out.println("p1 = p1_prime? " + p1.equals(p1_prime));
        System.out.println("p3 = p4? " + p3.equals(p4));
        System.out.println("p3 = p3_prime? " + p3.equals(p3_prime));
    }
    
    public String getRhsString()
    {
        StringBuilder sb = new StringBuilder();
        for (Symbol s : rhs)
        {
            sb.append(s.getName())
              .append(" ");
        }
        return sb.toString().trim();
    }
}
