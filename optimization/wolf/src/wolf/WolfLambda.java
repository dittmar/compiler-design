package wolf;

import wolf.interfaces.BinOp;
import wolf.interfaces.UnaryOp;
import wolf.interfaces.UserFuncName;
import wolf.interfaces.Visitor;
import wolf.interfaces.WolfFunction;

/**
 * A lambda object used for lambdas in the WOLF language
 * @author (Kevin Dittmar)
 * @author (William Ezekiel)
 * @author (Joseph Alacqua)
 * @version Apr 3, 2016
 */
public class WolfLambda extends WolfFunction 
    implements BinOp, UnaryOp, UserFuncName {
    Sig sig;
    WolfFunction function;
    private int id_number;
    
    public WolfLambda(Sig sig, WolfFunction function) {
        this.sig = sig;
        this.function = function;
    }
    
    /**
     * Accept a visitor
     * @param v a visitor
     * @return the type of the lambda
     */
    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
    
    /**
     * @return the lambda's identification number 
     */
    public int getId() {
        return id_number;
    }
    
    /**
     * Give the lambda function an identification number
     * @param id_number the id of the lambda
     */
    public void setId(int id_number) {
        this.id_number = id_number;
    }
    
    @Override
    public String toString() {
        return "\\" + sig.toString();
    }
}
