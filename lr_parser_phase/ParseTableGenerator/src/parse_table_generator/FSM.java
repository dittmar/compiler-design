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
    StartSymbol end_symbol;
    
    public FSM(NonterminalRuleLookupTable nrlt, NumberedProductionTable npt,
               StartSymbol es) 
    {
        nonterminal_Rule_lookup_table = nrlt;
        numbered_production_table = npt;
        end_symbol = es;
                // Initialize T to {Closure({S' -> .S$})}
        states = new LinkedHashSet();
        Set<Item> initialItemSet = new LinkedHashSet();
        initialItemSet.add(new Item(numbered_production_table.getRule(0),0));
        State initial = closure(initialItemSet);
        initial.id = 1;
        states.add(initial);
        
        // Initialize E to empty
        arcs = new LinkedHashSet();
    }
    
    public State closure(Set<Item> itemSet) {
        Set<Item> itemSet2 = new LinkedHashSet(itemSet);
        for(Item item : itemSet) {
            if(!item.atEnd()){
                Symbol next = item.getCurrentSymbol();
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
        }
        System.out.println(itemSet);
        System.out.println(itemSet2);
        return new State(itemSet);
    }
    
    public State goTo(State i, Symbol x)
    {
        HashSet<Item> j = new HashSet<>();
        for (Item a : i.items)
        {   if(!a.atEnd()) {
                if (a.getCurrentSymbol().equals(x)) {
                    j.add(new Item(a.getRule(), a.getPosition() + 1));
                }
            }
        }
        return closure(j);
    }
    
    public void build() {
        int arcCount = arcs.size();
        Set<State> newStates = new LinkedHashSet(states);
        for(State state : states) {
            for(Item item: state.items){
                if(!item.atEnd()) {
                    Symbol s = item.getCurrentSymbol();
                    if (!s.equals(end_symbol))
                    {
                        State j = goTo(state,s);
                        if(newStates.add(j)) {
                            j.id = newStates.size();
                        }
                        else{
                            j = findStateEqualTo(j,newStates);
                        }
                        arcs.add(new Arc(s,state,j));
                    }
                    else
                    {
                        state.setAcceptState();
                    }
                }
            }
        }
        if(arcCount != arcs.size() || states.size() != newStates.size()) {
            states = newStates;
            build();
        }
    }
    
    private State findStateEqualTo(State s,Set<State> stateSet) {
        for(State state: stateSet) {
          if(s.equals(state)) {
             return state; 
          }  
        }
        return null;
    }
    
    public Set<Arc> findArcsWithFromState(State s) {
        Set<Arc> arcSet = new LinkedHashSet();
        for(Arc arc : arcs) {
            if(arc.from.equals(s)) {
                arcSet.add(arc);
            }
        }
        return arcSet;
    }
    
    public Set<Arc> findArcsWithToState(State s) {
        Set<Arc> arcSet = new LinkedHashSet();
        for(Arc arc : arcs) {
            if(arc.to.equals(s)) {
                arcSet.add(arc);
            }
        }
        return arcSet;
    }
}
