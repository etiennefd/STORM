
package actions;

import storyelements.Location;
import storyelements.StoryCharacter;
import storyelements.Weapon;
import storygenerator.WorldProperty;
import storygenerator.WorldProperty.PropertyKey;

/**
 *
 * @author etienne
 */
public class ActionTemplate extends Action {
    
    public ActionTemplate(StoryCharacter pChar, Weapon pWeapon, Location pLoc) {
        super();
        
        //Text to be displayed when the action is performed in the story
        aNarrativeForm = "The " + pChar + " equipped the weapon " + pWeapon + ".";
             
        //Facts that need to be TRUE to fire the action
        aPreTrue.add(new WorldProperty(pChar, PropertyKey.OWNS, pWeapon));
        
        //Facts that need to be FALSE to fire the action
        aPreFalse.add(new WorldProperty(pChar, PropertyKey.HAS_EQUIPPED, pWeapon));
        
        //Facts that BECOME TRUE as a result
        aPostTrue.add(new WorldProperty(pChar, PropertyKey.HAS_EQUIPPED, pWeapon));
        
        //Facts that BECOME FALSE as a result
        aPostFalse.add(new WorldProperty(pChar, PropertyKey.IS_AT_LOCATION, pLoc));
        
    }

}
