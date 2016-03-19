package parse_table_generator;

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
    public ParseTable generate()
    {
        //ParseTable parse_table = new ParseTable();
        
        //return parse_table;
        return null;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        String billFile = "/Users/williamezekiel/Documents/Compiler_Design_Theory/cdt_git/compiler-design/lr_parser_phase/ParseTableGenerator/test/parse_table_generator/resources/class.txt";
        String kevFile = "test/parse_table_generator/resources/class.txt";
        //parse the grammar
        GrammarParser gp = new GrammarParser(kevFile);
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
        //Testing closure.
        //Set<Item> hs = new LinkedHashSet();
        //hs.add(new Item(gp.production_table.getRule(0),0));
        //fsm.closure(hs);
    }
    
}
