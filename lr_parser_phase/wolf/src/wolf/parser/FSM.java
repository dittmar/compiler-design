package wolf.parser;

/**
 * A Finite State Machine. Consists of states and arcs and uses a nonterminal
 * rule lookup table and numbered production table.
 * @author Joseph Alacqua
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @version Mar 14, 2016
 */
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class FSM
{
    Set<State> states;
    Set<Arc> arcs;
    NonterminalRuleLookupTable nonterminal_rule_lookup_table;
    NumberedProductionTable numbered_production_table;
    StartSymbol start_symbol;
    EndSymbol end_symbol;
    
    /**
     * Create a fsm object.
     * @param nrlt the nonterminal rule lookup table for this fsm.
     * @param npt the numbered production table for this fsm.
     * @param ss the starting symbol of the grammar (usually S').
     * @param es the EndSymbol for this grammar (usually $)
     */
    public FSM(NonterminalRuleLookupTable nrlt, NumberedProductionTable npt,
               StartSymbol ss, EndSymbol es) {
        nonterminal_rule_lookup_table = nrlt;
        numbered_production_table = npt;
        start_symbol = ss;
        end_symbol = es;
        // Initialize T to {Closure({S' -> .S$})}
        states = new LinkedHashSet();
        Set<Item> initialItemSet = new LinkedHashSet();
        initialItemSet.add(new Item(numbered_production_table.getRule(0),0,new AnySymbol()));
        State initial = closure(initialItemSet);
        initial.id = 1;
        states.add(initial);
        
        // Initialize E to empty
        arcs = new LinkedHashSet();
    }
    
    /**
     * Using the nonterminal rule lookup table and numbered production table, 
     * create the states and arcs. 
     */
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
    
    /**
     * Assume: build() was previously run.
     * @param s a state
     * @return A set of arcs with a from state equal to the given state.
     */
    public Set<Arc> findArcsWithFromState(State s) {
        Set<Arc> arcSet = new LinkedHashSet();
        for(Arc arc : arcs) {
            if(arc.from.equals(s)) {
                arcSet.add(arc);
            }
        }
        return arcSet;
    }
    
    /**
     * Assume: build() was previously run.
     * @param s a state
     * @return A set of arcs with a to state equal to the given state.
     */
    public Set<Arc> findArcsWithToState(State s) {
        Set<Arc> arcSet = new LinkedHashSet();
        for(Arc arc : arcs) {
            if(arc.to.equals(s)) {
                arcSet.add(arc);
            }
        }
        return arcSet;
    }
    
    /**
     * @return get the list of productions from the numbered production table
     */
    public ArrayList<Rule> getProductions() {
        return numbered_production_table.production_list;
    }
    
    /**
     * Given a set of items, add more items to the set wherever the 
     * cursor/dot/position is next to a Nonterminal.
     * @param itemSet a set of items
     * @return the "closure" of the set of items.
     */
    private State closure(Set<Item> itemSet) {
        Set<Item> itemSet2 = new LinkedHashSet(itemSet);
        for(Item item : itemSet) {
            if(!item.atEnd()){
                Symbol next = item.getCurrentSymbol();
                if(next instanceof Nonterminal) {
                    Nonterminal nt = (Nonterminal) next;
                    Set<Rule> nonterminalRules = 
                        nonterminal_rule_lookup_table.getRuleSet(nt);
                    for(Rule rule: nonterminalRules) {
                        //addition for LR(1)
                        Set<Item> itemSetFirst = new HashSet();
                        itemSetFirst.add(item);
                        Set<Terminal> firsts = first(itemSetFirst);
                        for(Terminal t: firsts) {
                            itemSet2.add(new Item(rule,0,t));
                        }
                    }
                    if(!(itemSet.size() == itemSet2.size())) {
                        return closure(itemSet2);
                    }
                }
            } 
        }
        return new State(itemSet);
    }
    
    /**
     * Given a set of items, find the set of terminal characters that 
     * immediately follow the Nonterminal left hand side of each item's rule.
     * Assume: Each item has the same left hand side in the rule.
     * @param items the initial set of items
     * @return the set of terminal characters that follow the nonterminal.
     */
    private Set<Terminal> first(Set<Item> items) {
        Set<Terminal> firstTerminals = new HashSet();
        for(Item item : items) {
            Symbol next = item.getNextSymbol();
            if(next == null) {
                firstTerminals.add(item.lookahead);
            }
            else if(next instanceof Terminal) {
                firstTerminals.add((Terminal) next);
            }
            else { //Nonterminal case
                Set<Item> itemsProducedByNonterminal = 
                        nonterminal_rule_lookup_table.getItemSet((Nonterminal) next);
                Set<Terminal> firstOfNonTerminal = first(itemsProducedByNonterminal); 
                for(Terminal t: firstOfNonTerminal) {
                    firstTerminals.add(t);
                }
            }
        }
        return firstTerminals;
        
    }
    
    /**
     * Given a state and state set, find the state (that already has an id) that 
     * has the same items as a state in the state set.
     * @param s a state
     * @param stateSet a state set
     * @return the state in the state set that has the same items or null if there
     *      is none.
     */
    private State findStateEqualTo(State s,Set<State> stateSet) {
        for(State state: stateSet) {
          if(s.hasSameItems(state)) {
             return state; 
          }  
        }
        return null;
    }
    
    /**
     * Given a State and a Symbol move the dot passed the symbol in all the
     * items in the state
     * @param state a state
     * @param symbol a Symbol
     * @return a state where the dot is moved passed the given symbol.
     */
    private State goTo(State state, Symbol symbol)
    {
        HashSet<Item> newItems = new HashSet<>();
        for (Item item : state.items)
        {   if(!item.atEnd()) {
                if (item.getCurrentSymbol().equals(symbol)) {
                    newItems.add(
                        new Item(item.getRule(), item.getPosition() + 1, item.lookahead)
                    );
                }
            }
        }
        return closure(newItems);
    }
}
