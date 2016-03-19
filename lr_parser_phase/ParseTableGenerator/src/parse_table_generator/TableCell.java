package parse_table_generator;

/**
 *
 * @author Kevin Dittmar
 * @version Mar 19, 2016
 */
public class TableCell 
{
    public enum Action 
    {
        SHIFT, REDUCE, GOTO, ERROR
    }
    
    Action action;
    int state_id;
    
    public TableCell(Action action, int state_id)
    {
        this.action = action;
        this.state_id = state_id;
    }
}
