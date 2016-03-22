package three_point_one.parser;

/**
 *
 * @author Kevin Dittmar
 * @version Mar 21, 2016
 */
public class ParserStackItem 
{
    Symbol symbol;
    int id_number;
    
    public ParserStackItem(Symbol symbol, int id_number)
    {
        this.symbol = symbol;
        this.id_number = id_number;
    }
    
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        if (symbol == null)
        {
            sb.append("");
        }
        else
        {
            sb.append(symbol.toString()).append("_");
        }
        return sb.append(id_number).toString();
    }
}
