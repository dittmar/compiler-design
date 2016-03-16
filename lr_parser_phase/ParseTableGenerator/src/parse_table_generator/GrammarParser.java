package parse_table_generator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author Kevin Dittmar
 * @version Mar 15, 2016
 */
public class GrammarParser 
{
    String filename;
    Set<Nonterminal> nonterminals;
    Set<Terminal> terminals;
    NonterminalRuleLookupTable nonterminal_rule_lookup_table;
    NumberedProductionTable production_table;
    
    public GrammarParser(String filename)
    {
        nonterminal_rule_lookup_table = new NonterminalRuleLookupTable();
        production_table = new NumberedProductionTable();
        nonterminals = new HashSet<>();
        this.filename = filename;
    }
    
    public void parse()
    {
        makeNonterminalSet();
        try
        {
            Scanner scanner = new Scanner(new File(filename));
            while (scanner.hasNextLine())
            {
                String next_line = scanner.nextLine();
                addProductionRule(next_line);
            }
        }
        catch (FileNotFoundException e)
        {
            System.err.println(e.getMessage());
        }
    }
    
    private void makeNonterminalSet()
    {
        try
        {
            Scanner scanner = new Scanner(new File(filename));
            while (scanner.hasNextLine())
            {
                String next_nonterminal = scanner.nextLine().split("=", 2)[0];
                nonterminals.add(new Nonterminal(next_nonterminal.trim()));
            }
        }
        catch (FileNotFoundException e)
        {
            System.err.println(e.getMessage());
        }
    }
    
    private void addProductionRule(String next_line)
    {
        String[] next = next_line.split("=", 2);
        Nonterminal rule_nt = new Nonterminal(next[0].trim());
        ArrayList<Symbol> rule_symbols = new ArrayList<>();
        String rule_string = next[1].trim();
        START: while (!rule_string.isEmpty())
        {
            for (Nonterminal nt : nonterminals)
            {
                if (rule_string.startsWith(nt.getName()))
                {
                    rule_string = rule_string.replaceFirst(nt.getName(), "");
                    rule_symbols.add(new Nonterminal(nt.getName()));
                    continue START;
                }
            }
            for (Terminal t : terminals)
            {
                if (rule_string.startsWith(t.getName()))
                {
                    rule_string = rule_string.replaceFirst(t.getName(), "");
                    rule_symbols.add(new Terminal(t.getName()));
                    continue START;
                }
            }
            rule_symbols.add(new Terminal(rule_string.substring(0, 1)));
            rule_string = rule_string.replaceFirst(".", "");
        }

        Rule rule = new Rule(rule_nt, rule_symbols);
        production_table.addRule(rule);
        nonterminal_rule_lookup_table.add(rule_nt, rule);
    }
}
