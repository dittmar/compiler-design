package wolf;

import java.util.List;
import wolf.interfaces.Visitor;

/**
 * A function signature
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @author Joseph Alacqua
 * @version Apr 3, 2016
 */
public class Sig {
    List<Identifier> sig_args;
    
    /**
     * Accept a visitor
     * @param v a visitor
     */
    public void accept(Visitor v) {
        v.visit(this);
    }
}
