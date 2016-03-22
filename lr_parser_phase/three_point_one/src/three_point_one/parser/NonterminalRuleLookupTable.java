package three_point_one.parser;

/**
 * A Nonterminal rule lookup table. Keeps a map of nonterminals and any
 * productions they create in the grammar.
 * @author Joseph Alacqua
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @version Mar 14, 2016
 */
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
public class NonterminalRuleLookupTable {
    HashMap<Nonterminal, Set<Rule>> ruleTable;
    
    /**
     * Create a nonterminal rule lookup table.
     */
    public NonterminalRuleLookupTable() {
        ruleTable = new HashMap<>();
    }
    
    /**
     * Add a nonterminal and rule to the map. If the nonterminal is already
     * added, but the rule is different, add the rule.
     * @param nt a nonterminal.
     * @param rule  a rule
     */
    void add(Nonterminal nt, Rule rule) {
        if (ruleTable.containsKey(nt)) {
            ruleTable.get(nt).add(rule);
        }
        else {
            HashSet<Rule> rules = new HashSet<>();
            rules.add(rule);
            ruleTable.put(nt, rules);
        }
    }
    
    /**
     * @param nt a nonterminal
     * @return the set of rules mapped to the nonterminal.
     */
    Set<Rule> getRuleSet(Nonterminal nt) {
        return ruleTable.get(nt);
    }
    
    /**
     * @param nt a nonterminal
     * @return a sett of items based on the rules mapped to the nonterminal.
     */
    Set<Item> getItemSet(Nonterminal nt) {
        Set<Item> itemSet = new HashSet();
        for(Rule rule: getRuleSet(nt)) {
            itemSet.add(new Item(rule, 0));
        }
        return itemSet;
    }
    
    /**
     * @return the string representation of this nonterminal rule lookup table.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Nonterminal, Set<Rule>> entry : ruleTable.entrySet()) {
            sb.append("Nonterminal: ");
            sb.append(entry.getKey());
            sb.append("\nRules:\n");
            sb.append(entry.getValue());
            sb.append("\n\n");
        }
        return sb.toString();
    }
}
