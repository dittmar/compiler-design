package wolf;

import wolf.interfaces.Arg;
import wolf.interfaces.BinOp;
import wolf.interfaces.ListArgument;
import wolf.interfaces.UnaryOp;
import wolf.interfaces.UserFuncName;
import wolf.interfaces.Visitor;
import wolf.node.TIdentifier;

/**
 * An identifier, a name used to store the values of a program
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @author Jospeh Alacqua
 * @version Apr 3, 2016
 */
public class Identifier implements BinOp, UnaryOp, Arg, ListArgument,
    UserFuncName {
    TIdentifier identifier;
    
    public Identifier(TIdentifier identifier) {
        this.identifier = identifier;
    }
    
    /**
     * Accepts a visitor
     * @param v a visitor
     */
    @Override
    public Object accept(Visitor v) {
        BuildSymbolTable bst = (BuildSymbolTable) v;
        TableValue tv = null;
        Binding b = bst.current_def_table.lookup(this);
        if(b == null) {
            SymbolTable parent = bst.current_def_table.parent_table;
            boolean hasParent = parent != null;
            while(hasParent && tv == null) {
                b = parent.lookup(this);
                if(b == null) {
                    parent = parent.parent_table;
                    hasParent = parent != null;
                }
                else {
                    tv = b.table_value;
                }
            }
        }
        else {
            tv = b.table_value;
        }
        if(tv == null) {
            return null;
        }
        return tv.type;
    }
    
    public String toString() {
        return identifier.toString().trim();
    }
}
