
package actions;

import storyelements.Location;
import storyelements.Item;
import storyelements.StoryCharacter;
import storyelements.StoryElement;
import storygenerator.WorldProperty;
import storygenerator.WorldProperty.PropertyKey;

/**
 *
 * @author etienne
 */
public class LootTheDeadAction extends Action {
    
    public LootTheDeadAction(StoryCharacter pChar, StoryElement pTarget, Item pItem, Location pLoc) {
        super();
        
        //Text to be displayed when the action is performed in the story
        aNarrativeForm = "The " + pChar + " looted the " + pTarget + " and obtained the " + pItem + ".";
                
        //Facts that need to be TRUE to fire the action
        aPreTrue.add(new WorldProperty(pChar, PropertyKey.IS_AT_LOCATION, pLoc));
        aPreTrue.add(new WorldProperty(pTarget, PropertyKey.IS_AT_LOCATION, pLoc, true));
        aPreTrue.add(new WorldProperty(pTarget, PropertyKey.IS_DEAD));
        aPreTrue.add(new WorldProperty(pTarget, PropertyKey.OWNS, pItem, true));
        
        //Facts that need to be FALSE to fire the action
        //none
        
        //Facts that BECOME TRUE as a result
        aPostTrue.add(new WorldProperty(pChar, PropertyKey.OWNS, pItem));
        
        //Facts that BECOME FALSE as a result
        aPostFalse.add(new WorldProperty(pTarget, PropertyKey.OWNS, pItem));
        
        
//        //After looting, the target disappears forever
//        aPostFalse.add(new WorldProperty(pTarget, PropertyKey.IS_AT_LOCATION, pLoc));
//        //And the character gets any items the target had
//        ArrayList<Item> loot = aWorld.getOwnedItems(pTarget);
//        if (loot.isEmpty()) {
//            aNarrativeForm = aNarrativeForm + "nothing.";
//        }
//        else {
//            for (Iterator<Item> i = loot.iterator(); i.hasNext(); ) {
//                Item item = i.next();
//                aPostTrue.add(new WorldProperty(pChar, PropertyKey.OWNS, item));
//                aNarrativeForm = aNarrativeForm + "the " + item;
//                if (i.hasNext()) aNarrativeForm = aNarrativeForm + " and ";
//                else aNarrativeForm = aNarrativeForm + ".";
//            }
//        }
        
    }

}
