package three_point_one.parser;

/**
 * A Numbered Production Table. Technical term for Grammar.
 * @author Joseph Alacqua
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @version Mar 14, 2016
 */
import java.util.ArrayList;
public class NumberedProductionTable 
{
    ArrayList<Rule> production_list;
    
    /**
     * Create a numbered production table.
     */
    public NumberedProductionTable()
    {
        production_list = new ArrayList<>();
    }
    
    /**
     * Add a rule to the table
     * @param r a rule.
     */
    public void addRule(Rule r)
    {
        production_list.add(r);
    }
    
    /**
     * @param index an integer index.
     * @return the rule with the current index.
     */
    public Rule getRule(int index) {
        return production_list.get(index);
    }
    
    /**
     * @return the string representation of this numbered production table. 
     */
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        int counter = 0;
        for (Rule rule : production_list)
        {
            sb.append(counter++);
            sb.append(") ");
            sb.append(rule);
            sb.append("\n");
        }
        return sb.toString();
    }
}
