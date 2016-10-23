
package storyelements;

/**
 *
 * @author etienne
 */
public abstract class StoryElement {
    
    private static int aNumberOfGameObjects = 0;
    
    private int aID;
    
    protected StoryElement() {
        aNumberOfGameObjects++;
        aID = aNumberOfGameObjects;
    }
    
    public int getID() {
        return aID;
    }
    
    /**
     * Returns a string "n" if the following word starts with a vowel.
     * @param noun
     * @return
     */
    public static String addN(String word) {
        char first = word.toLowerCase().charAt(0);
        if (first == 'a' || first == 'e' || first == 'i' || first == 'o'
                || first == 'u') {
            return "n";
        } else {
            return "";
        }
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o == this) return true;
        if (!(o instanceof StoryElement)) return false;
        StoryElement g = (StoryElement) o;
        if (g.aID == this.aID){
            return true;
        }
        else return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + this.aID;
        return hash;
    }
}
