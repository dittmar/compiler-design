/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parse_table_generator;

import java.util.Stack;

/**
 *
 * @author williamezekiel
 */
public class LRParser {
    ParseTable parse_table;
    int currentState;
    Stack<Symbol> stack;
    private Token token;
    private ThreePointOneLexing lexer;
    
    public LRParser(ParseTable pt) {
        parse_table = pt;
        currentState = 1;
        lexer = new WolfLexing(filename);
    }
    
    public void parse(String input) {
        // lex it
        Terminal next_terminal = tokenToTerminal(nextValidToken());
        while (next_terminal != null)
        {
            /* do parse stuff
                TODO
            */
            
            // Get next terminal
            next_terminal = tokenToTerminal(nextValidToken());
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
     * 
     * @param t is the token whose terminal should be found
     * @return the Terminal corresponding to Token t.
     */
    private Terminal tokenToTerminal(Token t)
    {
        return parse_table.getTerminalLookupTable().get(t.getText());
    }
}
