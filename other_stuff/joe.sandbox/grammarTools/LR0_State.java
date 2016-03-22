package grammarTools;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class LR0_State {
	private static int counter = 1;
	
	private final int id;
	private Set<LR0_Item> state;
	private final Grammar grammar;
	
	public static Set<LR0_Item> closure(Grammar grammar, LR0_Item item) {
		
//		System.out.println("Entering closure() with item " + item); //debug
		
		Production itemProd = item.getProduction();
		
		if (!grammar.getGrammarRulesAsSet().contains(itemProd))
			throw new IllegalArgumentException("LR0_Item.closure(): item production must appear in grammar");
		else {
			Set<LR0_Item> stateSet = new LinkedHashSet<LR0_Item>();
			stateSet.add(item);
			List<Symbol> notSeen = item.getNotSeen();
			
//			System.out.println("closure: notSeen = " + notSeen); //debug
			
			if (!notSeen.isEmpty()) {
				Symbol nextNotSeen = notSeen.get(0);
				
//				System.out.println("closure: nextNotSeen = " + nextNotSeen); //debug
				
				if (nextNotSeen instanceof Nonterminal) {
					Nonterminal nt = (Nonterminal) nextNotSeen;
					
//					System.out.println(grammar.getAlternativesAsSet(nt)); //debug
					
					for(Production p : grammar.getAlternativesAsSet(nt)) {
						
//						System.out.println("closure: this alternative is " + p); //debug
						
						stateSet.addAll(closure(grammar, new LR0_Item(p, 0)));
					}
				}
			}
			return Collections.unmodifiableSet(stateSet);
		}
	}
	
	public LR0_State(Grammar grammar, LR0_Item... items) {
		
//		System.out.println("Creating state with grammar: " + grammar); //debug
		
		if (items.length <= 0)
			throw new IllegalArgumentException("LR0_State: Cannot create state with empty list of items.");
		else {
			this.grammar = grammar;
			state = new LinkedHashSet<LR0_Item>();
			for(LR0_Item item : items) state.addAll(closure(grammar, item));
			id = counter;
			counter = counter + 1;
		}
	}
	
	public int getId() { return id; }
	
	public Set<LR0_Item> getState() { return new LinkedHashSet<LR0_Item>(state); }
	
	public Grammar getGrammar() { return new Grammar(grammar); }
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		else if (!(o instanceof LR0_State)) return false;
		else {
			LR0_State s = (LR0_State) o;
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
		for (LR0_Item i : state) result = result + "| " + i + System.lineSeparator();
		return result;
	}
	
	
	public static void main(String[] args) {
		Nonterminal S = new Nonterminal("S");
		Nonterminal A = new Nonterminal("A");
		Nonterminal B = new Nonterminal("B");
		Terminal a = new Terminal("a");
		Terminal b = new Terminal("b");

		Product empty = new Product();
		Product s1 = new Product(A, B, b);
		Product s2 = new Product(a);
		Product s3 = new Product(b);
		
		Production p1 = new Production(S, s1);
		Production p2 = new Production(A, s2);
		Production p3 = new Production(A, empty);
		Production p4 = new Production(B, s3);
		Production p5 = new Production(B, empty);
		Production p1_prime = new Production(S, s1);
		Production p3_prime = new Production(A, empty);
		
		Grammar g1 = new Grammar(p1, p2, p3, p4, p5, p1_prime, p3_prime);
		Grammar g2 = new Grammar(p1, p2, p3, p4, p5, p1_prime, p3_prime);
		Grammar g3 = new Grammar(p1, p2, p4, p5, p1_prime, p3_prime);
		Grammar g4 = new Grammar(p1, p2, p4, p5, p1_prime);
		
		LR0_State state = new LR0_State(g1, new LR0_Item(p1, 0));
		
		System.out.println(state);
		
	}
	
}
