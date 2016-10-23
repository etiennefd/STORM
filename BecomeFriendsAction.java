
package actions;

import storyelements.Location;
import storyelements.StoryCharacter;
import storygenerator.WorldProperty;
import storygenerator.WorldProperty.PropertyKey;

/**
 *
 * @author etienne
 */
public class BecomeFriendsAction extends Action {
    
    public BecomeFriendsAction(StoryCharacter pChar, StoryCharacter pOther, Location pLoc) {
        super();
        
        //Text to be displayed when the action is performed in the story
        aNarrativeForm = "The " + pChar + " and the " + pOther + " talked with each"
                + " other and became friends.";
             
        //Facts that need to be TRUE to fire the action
        aPreTrue.add(new WorldProperty(pChar, PropertyKey.IS_AT_LOCATION, pLoc));
        aPreTrue.add(new WorldProperty(pOther, PropertyKey.IS_AT_LOCATION, pLoc, true));
        
        //Facts that need to be FALSE to fire the action
        aPreFalse.add(new WorldProperty(pChar, PropertyKey.LOVES, pOther));
        aPreFalse.add(new WorldProperty(pOther, PropertyKey.LOVES, pChar));
        aPreFalse.add(new WorldProperty(pChar, PropertyKey.HATES, pOther));
        aPreFalse.add(new WorldProperty(pOther, PropertyKey.HATES, pChar));
        aPreFalse.add(new WorldProperty(pOther, PropertyKey.IS_DEAD));
        aPreFalse.add(new WorldProperty(pOther, PropertyKey.IS_CAPTIVE));
        
        //Facts that BECOME TRUE as a result
        aPostTrue.add(new WorldProperty(pChar, PropertyKey.LOVES, pOther));
        aPostTrue.add(new WorldProperty(pOther, PropertyKey.LOVES, pChar));
        
        //Facts that BECOME FALSE as a result
        //none
    }

}
