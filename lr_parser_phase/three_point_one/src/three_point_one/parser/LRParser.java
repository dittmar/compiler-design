package three_point_one.parser;

import java.util.ArrayList;
import java.util.Stack;
import three_point_one.Lexing;
import three_point_one.node.*;

/**
 *
 * @author williamezekiel
 */
public class LRParser {
    ParseTable parse_table;
    int currentState;
    private Token token;
    private Lexing lexer;
    Stack<ParserStackItem> parser_stack;
    NumberedProductionTable grammar;
    
    public LRParser(ParseTable pt, NumberedProductionTable grammar, 
        String filename) 
    {
        parse_table = pt;
        lexer = new Lexing(filename);
        this.grammar = grammar;
    }
    
    public void parse() {
        // lex it & setup
        Terminal next_terminal = tokenToTerminal(nextValidToken());
        parser_stack = new Stack();
        parser_stack.push(new ParserStackItem(null, 1));
        
        printCurrentStack();
        
        while (next_terminal != null) {
            System.out.println("Terminal: "+next_terminal);
            // Parse table to look at comes from the next terminal on the
            // stack and our current state.
            TableCell cell = parse_table.getTableCellAt(
                next_terminal, parser_stack.peek().id_number);
            if(cell == null) {
                System.out.println(
                    "REJECT: No Action for Terminal " + next_terminal + 
                    " at state " + parser_stack.peek().id_number
                );
                System.exit(1);
            }
            
            switch(cell.action) {
                case ACCEPT:
                    System.out.println("ACCEPT");
                    return;
                case REDUCE:
                    System.out.println("REDUCE: " + cell);
                    // The rule we're looking for is the number in our table
                    // cell since this is a reduce action.
                    int ruleNum = cell.id_number;
                    Rule rule = grammar.getRule(ruleNum);
                    
                    // Pop the terminals for the rule off the stack.
                    ArrayList<Symbol> popped_symbols = new ArrayList<>();
                    for(int j = 0; j < rule.rhs.size(); j++) {
                        popped_symbols.add(0, parser_stack.pop().symbol);
                    }
                    
                    // Make sure popped symbols match the right side 
                    // of the rule.
                    if (!popped_symbols.equals(rule.rhs))
                    {
                        System.err.println("Bad Reduce: " + 
                            popped_symbols.toString() + " doesn't match rule" +
                            rule.rhs.toString()
                        );
                    }
                    // This is the item on the parser stack that will have
                    // the state number for the new nonterminal that will be
                    // pushed on the stack.
                    ParserStackItem go_to_item = parser_stack.pop();
                    // We need the goto number for the table cell that
                    // matches the nonterminal.
                    TableCell goToCell = parse_table.getTableCellAt(
                        rule.lhs,
                        go_to_item.id_number
                    );
                    
                    if(goToCell == null) {
                        System.out.println(
                            "REJECT: No Action for Nonterminal " + 
                            parser_stack.peek().symbol + " at state " + 
                            parser_stack.peek().id_number
                        );
                        System.exit(1);
                    }
                    
                    // The nonterminal item on the stack has the goto cell
                    // number of the previous item as its state number.
                    ParserStackItem new_nonterminal_item = new ParserStackItem(
                        rule.lhs, goToCell.id_number
                    );
                    // Put the goto item back on the stack
                    parser_stack.push(go_to_item);
                    // Push the new nonterminal onto the stack
                    parser_stack.push(new_nonterminal_item);
                    System.out.println("GOTO: " + goToCell.id_number);
                    break;
                case SHIFT:
                    System.out.println("SHIFT: " + cell);
                    parser_stack.add(
                        new ParserStackItem(next_terminal, cell.id_number)
                    );
                    // Get next terminal
                    next_terminal = tokenToTerminal(nextValidToken());
                    break;
                case GOTO:
                    System.out.println("GOTO: " + cell);
                    ParserStackItem item = parser_stack.pop();
                    item.id_number = cell.id_number;
                    parser_stack.push(item);
                    // DO NOT GET NEXT TOKEN.
                    break;
                default:
                    System.err.println("Error: Unknown Action!");
                    System.exit(1);
            }
            printCurrentStack();
        }
    }
    
    /**
     * Get the next valid token, skipping comments and whitespace.
     * @return the next non-ignored token.
     */
    private Token nextValidToken()
    {
        token = lexer.getToken();
        while (token instanceof TSpace)
        {
            token = lexer.getToken();
        }
        return token;
    }
    
    /**
     * 
     * @param t is the token whose terminal should be found
     * @return the Terminal corresponding to Token t.
     */
    private Terminal tokenToTerminal(Token t)
    {   if(token instanceof EOF) {
            return new EndSymbol("$");
        }
        String[] splitToken = t.getClass().getName().split("\\.");
        String tokenName = splitToken[splitToken.length -1].toLowerCase();
        return parse_table.getTerminalLookupTable().get(tokenName);
    }
        
    public void printCurrentStack() {
        // symbol stack should always be one shorter than stateIdStack
        System.out.print("start [");
        for(ParserStackItem item : parser_stack){
            System.out.print(item.toString() + "  ");
        }
        System.out.println("\n");
    }
}