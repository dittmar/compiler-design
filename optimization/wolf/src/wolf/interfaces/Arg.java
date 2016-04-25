package wolf.interfaces;

import wolf.Type;

/**
 * Interface for an Arg in WOLF
 * @author William Ezekiel
 * @author Kevin Dittmar
 * @author Joseph Alacqua
 * @version Apr 3, 2016
 */
public abstract class Arg {
    private WolfFunction owning_function;
    private Type type;
    
    public Object accept(Visitor v) {
        return null;
    };
    
    public void setOwningFunction(WolfFunction owning_function) {
        this.owning_function = owning_function;
    }
    
    public WolfFunction getOwningFunction() {
        return owning_function;
    }
    
        /**
     * Set the type of the list
     * @param type the type of the list
     */
    public void setType(Type type) {
        this.type = type;
    }
    
    /**
     * @return the type of the list if it has been set.
     */
    public Type getType() {
        return type;
    }
}
