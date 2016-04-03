package wolf;

import wolf.interfaces.BinOp;
import wolf.interfaces.UnaryOp;
import wolf.interfaces.UserFuncName;
import wolf.interfaces.WolfFunction;

/**
 *
 * @author Kevin Dittmar
 * @version Apr 3, 2016
 */
public class WolfLambda implements WolfFunction, BinOp, UnaryOp, UserFuncName {
    Sig sig;
    WolfFunction function;
}
