package wolf;

import java.util.List;
import wolf.interfaces.Arg;
import wolf.interfaces.ListArgument;
import wolf.interfaces.Visitor;

/**
 * A list object used in the WOLF language
 * @author William Ezekiel
 * @author Kevin Dittmar
 * @author Joseph Alacqua
 * @version Apr 3, 2016
 */
public class WolfList implements Arg, ListArgument {
    List<Arg> arg_list;
    
    public WolfList(List<Arg> arg_list) {
        this.arg_list = arg_list;
    }
    
    /**
     * Accept a visitor
     * @param v a visitor
     * @return the type of this WolfList, LIST
     */
    @Override
    public Type accept(Visitor v) {
        if(v instanceof BuildSymbolTable) {
            Type type = (Type) this.arg_list.get(0).accept(v);
            return new Type(type.flat_type, true);
        }
        else if (v instanceof SemanticTypeCheck) {
            Type listType = (Type) this.arg_list.get(0).accept(v);
            for(Arg arg: arg_list) {
                Type argType = arg.accept(v);
                if(!argType.equals(listType)) {
                    throw new UnsupportedOperationException(arg.toString() + 
                        " is of type " + argType + " in a list of type " + 
                        listType);
                }
            }
            return new Type(listType.flat_type, true);
        }
        else {
            System.exit(1);
            return null;
        }
    }
}
