package parse_table_generator;

/**
 *
 * @author Joseph Alacqua
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @version Mar 14, 2016
 */
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
public class State {

    Set<Item> items;
    int id;
    
    public State(Set<Item> itemSet) {
        items = itemSet;
    }
    
    public State(Set<Item> itemSet, int id) {
        items = itemSet;
        this.id = id;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof State) {
            return ((State) obj).items.equals(items);
        }
        return false;
    }
    
    public String toString() {
        return items.toString();
    }
    
        @Override
    public int hashCode() {
        int result = 17;
        for(Item item : items) {
            result *= 31; 
            result += item.hashCode();
        }
        return result;
    }

    

    /*private static int counter = 1;
    private Set<Item> state;
    private final Grammar grammar;

    public static Set<Item> closure(Grammar grammar, Item item)
    {

//      System.out.println("Entering closure() with item " + item); //debug
        Rule rule = item.getRule();

        if (!grammar.getGrammarRulesAsSet().contains(rule)) {
            throw new IllegalArgumentException("Item.closure(): item production must appear in grammar");
        } else {
            Set<Item> stateSet = new LinkedHashSet<>();
            stateSet.add(item);
            
            List<Symbol> rhs = item.getRule().getRhs();
            if (item.getPosition() < rhs.size())
            {
                Symbol next_not_seen = rhs.get(item.getPosition());
                if (next_not_seen instanceof Nonterminal) {
                    Nonterminal nt = (Nonterminal) next_not_seen;
                    // System.out.println(grammar.getAlternativesAsSet(nt)); //debug
                    for (Rule r : grammar.getAlternativesAsSet(nt)) {
//			System.out.println("closure: this alternative is " + p); //debug
                        stateSet.addAll(closure(grammar, new Item(r, 0)));
                    }
                }
            }
                
            return Collections.unmodifiableSet(stateSet);
        }
    }

    public State(Grammar grammar, Item... items) {

//		System.out.println("Creating state with grammar: " + grammar); //debug
        if (items.length <= 0) {
            throw new IllegalArgumentException("State: Cannot create state with empty list of items.");
        } else {
            this.grammar = grammar;
            state = new LinkedHashSet<>();
            for (Item item : items) {
                state.addAll(closure(grammar, item));
            }
            id = counter;
            counter = counter + 1;
        }
    }

    public int getId() {
        return id;
    }

    public Set<Item> getState() {
        return new LinkedHashSet<>(state);
    }

    public Grammar getGrammar() {
        return new Grammar(grammar);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof State)) {
            return false;
        } else {
            State s = (State) o;
            return id == s.getId()
                    && state.equals(s.getState())
                    && grammar.equals(s.getGrammar());
        }
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + id;
        result = 31 * result + state.hashCode();
        result = 31 * result + grammar.hashCode();
        return result;
    }

    @Override
    public String toString() {
        String result = "| State #" + id + System.lineSeparator();
        for (Item i : state) {
            result = result + "| " + i + System.lineSeparator();
        }
        return result;
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

        Grammar g1 = new Grammar(p1, p2, p3, p4, p5, p1_prime, p3_prime);
        Grammar g2 = new Grammar(p1, p2, p3, p4, p5, p1_prime, p3_prime);
        Grammar g3 = new Grammar(p1, p2, p4, p5, p1_prime, p3_prime);
        Grammar g4 = new Grammar(p1, p2, p4, p5, p1_prime);

        State state = new State(g1, new Item(p1, 0));

        System.out.println(state);

    }*/
}
