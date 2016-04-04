package wolf;

import wolf.interfaces.ListArgument;
import wolf.interfaces.Arg;
import wolf.interfaces.Visitor;

/**
 * A list object used in the WOLF language
 * @author William Ezekiel
 * @author Kevin Dittmar
 * @author Joseph Alacqua
 * @version Apr 3, 2016
 */
public class WolfList implements Arg, ListArgument {
    ArgList arg_list;
    
    /**
     * Accept a visitor
     * @param v a visitor
     * @return the type of this WolfList, LIST
     */
    @Override
    public Object accept(Visitor v) {
        return Type.LIST;
    }
}
