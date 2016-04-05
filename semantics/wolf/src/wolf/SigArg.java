package wolf;

import wolf.interfaces.Visitor;

/**
 *
 * @author Kevin Dittmar
 * @version Apr 5, 2016
 */
public class SigArg {
    Type type;
    Identifier identifier;
    
    public SigArg(Type type, Identifier identifier) {
        this.type = type;
        this.identifier = identifier;
    }
    
    public void visit(Visitor v) {
        v.visit(this);
    }
}
