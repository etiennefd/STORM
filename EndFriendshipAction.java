
package actions;

import storyelements.Location;
import storyelements.StoryCharacter;
import storygenerator.WorldProperty;
import storygenerator.WorldProperty.PropertyKey;

/**
 *
 * @author etienne
 */
public class EndFriendshipAction extends Action {
    
    public EndFriendshipAction(StoryCharacter pChar, StoryCharacter pOther, Location pLoc) {
        super();
        
        //Text to be displayed when the action is performed in the story
        aNarrativeForm = "The " + pChar + " and the " + pOther + " talked with each"
                + " other and found that they are no longer very close.";
             
        //Facts that need to be TRUE to fire the action
        aPreTrue.add(new WorldProperty(pChar, PropertyKey.IS_AT_LOCATION, pLoc));
        aPreTrue.add(new WorldProperty(pOther, PropertyKey.IS_AT_LOCATION, pLoc, true));
        aPreTrue.add(new WorldProperty(pChar, PropertyKey.LOVES, pOther));
        aPreTrue.add(new WorldProperty(pOther, PropertyKey.LOVES, pChar));
        
        //Facts that need to be FALSE to fire the action
        aPreFalse.add(new WorldProperty(pOther, PropertyKey.IS_DEAD, true));
        aPreFalse.add(new WorldProperty(pOther, PropertyKey.IS_CAPTIVE));
        
        //Facts that BECOME TRUE as a result
        //none
        
        //Facts that BECOME FALSE as a result
        aPostFalse.add(new WorldProperty(pChar, PropertyKey.LOVES, pOther));
        aPostFalse.add(new WorldProperty(pOther, PropertyKey.LOVES, pChar));
    }

}
