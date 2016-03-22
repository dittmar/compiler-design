package wolf.parser;

/**
 * An arc that connects to states. A state transitions
 * to another state via a transition symbol.
 * @author Joseph Alacqua
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @version Mar 14, 2016
 */
public class Arc {
    Symbol transition_symbol;
    State from;
    State to;
    
    /**
     * Create an arc using two states and a symbol.
     * @param s the transition symbol.
     * @param from the current state, transition from this state.
     * @param to the new state, transition to this state.
     */
    public Arc(Symbol s, State from, State to) {
        transition_symbol = s;
        this.from = from;
        this.to = to;
    }
    
    /**
     * Check if this arc equals another object. Two arcs are equal if
     * their to and from states are the same and their transition symbols
     * are the same.
     * @param obj the object being compared.
     * @return true if this arc is equal to the object.
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Arc)){
            return false;
        }
        Arc arc = (Arc) obj;
        return arc.from.equals(from) && 
               arc.to.equals(to) && 
               arc.transition_symbol.equals(transition_symbol);
    }
    
    /**
     * @return the hash code of this arc.
     */
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + from.hashCode();
        result = 31 * result + to.hashCode();
        result = 31 * result + transition_symbol.hashCode();
        return result;
    }
    
    /**
     * @return the String representation of this arc.
     */
    @Override
    public String toString() {
        return from.id + " --"+ transition_symbol + "-> " + to.id;
    }
}
