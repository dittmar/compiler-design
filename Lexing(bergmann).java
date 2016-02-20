// This class starts the lexical phase from a main method.
// William Ezekiel
package lexing;
import lexing.lexer.*;
import lexing.node.*;
import java.io.*;

class Lexing {
        static Lexer lexer;
        static Object token;

        public static void main (String[] args) {

                lexer = new Lexer(new PushbackReader(new InputStreamReader(
                        System.in),1024)); // reading from stdin, 
                                           // buffer size = 1024 bytes
                token = null;

                try {

                        while(! (token instanceof EOF)) {
                                token = lexer.next();
                                System.out.println(token.getClass() + ": " + token); // print token
                        }
                }
                catch (LexerException le) {
                        System.err.println(le);
                }
                catch (IOException ioe) {
                        System.err.print(ioe);
                }
        }
}
