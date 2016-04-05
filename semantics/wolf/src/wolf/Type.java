package wolf;

import wolf.interfaces.Visitor;

/**
 *
 * @author Kevin Dittmar
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
     * Accept a visitor
     * @param v a visitor
     * @return this, which is the type of the parameter
     */
    public Type accept(Visitor v) {
        return this;
    }
}
