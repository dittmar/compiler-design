package grammarTools;

import java.util.ArrayList;
import java.util.List;

public class LR0_Item {
	private final Production production;
	private final int numSeen;
	private final List<Symbol> seen;
	private final List<Symbol> notSeen;
	
	public LR0_Item(Production production, int numSeen) {
		if (numSeen < 0 || numSeen > production.length())
			throw new IllegalArgumentException("LR0_Item: invalid number of symbols seen " + numSeen);
		else if (production.getProduct().equals(Product.EMPTY)) {
			this.production = production;
			this.numSeen = 0;
			seen = new ArrayList<Symbol>();
			notSeen = new ArrayList<Symbol>();
		}
		else {
			this.production = production;
			this.numSeen = numSeen;
			seen = production.getProductAsList().subList(0, numSeen);
			notSeen = production.getProductAsList().subList(numSeen, production.length());
		}
	}
	
	public Production getProduction() { return production; }
	
	public int getNumSeen() { return numSeen; }
	
	public List<Symbol> getSeen() { return new ArrayList<Symbol>(seen); }
	
	public List<Symbol> getNotSeen() { return new ArrayList<Symbol>(notSeen); }
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		else if (!(o instanceof LR0_Item)) return false;
		else {
			LR0_Item i = (LR0_Item) o;
			return this.production.equals(i.getProduction())
				&& this.numSeen == i.getNumSeen();
		}
	}
	
	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + production.hashCode();
		result = 31 * result + numSeen;
		return result;
	}
	
	@Override
	public String toString() {
		String result = production.getSource() + "\t -> \t";
		for (Symbol s : seen) result = result + s + " ";
		result = result + "* ";
		for (Symbol s : notSeen) result = result + s + " ";
		
		return result;
	}
	
	public static void main(String[] args) {
		Nonterminal S = new Nonterminal("S");
		Nonterminal A = new Nonterminal("A");
		Nonterminal B = new Nonterminal("B");
		Terminal a = new Terminal("a");
		Terminal b = new Terminal("b");

		Product empty = new Product();
		Product s1 = new Product(A, B, b);
		Product s2 = new Product(a);
		Product s3 = new Product(b);
		
		Production p1 = new Production(S, s1);
		Production p2 = new Production(A, s2);
		Production p3 = new Production(A, empty);
		Production p4 = new Production(B, s3);
		Production p5 = new Production(B, empty);
		Production p1_prime = new Production(S, s1);
		Production p3_prime = new Production(A, empty);
		
		LR0_Item i1 = new LR0_Item(p1, 0);
		LR0_Item i2 = new LR0_Item(p1, 1);
		LR0_Item i3 = new LR0_Item(p1, 2);
		LR0_Item i4 = new LR0_Item(p1, 3);
//		LR0_Item i5 = new LR0_Item(p1, 4);
//		LR0_Item i6 = new LR0_Item(p1, 5);
		
		LR0_Item i7 = new LR0_Item(p2, 0);
		LR0_Item i8 = new LR0_Item(p3, 0);
		LR0_Item i9 = new LR0_Item(p4, 0);
		LR0_Item i10 = new LR0_Item(p5, 0);
		
		LR0_Item i11 = new LR0_Item(p1_prime, 0);
		LR0_Item i12 = new LR0_Item(p3_prime, 0);
		
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
