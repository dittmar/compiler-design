package grammarTools;

public class Terminal implements Symbol {
	private final String name;

	public Terminal(String name) {
        name = name.replaceAll("\\s", "");
		if (name.isEmpty() || name.matches("[A-Z].*"))
        {
			throw new IllegalArgumentException(
                "Terminal: Terminal names must begin with a lowercase letter");
        }
		else
        {
			this.name = name;
        }
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		else if (!(o instanceof Terminal))
			return false;
		else {
			Terminal nt = (Terminal) o;
			return this.name.equals(nt.getName());
		}
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + name.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return quietToString();
		//return verboseToString();
	}

	public String quietToString() {
		return name;
	}

	public String verboseToString() {
		return "Terminal[name=\"" + name + "\"]";
	}
	
	public static void main(String[] args) {
		Terminal s = new Terminal("s");
		Terminal x = new Terminal("x");
		Terminal s2 = new Terminal("s");
		
		System.out.println("s = " + s + " = " + s.verboseToString());
		System.out.println("x = " + x + " = " + x.verboseToString());
		System.out.println("s2 = " + s2 + " = " + s2.verboseToString());
		
		System.out.println("s = x? " + s.equals(x));
		System.out.println("s = s2? " + s.equals(s2));
		
		assert !s.equals(x);
		assert s.equals(s2);
	}

}
