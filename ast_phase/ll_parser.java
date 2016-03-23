// LL Parser

public class Program {
  MainClass main_class;
  ClassDeclList class_decls;
  
  Program(MainClass mc, ClassDeclList cdl)
  {
    main_class = mc;
    class_decls = cdl;
  }
}

public abstract class Stmt {
  Stmt() {

  }
}

public class If extends Stmt {
  Exp cond;
  Stmt true_stmt;
  Stmt false_stmt;

  public If(Exp c, Stmt t, Stmt f) {
    cond = c;
    true_stmt = t;
    false_stmt = f;
  }
}

public class Parser {
  Lexer lexer;
  public Parser(Lexer lexer) {
    this.lexer = lexer;
  }
}

//find an example of a Program
public Program program() {
  advance();  // get first token
  MainClass main_class = mainClass();
  ClassDeclList<ClassDecl> other_classes = new ClassDeclList<ClassDecl>();
  while (is(TokenType.KEY, "class")) {
    other_classes.add(ClassDecl());
  }
  if (token != null) {
    error("Expected EOF after Classes");
  } else {
    System.out.println("OK");
  }

  return new Program(main_class, other_classes);
}

public ClassDecl classDecl() {
  Identifier class_name - classDeclSpec();
  return classDeclDef(className);
}

public ClassDeclSpec classDeclSpec() {
  eat("class");
  Identifier class_name = new Identifier(value);
  eat(tokenType.ID);
  return className;
}

public ClassDeclDef(Identifier class_name) {
  MethodDeclList<MethodDecl methods = new MethodDeclList<MethodDecl>();
  VarDeclList<VarDecl> fields = new VarDeclList<VarDecl>();
  if (token == LBRACE) {
    eat(LBRACE);
    while () {
      fields.add(varDecl());
      return new ClassDecl(class_name, fields, methods);
    }
  } else {
    // do extends
  }
}

public Exp exp() {
  Exp left = And();
  Exp right = Elist(left);
  if (right == null) {
    return left;
  }
  return right;
}
