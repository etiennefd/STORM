
package actions;

import storyelements.Location;
import storyelements.Item;
import storyelements.StoryCharacter;
import storygenerator.WorldProperty;
import storygenerator.WorldProperty.PropertyKey;

/**
 *
 * @author etienne
 */
public class StealItemAction extends Action {
    
    public StealItemAction(StoryCharacter pChar, StoryCharacter pTarget, Item pItem, Location pLoc) {
        super();
        
        //Text to be displayed when the action is performed in the story
        aNarrativeForm = "The " + pChar + " stole the " + pItem + " from the " + pTarget + ".";
             
        //Facts that need to be TRUE to fire the action
        aPreTrue.add(new WorldProperty(pChar, PropertyKey.IS_AT_LOCATION, pLoc));
        aPreTrue.add(new WorldProperty(pTarget, PropertyKey.IS_AT_LOCATION, pLoc, true));
        aPreTrue.add(new WorldProperty(pTarget, PropertyKey.OWNS, pItem, true));
        
        //Facts that need to be FALSE to fire the action
        aPreFalse.add(new WorldProperty(pTarget, PropertyKey.HAS_EQUIPPED, pItem, true));
        aPreFalse.add(new WorldProperty(pChar, PropertyKey.LOVES, pTarget));
        aPreFalse.add(new WorldProperty(pTarget, PropertyKey.IS_CAPTIVE));
        aPreFalse.add(new WorldProperty(pTarget, PropertyKey.IS_DEAD, true));
        
        //Facts that BECOME TRUE as a result
        aPostTrue.add(new WorldProperty(pChar, PropertyKey.OWNS, pItem));
        
        //Facts that BECOME FALSE as a result
        aPostFalse.add(new WorldProperty(pTarget, PropertyKey.OWNS, pItem));
        
    }

}
