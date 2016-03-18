package parse_table_generator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.NoSuchElementException;
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
        terminals = new HashSet<>();
        this.filename = filename;
    }
    
    public void parse()
    {
        int lines_to_skip = makeTerminalAndNonterminalSets();
        try
        {
            Scanner scanner = new Scanner(new File(filename));
            for (; lines_to_skip > 0; lines_to_skip--)
            {
                scanner.nextLine();
            }
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
    
    /**
     * 
     * @return number of lines to skip to get to grammar
     */
    private int makeTerminalAndNonterminalSets()
    {
        int lines_to_skip = 0;
        try
        {
            Scanner scanner = new Scanner(new File(filename));
            /* Make terminal set from the list of terminals at start of the
             * file.
             */
            boolean more_terminals = true;
            StringBuilder terminals_sb = new StringBuilder();
            try
            {
                while (more_terminals) {
                    String next = scanner.nextLine();
                    if (next.isEmpty() || next.matches("\\s+"))
                    {
                        more_terminals = false;
                    }
                    else
                    {
                        terminals_sb.append(next);
                    }
                    lines_to_skip++;
                }
            }
            catch (NoSuchElementException e)
            {
                System.err.println(
                    "Terminal list in " + filename + " never ended");
                System.exit(1);
            }
            
            // Add all of the terminals to the set.
            for (String terminal : terminals_sb.toString().split("\\s"))
            {
                terminals.add(new Terminal(terminal));
            }
            
            // Make nonterminal set from the rules
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
        
        return lines_to_skip;
    }
    
    private void addProductionRule(String next_line)
    {
        String[] next = next_line.split("=", 2);
        Nonterminal rule_nt = new Nonterminal(next[0].trim());
        ArrayList<Symbol> rule_symbols = new ArrayList<>();
        // Remove all whitespace
        String rule_string = next[1].replaceAll("\\s+", "");
        START: while (!rule_string.isEmpty())
        {
            if (rule_string.matches("[A-Z].*"))
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
            }
            else
            {
                for (Terminal t : terminals)
                {
                    if (rule_string.startsWith(t.getName()))
                    {
                        rule_string = rule_string.substring(t.getName().length());
                        rule_symbols.add(new Terminal(t.getName()));
                        continue START;
                    }
                }
            }
            // Didn't match terminals or non-terminals.  Print error and exit.
            System.err.println("Failed to parse:" + rule_string);
            System.exit(1);
        }

        Rule rule = new Rule(rule_nt, rule_symbols);
        production_table.addRule(rule);
        nonterminal_rule_lookup_table.add(rule_nt, rule);
    }
}