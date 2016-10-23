
package actions;

import storyelements.Location;
import storyelements.StoryCharacter;
import storyelements.Weapon;
import java.util.List;
import storygenerator.WorldProperty;
import storygenerator.WorldProperty.PropertyKey;

/**
 *
 * @author etienne
 */
public class DisarmOpponentAction extends Action {
    
    public DisarmOpponentAction(StoryCharacter pChar, StoryCharacter pOpponent, List<Weapon> pWeaponList, Location pLoc) {
        super();
        
        //Text to be displayed when the action is performed in the story
        aNarrativeForm = "The " + pChar + " fought with the " + pOpponent 
                + " and disarmed " + pOpponent.getObjectPronoun() + ".";
             
        //Facts that need to be TRUE to fire the action
        aPreTrue.add(new WorldProperty(pChar, PropertyKey.IS_AT_LOCATION, pLoc));
        aPreTrue.add(new WorldProperty(pOpponent, PropertyKey.IS_AT_LOCATION, pLoc, true));
        aPreTrue.add(new WorldProperty(pChar, PropertyKey.IS_ARMED));
        aPreTrue.add(new WorldProperty(pOpponent, PropertyKey.IS_ARMED, true));
        aPreTrue.add(new WorldProperty(pChar, PropertyKey.HATES, pOpponent));
        
        //Facts that need to be FALSE to fire the action
        aPreFalse.add(new WorldProperty(pChar, PropertyKey.LOVES, pOpponent));
        aPreFalse.add(new WorldProperty(pOpponent, PropertyKey.IS_DEAD, true));
        aPreFalse.add(new WorldProperty(pOpponent, PropertyKey.IS_CAPTIVE));
        
        //Facts that BECOME TRUE as a result
        //none
        
        //Facts that BECOME FALSE as a result
        for (Weapon w : pWeaponList) {
            aPostFalse.add(new WorldProperty(pOpponent, PropertyKey.HAS_EQUIPPED, w));
        }
        aPostFalse.add(new WorldProperty(pOpponent, PropertyKey.IS_ARMED));
        
    }

}
