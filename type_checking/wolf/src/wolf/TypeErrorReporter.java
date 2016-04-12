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
    public static void mismatchErrorBinary(Arg left, Type l, Arg right, Type r,
            String op, List<Type> expected_types)
            throws UnsupportedOperationException {
        StringBuilder sb = new StringBuilder();
        sb.append(op).append(" can use: ");
        
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
        sb.append(op).append(" can use: ");
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
    
    public static void mismatchErrorUnary(Arg arg, Type type, String op,
            List<Type> expected) throws UnsupportedOperationException {
        StringBuilder sb = new StringBuilder();
        sb.append(op).append(" can use: ");
        if (expected == null || expected.isEmpty()) {
            sb.append("any type");
        } else {
            sb.append("[");
            StringBuilder types = new StringBuilder();
            for (Type t : expected) {
                types.append(t).append(" ");
            }
            sb.append(types.toString().trim());
            sb.append("]");
        }
        sb.append("\n");
        sb.append(arg.toString()).append(" is of type ").append(type);
    }
    
    public static void mismatchErrorListUnary(ListArgument arg, Type type,
            String op, List<Type> expected)
            throws UnsupportedOperationException {
        StringBuilder sb = new StringBuilder();
        sb.append(op).append(" can use: ");
        if (expected == null || expected.isEmpty()) {
            sb.append("any list type");
        } else {
            sb.append("[");
            StringBuilder types = new StringBuilder();
            for (Type t : expected) {
                types.append(t).append(" ");
            }
            sb.append(types.toString().trim());
            sb.append("]");
        }
        sb.append("\n");
        sb.append(arg.toString()).append(" is of type ").append(type);
    }
    
    /**
     * When creating a user defined function, this error message occurs if
     * the expected type the function should return differs from what is
     * actually given.
     * @param defGiven the type actually given
     * @param defExpected the expected type
     * @param defName the name of the user defined function. Lambda functions
     *  use the format Lambda#.
     */
    public static void mismatchDefType(Type defGiven, Type defExpected, String defName) {
        StringBuilder sb = new StringBuilder();
        sb.append("ERROR - Expecting Type ").append(defExpected)
                .append(" in \"").append(defName).append("\".")
                .append(" Given Type: ").append(defGiven);
        System.out.println(sb.toString());
    }

    public static void mismatchArgumentFormat(List<Type> argFormatGiven,
            List<Type> argFormatExpected, String funcName) {
        StringBuilder sb = new StringBuilder();
        sb.append("ERROR - Function \"").append(funcName).append("\"");
        if(argFormatExpected.isEmpty()) {
            sb.append(" expecting no arguments.");
        }
        else {
            sb.append(" expecting arguments of the form (");
            for(int i = 0; i < argFormatExpected.size()-1; i++) {
                Type type = argFormatExpected.get(i);
                sb.append(type).append(", ");
            }
            sb.append(argFormatExpected.get(argFormatExpected.size()-1))
                    .append(").");
        }
        sb.append(" Given Format: ");
        if(argFormatExpected.isEmpty()) {
            sb.append(" None.");
        }
        else {
            sb.append(" (");
            for(int i = 0; i < argFormatGiven.size()-1; i++) {
                Type type = argFormatGiven.get(i);
                sb.append(type).append(", ");
            }
            sb.append(argFormatGiven.get(argFormatGiven.size()-1))
                    .append(").");
        }
        System.out.println(sb.toString());
    }

    public static void mismatchBranchCondition(Type givenType) {
        StringBuilder sb = new StringBuilder();
        sb.append("ERROR - Condition statement in branch requires INTEGER return type")
                .append("Given Type: ").append(givenType);
        System.out.println(sb.toString());
    }
    
    public static void mismatchBranchTrueFalse(Type trueType, Type falseType) {
        StringBuilder sb = new StringBuilder();
        sb.append("ERROR - Types in branch choices differ. Type 1: ").append(trueType)
                .append(" Type 2: ").append(falseType);
        System.out.println(sb.toString());
    }
    
    public static void mismatchListItemWithListType(Arg item, Type itemType, 
            Type listType) {
        StringBuilder sb = new StringBuilder();
        sb.append("ERROR - \"").append(item.toString()).append("\" is of type ")
                .append(itemType).append(" in a list of type ")
                .append(listType).append(".");
        System.out.println(sb.toString());
    }
}
