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
}
