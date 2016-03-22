package three_point_one.parser;

/**
 * A table cell consists of an identification number and an action. 
 * @author Kevin Dittmar
 * @version Mar 19, 2016
 */
public class TableCell 
{
    /**
     * Action enums for possible table cell actions. 
     */
    public enum Action 
    {
        SHIFT, REDUCE, GOTO, ACCEPT, ERROR
    }
    
    Action action;
    int id_number;
    
    /**
     * Create a table cell with the specified action and identification number
     * @param action
     * @param state_id 
     */
    public TableCell(Action action, int state_id)
    {
        this.action = action;
        this.id_number = state_id;
    }
    
    /**
     * Create a empty table cell. An empty table cell represents a program
     * being rejected by the parser.
     */
    public TableCell() {
        this.action = Action.ERROR;
    }
    
    /**
     * @return the string representation of this table cell.
     */
    @Override
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
