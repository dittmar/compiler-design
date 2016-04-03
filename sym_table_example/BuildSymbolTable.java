public class BuildSymbolTable implements Visitor {
  SymbolTable symbol_table,
              symbol_table_method,
              symbol_table_class, 
              symbol_table_program;
  public BuildSymbolTable() {
  
  }

  public void visit(Program n) {
    symbol_table = new SymbolTable();
    n.mainClass.accept(this);
    n.classDecls.accept(this);
  }

  public void visit(MainClass n) {
    symbol_table_program.put(
      n.className,
      new Binding(n.className, IdType.CLASS));
    symbol_table_program.put(n.args, new Binding(n.args, IdType.VARIABLE));
    // MainClass needs its own SymbolTable
    n.symbol_table = new SymbolTable();
    n.className.accept(this);
    n.args.accept(this);
    n.stmt.accept(this);
  }

  public void visit(ClassDecl n) {

  }

  public void visit(ClassDeclSimple n) {
    symbol_table_program.put(
      n.className,
      new Binding(n.className, IdType.CLASS)
    );
    n.className.accept(this);
    symbol_table = symbol_table_class = n.symbol_table = new SymbolTable();
    n.fields.accept(this);
    n.methods.accept(this);
  }

  /* continue in this manner for the following classes:
   * ClassDeclExtends
   * VarDecl
   * MethodDecl
   * Formal
   *
   */
}
