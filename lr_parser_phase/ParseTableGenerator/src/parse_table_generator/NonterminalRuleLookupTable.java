package parse_table_generator;

/**
 *
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
    
    public NonterminalRuleLookupTable()
    {
        rule_table = new HashMap<>();
    }
    
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
    
    Set<Rule> getRuleSet(Nonterminal nt) {
        return rule_table.get(nt);
    }
    
    Set<Item> getItemSet(Nonterminal nt) {
        Set<Item> itemSet = new HashSet();
        for(Rule rule: getRuleSet(nt)) {
            itemSet.add(new Item(rule,0));
        }
        return itemSet;
    }
    
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
