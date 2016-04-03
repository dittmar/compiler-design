package wolf;
import wolf.interfaces.TableValue;
import wolf.node.*;

/**
 * A binding ties an identifier to a type. In minijava,for example,
 * g|-> int means that identifier g is an integer. (p.103)
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @author Joe Alacqua
 * @version(April 3 2016)
 */
public class Binding {
    TIdentifier identifier;
    TableValue table_value;
    
    /**
     * Create a binding. 
     * @param id the identifier
     * @param table_value the type the identifier will be bound to
     */
    public Binding(TIdentifier id, TableValue table_value) {
        identifier = id;
        this.table_value = table_value;
    }
    
    /**
     * @return the string representation of this binding.
     */
    @Override
    public String toString() {
        return identifier.toString() + " |-> " + table_value.toString();
    }
    
    
    /* Some testing stuff, ignore.
    public static void main(String args[]) {
        Binding b = new Binding(new TIdentifier("isaac"),Type.STRING);
        System.out.println(b);
    }
    */
    
}
