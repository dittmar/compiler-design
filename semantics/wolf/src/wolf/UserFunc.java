package wolf;

import wolf.interfaces.UserFuncName;
import wolf.interfaces.Visitor;
import wolf.interfaces.WolfFunction;

/**
 * A user defined function
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @author Joseph Alacqua
 * @version Apr 3, 2016
 */
public class UserFunc implements WolfFunction {
    UserFuncName user_func_name;
    ArgList arg_list;
    
    /**
     * Accept a visitor
     * @param v a visitor
     */
    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
