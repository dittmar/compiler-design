package wolf.compiler;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PushbackReader;
import wolf.lexer.Lexer;
import wolf.lexer.LexerException;
import wolf.node.Start;
import wolf.parser.Parser;
import wolf.parser.ParserException;

public class Compiler {
    public static void main(String[] args) {
        for (String filename : args)
        {
            File file = new File(filename);
            try {
                Parser p = new Parser(
                    new Lexer(
                        new PushbackReader(new FileReader(file), 1024)
                    )
                );
                Start tree = p.parse();
                tree.apply(new Translation());
            } catch (ParserException | LexerException | IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }   
}
