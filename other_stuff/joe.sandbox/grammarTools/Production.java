package grammarTools;

import java.util.List;

public class Production {
	private final Nonterminal source;
	private final Product product;
	
	public Production(final Nonterminal source, final Product product) {
		this.source = source;
		this.product = product;
	}
	
	public Nonterminal getSource() { return source; }
	
	public Product getProduct() { return product; }
	public List<Symbol> getProductAsList() { return product.getProduct(); }
	
	public int length() { return product.length(); }
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		else if (!(o instanceof Production)) return false;
		else {
			Production p = (Production) o;
			return p.source.equals(source)
				&& p.product.equals(product);
		}
	}
	
	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + source.hashCode();
		result = 31 * result + product.hashCode();
		return result;
	}
	
	@Override
	public String toString() {
		return quietToString();
		//return verboseToString();
	}
	
	public String quietToString() {
		return source + "\t -> \t" + product;
	}
	
	public String verboseToString() {
		return "Production[source=\"" + source + "\", product=\"" + product + "\"]";
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
		
		System.out.println("p1 = " + p1 + " = " + p1.verboseToString());
		System.out.println("p2 = " + p2 + " = " + p2.verboseToString());
		System.out.println("p3 = " + p3 + " = " + p3.verboseToString());
		System.out.println("p4 = " + p4 + " = " + p4.verboseToString());
		System.out.println("p5 = " + p5 + " = " + p5.verboseToString());
		System.out.println("p1_prime = " + p1_prime + " = " + p1_prime.verboseToString());
		System.out.println("p3_prime = " + p3_prime + " = " + p3_prime.verboseToString());
		
		System.out.println("p1 = p2? " + p1.equals(p3));
		System.out.println("p1 = p1_prime? " + p1.equals(p1_prime));
		System.out.println("p3 = p4? " + p3.equals(p4));
		System.out.println("p3 = p3_prime? " + p3.equals(p3_prime));
	}
	
}
