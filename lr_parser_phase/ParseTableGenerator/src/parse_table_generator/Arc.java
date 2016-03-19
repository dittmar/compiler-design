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
    
    public boolean equals(Object obj)
    {
        if (!(obj instanceof Arc))
        {
            return false;
        }
        Arc arc = (Arc) obj;
        return arc.from.equals(from) && 
               arc.to.equals(to) && 
               arc.transition_symbol.equals(transition_symbol);
    }
    
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + from.hashCode();
        result = 31 * result + to.hashCode();
        result = 31 * result + transition_symbol.hashCode();
        return result;
    }
    
    public String toString() {
        return from.id + " --"+ transition_symbol + "-> " + to.id;
    }
}
