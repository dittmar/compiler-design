package wolf.interfaces;

/**
 * Interface for an Arg in WOLF
 * @author William Ezekiel
 * @author Kevin Dittmar
 * @author Joseph Alacqua
 * @version Apr 3, 2016
 */
public abstract class Arg {
    private WolfFunction owning_function;
    public Object accept(Visitor v) {
        return null;
    };
    
    public void setOwningFunction(WolfFunction owning_function) {
        this.owning_function = owning_function;
    }
    
    public WolfFunction getOwningFunction() {
        return owning_function;
    }
}
