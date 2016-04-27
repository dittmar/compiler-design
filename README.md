# compiler-design
Spring 2016 Compiler Design project

WOL(F) works... mostly.  It has a working lexer made by SableCC, a hand-made LL Parser,
type-checking, an optimizer, and a compiler.  Everything should work entirely except the
compiler.  The compiler rewrites WOL(F) functional code into Java code and bootstraps the
Java compiler.  The problem is that Map, Fold, and Lambdas probably don't work.  If either
Bill or I ever work on this again, we should start from sample_program2 and continue to
make all of our valid sample programs work.  That probably won't happen, but this message
is here to remind us just in case.

For future reference, you compile a program by running Parser.  No, it doesn't make sense,
and yes, it should probably be changed to have WolfCompiler user Parser, but oh well.
-- Kevin Dittmar
