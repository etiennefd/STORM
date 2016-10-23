
package storyelements;

/**
 *
 * @author etienne
 */
public class Item extends StoryElement {
    
    private String aName;
    private String aPreAdjective;
    private String aPostAdjective;
    
    public Item (String pName, String pPre, String pPost) {
        aName = pName;
        aPreAdjective = pPre;
        aPostAdjective = pPost;
    }
    
    @Override
    public String toString() {
        String s = "";
        if (!aPreAdjective.equals("")) s = s + aPreAdjective + " ";
        s = s + aName;
        if (!aPostAdjective.equals("")) s = s + " " + aPostAdjective;
        return s;
    }
}
