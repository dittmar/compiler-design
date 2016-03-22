package three_point_one.parser;

import java.util.ArrayList;
import java.util.Stack;
import three_point_one.Lexing;
import three_point_one.node.*;

/**
 * An LRParser parses a input and determines if said input is accepted 
 * in a specified grammar using a parse table.
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @version Mar 19, 2016
 */
public class LRParser {
    ParseTable parseTable;
    int currentState;
    private Token token;
    private Lexing lexer;
    Stack<ParserStackItem> parserStack;
    NumberedProductionTable grammar;
    
    /**
     * Create an LRParser
     * @param pt a parse table
     * @param grammar a grammar in a NumberedProductionTable
     * @param filename the name of the file
     */
    public LRParser(ParseTable pt, NumberedProductionTable grammar, 
        String filename) {
        parseTable = pt;
        lexer = new Lexing(filename);
        this.grammar = grammar;
    }
    
    /**
     * Parse the input. Throws errors if rejected.
     */
    public void parse() {
        // lex it & setup
        Terminal next_terminal = tokenToTerminal(nextValidToken());
        parserStack = new Stack();
        parserStack.push(new ParserStackItem(null, 1));
        
        printCurrentStack();
        
        while (next_terminal != null) 
        {
            System.out.println("Terminal: "+next_terminal);
            // Parse table to look at comes from the next terminal on the
            // stack and our current state.
            TableCell cell = parseTable.getTableCellAt(
                next_terminal, parserStack.peek().idNumber);
            if(cell == null) {
                badShift(next_terminal);
            }
            
            switch(cell.action) {
                case ACCEPT:
                    System.out.println("ACCEPT");
                    return;
                case REDUCE:
                    System.out.println("REDUCE: " + cell);
                    // The rule we're looking for is the number in our table
                    // cell since this is a reduce action.
                    int ruleNum = cell.idNumber;
                    Rule rule = grammar.getRule(ruleNum);
                    
                    // Pop the terminals for the rule off the stack.
                    ArrayList<Symbol> popped_symbols = new ArrayList<>();
                    for(int j = 0; j < rule.rhs.size(); j++) {
                        popped_symbols.add(0, parserStack.pop().symbol);
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
                    ParserStackItem go_to_item = parserStack.pop();
                    // We need the goto number for the table cell that
                    // matches the nonterminal.
                    TableCell goToCell = parseTable.getTableCellAt(
                        rule.lhs,
                        go_to_item.idNumber
                    );
                    
                    if(goToCell == null)
                    {
                        badReduce();
                    }
                    
                    // The nonterminal item on the stack has the goto cell
                    // number of the previous item as its state number.
                    ParserStackItem new_nonterminal_item = new ParserStackItem(
                        rule.lhs, goToCell.idNumber
                    );
                    // Put the goto item back on the stack
                    parserStack.push(go_to_item);
                    // Push the new nonterminal onto the stack
                    parserStack.push(new_nonterminal_item);
                    System.out.println("GOTO: " + goToCell.idNumber);
                    break;
                case SHIFT:
                    System.out.println("SHIFT: " + cell);
                    parserStack.add(
                        new ParserStackItem(next_terminal, cell.idNumber)
                    );
                    // Get next terminal
                    next_terminal = tokenToTerminal(nextValidToken());
                    break;
                case GOTO:
                    System.out.println("GOTO: " + cell);
                    ParserStackItem item = parserStack.pop();
                    item.idNumber = cell.idNumber;
                    parserStack.push(item);
                    // DO NOT GET NEXT TOKEN.
                    break;
                default:
                    System.err.println("Error: Unknown Action!");
                    System.exit(1);
            }
            printCurrentStack();
        }
        System.err.println("Unrecognized token: " + token.getText());
    }
    
    /**
     * Main function. 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Grammar file for the language
        String grammarFilename = "resources/g3.1.txt";
        //parse the grammar
        GrammarParser gp = new GrammarParser(grammarFilename);
        gp.parse();
        
        FSM fsm = new FSM(
            gp.nonterminalRuleLookupTable,
            gp.productionTable,
            gp.startSymbol,
            gp.endSymbol
        );
        
        fsm.build();
      
        ParseTable pt = new ParseTableGenerator().generate(
            fsm,
            gp.terminals,
            gp.nonterminals,
            gp.terminalLookupTable
        );
        System.out.println(pt);
        for (String programFile : args) {
            System.out.println("********************************************");
            System.out.println("Parsing " + programFile + ":\n");
            LRParser parser = new LRParser(
                pt,
                gp.productionTable,
                programFile
            );
            parser.parse();
            System.out.println("********************************************");
        }
    }
    
    /**
     * Get the next valid token, skipping comments and whitespace.
     * @return the next non-ignored token.
     */
    private Token nextValidToken(){
        token = lexer.getToken();
        while (token instanceof TSpace) {
            token = lexer.getToken();
        }
        return token;
    }
    
    /**
     * @param t is the token whose terminal should be found
     * @return the Terminal corresponding to Token t.
     */
    private Terminal tokenToTerminal(Token t) {   
        if(token instanceof EOF) {
            return new EndSymbol("$");
        }
        String[] splitToken = t.getClass().getName().split("\\.");
        String tokenName = splitToken[splitToken.length -1].toLowerCase();
        return parseTable.getTerminalLookupTable().get(tokenName);
    }
        
    /**
     * Print the current stack.
     */
    private void printCurrentStack() {
        // symbol stack should always be one shorter than stateIdStack
        System.out.print("start [");
        for(ParserStackItem item : parserStack){
            System.out.print(item.toString() + "  ");
        }
        System.out.println("\n");
    }
    
    /**
     * Print an error message if an error occurs during a shift action.
     * @param next_terminal  a terminal
     */
    private void badShift(Terminal next_terminal) {
        System.out.println(
            "REJECT: No Action for Terminal " + next_terminal + 
            " at state " + parserStack.peek().idNumber
        );
        parserDie(next_terminal);
    }
    
    /**
     * Print an error message if an error occurs during a reduce action.
     * @param next_terminal  a terminal
     */
    private void badReduce() {
        System.out.println(
            "REJECT: No Action for Nonterminal " + 
            parserStack.peek().symbol + " at state " + 
            parserStack.peek().idNumber
        );
        parserDie(parserStack.peek().symbol);
    }
    
    /**
     * The program given to the LR Parser is not in the language.
     * Print the token that it failed on and the column of the character
     * that made the parser fail.
     */
    private void parserDie(Symbol symbol) {
        System.err.println(
            "Unexpected token: " + symbol +
            " at position " + token.getPos()
        );
        System.exit(1);
    }
}