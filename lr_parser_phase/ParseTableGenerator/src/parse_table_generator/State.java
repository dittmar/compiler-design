package parse_table_generator;

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
        isAcceptState = true;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof State) {
            return ((State) obj).items.equals(items);
        }
        return false;
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
