package parse_table_generator;

/**
 *
 * @author Joseph Alacqua
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @version Mar 14, 2016
 */
public class Arc 
{
    Symbol transition_symbol;
    State from;
    State to;
    
    public Arc(Symbol s, State from, State to) {
        transition_symbol = s;
        this.from = from;
        this.to = to;
    }
}
