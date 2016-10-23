
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
public class PickupAction extends Action {
    
    public PickupAction(StoryCharacter pChar, Item pItem, Location pLoc) {
        super();
        
        //Text to be displayed when the action is performed in the story
        aNarrativeForm = "The " + pChar + " found the " + pItem + ".";
                
        //Facts that need to be TRUE to fire the action
        aPreTrue.add(new WorldProperty(pChar, PropertyKey.IS_AT_LOCATION, pLoc));
        aPreTrue.add(new WorldProperty(pItem, PropertyKey.IS_AT_LOCATION, pLoc, true));
        
        //Facts that need to be FALSE to fire the action
        //none
        
        //Facts that BECOME TRUE as a result
        aPostTrue.add(new WorldProperty(pChar, PropertyKey.OWNS, pItem));
        
        //Facts that BECOME FALSE as a result
        aPostFalse.add(new WorldProperty(pItem, PropertyKey.IS_AT_LOCATION, pLoc));
        
    }

}
