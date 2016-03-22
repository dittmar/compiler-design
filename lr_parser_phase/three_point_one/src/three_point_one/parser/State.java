package three_point_one.parser;

/**
 *
 * @author Joseph Alacqua
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @version Mar 14, 2016
 */

import java.util.Set;
public class State {

    Set<Item> items;
    int id;
    boolean is_accept_state = false;
    
    public State(Set<Item> itemSet) {
        items = itemSet;
    }
    
    public State(Set<Item> itemSet, int id) {
        items = itemSet;
        this.id = id;
    }
    
    public void setAcceptState()
    {
        is_accept_state = true;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof State) {
            State state = (State) obj;
            return hasSameItems(state);
        }
        return false;
    }
    
    public boolean hasSameItems(State state) {
        return state.items.equals(items);
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        //sb.append(id).append(".");
        sb.append("{");
        for(Item item: items) {
            sb.append(item).append("\t");
        }
        sb.append("}").append("\n");
        return sb.toString();
    }
    
    @Override
    public int hashCode() {
        int result = 17;
        for(Item item : items) {
            result *= 31; 
            result += item.hashCode();
        }
        return result;
    }
}
