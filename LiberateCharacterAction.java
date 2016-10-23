
package actions;

import storyelements.Location;
import storyelements.Creature;
import storyelements.StoryCharacter;
import storygenerator.WorldProperty;
import storygenerator.WorldProperty.PropertyKey;

/**
 *
 * @author etienne
 */
public class LiberateCharacterAction extends Action {
    
    public LiberateCharacterAction(StoryCharacter pChar, StoryCharacter pCaptive, Creature pCreature, Location pLoc) {
        super();
        
        //Text to be displayed when the action is performed in the story
        aNarrativeForm = "The " + pChar + " liberated the " + pCaptive + " from the "
                + "clutches of the " + pCreature + ".";
             
        //Facts that need to be TRUE to fire the action
        aPreTrue.add(new WorldProperty(pChar, PropertyKey.IS_AT_LOCATION, pLoc));
        aPreTrue.add(new WorldProperty(pCaptive, PropertyKey.IS_AT_LOCATION, pLoc, true));
        aPreTrue.add(new WorldProperty(pCreature, PropertyKey.IS_AT_LOCATION, pLoc, true));
        aPreTrue.add(new WorldProperty(pCreature, PropertyKey.IS_DEAD));
        aPreTrue.add(new WorldProperty(pCaptive, PropertyKey.IS_CAPTIVE, true));
        aPreTrue.add(new WorldProperty(pCaptive, PropertyKey.IS_PRISONER_OF, pCreature, true));
        
        //Facts that need to be FALSE to fire the action
        aPreFalse.add(new WorldProperty(pCaptive, PropertyKey.IS_DEAD, true));
        
        //Facts that BECOME TRUE as a result
        aPostTrue.add(new WorldProperty(pChar, PropertyKey.LOVES, pCaptive));
        aPostTrue.add(new WorldProperty(pCaptive, PropertyKey.LOVES, pChar));
        
        //Facts that BECOME FALSE as a result
        aPostFalse.add(new WorldProperty(pCaptive, PropertyKey.IS_CAPTIVE));
        aPostFalse.add(new WorldProperty(pCaptive, PropertyKey.IS_PRISONER_OF, pCreature));
        aPostFalse.add(new WorldProperty(pChar, PropertyKey.HATES, pCaptive));
        aPostFalse.add(new WorldProperty(pCaptive, PropertyKey.HATES, pChar));
        
    }

}
