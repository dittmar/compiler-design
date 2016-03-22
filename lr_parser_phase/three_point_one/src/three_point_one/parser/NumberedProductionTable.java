package three_point_one.parser;

/**
 * A Numbered Production Table. Technical term for Grammar.
 * @author Joseph Alacqua
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @version Mar 14, 2016
 */
import java.util.ArrayList;
public class NumberedProductionTable  {
    ArrayList<Rule> productionList;
    
    /**
     * Create a numbered production table.
     */
    public NumberedProductionTable() {
        productionList = new ArrayList<>();
    }
    
    /**
     * Add a rule to the table
     * @param r a rule.
     */
    public void addRule(Rule r) {
        productionList.add(r);
    }
    
    /**
     * @param index an integer index.
     * @return the rule with the current index.
     */
    public Rule getRule(int index) {
        return productionList.get(index);
    }
    
    /**
     * @return the string representation of this numbered production table. 
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int counter = 0;
        for (Rule rule : productionList) {
            sb.append(counter++);
            sb.append(") ");
            sb.append(rule);
            sb.append("\n");
        }
        return sb.toString();
    }
}
