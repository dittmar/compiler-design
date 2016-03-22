package wolf.parser;

/**
 * An item is a rule with the current position marked.
 * @author Joseph Alacqua
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @version Mar 14, 2016
 */
public class Item {
    Terminal lookahead;

    private final Rule rule;
    private final int position;

    /**
     * Item with a lookahead symbol LR(1). 
     * @param rule the production rule.
     * @param position the position of the "cursor".
     * @param lookahead the lookahead terminal symbol.
     */
    public Item(Rule rule, int position, Terminal lookahead) {
        if (position < 0 || position > rule.rhs.size()) {
            throw new IllegalArgumentException(
                "Item: invalid rule position" + position);
        } else if (rule.rhs.isEmpty()) {
            this.rule = rule;
            this.position = 0;
        } else {
            this.rule = rule;
            this.position = position;
        }
        this.lookahead = lookahead;
    }
    
    /**
     * Item without a lookahead symbol LR(0).
     * @param rule the production rule
     * @param position the position of the "cursor".
     */
    public Item(Rule rule, int position) {
        this(rule,position, new AnySymbol());
    }

    /**
     * @return this items rule
     */
    public Rule getRule() {
        return rule;
    }

    /**
     * @return the position of this item
     */
    public int getPosition() {
        return position;
    }
    
    /**
     * @return the lookahead symbol of this item
     */
    public Symbol getCurrentSymbol() {
        return rule.getSymbolOnRight(position);
    }
    
    /**
     * @return the next symbol in the RHS of the rule or null if at the end.
     */
    public Symbol getNextSymbol() {
        if(position + 1 >= rule.rhs.size()) {
            return null;
        }
        return rule.getSymbolOnRight(position+1);
    }

    /**
     * Check if this item equals another object. Two items are equal if they
     * have the same set of rules, position, and lookahead symbol.
     * @param o an object
     * @return true if this item equals the given object.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof Item)) {
            return false;
        } else {
            Item i = (Item) o;
            return this.rule.equals(i.getRule())
                    && this.position == i.getPosition()
                    && (this.lookahead.equals(i.lookahead));
        }
    }

    /**
     * @return the hash code of this item
     */
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + rule.hashCode();
        result = 31 * result + position;
        if(lookahead != null) {
            result = 31 * result + lookahead.hashCode();
        }
        return result;
    }

    /**
     * @return the string representation of this item
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(rule.lhs.toString())
          .append("\t -> \t");
        for (int i = 0; i < rule.rhs.size(); i++) {
          if (position == i) {
              sb.append("*");
          }
          sb.append(rule.rhs.get(i).toString());  
        }
        if(position>=rule.rhs.size()) {
            sb.append("*");
        }
        sb.append("\t la:").append(lookahead);

        return sb.toString();
    }
    
    /**
     * @return true if the position (cursor) is at the end of the right hand
     * side
     */
    public boolean atEnd() {
        return position >= rule.getRhs().size();
    }
}
