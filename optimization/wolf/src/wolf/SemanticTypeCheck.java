package wolf;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import wolf.enums.*;
import wolf.interfaces.*;
import wolf.node.*;

/**
 * Type Checking
 *
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @version Apr 10, 2016
 */
public class SemanticTypeCheck implements Visitor {
  List<SymbolTable> tables;
  SymbolTable program_table;
  SymbolTable current_def_table;
  Stack<String> last_table_names;
  List<SymbolTable> lambda_table_list;

  public SemanticTypeCheck(List<SymbolTable> tables,
      SymbolTable program_table, List<SymbolTable> lambda_table_list) {
    this.tables = tables;
    this.program_table = this.current_def_table = program_table;
    last_table_names = new Stack<>();
    this.lambda_table_list = lambda_table_list;
  }

  /**
   * Visit a program
   *
   * @param n a program
   * @return the given program
   */
  @Override
  public Object visit(Program n) {
    for (Def def : n.def_list) {
      last_table_names.push(current_def_table.table_name);
      current_def_table = getTableWithName(def.def_name.identifier.getText());
      def.accept(this);
      current_def_table = getTableWithName(last_table_names.pop());
    }
    n.function.accept(this);
    System.out.println("Type Checking Completed Successfully!");
    return n;
  }

  /**
   * Visit a definition
   *
   * @param n a definition object (Def)
   * @return the return type of the function definition
   */
  @Override
  public Type visit(Def n) {
    Type actual_type = (Type) n.function.accept(this);
    Type expected_type = n.type;
    if(!expected_type.equals(actual_type)) {
      String def_name = n.def_name.identifier.getText();
      TypeErrorReporter.mismatchDefType(
          actual_type, expected_type, def_name);
    }
    return expected_type;
  }

  /**
   * Visit a function signature
   *
   * @param n a signature (Sig)
   * @return null, nothing to do in SemanticTypeCheck
   */
  @Override
  public Type visit(Sig n) {
    // do nothing, return null
    return null;
  }

  /**
   * Visit a signature argument.
   *
   * @param n is the SigArg to visit
   * @return null, nothing to do in SemanticTypeCheck
   */
  @Override
  public Type visit(SigArg n) {
    //do nothing, return null
    return null;
  }

  /**
   * Visit a user function.
   *
   * @param n a user function
   * @return the return type of the user function
   */
  @Override
  public Type visit(UserFunc n) {
    Type type;
    if (n.user_func_name instanceof Identifier) {
      Identifier id = (Identifier) n.user_func_name;
      last_table_names.push(current_def_table.table_name);
      current_def_table = getTableWithName(id.identifier.getText());
      type = (Type) n.user_func_name.accept(this);
      n.arg_list.accept(this);
      current_def_table = getTableWithName(last_table_names.pop());
    } else if (n.user_func_name instanceof WolfLambda) {
      //Save the current scope.
      last_table_names.push(current_def_table.table_name);
      //Get the lambda and its corresponding table.
      WolfLambda lambda = (WolfLambda) n.user_func_name;
      current_def_table = lambda_table_list.get(lambda.getId());
      //Check the lambda's arguments.
      n.arg_list.accept(this);
      //Reset the scope
      current_def_table = getTableWithName(last_table_names.pop());
      //Get the lambda's return type
      type = visitLambda((WolfLambda) n.user_func_name);
    } else {
      throw new IllegalArgumentException("Invalid UserFunc");
    }
    return type;
  }

  /**
   * Visit a branch
   *
   * @param n a branch
   * @return the return type of the branch
   */
  @Override
  public Type visit(Branch n) {
    Type cond_type = (Type) n.condition.accept(this);
    if (!cond_type.equals(new Type(FlatType.INTEGER))) {
      TypeErrorReporter.mismatchBranchCondition(cond_type);
    }

    Type trueType = (Type) n.true_branch.accept(this);
    Type falseType = (Type) n.false_branch.accept(this);
    if (!(trueType.equals(falseType))) {
      TypeErrorReporter.mismatchBranchTrueFalse(trueType, falseType);
    }
    return trueType;
  }

  /**
   * Visit a lambda in the WOLF language
   *
   * @param n a lambda object (WolfLambda)
   * @return type of lambda
   */
  @Override
  public Type visit(WolfLambda n) {
    if (n.sig != null) {
      n.sig.accept(this);
    }
    SymbolTable lambda_table = lambda_table_list.get(n.getId());
    SymbolTable parent_table = lambda_table.parent_table;
    last_table_names.add(current_def_table.table_name);
    current_def_table = lambda_table;
    if (n.function != null) {
      n.function.accept(this);
    }
    current_def_table = getTableWithName(last_table_names.pop());
    return parent_table.symbol_table.get(
        lambda_table.table_name
    ).table_value.type;
  }

  /**
   * Visit a fold function
   *
   * @param n a fold function
   * @return the return type of the fold
   */
  @Override
  public Type visit(Fold n) {
    n.fold_symbol.accept(this);
    return (Type) n.fold_body.accept(this);
  }

  /**
   * Visit a fold symbol
   *
   * @param n a fold symbol
   * @return null, never needed in SemanticTypeCheck
   */
  public Type visit(FoldSymbol n) {
    // do nothing, return null
    return null;
  }

  /**
   * Visit a fold body
   *
   * @param n a fold body
   * @return the return type of the fold
   */
  @Override
  public Type visit(FoldBody n) {
    Object type_object = n.bin_op.accept(this);
    Type arg_type = (Type) n.list_argument.accept(this);
    if(!arg_type.is_list) {
      TypeErrorReporter.noListArgument("Fold",arg_type);
    }
    //There's a single valid type for the operation.
    if (type_object instanceof Type) {
      Type valid_type = (Type) type_object;
      if (valid_type.equals(new Type(arg_type.flat_type))) {
        return arg_type;
      }
    }
    //The operation supports several different types
    else if (type_object instanceof List) {
      List<Type> valid_types = (List<Type>) type_object;
      if(valid_types.contains(new Type(arg_type.flat_type))) {
        return new Type(arg_type.flat_type);
      }
    }
    throw new UnsupportedOperationException(
        n.bin_op + " does not accept " + arg_type);
  }

  /**
   * Visit a mapping function in the WOLF language
   *
   * @param n a mapping function object (WolfMap)
   * @return the return type of the map
   */
  @Override
  public Type visit(WolfMap n) {
    Object type_object = n.unary_op.accept(this);
    Type arg_type = (Type) n.list_argument.accept(this);
    if(!arg_type.is_list) {
      TypeErrorReporter.noListArgument("Map",arg_type);
    }
    //There's a single valid type for the operation.
    if (type_object instanceof Type) {
      Type valid_type = (Type) type_object;
      if (valid_type.equals(new Type(arg_type.flat_type))) {
        return new Type(arg_type.flat_type);
      }
    }
    //The operation supports several different types
    else if (type_object instanceof List) {
      List<Type> valid_types = (List<Type>) type_object;
      if(valid_types.contains(new Type(arg_type.flat_type))) {
        return arg_type;
      }
    }
    throw new UnsupportedOperationException(
        n.unary_op + " does not accept " + arg_type);

  }

  /**
   * Visit a list of arguments for a list.
   *
   * @param n a list of arguments for a list (ListArgs)
   * @return the type of the list.
   */
  @Override
  public Type visit(ListArgsList n) {
    Type type = null;
    for (Arg arg : n.getArgList()) {
      Type next_type = (Type) arg.accept(this);
      if (type != null && !type.equals(next_type)) {
        throw new IllegalArgumentException("List has multiple types: "
            + type.toString() + " and " + next_type.toString());
      }
      type = next_type;
    }
    return type;
  }

  /**
   * Visit a list of arguments.
   *
   * @param n a list of arguments (Args)
   * @return null, the type returned does not matter.
   */
  @Override
  public Type visit(ArgsList n) {
    List<Type> actualArgFormat = new ArrayList();
    for (Arg arg : n.getArgList()) {
      actualArgFormat.add((Type) arg.accept(this));
    }
    List<Type> expectedArgFormat = current_def_table.getArgFormat();

    //Must have same number of arguments AND argument types
    //must be the same
    if(!actualArgFormat.equals(expectedArgFormat)) {
      TypeErrorReporter.mismatchArgumentFormat(actualArgFormat,
          expectedArgFormat, current_def_table.table_name);
    }
    return null;
  }

  /**
   * Visit a native unary operator that can have either list
   * or non-list arguments depending on the operation.
   *
   * @param n a native unary
   * @return the type the native unary should return
   */
  public Type visit(NativeUnary n) {
    Type argType = (Type) n.arg.accept(this);
    switch (n.unary_op) {
      case NEG:
        if (!argType.isNumeric()) {
          System.err.println("Invalid Argument " + argType + " for "
              + "~. Expecting Integer or Float.");
          return null;
        }
        return argType;
      case LOGICAL_NOT:
        if (argType.flat_type != FlatType.INTEGER) {
          System.err.println("Invalid Argument " + argType + " for "
              + "!. Expecting Integer.");
          return null;
        }
        return argType;
      case IDENTITY:
      case PRINT:
        return argType;
      default:
        System.err.println("Invalid Native Unary Operation");
        return null;
    }
  }

  /**
   * Visit a native unary operator that has a list argument.
   *
   * @param n a native list unary
   * @return the type the native list unary should return
   */
  @Override
  public Type visit(NativeListUnary n) {
    Type arg_type = (Type) n.list_argument.accept(this);
    switch(n.list_unary_op) {
      case HEAD:
        if(!arg_type.is_list) {
          TypeErrorReporter.mismatchErrorListUnary(
              n.list_argument, arg_type, n.list_unary_op.toString(), null
          );
        }
        return new Type(arg_type.flat_type);
      case TAIL:
        if(!arg_type.is_list) {
          TypeErrorReporter.mismatchErrorListUnary(
              n.list_argument, arg_type, n.list_unary_op.toString(), null
          );
        }
        return arg_type;
      case REVERSE:
        if(!arg_type.is_list) {
          TypeErrorReporter.mismatchErrorListUnary(
              n.list_argument, arg_type, n.list_unary_op.toString(), null
          );
        }
        return arg_type;
      case LAST:
        if(!arg_type.is_list) {
          TypeErrorReporter.mismatchErrorListUnary(
              n.list_argument, arg_type, n.list_unary_op.toString(), null
          );
        }
        return new Type(arg_type.flat_type);
      case LENGTH:
        if(!arg_type.is_list) {
          TypeErrorReporter.mismatchErrorListUnary(
              n.list_argument, arg_type, n.list_unary_op.toString(), null
          );
        }
        return new Type(arg_type.flat_type);
      default:
        System.err.println("Invalid Native List Unary Operation");
        return null;
    }
  }

  /**
   * Visit a native binary operator that can have either list
   * or non-list arguments depending on the operation.
   *
   * @param n a native binary
   * @return the type the native binary should return
   */
  @Override
  public Type visit(NativeBinary n) {
    Type left_type = (Type) n.arg_left.accept(this);
    Type right_type = (Type) n.arg_right.accept(this);
    boolean areSameType = (left_type.equals(right_type) ||
        (left_type.isNumeric() && right_type.isNumeric()));

    switch (n.binary_op) {
      case PLUS:
      case MINUS:
        if (!areSameType) {
          TypeErrorReporter.mismatchArgTypes(
              n.arg_left, left_type,
              n.arg_right, right_type
          );
        }
        return left_type;
      case MULT:
      case DIV:
      case MOD:
        if (!left_type.isNumeric() || !right_type.isNumeric()) {
          ArrayList<Type> types = new ArrayList<>();
          types.add(new Type(FlatType.INTEGER));
          types.add(new Type(FlatType.FLOAT));
          TypeErrorReporter.mismatchErrorBinary(
              n.arg_left, left_type,
              n.arg_right, right_type,
              n.binary_op.toString(), types);
        }
        return left_type;
      case LT:
      case GT:
      case LTE:
      case GTE:
        if (!left_type.isNumeric() || !right_type.isNumeric()) {
          ArrayList<Type> types = new ArrayList<>();
          types.add(new Type(FlatType.INTEGER));
          types.add(new Type(FlatType.FLOAT));
          TypeErrorReporter.mismatchErrorBinary(
              n.arg_left, left_type,
              n.arg_right, right_type,
              n.binary_op.toString(), types);
        }
        return new Type(FlatType.INTEGER);
      case AND:
      case OR:
      case XOR:
        if (left_type.flat_type != FlatType.INTEGER ||
            right_type.flat_type != FlatType.INTEGER) {
          ArrayList<Type> types = new ArrayList<>();
          types.add(new Type(FlatType.INTEGER));
          TypeErrorReporter.mismatchErrorBinary(
              n.arg_left, left_type,
              n.arg_right, right_type,
              n.binary_op.toString(), types);
        }
        return new Type(FlatType.INTEGER);
      case EQUAL:
      case NOT_EQUAL:
        return new Type(FlatType.INTEGER);
      default:
        System.err.println("Invalid Binary Operator!");
        return null;
    }
  }

  /**
   * Visit a native binary that one non-list and one list argument
   *
   * @param n a native list binary
   * @return the type the native list binary should return
   */
  @Override
  public Type visit(NativeListBinary n) {
      Type arg_type = (Type) n.arg.accept(this);
      Type list_type = (Type) n.list_argument.accept(this);
      switch(n.binary_op) {
          case APPEND:
          case PREPEND:
              if(!list_type.is_list ||
                 !arg_type.flat_type.equals(list_type.flat_type)) {
                  List<Type> list_types = new ArrayList<>();
                  list_types.add(list_type);
                  List<Type> arg_types = new ArrayList<>();
                  arg_types.add(new Type(list_type.flat_type));
                  TypeErrorReporter.mismatchErrorBinaryList(
                      n.arg, arg_type,
                      n.list_argument, list_type,
                      n.binary_op.toString(), arg_types, list_types
                  );
              }
              return list_type;
          default:
              System.err.println("Invalid Native List Binary Operation!");
              return null;
      }
  }

  /**
   * Visit an identifier
   *
   * @param n an identifier
   * @return the type of the identifier
   * An identifier can be anything, it depends on the context of the
   * function. Identifiers are only used for user-defined functions and
   * their parameters, which have user-specified types
   */
  @Override
  public Type visit(Identifier n) {
    TableValue tv = null;
    Binding binding = current_def_table.lookup(n);
    if (binding == null) {
        binding = getTableWithName(last_table_names.peek()).lookup(n);
    }
    if (binding == null) {
      SymbolTable parent = current_def_table.parent_table;
      while (parent != null && tv == null) {
        binding = parent.lookup(n);
        if (binding == null) {
          parent = parent.parent_table;
        } else {
          tv = binding.table_value;
        }
      }
    } else {
      tv = binding.table_value;
    }
    if (tv == null) {
      return null;
    }
    return tv.type;
  }

  /**
   * Visit a float literal
   *
   * @param n a float literal
   * @return the FLOAT type
   */
  public Type visit(FloatLiteral n) {
    return new Type(FlatType.FLOAT);
  }

  /**
   * Visit an integer literal
   *
   * @param n an integer literal
   * @return the INTEGER type
   */
  @Override
  public Type visit(IntLiteral n) {
    return new Type(FlatType.INTEGER);
  }

  /**
   * Visit a list in the WOLF language
   *
   * @param n a list object (WolfList)
   * @return the X_LIST type where X is either FLOAT, INTEGER, or STRING, or
   * null if the list is empty.
   */
  @Override
  public Type visit(WolfList n) {
    Type list_type = null;
    for (ListElement element : n.list_elements) {
      Type element_type = (Type) element.accept(this);
      if (list_type != null) {
        if (!element_type.equals(list_type)) {
          TypeErrorReporter.mismatchListItemWithListType(element,element_type,list_type);
        }
      } else { // set list type on first element
        list_type = element_type;
      }
    }
    return (list_type == null) ? null : new Type(list_type.flat_type, true);
  }

  /**
   * Visit a string in the WOLF language
   *
   * @param n a string object (WolfString)
   * @return the STRING type
   */
  @Override
  public Type visit(WolfString n) {
    for (StringMiddle sm : n.string) {
      sm.accept(this);
    }
    return new Type(FlatType.STRING);
  }

  /**
   * Visit a string body
   *
   * @param n a string body
   * @return null, no use in SemanticTypeCheck
   */
  @Override
  public Type visit(StringBody n) {
    // do nothing, return null
    return null;
  }

  /**
   * Visit a string escape sequence
   *
   * @param n a string escape sequence
   * @return null, no use in SemanticTypeCheck
   */
  @Override
  public Type visit(StringEscapeSeq n) {
    // do nothing, return null
    return null;
  }

  /**
   * Visit a string escape character.
   *
   * @param n the escape character
   * @return null, no use in SemanticTypeCheck
   */
  @Override
  public Type visit(EscapeChar n) {
    // do nothing, return null
    return null;
  }

  /**
   * Visit a native binary operator.
   *
   * @param n the native binary operator
   * @return a list of valid types for the operator.
   */
  @Override
  public List<Type> visit(NativeBinOp n) {
    List<Type> valid_types = new ArrayList();
    Class token_class = n.getTokenClass();
    valid_types.add(new Type(FlatType.INTEGER));
    if(!token_class.equals(TXor.class) &&
       !token_class.equals(TOr.class) &&
       !token_class.equals(TAnd.class) &&
       !token_class.equals(TNotEqual.class) &&
       !token_class.equals(TEqual.class) &&
       !token_class.equals(TGte.class) &&
       !token_class.equals(TLte.class) &&
       !token_class.equals(TGt.class) &&
       !token_class.equals(TLt.class)) {
        valid_types.add(new Type(FlatType.FLOAT));
        if(!token_class.equals(TMod.class) &&
           !token_class.equals(TDiv.class) &&
           !token_class.equals(TMult.class)) {
            valid_types.add(new Type(FlatType.STRING));
            if(!token_class.equals(TPlus.class) &&
               !token_class.equals(TMinus.class)) {
                return null;
            }
        }
    }
    return valid_types;
  }

  /**
   * Visit a native unary operator.
   *
   * @param n the native unary operator
   * @return a list of valid types for the operator.
   */
  @Override
  public List<Type> visit(NativeUnaryOp n) {
    List<Type> valid_types = new ArrayList<>();
    Class token_class = n.getTokenClass();
    valid_types.add(new Type(FlatType.INTEGER));
    if (!token_class.equals(TLogicalNot.class)) {
        valid_types.add(new Type(FlatType.FLOAT));
        if (!token_class.equals(TNeg.class)) {
            valid_types.add(new Type(FlatType.STRING));
            if (!token_class.equals(TPrint.class) &&
                !token_class.equals(TIdentity.class)) {
                return null;
            }
        }
    }
    return valid_types;
  }

  /**
   * Get a table from the table list based on its name.
   *
   * @param name the table name
   * @return the table with the given name or null if none exists.
   */
  public SymbolTable getTableWithName(String name) {
    for (SymbolTable table : tables) {
      if (table.table_name.equals(name)) {
        return table;
      }
    }
    return null;
  }

  /**
   * Get a table from the list of lambda tables.
   *
   * @param name the table name
   * @return the table with the given name or null if none exists.
   */
  public SymbolTable getLambdaTableWithName(String name) {
    for (SymbolTable table : lambda_table_list) {
      if (table.table_name.equals(name)) {
        return table;
      }
    }
    return null;
  }

  /**
   * Saves current table state, changes to lambda scope, visits lambda,
   * and then returns to the original scope before returning the type.
   * @param lambda
   * @return the type of the lambda
   */
  private Type visitLambda(WolfLambda lambda) {
    last_table_names.push(current_def_table.table_name);
    current_def_table = lambda_table_list.get(lambda.getId());
    Type returnType = visit(lambda);
    current_def_table = getTableWithName(last_table_names.pop());
    return returnType;
  }
}