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
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class FSM
{
    Set<State> states;
    Set<Arc> arcs;
    NonterminalRuleLookupTable nonterminal_Rule_lookup_table;
    NumberedProductionTable numbered_production_table;
    int stateCount;
    
    public FSM(NonterminalRuleLookupTable nrlt, NumberedProductionTable npt) {
        nonterminal_Rule_lookup_table = nrlt;
        numbered_production_table = npt;
    }
    
    public State closure(Set<Item> itemSet) {
        Set<Item> itemSet2 = new LinkedHashSet(itemSet);
        for(Item item : itemSet) {
            Symbol next = item.getRule().rhs.get(item.getPosition());
            if(next instanceof Nonterminal) {
                Nonterminal nt = (Nonterminal) next;
                Set<Rule> nonterminalRules = nonterminal_Rule_lookup_table.getRuleSet(nt);
                for(Rule rule: nonterminalRules) {
                    itemSet2.add(new Item(rule,0));
                }
                if(!(itemSet.size() == itemSet2.size())) {
                    return closure(itemSet2);
                }
            }
        }
        System.out.println(itemSet);
        System.out.println(itemSet2);
        return new State(itemSet,++stateCount);
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
    
    public void build() {
        
        // Initialize T to {Closure({S' -> .S$})}
        states = new LinkedHashSet();
        Set<Item> initialItemSet = new LinkedHashSet();
        initialItemSet.add(new Item(numbered_production_table.getRule(0),0));
        states.add(closure(initialItemSet));
        
        // Initialize E to empty
        arcs = new LinkedHashSet();
        
        // the rest
        Set<State> newStates = new LinkedHashSet(states);
        for(State state : states) {
            for(Item item: state.items){
                if(!item.atEnd()) {
                    Symbol s = item.getCurrentSymbol();
                    State j = goTo(state,s);
                    if(newStates.add(j)) {
                        arcs.add(new Arc(s,state,j));
                    }

                }
            }
        }
        
        System.out.println(newStates);
        System.out.println(arcs);
        
    }
    
    // never gonna give you up
    // never gonna let you down
    // never gonna run around
    // and desert you ;P
}
