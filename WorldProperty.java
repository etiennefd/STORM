
package storygenerator;

import storyelements.StoryElement;

/**
 *
 * @author etienne
 */
public class WorldProperty {
    
    public enum PropertyKey {
        IS_ARMED, IS_DEAD, IS_DESTROYED, IS_CAPTIVE,
        OWNS, IS_AT_LOCATION, LOVES, HATES, HAS_EQUIPPED, IS_PRISONER_OF
    }
    
    //This determines what kind of fact about the world this property is.
    public PropertyKey aKey;
    
    //These variables determine to what StoryElement(s) this property applies. 
    //There must be a subject, but not necessarily an object
    //E.g. Subject OWNS Object
    //E.g. Subject IS_DEAD [no object]
    public StoryElement aSubject;
    public StoryElement aObject;
    
    //This is true if no actions can have an effect on the property. 
    //Then the property is either always true or always false. 
    public boolean aIsContext;
    
    /*
     * There are 4 constructors. If no context boolean is provided, the context boolean
     * is set to false. 
     */
    
    public WorldProperty(StoryElement pSubject, PropertyKey pKey, StoryElement pObject) {
        aKey = pKey;
        aSubject = pSubject;
        aObject = pObject;
        aIsContext = false;
    }
    
    public WorldProperty(StoryElement pSubject, PropertyKey pKey) {
        aKey = pKey;
        aSubject = pSubject;
        aObject = null;
        aIsContext = false;
    }
    
    public WorldProperty(StoryElement pSubject, PropertyKey pKey, StoryElement pObject, boolean pIsContext) {
        aKey = pKey;
        aSubject = pSubject;
        aObject = pObject;
        aIsContext = pIsContext;
    }
    
    public WorldProperty(StoryElement pSubject, PropertyKey pKey, boolean pIsContext) {
        aKey = pKey;
        aSubject = pSubject;
        aObject = null;
        aIsContext = pIsContext;
    }
    
    @Override
    public String toString() {
        String s = aSubject.toString();
//        switch(aKey) {
//            case IS_AT_LOCATION: s = s + " is at the "; break;
//            case IS_DEAD: s = s + " is dead."; break;
//            default: s = s + " " + aKey.toString(); break;
//        }
        s = s + " " + aKey.toString();
        if (aObject != null) {
            s = s + " " + aObject.toString() + ".";
        }
        else {
            s = s + ".";
        }
        return s;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o == this) return true;
        if (!(o instanceof WorldProperty)) return false;
        WorldProperty w = (WorldProperty) o;
        if (w.aKey == this.aKey && w.aSubject.equals(this.aSubject) &&
                ((w.aObject == null && this.aObject == null) ||
                (w.aObject != null && this.aObject != null && 
                w.aObject.equals(this.aObject)))){
            return true;
        }
        else return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + (this.aKey != null ? this.aKey.hashCode() : 0);
        hash = 79 * hash + (this.aSubject != null ? this.aSubject.hashCode() : 0);
        hash = 79 * hash + (this.aObject != null ? this.aObject.hashCode() : 0);
        return hash;
    }
}
