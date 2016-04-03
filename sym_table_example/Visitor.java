public interface Visitor {
  void visit(Program n);
  void visit(MainClass n);
  void visit(ClassDecl n);
  void visit(ClassDeclSimple n);
  void visit(ClassDeclExtends n);
  



  void visit(MethodDecl n);

  String visit(Exp n);
  String visit(And n);
  String visit(Plus n);
  String visit(Mult n);
}
