package wolf.interfaces;

import wolf.Def;
import wolf.Program;

/**
 *
 * @author Kevin Dittmar
 * @version Apr 3, 2016
 */
public interface Visitor {

    void visit(Program n);

    void visit(Def n);
/*
    void visit(ClassDecl n);

    void visit(ClassDeclSimple n);

    void visit(ClassDeclExtends n);

    void visit(MethodDecl n);

    String visit(Exp n);

    String visit(And n);

    String visit(Plus n);

    String visit(Mult n);
*/
}
