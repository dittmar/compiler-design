package wolf;

import java.util.List;
import wolf.interfaces.Arg;
import wolf.interfaces.ListArgument;

/**
 *
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @author Joe Alacqua
 * @version Apr 10, 2016
 */
public final class TypeErrorReporter {
    public static void error(String message) 
            throws UnsupportedOperationException {
        throw new UnsupportedOperationException(message);
    }
    
    public static void mismatchErrorBinary(Arg left, Type l, Arg right, Type r,
            String op, List<Type> expected_types)
            throws UnsupportedOperationException {
        StringBuilder sb = new StringBuilder();
        sb.append(op).append(" requires: ");
        
        if (expected_types == null || expected_types.isEmpty()) {
            sb.append("any type");
        }
        else {
            sb.append("[");
            StringBuilder types = new StringBuilder();
            for (Type type : expected_types) {
                types.append(type.toString())
                     .append(" ");
            }
            sb.append(types.toString().trim());
            sb.append("]");
        }
        sb.append("\n")
          .append(left.toString()).append(" is of type ").append(l)
          .append("\n")
          .append(right.toString()).append(" is of type ").append(r)
          .append("\n");
        throw new UnsupportedOperationException(sb.toString());
    }
    
    public static void mismatchErrorBinaryList(Arg arg, Type arg_t,
            ListArgument list, Type list_t, String op, List<Type> arg_expected,
            List<Type> list_expected) throws UnsupportedOperationException {
        StringBuilder sb = new StringBuilder();
        sb.append(op).append(" requires: ");
        if (arg_expected == null || arg_expected.isEmpty()) {
            sb.append("any type");
        } else {
            sb.append("[");
            StringBuilder types = new StringBuilder();
            for (Type type : arg_expected) {
                types.append(type.toString())
                     .append(" ");
            }
            sb.append(types.toString().trim());
            sb.append("]");
        }
        sb.append(";\n");
        if (list_expected == null || list_expected.isEmpty()) {
            sb.append("any list type");
        } else {
            sb.append("[");
            StringBuilder types = new StringBuilder();
            for (Type type : list_expected) {
                types.append(type.toString())
                     .append(" ");
            }
            sb.append(types.toString().trim());
            sb.append("]");
        }
        
        sb.append("\n")
          .append(list.toString()).append(" is of type ").append(list_t)
          .append("\n")
          .append(arg.toString()).append(" is of type ").append(arg_t)
          .append("\n");
        throw new UnsupportedOperationException(sb.toString());
    }
}
