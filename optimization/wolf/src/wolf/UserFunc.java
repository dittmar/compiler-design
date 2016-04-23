package wolf;

import wolf.interfaces.Args;
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
    Args arg_list;
    
    public UserFunc(UserFuncName user_func_name, Args arg_list) {
        this.user_func_name = user_func_name;
        this.arg_list = arg_list;
    }
    
    /**
     * Accept a visitor
     * @param v a visitor
     * @return the type of this user-defined function
     */
    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
