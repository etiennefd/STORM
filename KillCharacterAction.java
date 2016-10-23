
package actions;

import storyelements.Location;
import storyelements.StoryCharacter;
import storygenerator.WorldProperty;
import storygenerator.WorldProperty.PropertyKey;

/**
 *
 * @author etienne
 */
public class KillCharacterAction extends Action {
    
    public KillCharacterAction(StoryCharacter pChar, StoryCharacter pTarget, Location pLoc) {
        super();
        
        //Text to be displayed when the action is performed in the story
        aNarrativeForm = "The " + pChar + " murdered the " + pTarget + ".";
                
        //Facts that need to be TRUE to fire the action
        aPreTrue.add(new WorldProperty(pChar, PropertyKey.IS_AT_LOCATION, pLoc));
        aPreTrue.add(new WorldProperty(pTarget, PropertyKey.IS_AT_LOCATION, pLoc, true));
        aPreTrue.add(new WorldProperty(pChar, PropertyKey.IS_ARMED));
        aPreTrue.add(new WorldProperty(pChar, PropertyKey.HATES, pTarget));
        
        //Facts that need to be FALSE to fire the action
        aPreFalse.add(new WorldProperty(pTarget, PropertyKey.IS_DEAD, true));
        aPreFalse.add(new WorldProperty(pTarget, PropertyKey.IS_CAPTIVE));
        aPreFalse.add(new WorldProperty(pChar, PropertyKey.LOVES, pTarget));
        aPreFalse.add(new WorldProperty(pTarget, PropertyKey.IS_ARMED, true));
        
        //Facts that BECOME TRUE as a result
        aPostTrue.add(new WorldProperty(pTarget, PropertyKey.IS_DEAD));
        
        //Facts that BECOME FALSE as a result
        //none
        
        //Love-hate relationships influenced by the murder? Nah. 
        
    }

}
