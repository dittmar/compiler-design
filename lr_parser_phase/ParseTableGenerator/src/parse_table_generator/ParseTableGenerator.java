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
public static ParseTable generate(FSM fsm, Set<Terminal> terminals) {
        ArrayList<LinkedHashMap<Symbol,TableCell>> table = new ArrayList();
        Set<Symbol> shiftReduceColumnSymbols = new LinkedHashSet(terminals);
        shiftReduceColumnSymbols.add(fsm.end_symbol);
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
                    int rule_id = fsm.numbered_production_table.production_list.indexOf(rule);
                    if(rule_id == -1) {
                        System.err.println("Unknown Rule: " + rule);
                        System.exit(1);
                    }
                    TableCell tc = new TableCell(TableCell.Action.REDUCE,rule_id);
                    for(Symbol s : shiftReduceColumnSymbols) {
                        System.out.println();
                        if(row.get(s) == null){
                            row.put(s,tc);
                        }
                        else {
                            System.out.println(s);
                            System.err.print("Shift Reduce Conflict: " + tc + " vs " + row.get(s));
                            //System.exit(1);
                        }
                    }
                }
                else if(item.getCurrentSymbol() instanceof EndSymbol) {
                    // accept
                    row.put(item.getCurrentSymbol(), new TableCell(TableCell.Action.ACCEPT,-1));
                }
            }
            table.add(row);
        }
        
        return new ParseTable(table);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String billFile = "/Users/williamezekiel/Documents/Compiler_Design_Theory/cdt_git/compiler-design/lr_parser_phase/ParseTableGenerator/test/parse_table_generator/resources/class.txt";
        String kevFile = "test/parse_table_generator/resources/class2.txt";
        //parse the grammar
        GrammarParser gp = new GrammarParser(billFile);
        gp.parse();
        
        FSM fsm = new FSM(
            gp.nonterminal_rule_lookup_table,
            gp.production_table,
            gp.end_symbol
        );
        
        fsm.build();
        
        System.out.println(fsm.states);
        System.out.println(fsm.states.size());
        System.out.println(fsm.arcs);
        System.out.println(fsm.arcs.size());
      
        ParseTable pt = generate(fsm,gp.terminals);
        System.out.println(pt);
    }
    
}
