
package actions;

import storyelements.Item;
import storyelements.StoryCharacter;
import storygenerator.WorldProperty;
import storygenerator.WorldProperty.PropertyKey;

/**
 *
 * @author etienne
 */
public class DestroyItemAction extends Action {
    
    public DestroyItemAction(StoryCharacter pChar, Item pItem) {
        super();
        
        //Text to be displayed when the action is performed in the story
        aNarrativeForm = "The " + pChar + " destroyed the " + pItem + ".";
             
        //Facts that need to be TRUE to fire the action
        aPreTrue.add(new WorldProperty(pChar, PropertyKey.OWNS, pItem));
        
        //Facts that need to be FALSE to fire the action
        aPreFalse.add(new WorldProperty(pChar, PropertyKey.HAS_EQUIPPED, pItem));
        
        //Facts that BECOME TRUE as a result
        aPostTrue.add(new WorldProperty(pItem, PropertyKey.IS_DESTROYED));
        
        //Facts that BECOME FALSE as a result
        aPostFalse.add(new WorldProperty(pChar, PropertyKey.OWNS, pItem));
        
    }

}
