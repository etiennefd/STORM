
package actions;

import storyelements.StoryCharacter;
import storyelements.Weapon;
import java.util.List;
import storygenerator.WorldProperty;
import storygenerator.WorldProperty.PropertyKey;

/**
 *
 * @author etienne
 */
public class UnequipWeaponAction extends Action {
    
    public UnequipWeaponAction(StoryCharacter pChar, List<Weapon> pWeaponList) {
        super();
        
        //Text to be displayed when the action is performed in the story
        aNarrativeForm = "The " + pChar + " put " + pChar.getPossessivePronoun() 
                + " weapon back in " 
                + pChar.getPossessivePronoun() + " bag.";
             
        //Facts that need to be TRUE to fire the action
        aPreTrue.add(new WorldProperty(pChar, PropertyKey.IS_ARMED));
        
        //Facts that need to be FALSE to fire the action
        //none
        
        //Facts that BECOME TRUE as a result
        //none
        
        //Facts that BECOME FALSE as a result
        aPostFalse.add(new WorldProperty(pChar, PropertyKey.IS_ARMED));
        for (Weapon w : pWeaponList) {
            aPostFalse.add(new WorldProperty(pChar, PropertyKey.HAS_EQUIPPED, w));
        }
    }

}
