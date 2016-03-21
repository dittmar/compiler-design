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
    
    public LRParser(ParseTable pt) {
        parse_table = pt;
        currentState = 1;
    }
    
    public void parse(String input) {
        // lex it
        List<Token> tokens = lexInput(input);
        // parse it
        
        
        // bop it
    }
    
    public List<Token> lexInput(String input) {
        List<Token> tokens = new ArrayList();
        // Lexer magic, parseTable has token to symbol magic. 
        
        return tokens;
    }
}
