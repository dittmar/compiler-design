package parsetablegenerator;

/**
 *
 * @author Joseph Alacqua
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @version Mar 15, 2016
 */

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class Grammar {
	private final Nonterminal startSymbol;
	private Set<Rule> grammarRulesSet;
	private Map<Integer, Rule> grammarRulesMap;
	private Map<Nonterminal, Set<Integer>> sourceIndex;
	private int numberOfRules = 0;
	
	public Grammar(Nonterminal startSymbol, Rule... rules) {
		if (rules.length <= 0)
			throw new IllegalArgumentException("Grammar: set of rules cannot be empty");
		else if (startSymbol == null)
			throw new IllegalArgumentException("Grammar: startSymbol cannot be null");
		else {
			boolean foundStartSymbol = false;
			for (Rule p : rules) if (startSymbol.equals(p.getLhs())) { foundStartSymbol = true; break; };
			if (!foundStartSymbol)
				throw new IllegalArgumentException("Grammar: Start symbol must appear on lhs of a rule in Grammar.");
			else {
				this.startSymbol = startSymbol;
				grammarRulesSet = new LinkedHashSet<>();
				grammarRulesMap = new LinkedHashMap<>();
				sourceIndex = new LinkedHashMap<>();
				for (Rule r : rules) {
					
//					System.out.println("Grammar: adding rule " + p); //debug
					
					if (!grammarRulesSet.contains(r)) {
						numberOfRules = numberOfRules + 1;
						grammarRulesSet.add(r);
						grammarRulesMap.put(numberOfRules, r);
						Nonterminal currentSource = r.getLhs();
						
//						System.out.println("Grammar: currentSource is " + currentSource); //debug
						
						if (sourceIndex.containsKey(currentSource)) sourceIndex.get(currentSource).add(numberOfRules);
						else {
							Set<Integer> ruleNumberSingleton = new LinkedHashSet<>();
							ruleNumberSingleton.add(numberOfRules);
							sourceIndex.put(currentSource, ruleNumberSingleton);
						}
						
//						System.out.println("Rules mapped to " + currentSource + ": " + sourceIndex.get(currentSource)); //debug
					}
				}
			}
		}
	}
	
	public Grammar(Rule... rules) {
		this(
			rules.length <= 0 ? null : rules[0].getLhs(),
			rules
		);
	}	
	
	
	public Grammar(Grammar g) {
		this(g.getStartSymbol(), g.getRulesAsArray());
	}
	
	
	public void add(Rule p) {
		if (!grammarRulesSet.contains(p)) {
			numberOfRules = numberOfRules + 1;
			grammarRulesSet.add(p);
			grammarRulesMap.put(numberOfRules, p);
			Nonterminal currentSource = p.getLhs();
			if (sourceIndex.containsKey(currentSource)) sourceIndex.get(currentSource).add(numberOfRules);
			else {
				Set<Integer> ruleNumberSingleton = new LinkedHashSet<>(numberOfRules);
				sourceIndex.put(currentSource, ruleNumberSingleton);
			}
		}
	}
	
	public Nonterminal getStartSymbol() { return startSymbol; }
	
	public Set<Rule> getGrammarRulesAsSet() {
		return new LinkedHashSet<>(grammarRulesSet);
	}
	
	public Map<Integer, Rule> getGrammarRulesAsMap() {
		return new LinkedHashMap<>(grammarRulesMap);
	}
	
	public Set<Rule> getAlternativesAsSet(Nonterminal source) {
		Set<Rule> result = new LinkedHashSet<>();
		for (Integer i : sourceIndex.get(source)) result.add(grammarRulesMap.get(i));
		return result;
	}
	
	public Rule[] getRulesAsArray() {
		Rule[] array = grammarRulesSet.toArray(new Rule[grammarRulesSet.size()]);
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
		String result = "0.\t" + startSymbol + "'" + "\t -> \t" + startSymbol + "$" + System.lineSeparator();
		for (Integer i : grammarRulesMap.keySet()) {
			Rule p = grammarRulesMap.get(i);
			result = result + i + ".\t" + p.getLhs() + "\t -> \t" + p.getRhsString()+ System.lineSeparator();
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
		
		System.out.println("g1 = " + System.lineSeparator() + g1);
		System.out.println("g2 = " + System.lineSeparator() + g2);
		System.out.println("g3 = " + System.lineSeparator() + g3);
		System.out.println("g4 = " + System.lineSeparator() + g4);
		
		System.out.println("g1 = g2? " + g1.equals(g2));
		System.out.println("g1 = g3? " + g1.equals(g3));
		System.out.println("g1 = g4? " + g1.equals(g4));
	}
	
}

