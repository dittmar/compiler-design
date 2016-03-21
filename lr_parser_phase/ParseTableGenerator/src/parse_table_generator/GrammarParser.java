package parse_table_generator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
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
    StartSymbol start_symbol;
    EndSymbol end_symbol;
    NonterminalRuleLookupTable nonterminal_rule_lookup_table;
    NumberedProductionTable production_table;
    
    private int rule_number = 0;
    private List<Terminal> sorted_terminals;
    private List<Nonterminal> sorted_nonterminals;
    public GrammarParser(String filename)
    {
        nonterminal_rule_lookup_table = new NonterminalRuleLookupTable();
        production_table = new NumberedProductionTable();
        nonterminals = new LinkedHashSet<>();
        terminals = new LinkedHashSet<>();
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
                rule_number++;
            }
            // Sort terminals and nonterminals by length to 
            // prevent parsing errors
            sorted_terminals = new ArrayList<>(terminals);
            sorted_terminals.sort((Terminal t1, Terminal t2) -> 
                t2.getName().length() - t1.getName().length());
            
            sorted_nonterminals = new ArrayList<>(nonterminals);
            sorted_nonterminals.sort((Nonterminal nt1, Nonterminal nt2) ->
                nt2.getName().length() - nt1.getName().length());
            
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
                        terminals_sb.append(" ").append(next);
                    }
                    // We don't want to read the terminals as part of the
                    // grammar productions
                    lines_to_skip++;
                }
                // The start symbol is specified on the line after the 
                // terminals.
                start_symbol = new StartSymbol(scanner.nextLine().trim());
                // A start_symbol is a nonterminal, so add it to the list.
                nonterminals.add(start_symbol);
                // We don't want to read the start symbol as part of the
                // grammar productions.
                lines_to_skip++;
                
                // The end symbol is specified on the line after the start
                // symbol
                end_symbol = new EndSymbol(scanner.nextLine().trim());
                // We don't want to read the end symbol as part of the
                // grammar productions.
                lines_to_skip++;
            }
            catch (NoSuchElementException e)
            {
                System.err.println(
                    "Terminal list in " + filename + " never ended");
                System.exit(1);
            }
            
            // Add all of the terminals to the set.
            for (String terminal : terminals_sb.toString().trim().split("\\s"))
            {
                terminals.add(new Terminal(terminal));
            }
            // Add the end symbol after all of the other terminals so that
            // it appears at the end of the parse table later.
            terminals.add(end_symbol);
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
            if (rule_string.equals(end_symbol.getName()))
            {
                rule_string = "";
                rule_symbols.add(end_symbol);
            }
            else if (rule_string.matches("[A-Z].*"))
            {
                for (Nonterminal nt : sorted_nonterminals)
                {
                    if (rule_string.startsWith(nt.getName()))
                    {
                        rule_string = rule_string.replaceFirst(nt.getName(), "");
                        rule_symbols.add(new Nonterminal(nt.getName()));
                        continue START;
                    }
                }
                failedToParse(rule_string);
            }
            else
            {
                for (Terminal t : sorted_terminals)
                {
                    if (rule_string.startsWith(t.getName()))
                    {
                        rule_string = rule_string.substring(t.getName().length());
                        rule_symbols.add(new Terminal(t.getName()));
                        continue START;
                    }
                }
                failedToParse(rule_string);
            }
        }

        Rule rule = new Rule(rule_nt, rule_symbols);
        production_table.addRule(rule);
        nonterminal_rule_lookup_table.add(rule_nt, rule);
        rule_number++;
    }
    
    // Didn't match terminals or non-terminals.
    // Print error and exit.
    private void failedToParse(String rule_string)
    {
        System.err.println(
            "Failed to parse " + rule_number + 
            ":" + rule_string + " remaining"
        );
        System.exit(1);  
    }
}