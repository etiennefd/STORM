
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
public class KillCreatureAction extends Action {
    
    public KillCreatureAction(StoryCharacter pChar, Creature pCreature, Location pLoc) {
        super();
        
        //Text to be displayed when the action is performed in the story
        aNarrativeForm = "The " + pChar + " killed the " + pCreature + ".";
                
        //Facts that need to be TRUE to fire the action
        aPreTrue.add(new WorldProperty(pChar, PropertyKey.IS_AT_LOCATION, pLoc));
        aPreTrue.add(new WorldProperty(pCreature, PropertyKey.IS_AT_LOCATION, pLoc, true));
        aPreTrue.add(new WorldProperty(pChar, PropertyKey.IS_ARMED));
        
        //Facts that need to be FALSE to fire the action
        aPreFalse.add(new WorldProperty(pCreature, PropertyKey.IS_DEAD, true));
        
        //Facts that BECOME TRUE as a result
        aPostTrue.add(new WorldProperty(pCreature, PropertyKey.IS_DEAD));
        
        //Facts that BECOME FALSE as a result
        //none
    }

}
