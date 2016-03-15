package parsetablegenerator;

/**
 *
 * @author Joseph Alacqua
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @version Mar 14, 2016
 */
public interface Symbol {
    public abstract String getName();

    public enum Epsilon implements Symbol {

        EPSILON;

        public String getName() {
            return "";
        }
        
        public String toString() {
            return "";
        }
    }
}
