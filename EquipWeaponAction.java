
package actions;

import storyelements.StoryCharacter;
import storyelements.Weapon;
import storygenerator.WorldProperty;
import storygenerator.WorldProperty.PropertyKey;

/**
 *
 * @author etienne
 */
public class EquipWeaponAction extends Action {
    
    public EquipWeaponAction(StoryCharacter pChar, Weapon pWeapon) {
        super();
        
        //Text to be displayed when the action is performed in the story
        aNarrativeForm = "The " + pChar + " equipped " + pChar.getPossessivePronoun() + 
                " " + pWeapon + ".";
             
        //Facts that need to be TRUE to fire the action
        aPreTrue.add(new WorldProperty(pChar, PropertyKey.OWNS, pWeapon));
        
        //Facts that need to be FALSE to fire the action
        aPreFalse.add(new WorldProperty(pChar, PropertyKey.IS_ARMED));
        
        //Facts that BECOME TRUE as a result
        aPostTrue.add(new WorldProperty(pChar, PropertyKey.HAS_EQUIPPED, pWeapon));
        aPostTrue.add(new WorldProperty(pChar, PropertyKey.IS_ARMED));
        
        //Facts that BECOME FALSE as a result
        //none
    }

}
