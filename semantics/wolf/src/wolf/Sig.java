package wolf;

import java.util.List;
import wolf.interfaces.Visitor;

/**
 *
 * @author Kevin Dittmar
 * @version Apr 3, 2016
 */
public class Sig {
    List<Identifier> sig_args;
    public void accept(Visitor v) {
        v.visit(this);
    }
}
