package parse_table_generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import jdk.nashorn.internal.parser.Token;

/**
 * The LRParser
 * @author Joseph Alacqua
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @version Mar 14, 2016
 */
public class LRParser {
    ParseTable parse_table;
    int currentState;
    private Token token;
    private ThreePointOneLexing lexer;
    Stack<Symbol> symbolStack;
    Stack<Integer> stateIdStack;
    NumberedProductionTable grammar;
    
    /**
     * Create an LR Parser
     * @param pt the parse table
     * @param grammar the grammar. 
     */
    public LRParser(ParseTable pt, NumberedProductionTable grammar) {
        parse_table = pt;
        lexer = new WolfLexing(filename);
        this.grammar = grammar;
    }
    
    /**
     * Parse the input file.
     */
    public void parse() {
        // lex it & setup
        Terminal next_terminal = tokenToTerminal(nextValidToken());
        symbolStack = new Stack();
        stateIdStack = new Stack();
        stateIdStack.push(1);
        while (next_terminal != null) {
            // parse it
            symbolStack.add(next_terminal);
            TableCell cell = parse_table.getTableCellAt(next_terminal, stateIdStack.peek());
            switch(cell.action) {
                case ACCEPT:
                    return;
                case REDUCE:
                    int ruleNum = cell.state_id;
                    Rule rule = grammar.getRule(ruleNum);
                    for(int j = 0; j < rule.rhs.size(); j++) {
                        symbolStack.pop();
                        stateIdStack.pop();
                    }
                    symbolStack.push(rule.lhs);
                    // Nonterminal and previous state 
                    TableCell goToCell = parse_table.getTableCellAt(symbolStack.peek(), stateIdStack.peek());
                    stateIdStack.push(goToCell.state_id);
                    break;
                case SHIFT:
                    stateIdStack.push(cell.state_id);
                    // Get next terminal
                    next_terminal = tokenToTerminal(nextValidToken());
                    break;
                case GOTO:
                    stateIdStack.push(cell.state_id);
                    // DO NOT GET NEXT TOKEN.
                    break;
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
        while (token instanceof TSpace || token instanceof TComment)
        {
            token = lexer.getToken();
        }
        return token;
    }
    
    /**
     * @param t is the token whose terminal should be found
     * @return the Terminal corresponding to Token t.
     */
    private Terminal tokenToTerminal(Token t)
    {
        return parse_table.getTerminalLookupTable().get(t.getText());
    }
        
    /**
     * Print the current stack containing state numbers and symbols.
     */
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