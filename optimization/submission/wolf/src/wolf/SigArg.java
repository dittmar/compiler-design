package wolf;

import wolf.interfaces.Visitor;

/**
 * An argument in a signature.  It is an identifier with a type.
 * @author (Kevin Dittmar)
 * @author (William Ezekiel)
 * @author (Joseph Alacqua)
 * @version Apr 5, 2016
 */
public class SigArg {
    Type type;
    Identifier identifier;
    
    public SigArg(Type type, Identifier identifier) {
        this.type = type;
        this.identifier = identifier;
    }


    public Object accept(Visitor v) {
        return v.visit(this);
    }
    
    @Override
    public String toString() {
        return type + " " + identifier;
    }
}
