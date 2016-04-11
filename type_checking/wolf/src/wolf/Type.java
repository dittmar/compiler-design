package wolf;

import wolf.interfaces.Visitor;

/**
 * Represents a type in WOLF.  That type can be one of a list of flat types
 * (Strings, ints, floats), and it can be a list or not.
 * @author (Kevin Dittmar)
 * @author (William Ezekiel)
 * @author (Joseph Alacqua)
 * @version Apr 5, 2016
 */
public class Type {
    FlatType flat_type;
    boolean is_list;
    
    public Type(FlatType flat_type) {
        this.flat_type = flat_type;
        is_list = false;
    }
    
    public Type(FlatType flat_type, boolean is_list) {
        this.flat_type = flat_type;
        this.is_list = is_list;
    }
    
    public boolean equals(Object o) {
        if(o instanceof Type) {
            Type t = (Type) o;
            return this.flat_type == t.flat_type && this.is_list == t.is_list;
        }
        return false;
    }
    
    public String toString() {
        return (is_list) ? "LIST_"+ flat_type.toString() : flat_type.toString();
    }
    
    /**
     * Decide if a type is numeric
     * @return true if the FlatType is FLOAT or INTEGER, false otherwise.
     */
    public boolean isNumeric() {
        return (flat_type == FlatType.FLOAT || flat_type == FlatType.INTEGER);
    }
    
    /**
     * Accept a visitor
     * @param v a visitor
     * @return this, which is the type of the parameter
     */
    public Type accept(Visitor v) {
        return this;
    }
}
