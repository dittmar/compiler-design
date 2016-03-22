package wolf.parser;

/**
 * A stack item used in the LRParser's stack. Contains a Symbol and an id
 * number.
 * @author Joseph Alacqua
 * @author Kevines Dittmar
 * @author William Ezekiel
 * @version Mar 21, 2016
 */
public class ParserStackItem  {
    Symbol symbol;
    int idNumber;
    
    /**
     * Create a ParserStackItem
     * @param symbol the symbol
     * @param idNumber an id number.
     */
    public ParserStackItem(Symbol symbol, int idNumber) {
        this.symbol = symbol;
        this.idNumber = idNumber;
    }
    
    /**
     * @return the string implementation of this parser stack item.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (symbol == null)
        {
            sb.append("");
        }
        else
        {
            sb.append(symbol.toString()).append("_");
        }
        return sb.append(idNumber).toString();
    }
}
