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
    
    /**
     * Accept a visitor
     * @param v a visitor
     * @return this, which is the type of the parameter
     */
    public Type accept(Visitor v) {
        return this;
    }
}
