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
    int id_number;
    
    public TableCell(Action action, int state_id)
    {
        this.action = action;
        this.id_number = state_id;
    }
    
    public TableCell() {
        this.action = Action.ERROR;
    }
    
    public String toString() {
        switch(action) {
            case SHIFT:
                return "s" + id_number;
            case REDUCE:
                return "r" + id_number;
            case GOTO:
                return "g" + id_number;
            case ACCEPT:
                return "a";
            default:
                return "     ";
        }
    }
}
