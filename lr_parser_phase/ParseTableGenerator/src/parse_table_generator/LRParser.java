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
    
    public LRParser(ParseTable pt) {
        parse_table = pt;
        currentState = 1;
    }
    
    public void parse(String input, Stack<Symbol> stack) {
        Symbol symbol = eat(input);
    }
    
    public Symbol eat(String input) {
        
    }
}
