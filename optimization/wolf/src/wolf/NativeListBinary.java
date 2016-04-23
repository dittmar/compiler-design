package wolf;

import java.util.ArrayList;
import java.util.List;
import wolf.enums.NativeListBinaryOp;
import wolf.interfaces.Arg;
import wolf.interfaces.ListArgument;
import wolf.interfaces.Visitor;
import wolf.interfaces.WolfFunction;

/**
 * A Native List Binary, or binary functions native to WOLF that operate
 * on lists
 * 
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @author Joseph Alacqua
 * @version Apr 3, 2016
 */
public class NativeListBinary implements WolfFunction {
    NativeListBinaryOp binary_op;
    Arg arg;
    ListArgument list_argument;
    
    public NativeListBinary(NativeListBinaryOp binary_op, Arg arg, 
        ListArgument list_argument) {
        this.binary_op = binary_op;
        this.arg = arg;
        this.list_argument = list_argument;
    }
    
    /**
     * Accept a visitor
     * @param v a visitor
     * @return the return type of the binary list function
     */
    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
        /*Type arg_type = (Type) v.visit(arg);
        Type list_type = (Type) v.visit(list_argument);
        switch(binary_op) {
            case APPEND:
            case PREPEND:
                if(!list_type.is_list ||
                   !arg_type.flat_type.equals(list_type.flat_type)) {
                    List<Type> list_types = new ArrayList<>();
                    list_types.add(list_type);
                    List<Type> arg_types = new ArrayList<>();
                    arg_types.add(new Type(list_type.flat_type));
                    TypeErrorReporter.mismatchErrorBinaryList(
                        arg, arg_type, 
                        list_argument, list_type,
                        binary_op.toString(), arg_types, list_types
                    );
                }
                return list_type;
            default:
                System.err.println("Invalid Native List Binary Operation!");
                return null;
        }*/
    }
    
    /**
     * @return string representation of a list binary op
     */
    @Override
    public String toString() {
        return binary_op.toString() + "(" + 
            arg.toString() + ", " + list_argument.toString() + ")";
    }
}
