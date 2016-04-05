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
        TableValue tv = bst.program_table.lookup(this).table_value;
        return tv.type;
    }
    
    public String toString() {
        return "<" + identifier.toString() + ">";
    }
}
