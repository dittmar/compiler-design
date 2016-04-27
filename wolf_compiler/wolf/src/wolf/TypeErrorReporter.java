package wolf;

import java.util.List;
import wolf.interfaces.Arg;
import wolf.interfaces.ListArgument;
import wolf.interfaces.ListElement;

/**
 *
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @author Joe Alacqua
 * @version Apr 10, 2016
 */
public final class TypeErrorReporter {
    /**
     * Report an error for an offending binary function
     * @param left the left argument
     * @param l the left argument's type
     * @param right the right argument
     * @param r the right argument's type
     * @param op the operation
     * @param expected_types the types expected
     * @throws UnsupportedOperationException 
     */
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
    
    /**
     * Report an error for an argument that doesn't match the binary function's
     * expected arg type
     * @param arg the offending argument
     * @param arg_t the offending arg's type
     * @param op the operation
     * @param arg_expected the list of expected types for the argument
     * @param list the list argument
     * @param list_t the list argument's type
     * @param list_expected the expected types for the list argument
     * @throws UnsupportedOperationException 
     */
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
          .append(arg.toString()).append(" is of type ").append(arg_t)
          .append("\n")
          .append(list.toString()).append(" is of type ").append(list_t)
          .append("\n");
        throw new UnsupportedOperationException(sb.toString());
    }
    
    /**
     * Report an error for an argument that doesn't match the unary function's
     * expected arg type
     * @param arg the offending argument
     * @param type the offending type
     * @param op the operation
     * @param expected the list of expected types
     * @throws UnsupportedOperationException 
     */
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
        throw new UnsupportedOperationException(sb.toString());
    }
    
    /**
     * Report an error for an argument that doesn't match the unary function's
     * expected arg type
     * @param arg the offending argument
     * @param type the offending type
     * @param op the operation
     * @param expected the list of expected types
     * @throws UnsupportedOperationException 
     */
    public static void mismatchErrorListUnary(ListArgument arg, Type type,
            String op, List<Type> expected)
            throws UnsupportedOperationException {
        StringBuilder sb = new StringBuilder();
        sb.append("Unary function ").append(op).append(" must be used on ");
        if (expected == null || expected.isEmpty()) {
            sb.append("any LIST type.");
        } else {
            sb.append("[");
            StringBuilder types = new StringBuilder();
            for (Type t : expected) {
                types.append(t).append(" ");
            }
            sb.append(types.toString().trim());
            sb.append("]");
        }
        sb.append(" Argument ")
          .append(arg.toString())
          .append(" is of type ")
          .append(type);
        throw new UnsupportedOperationException(sb.toString());
    }
    
    /**
     * When creating a user defined function, this error message occurs if
     * the expected type the function should return differs from what is
     * actually given.
     * @param actual_type the type actually given
     * @param expected_type the expected type
     * @param def_name the name of the user defined function. Lambda functions
     *  use the format Lambda#.
     */
    public static void mismatchDefType(Type actual_type, Type expected_type, 
            String def_name) throws UnsupportedOperationException {
        StringBuilder sb = new StringBuilder();
        sb.append("ERROR - Expecting Type ").append(expected_type)
                .append(" in \"").append(def_name).append("\".")
                .append(" Actual Type: ").append(actual_type);
        throw new UnsupportedOperationException(sb.toString());
    }

    /**
     * Report an error for an argument list whose format differs from the
     * formal argument list
     * @param argFormatGiven argument list given
     * @param argFormatExpected argument list expected
     * @param funcName offending function's name
     * @throws UnsupportedOperationException 
     */
    public static void mismatchArgumentFormat(List<Type> argFormatGiven,
            List<Type> argFormatExpected, String funcName)
            throws UnsupportedOperationException {
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
        throw new UnsupportedOperationException(sb.toString());
    }

    /**
     * Error reported when a branch's conditional function doesn't return an
     * Integer
     * @param givenType the type that the conditional function returns.
     * @throws UnsupportedOperationException 
     */
    public static void mismatchBranchCondition(Type givenType)
            throws UnsupportedOperationException {
        StringBuilder sb = new StringBuilder();
        sb.append("ERROR - Condition statement in branch requires " + 
                  "INTEGER return type")
          .append("Given Type: ").append(givenType);
        throw new UnsupportedOperationException(sb.toString());
    }
    
    /**
     * Report error for a branch whose true branch choice differs in type from
     * its false branch type
     * @param trueType the type returned by the true branch
     * @param falseType the type returned by the false branch
     * @throws UnsupportedOperationException 
     */
    public static void mismatchBranchTrueFalse(Type trueType, Type falseType) 
            throws UnsupportedOperationException {
        StringBuilder sb = new StringBuilder();
        sb.append("ERROR - Types in branch choices differ. Type 1: ")
          .append(trueType)
          .append(" Type 2: ").append(falseType);
        throw new UnsupportedOperationException(sb.toString());
    }
    
    /**
     * reports an error for a list item not matching the type of other items
     * in the list
     * @param item the offending item
     * @param itemType the type of the item
     * @param listType the type of the list
     * @throws UnsupportedOperationException 
     */
    public static void mismatchListItemWithListType(ListElement item,
        Type itemType, Type listType) throws UnsupportedOperationException {
        StringBuilder sb = new StringBuilder();
        sb.append("ERROR - \"")
            .append(item.toString()).append("\" is of type ")
            .append(itemType).append(" in a list of type ")
            .append(listType).append(".");
        throw new UnsupportedOperationException(sb.toString());
    }
    
    /**
     * report an error for an arg that doesn't match its formal type
     * @param expected the expected argument
     * @param e_type the expected type
     * @param actual the argument given
     * @param a_type the given argument's type
     */
    public static void mismatchArgTypes(Arg expected, Type e_type,
            Arg actual, Type a_type) {
        StringBuilder sb = new StringBuilder();
        sb.append("ERROR - Types must match. ")
          .append(expected).append(" is of type ").append(e_type)
          .append(", ").append(actual).append(" is of type ").append(a_type);
        throw new UnsupportedOperationException(sb.toString());
    }

    public static void noListArgument(String function_name, Type arg_type) {
        StringBuilder sb = new StringBuilder();
        sb.append("ERROR  - ")
          .append(function_name).append(" must have an argument that is ")
          .append("a LIST. ")
          .append("Given type: ").append(arg_type);
        throw new UnsupportedOperationException(sb.toString());
    }
}
