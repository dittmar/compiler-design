// This class starts the lexical phase from a main method.
// William Ezekiel
package wolf;
import wolf.lexer.*;
import wolf.node.*;
import java.io.*;

class WolfLexing 
{
    static Lexer lexer;
	static Token token;
	
	// Command line format: java wolf/WolfLexing file1 file2 file3 ...
	// If no file names are given, reads from stdin.	
	public static void main (String[] args)
    {
        if(args.length == 0)
        {
			lexStdIn();
        }
		else
        {
			for(String file : args)
            {
				lexFile(file);
			}
		}	
	}
	
	
	// Do the lexical phase for WOLF from stdin, and
    // send the token output to stdout.
	private static void lexStdIn()
    {
		token = null;
		lexer = new Lexer(
            new PushbackReader(
                new InputStreamReader(System.in), 1024
            )
        );
		try
        {
            // Collect all of the tokens in a StringBuilder to write
            // to the file.
            while(!(token instanceof EOF))
            {
                token = lexer.next();
                // print token
                System.out.println(token.getClass() + ": " + token.getText());
            }
        }
		catch (LexerException le)
        {
            System.err.println(le);
        }
        catch (IOException ioe) 
        {
            System.err.print(ioe);
        }
	}

	// Do the lexical phase for WOLF from a file,
    // save the output to a file of the same name with the .tokens extension.
	private static void lexFile(String filename)
    {
        token = null;
        try
        {
            lexer = new Lexer(
                new PushbackReader(
                    new FileReader(filename), 1024
                )
            );
			
			String outputFile = filename + ".tokens";
			File file = new File(outputFile);

			if(!file.exists())
            {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);

			StringBuilder sb = new StringBuilder();

            // Collect all of the tokens in a StringBuilder to write
            // to the file.
            while(!(token instanceof EOF))
            {
                token = lexer.next();
                sb.append(token.getClass() + ": " + token.getText() + "\n");
            }

			bw.write(sb.toString());
			bw.close();
        }
        catch (LexerException le)
        {
            System.err.println(le);
        }
        catch(FileNotFoundException fnfe)
        {
            System.err.println(fnfe);
        }
        catch (IOException ioe)
        {
            System.err.print(ioe);
        }
	}
}
