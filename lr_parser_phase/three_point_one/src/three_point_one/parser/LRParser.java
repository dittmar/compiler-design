package three_point_one.parser;

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
    Stack<Symbol> symbolStack;
    Stack<Integer> stateIdStack;
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
        symbolStack = new Stack();
        stateIdStack = new Stack();
        stateIdStack.push(1);
        while (next_terminal != null) {
            // parse it
            TableCell cell = parse_table.getTableCellAt(
                next_terminal, stateIdStack.peek()-1);
            
            switch(cell.action) {
                case ACCEPT:
                    System.out.println("ACCEPT");
                    return;
                case REDUCE:
                    System.out.println("REDUCE");
                    int ruleNum = cell.state_id;
                    Rule rule = grammar.getRule(ruleNum);
                    for(int j = 0; j < rule.rhs.size(); j++) {
                        symbolStack.pop();
                        stateIdStack.pop();
                    }
                    symbolStack.push(rule.lhs);
                    // Nonterminal and previous state 
                    TableCell goToCell = parse_table.getTableCellAt(
                        symbolStack.peek(), stateIdStack.peek()-1);
                    
                    stateIdStack.push(goToCell.state_id);
                    break;
                case SHIFT:
                    System.out.println("SHIFT");
                    symbolStack.add(next_terminal);
                    stateIdStack.push(cell.state_id);
                    // Get next terminal
                    next_terminal = tokenToTerminal(nextValidToken());
                    break;
                case GOTO:
                    System.out.println("GOTO");
                    stateIdStack.push(cell.state_id);
                    // DO NOT GET NEXT TOKEN.
                    break;
                default:
                    System.err.println("WTF U DOIN M8");
                    System.exit(1);
            }
            printCurrentStack();
        }
        System.err.println("ERROR: ENDED EARLY");
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
    {
        String[] splitToken = t.getClass().getName().split("\\.");
        String tokenName = splitToken[splitToken.length -1].toLowerCase();
        return parse_table.getTerminalLookupTable().get(tokenName);
    }
        
    public void printCurrentStack() {
        // symbol stack should always be one shorter than stateIdStack
        StringBuilder sb = new StringBuilder();
        sb.append("start |");
        for(int i = 0; i < symbolStack.size(); i++){
            sb.append("_").append(stateIdStack.get(i)).append(symbolStack.get(i));
        }
        sb.append("_").append(stateIdStack.peek());
        System.out.println(sb.toString());
    }
}