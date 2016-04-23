package wolf;

import wolf.interfaces.Arg;
import wolf.interfaces.BinOp;
import wolf.interfaces.ListArgument;
import wolf.interfaces.ListElement;
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
    UserFuncName, ListElement {
    TIdentifier identifier;
    
    public Identifier(TIdentifier identifier) {
        this.identifier = identifier;
    }
    
    /**
     * Accepts a visitor
     * @param v a visitor
     * @return the type of this identifier
     */
    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
        /*TableValue tv = null;
        Binding b = v.getCurrentDefTable().lookup(this);
        if(b == null) {
            SymbolTable parent = v.getCurrentDefTable().parent_table;
            while(parent != null && tv == null) {
                b = parent.lookup(this);
                if(b == null) {
                    parent = parent.parent_table;
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
            throw new IllegalArgumentException();
        }
        return tv.type;*/
    }
    
    @Override
    public String toString() {
        return identifier.toString().trim();
    }
}
