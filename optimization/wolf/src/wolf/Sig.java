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
    List<SigArg> sig_args;
    
    public Sig(List<SigArg> sig_args) {
        this.sig_args = sig_args;
    }

    /**
     * Accept a visitor
     * @param v a visitor
     */
    public Object accept(Visitor v) {
        return v.visit(this);
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (int i = 0; i < sig_args.size(); i++) {
            sb.append(sig_args.get(i));
            if (i < sig_args.size() - 1) {
                sb.append(",");
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
