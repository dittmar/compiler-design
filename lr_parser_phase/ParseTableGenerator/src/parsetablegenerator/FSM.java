package parsetablegenerator;

/**
 *
 * @author Joseph Alacqua
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @version Mar 14, 2016
 */
import java.util.List;

public class FSM
{
    List<State> states;
    List<Arc> arcs;
    NonterminalRuleLookupTable nonterminal_Rule_lookup_table;
    NumberedProductionTable numbered_production_table;
}
