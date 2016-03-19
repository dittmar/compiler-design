/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package parse_table_generator;

import java.nio.file.Paths;
import java.util.HashSet;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Kevin Dittmar
 */
public class GrammarParserTest {
    
    public GrammarParserTest()
    {
        // Empty Constructor
    }

    /**
     * Test of parse method, of class GrammarParser.
     */
    @Test
    public void testParse() 
    {
        // This test is terrible.  It should use asserts.  It doesn't because
        // I haven't written them yet.
        System.out.println("parse");
        //HashSet<Terminal> terminals = new HashSet<>();
        //HashSet<Nonterminal> nonterminals = new HashSet<>();
        // My File got sent somewhere else after setting it up.
        String billFile = "/Users/williamezekiel/Documents/Compiler_Design_Theory/cdt_git/compiler-design/lr_parser_phase/ParseTableGenerator/test/parse_table_generator/resources/g3.1.txt";
        String kevFile = "test/parse_table_generator/resources/g3.1.txt";
        GrammarParser g_parser = new GrammarParser(kevFile);
        g_parser.parse();
        System.out.println("Terminals:");
        System.out.println(g_parser.terminals);
        System.out.println("Nonterminals:");
        System.out.println(g_parser.nonterminals);
        System.out.println("Start Symbol: " + g_parser.start_symbol);
        System.out.println("End Symbol: " + g_parser.end_symbol);
        System.out.println("Rule Lookup Table:");
        System.out.println(g_parser.nonterminal_rule_lookup_table);
        System.out.println("Production Table:");
        System.out.println(g_parser.production_table);
    }
}
