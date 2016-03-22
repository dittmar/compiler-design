package grammarTools;

import static grammarTools.Symbol.Epsilon.EPSILON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Product {
	private final List<Symbol> product;
	private static final List<Symbol> EMPTY_LIST = Collections.unmodifiableList(Arrays.asList(EPSILON));
	public static final Product EMPTY = new Product();
	
	public Product() { product = EMPTY_LIST; }
	
	public Product(Symbol... symbols) {
		List<Symbol> temp;
		if (symbols.length <= 0) temp = EMPTY_LIST;
		else if (symbols.length == 1) temp = Arrays.asList(symbols);
		else {
			temp = new ArrayList<Symbol>(Arrays.asList(symbols));
			temp.removeAll(EMPTY_LIST);
			if (temp.size() <= 0) temp = EMPTY_LIST;
		}
		product = Collections.unmodifiableList(temp);
	}
	
	public List<Symbol> getProduct() { return product; }
	
	public int length() { return product.size(); }
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		else if (!(o instanceof Product)) return false;
		else {
			Product p = (Product) o;
			return this.product.equals(p.getProduct());
		}
	}
	
	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + product.hashCode();
		return result;
	}
	
	@Override
	public String toString() {
		return quietToString();
		//return verboseToString();
	}
	
	public String quietToString() {
		String result = "";
		for(Symbol s : product) {
			result = result + s + " ";
		}
		return result;
	}
	
	public String verboseToString() {
		return "Product[product=\"" + product + "\"]";
	}
	
	public String altToString() {
		return product.toString();
	}
	
	public static void main(String[] args) {
		Product empty = new Product();
		Product p1 = new Product(new Nonterminal("S"));
		Product p2 = new Product(new Nonterminal("X"), EPSILON, new Terminal("x"));
		Product p3 = new Product(EPSILON, EPSILON, EPSILON);
		Product p4 = new Product(new Nonterminal("S"));
		
		System.out.println("empty = " + empty + " = " + empty.verboseToString());
		System.out.println("p1 = " + p1 + " = " + p1.verboseToString());
		System.out.println("p2 = " + p2 + " = " + p2.verboseToString());
		System.out.println("p3 = " + p3 + " = " + p3.verboseToString());
		System.out.println("p4 = " + p4 + " = " + p4.verboseToString());
		
		System.out.println("empty = p1? " + empty.equals(p1));
		System.out.println("empty = p3? " + empty.equals(p3));
		System.out.println("p1 = p2? " + p1.equals(p2));
		System.out.println("p1 = p4? " + p1.equals(p4));
	}

}
