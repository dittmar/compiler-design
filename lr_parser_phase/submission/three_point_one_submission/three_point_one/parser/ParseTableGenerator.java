package three_point_one.parser;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Generates a parse table
 * @author Joseph Alacqua
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @version Mar 14, 2016
 */
public class ParseTableGenerator  {
    /**
     * Generate a parse table.
     * @param fsm a finite state machine
     * @param terminals a set of terminals
     * @param nonterminals a set of nonterminals
     * @param terminalLookupTable the terminal lookup table.
     * @return the generated parse table.
     */
    public ParseTable generate(
        FSM fsm, 
        Set<Terminal> terminals,
        Set<Nonterminal> nonterminals,
        Map<String, Terminal> terminalLookupTable) {
        ArrayList<LinkedHashMap<Symbol,TableCell>> parse_table =
            new ArrayList();
        for(State state:fsm.states) {
            Set<Arc> arcSet = new LinkedHashSet<>(
                fsm.findArcsWithFromState(state)
            );
            LinkedHashMap<Symbol,TableCell> row = new LinkedHashMap();
            // shifts and gotos
            
            for(Arc arc :arcSet) {
                TableCell tc = new TableCell();
                if(arc.transitionSymbol instanceof Nonterminal) {
                    tc.action = TableCell.Action.GOTO;
                    tc.idNumber = arc.to.id;
                    row.put(arc.transitionSymbol,tc);
                }
                else if(arc.transitionSymbol instanceof Terminal) {
                    tc.action = TableCell.Action.SHIFT;
                    tc.idNumber = arc.to.id;
                    row.put(arc.transitionSymbol,tc);
                }
                else{
                    //error just pretend okay
                }
            }
            
            //reduce
            for(Item item: state.items) {
                if(item.atEnd()) {
                    Rule rule = item.getRule();
                    int ruleId = fsm.getProductions().indexOf(rule);
                    if(ruleId == -1) {
                        System.err.println("Unknown Rule: " + rule);
                        System.exit(1);
                    }
                    TableCell tc = new TableCell(
                        TableCell.Action.REDUCE,
                        ruleId
                    );
                    
                    TableCell existingCell = row.get(item.lookahead);
                    if(existingCell == null){
                        row.put(item.lookahead,tc);
                    }
                    else if(!existingCell.equals(tc)){
                        System.err.println("Conflict: " + tc + " vs " + 
                            row.get(item.lookahead));
                        System.exit(1);
                    }
                }
                else if(item.getCurrentSymbol() instanceof EndSymbol) {
                    // accept
                    row.put(
                        item.getCurrentSymbol(), 
                        new TableCell(TableCell.Action.ACCEPT,-1)
                    );
                }
            }
            parse_table.add(row);
        }
        
        // We want all of the terminals to be in the parse_table, including
        // the end symbol
        Set<Symbol> parse_table_symbols = new LinkedHashSet(terminals);
        // We want all of the nonterminals to be in the parse_table...
        parse_table_symbols.addAll(nonterminals);
        // ... but we don't want the start symbol to be in the parse_table.
        parse_table_symbols.remove(fsm.startSymbol);
        return new ParseTable(
            parse_table,
            parse_table_symbols,
            terminalLookupTable
        );
    }
}
