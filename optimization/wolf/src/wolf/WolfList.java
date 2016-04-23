package wolf;

import java.util.List;
import wolf.interfaces.Arg;
import wolf.interfaces.ListArgument;
import wolf.interfaces.ListElement;
import wolf.interfaces.Visitor;

/**
 * A list object used in the WOLF language
 * @author William Ezekiel
 * @author Kevin Dittmar
 * @author Joseph Alacqua
 * @version Apr 3, 2016
 */
public class WolfList implements Arg, ListArgument {
    List<ListElement> list_elements;
    
    public WolfList(List<ListElement> arg_list) {
        this.list_elements = arg_list;
    }
    
    /**
     * Accept a visitor
     * @param v a visitor
     * @return the type of this WolfList, LIST
     */
    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
        /*
        if(v instanceof BuildSymbolTable) {
            Type type = this.list_elements.get(0).accept(v);
            return new Type(type.flat_type, true);
        }
        else if (v instanceof SemanticTypeCheck) {
            Type listType = this.list_elements.get(0).accept(v);
            for(ListElement list_element: list_elements) {
                Type argType = list_element.accept(v);
                if(!argType.equals(listType)) {
                    TypeErrorReporter.mismatchListItemWithListType(list_element,
                            argType, listType);
                }
            }
            return new Type(listType.flat_type, true);
        }
        else {
            System.exit(1);
            return null;
        }*/
    }
}
