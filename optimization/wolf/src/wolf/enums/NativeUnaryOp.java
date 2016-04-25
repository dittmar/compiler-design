package wolf.enums;

import wolf.interfaces.UnaryOp;
import wolf.interfaces.Visitor;
import wolf.node.TIdentity;
import wolf.node.TLogicalNot;
import wolf.node.TNeg;
import wolf.node.TPrint;

/**
 * Enumerated type representing WOLF's native unary operations
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @author Joseph Alacqua
 * @version Apr 3, 2016
 */
public enum NativeUnaryOp implements UnaryOp {
    NEG(TNeg.class),
    LOGICAL_NOT(TLogicalNot.class),
    IDENTITY(TIdentity.class),
    PRINT(TPrint.class);
    
    private Class token_class;
    
    /**
     * Create a NativeUnaryOp
     * @param token_class the name of the token representing the
     *      native unary operation
     */
    NativeUnaryOp(Class token_class) {
        this.token_class = token_class;
    }
    
    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
    
    @Override
    public String toString() {
        return EnumReporter.report(token_class);
    }

    public Class getTokenClass() {
        return token_class;
    }
}
