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
        GrammarParser g_parser = new GrammarParser("test/parse_table_generator/resources/g3.1.txt");
        g_parser.parse();
        System.out.println("Terminals:");
        System.out.println(g_parser.terminals);
        System.out.println("Nonterminals:");
        System.out.println(g_parser.nonterminals);
        System.out.println("Rule Lookup Table:");
        System.out.println(g_parser.nonterminal_rule_lookup_table);
        System.out.println("Production Table:");
        System.out.println(g_parser.production_table);
    }
}
