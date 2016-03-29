package wolf.compiler;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
            Translation translation = new Translation(filename + ".parse");
            try {
                Parser p = new Parser(
                    new Lexer(
                        new PushbackReader(new FileReader(file), 1024)
                    )
                );
                Start tree = p.parse();
                tree.apply(translation);
                System.out.println(filename + " parsed successfully.");
            } catch (ParserException | LexerException | IOException e) {
                System.err.println("Error parsing " + filename + ": " +
                    e.getMessage());
                translation.die(e.getMessage());
            }
        }
    }   
}
