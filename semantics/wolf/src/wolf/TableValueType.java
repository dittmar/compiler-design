package wolf;

import wolf.interfaces.TableValue;

/**
 * A TableValue that stores a type
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @author Joseph Alacqua
 * @version Apr 3, 2016
 */
public class TableValueType implements TableValue {
    Type type;
    
    /**
     * Create a TableValueType
     * @param type a type
     */
    public TableValueType(Type type) {
        this.type = type;
    }
    
    /**
     * @return the string representation of this table value type
     */
    @Override
    public String toString() {
        return type.toString();
    }
}
