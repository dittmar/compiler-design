package grammarTools;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class Grammar {
	private final Nonterminal startSymbol;
	private Set<Production> grammarRulesSet;
	private Map<Integer, Production> grammarRulesMap;
	private Map<Nonterminal, Set<Integer>> sourceIndex;
	private int numberOfProductions = 0;
	
	public Grammar(Nonterminal startSymbol, Production... productions) {
		if (productions.length <= 0)
			throw new IllegalArgumentException("Grammar: set of productions cannot be empty");
		else if (startSymbol == null)
			throw new IllegalArgumentException("Grammar: startSymbol cannot be null");
		else {
			boolean foundStartSymbol = false;
			for (Production p : productions) if (startSymbol.equals(p.getSource())) { foundStartSymbol = true; break; };
			if (!foundStartSymbol)
				throw new IllegalArgumentException("Grammar: Start symbol must appear on lhs of a production in Grammar.");
			else {
				this.startSymbol = startSymbol;
				grammarRulesSet = new LinkedHashSet<Production>();
				grammarRulesMap = new LinkedHashMap<Integer, Production>();
				sourceIndex = new LinkedHashMap<Nonterminal, Set<Integer>>();
				for (Production p : productions) {
					
//					System.out.println("Grammar: adding production " + p); //debug
					
					if (!grammarRulesSet.contains(p)) {
						numberOfProductions = numberOfProductions + 1;
						grammarRulesSet.add(p);
						grammarRulesMap.put(numberOfProductions, p);
						Nonterminal currentSource = p.getSource();
						
//						System.out.println("Grammar: currentSource is " + currentSource); //debug
						
						if (sourceIndex.containsKey(currentSource)) sourceIndex.get(currentSource).add(numberOfProductions);
						else {
							Set<Integer> productionNumberSingleton = new LinkedHashSet<Integer>();
							productionNumberSingleton.add(numberOfProductions);
							sourceIndex.put(currentSource, productionNumberSingleton);
						}
						
//						System.out.println("Productions mapped to " + currentSource + ": " + sourceIndex.get(currentSource)); //debug
					}
				}
			}
		}
	}
	
	public Grammar(Production... productions) {
		this(
			productions.length <= 0 ? null : productions[0].getSource(),
			productions
		);
	}	
	
	
	public Grammar(Grammar g) {
		this(g.getStartSymbol(), g.getProductionsAsArray());
	}
	
	
	public void add(Production p) {
		if (!grammarRulesSet.contains(p)) {
			numberOfProductions = numberOfProductions + 1;
			grammarRulesSet.add(p);
			grammarRulesMap.put(numberOfProductions, p);
			Nonterminal currentSource = p.getSource();
			if (sourceIndex.containsKey(currentSource)) sourceIndex.get(currentSource).add(numberOfProductions);
			else {
				Set<Integer> productionNumberSingleton = new LinkedHashSet<Integer>(numberOfProductions);
				sourceIndex.put(currentSource, productionNumberSingleton);
			}
		}
	}
	
	public Nonterminal getStartSymbol() { return startSymbol; }
	
	public Set<Production> getGrammarRulesAsSet() {
		return new LinkedHashSet<Production>(grammarRulesSet);
	}
	
	public Map<Integer, Production> getGrammarRulesAsMap() {
		return new LinkedHashMap<Integer, Production>(grammarRulesMap);
	}
	
	public Set<Production> getAlternativesAsSet(Nonterminal source) {
		Set<Production> result = new LinkedHashSet<Production>();
		for (Integer i : sourceIndex.get(source)) result.add(grammarRulesMap.get(i));
		return result;
	}
	
	public Production[] getProductionsAsArray() {
		Production[] array = grammarRulesSet.toArray(new Production[grammarRulesSet.size()]);
		return array;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		else if (!(o instanceof Grammar)) return false;
		else {
			Grammar g = (Grammar) o;
			return startSymbol.equals(g.getStartSymbol())
				&& grammarRulesMap.equals(g.getGrammarRulesAsMap());
		}
	}
	
	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + startSymbol.hashCode();
		result = 31 * result + grammarRulesMap.hashCode();
		return result;
	}
	
	@Override
	public String toString() {
		String result = "0.\t" + startSymbol + "'" + "\t -> \t" + startSymbol + " $" + System.lineSeparator();
		for (Integer i : grammarRulesMap.keySet()) {
			Production p = grammarRulesMap.get(i);
			result = result + i + ".\t" + p.getSource() + "\t -> \t" + p.getProduct() + System.lineSeparator();
		}
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
		
		System.out.println("g1 = " + System.lineSeparator() + g1);
		System.out.println("g2 = " + System.lineSeparator() + g2);
		System.out.println("g3 = " + System.lineSeparator() + g3);
		System.out.println("g4 = " + System.lineSeparator() + g4);
		
		System.out.println("g1 = g2? " + g1.equals(g2));
		System.out.println("g1 = g3? " + g1.equals(g3));
		System.out.println("g1 = g4? " + g1.equals(g4));
	}
	
}
