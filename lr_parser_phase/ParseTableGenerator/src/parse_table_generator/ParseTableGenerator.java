package parse_table_generator;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * @author Joseph Alacqua
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @version Mar 14, 2016
 */
public class ParseTableGenerator 
{
    public static ParseTable generate(
        FSM fsm, 
        Set<Terminal> terminals,
        Set<Nonterminal> nonterminals) 
    {
        ArrayList<LinkedHashMap<Symbol,TableCell>> table = new ArrayList();
        for(State state:fsm.states) {
            System.out.println("State: " + state.id);
            Set<Arc> arcSet = new LinkedHashSet<>(fsm.findArcsWithFromState(state));
            LinkedHashMap<Symbol,TableCell> row = new LinkedHashMap();
            // shifts and gotos
            
            for(Arc arc :arcSet) {
                TableCell tc = new TableCell();
                if(arc.transition_symbol instanceof Nonterminal) {
                    tc.action = TableCell.Action.GOTO;
                    tc.state_id = arc.to.id;
                    row.put(arc.transition_symbol,tc);
                }
                else if(arc.transition_symbol instanceof Terminal) {
                    tc.action = TableCell.Action.SHIFT;
                    tc.state_id = arc.to.id;
                    row.put(arc.transition_symbol,tc);
                }
                else{
                    //error just pretend okay
                }
            }
            
            //reduce
            for(Item item: state.items) {
                if(item.atEnd()) {
                    Rule rule = item.getRule();
                    int rule_id = fsm.getProductions().indexOf(rule);
                    if(rule_id == -1) {
                        System.err.println("Unknown Rule: " + rule);
                        System.exit(1);
                    }
                    TableCell tc = new TableCell(
                        TableCell.Action.REDUCE,
                        rule_id
                    );
                    System.out.println();
                    TableCell existingCell = row.get(item.lookahead);
                    if(existingCell == null){
                        row.put(item.lookahead,tc);
                    }
                    else if(!existingCell.equals(tc)){
                        //System.out.println(s);
                        System.err.print("Shift Reduce Conflict: " + tc + " vs " + row.get(item.lookahead));
                        //System.exit(1);
                    }
                }
                else if(item.getCurrentSymbol() instanceof EndSymbol) {
                    // accept
                    row.put(item.getCurrentSymbol(), new TableCell(TableCell.Action.ACCEPT,-1));
                }
            }
            table.add(row);
        }
        
        // We want all of the terminals to be in the table, including
        // the end symbol
        Set<Symbol> parse_table_symbols = new LinkedHashSet(terminals);
        // We want all of the nonterminals to be in the table...
        parse_table_symbols.addAll(nonterminals);
        // ... but we don't want the start symbol to be in the table.
        parse_table_symbols.remove(fsm.start_symbol);
        return new ParseTable(table, parse_table_symbols);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String billFile = "/Users/williamezekiel/Documents/Compiler_Design_Theory/cdt_git/compiler-design/lr_parser_phase/ParseTableGenerator/test/parse_table_generator/resources/class.txt";
        String kevFile = "test/parse_table_generator/resources/class.txt";
        //parse the grammar
        GrammarParser gp = new GrammarParser(billFile);
        gp.parse();
        
        FSM fsm = new FSM(
            gp.nonterminal_rule_lookup_table,
            gp.production_table,
            gp.start_symbol,
            gp.end_symbol
        );
        
        fsm.build();
        
        System.out.println(fsm.states);
        System.out.println(fsm.states.size());
        System.out.println(fsm.arcs);
        System.out.println(fsm.arcs.size());
      
        ParseTable pt = generate(fsm,gp.terminals,gp.nonterminals);
        System.out.println(pt);
    }
    
}
