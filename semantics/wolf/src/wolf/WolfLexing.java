/** This class starts the lexical phase from a main method.
  * @author(Joe Alacqua)
  * @author(Kevin Dittmar)
  * @author(William Ezekiel)
  * @version(2/23/16)
  */
package wolf;
import wolf.lexer.*;
import wolf.node.*;
import java.io.*;

public class WolfLexing 
{
    private Lexer lexer;
    private Token token;
    
    public WolfLexing()
    {
        lexer = new Lexer(
            new PushbackReader(
                new InputStreamReader(System.in), 1024
            )
        );
    }
    
    public WolfLexing(String filename)
    {
        try
        {
            lexer = new Lexer(
                new PushbackReader(
                    new FileReader(filename), 1024
                )
            );
        }
        catch (FileNotFoundException fnfe)
        {
            System.err.println(fnfe.getMessage());
        }
    }
    
    // Command line format: java wolf/WolfLexing file1 file2 file3 ...
    // If no file names are given, reads from stdin.
    public static void main (String[] args)
    {
        if(args.length == 0)
        {
            WolfLexing wl = new WolfLexing();
            wl.lexStdIn();
        }
        else
        {
            for(String file : args)
            {
                WolfLexing wl = new WolfLexing(file);
                wl.lexFile(file);
            }
        }
    }
    
    
    // Do the lexical phase for WOLF from stdin, and
    // send the token output to stdout.
    private void lexStdIn()
    {
      // Collect all of the tokens in a StringBuilder to write
      // to the file.
      while((token = getToken()) != null)
      {
        // print token
        System.out.println(token.getClass() + ": " + token.getText());
      }
    }

    // Do the lexical phase for WOLF from a file,
    // save the output to a file of the same name with the .tokens extension.
    private void lexFile(String filename)
    {
      try
      {
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
        while((token = getToken()) != null)
        {
            sb.append(token.getClass());
            sb.append(": ");
            sb.append(token.getText());
            sb.append("\n");
        }

        bw.write(sb.toString());
        bw.close();
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

  /**
   * Gets the next Token from the input stream.
   * @return a Token representing the next token in the input stream
   * or null if there are no more tokens.
   */
  public Token getToken()
  {
    if(token instanceof EOF)
    {
  	return null;
    }
    try
    {
  	return lexer.next();
    }	
    catch (LexerException | IOException le)
    {
        System.err.println(le);
  	return null;
    }
  }
}
