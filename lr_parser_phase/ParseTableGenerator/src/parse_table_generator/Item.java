package parse_table_generator;

/**
 *
 * @author Joseph Alacqua
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @version Mar 14, 2016
 */
public class Item {

    //Rule rule;
    //int position;
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
    public Item(Rule rule, int position){
        this(rule,position,null);
    }

    public Rule getRule() {
        return rule;
    }

    public int getPosition() {
        return position;
    }
    
    public Symbol getCurrentSymbol() {
        return rule.getSymbolOnRight(position);
    }
    
    /**
     * @return the next symbol in the RHS of the rule or null if at the end.
     */
    public Symbol getNextSymbol() {
        if(atEnd()) {
            return null;
        }
        return rule.getSymbolOnRight(position+1);
    }

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
                    && this.lookahead.equals(i.lookahead);
        }
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + rule.hashCode();
        result = 31 * result + position;
        result = 31 * result + lookahead.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(rule.lhs.toString())
          .append("\t -> \t");
        for (int i = 0; i < rule.rhs.size(); i++)
        {
          if (position == i) {
              sb.append("*");
          }
          sb.append(rule.rhs.get(i).toString());  
        }
        if(position>=rule.rhs.size()){
            sb.append("*");
        }

        return sb.toString();
    }
    
    public boolean atEnd() {
        return position == rule.getRhs().size();
    }

    public static void main(String[] args) {
        Nonterminal S = new Nonterminal("S");
        Nonterminal A = new Nonterminal("A");
        Nonterminal B = new Nonterminal("B");
        Terminal a = new Terminal("a");
        Terminal b = new Terminal("b");

        Rule p1 = new Rule(S, A, B, b);
        Rule p2 = new Rule(A, a);
        Rule p3 = new Rule(A, Symbol.Epsilon.EPSILON);
        Rule p4 = new Rule(B, b);
        Rule p5 = new Rule(B, Symbol.Epsilon.EPSILON);
        Rule p1_prime = new Rule(S, A, B, b);
        Rule p3_prime = new Rule(A, Symbol.Epsilon.EPSILON);

        Item i1 = new Item(p1, 0);
        Item i2 = new Item(p1, 1);
        Item i3 = new Item(p1, 2);
        Item i4 = new Item(p1, 3);
//		Item i5 = new Item(p1, 4);
//		Item i6 = new Item(p1, 5);

        Item i7 = new Item(p2, 0);
        Item i8 = new Item(p3, 0);
        Item i9 = new Item(p4, 0);
        Item i10 = new Item(p5, 0);

        Item i11 = new Item(p1_prime, 0);
        Item i12 = new Item(p3_prime, 0);

        System.out.println("i1 = " + i1);
        System.out.println("i2 = " + i2);
        System.out.println("i3 = " + i3);
        System.out.println("i4 = " + i4);
//		System.out.println("i5 = " + i5);
//		System.out.println("i6 = " + i6);
        System.out.println("i7 = " + i7);
        System.out.println("i8 = " + i8);
        System.out.println("i9 = " + i9);
        System.out.println("i10 = " + i10);
        System.out.println("i11 = " + i11);
        System.out.println("i12 = " + i12);

        System.out.println("i1 = i2? " + i1.equals(i2));
        System.out.println("i1 = i11? " + i1.equals(i11));
        System.out.println("i8 = i9? " + i8.equals(i9));
        System.out.println("i8 = i10? " + i8.equals(i10));
        System.out.println("i8 = i11? " + i8.equals(i11));
        System.out.println("i8 = i12? " + i8.equals(i12));
    }

}
