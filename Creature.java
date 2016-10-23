
package storyelements;

/**
 *
 * @author etienne
 */
public class Creature extends StoryElement {
    
    private String aName;
    private String aAdjective;
    
    public Creature (String pName, String pAdj) {
        aName = pName;
        aAdjective = pAdj;
    }
    
    @Override
    public String toString() {
        return aAdjective + " " + aName;
    }
}
