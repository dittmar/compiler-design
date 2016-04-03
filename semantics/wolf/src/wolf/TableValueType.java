package wolf;

/**
 *
 * @author Kevin Dittmar
 * @version Apr 3, 2016
 */
public class TableValueType implements TableValue {
    Type type;
    
    public TableValueType(Type type) {
        this.type = type;
    }
    
    @Override
    public String toString() {
        return type.toString();
    }
}
