package wolf.compiler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import wolf.analysis.DepthFirstAdapter;
import wolf.node.Node;
import wolf.node.Start;

public class Translation extends DepthFirstAdapter {
    private FileWriter writer;
    public Translation(String output_filename) {
        try {
            writer = new FileWriter(new File(output_filename));
        } catch (IOException e) {
            writer = null;
            System.err.println(
                "Error: writer could not be created.  Writing to stdout."
            );
        }
    }
    
    @Override
    public void defaultOut(Node node) {
        String[] node_name = node.getClass().getName().split("\\.");
        String log = "Parsed "+node_name[node_name.length - 1]+": "+node+"\n";
        if (writer != null) {
            try {
                writer.append(log);
            } catch (IOException e) {
                System.out.println(log);
            }
        } else {
            System.out.println(log);
        }
        if (node instanceof Start) {
            try {
                writer.close();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }
    
    /**
     * On death of the Translation class, close the writer if it's still open.
     */
    public void die() {
        try {
            writer.close();
        } catch (IOException e) {
            // writer already closed.
        }
    }
}
