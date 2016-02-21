// This class starts the lexical phase from a main method.
// William Ezekiel
package lexing;
import lexing.lexer.*;
import lexing.node.*;
import java.io.*;

class Lexing {
	static Lexer lexer;
	static Object token;
	
	// Command line format: java lexing/Lexing.java file1 file2 file3 ...
	// If no file names are given, reads from stdin.	
	public static void main (String[] args) {
		if(args.length == 0) {
			lexStdIn();
    }
		else {
			for(String file : args) {
				lexFile(file);
			}
		}	
	}
	
	
	// Do the lexical phase for WOLF from stdin, and send the token output to stdout.
	private static void lexStdIn() {
		token = null;
		lexer = new Lexer(new PushbackReader(new InputStreamReader(System.in),1024));
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

	
	// Do the lexical phase for WOLF from a file, save the output to a file of the same name with the .tokens extension.
	private static void lexFile(String filename) {                          
            
                token = null;              
                //String filename = "lexing/proga.wlf";

                try {
                        lexer = new Lexer(new PushbackReader(new FileReader(filename),1024));
			
			String outputFile = filename + ".tokens";
			File file = new File(outputFile);

			if(!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);

			String output = "";

                        while(! (token instanceof EOF)) {
                                token = lexer.next();
                                output += token.getClass() + ": " + token + "\n";
                        }

			bw.write(output);
			bw.close();
                }
                catch (LexerException le) {
                        System.err.println(le);
                }
                catch(FileNotFoundException fnfe) {
                        System.err.println(fnfe);
                }
                catch (IOException ioe) {
                        System.err.print(ioe);
                }
	
	}
}
