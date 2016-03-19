package parse_table_generator;

/**
 *
 * @author Joseph Alacqua
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @version Mar 14, 2016
 */
import java.util.ArrayList;
public class NumberedProductionTable 
{
    ArrayList<Rule> production_list;
    
    public NumberedProductionTable()
    {
        production_list = new ArrayList<>();
    }
    
    public void addRule(Rule r)
    {
        production_list.add(r);
    }
    
    public Rule getRule(int index) {
        return production_list.get(index);
    }
    
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
