package wolf.parser;

/**
 * A state is a set of items and an id item. 
 * @author Joseph Alacqua
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @version Mar 14, 2016
 */

import java.util.Set;
public class State {

    Set<Item> items;
    int id;
    
    /**
     * Create a state
     * @param itemSet a set of items.
     */
    public State(Set<Item> itemSet) {
        items = itemSet;
    }
    
    /**
     * Create a state with a specific id.
     * @param itemSet a set of items
     * @param id a numeric identification
     */
    public State(Set<Item> itemSet, int id) {
        items = itemSet;
        this.id = id;
    }
    
    /**
     * Check if this state equals another object. Two states are equal if they
     * have the same set of items.
     * @param obj an object.
     * @return true if this state equals the given object.
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof State) {
            State state = (State) obj;
            return hasSameItems(state);
        }
        return false;
    }
    
    /**
     * @param state a state
     * @return true if the given state has the same items as this state
     */
    public boolean hasSameItems(State state) {
        return state.items.equals(items);
    }
    
    /**
     * @return the string representation of this state
     */
    @Override
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
    
    /**
     * @return the integer hash code.
     */
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
