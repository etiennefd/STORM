
package storyelements;

/**
 *
 * @author etienne
 */
public class Location extends StoryElement {
    
    private String aName;
    private String aAdjective;
    
    public Location (String pName, String pAdj) {
        aName = pName;
        aAdjective = pAdj;
    }
    
    @Override
    public String toString() {
        return aAdjective + " " + aName;
    }
    
}
