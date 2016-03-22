package three_point_one.parser;

/**
 *
 * @author Kevin Dittmar
 * @version Mar 19, 2016
 */
public class TableCell 
{
    public enum Action 
    {
        SHIFT, REDUCE, GOTO, ACCEPT, ERROR
    }
    
    Action action;
    int state_id;
    
    public TableCell(Action action, int state_id)
    {
        this.action = action;
        this.state_id = state_id;
    }
    
    public TableCell() {
        this.action = Action.ERROR;
    }
    
    public String toString() {
        switch(action) {
            case SHIFT:
                return "s" + state_id;
            case REDUCE:
                return "r" + state_id;
            case GOTO:
                return "g" + state_id;
            case ACCEPT:
                return "a";
            default:
                return "     ";
        }
    }
}
