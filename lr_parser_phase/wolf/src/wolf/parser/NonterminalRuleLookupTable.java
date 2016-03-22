package wolf.parser;

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
public class NonterminalRuleLookupTable 
{
    HashMap<Nonterminal, Set<Rule>> rule_table;
    
    /**
     * Create a nonterminal rule lookup table.
     */
    public NonterminalRuleLookupTable()
    {
        rule_table = new HashMap<>();
    }
    
    /**
     * Add a nonterminal and rule to the map. If the nonterminal is already
     * added, but the rule is different, add the rule.
     * @param nt a nonterminal.
     * @param rule  a rule
     */
    void add(Nonterminal nt, Rule rule)
    {
        if (rule_table.containsKey(nt))
        {
            rule_table.get(nt).add(rule);
        }
        else
        {
            HashSet<Rule> rules = new HashSet<>();
            rules.add(rule);
            rule_table.put(nt, rules);
        }
    }
    
    /**
     * @param nt a nonterminal
     * @return the set of rules mapped to the nonterminal.
     */
    Set<Rule> getRuleSet(Nonterminal nt) {
        return rule_table.get(nt);
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
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Nonterminal, Set<Rule>> entry : rule_table.entrySet())
        {
            sb.append("Nonterminal: ");
            sb.append(entry.getKey());
            sb.append("\nRules:\n");
            sb.append(entry.getValue());
            sb.append("\n\n");
        }
        return sb.toString();
    }
}
