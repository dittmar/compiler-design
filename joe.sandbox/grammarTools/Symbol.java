package grammarTools;

public interface Symbol {
	public abstract String getName();

	public enum Epsilon implements Symbol {
		EPSILON;
		public String getName() { return "EPSILON"; }
	}
}
