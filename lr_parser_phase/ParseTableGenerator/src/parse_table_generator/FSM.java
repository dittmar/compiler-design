package parse_table_generator;

/**
 *
 * @author Joseph Alacqua
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @version Mar 14, 2016
 */
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FSM
{
    List<State> states;
    List<Arc> arcs;
    NonterminalRuleLookupTable nonterminal_Rule_lookup_table;
    NumberedProductionTable numbered_production_table;
    
    public FSM(NonterminalRuleLookupTable nrlt, NumberedProductionTable npt) {
        nonterminal_Rule_lookup_table = nrlt;
        numbered_production_table = npt;
        states = new ArrayList();
        arcs = new ArrayList();
    }
    
    public State closure(Set<Item> itemSet) {
        for(Item item : itemSet) {
            Symbol next = item.getRule().rhs.get(item.getPosition());
            if(next instanceof Nonterminal) {
                Nonterminal nt = (Nonterminal) next;
                Set<Rule> nonterminalRules = nonterminal_Rule_lookup_table.getRuleSet(nt);
                for(Rule rule: nonterminalRules) {
                    itemSet.add(new Item(rule,0));
                }
            }
        }
        System.out.println(itemSet);
        return new State(itemSet);
    }
    
    public State goTo(State i, Symbol x)
    {
        HashSet<Item> j = new HashSet<>();
        for (Item a : i.items)
        {
            if (a.getRule().getRhs().get(a.getPosition()).equals(x)) {
                j.add(new Item(a.getRule(), a.getPosition() + 1));
            }
        }
        return closure(j);
    }
}