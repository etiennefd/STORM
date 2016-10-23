
package actions;

import storyelements.Location;
import storyelements.Item;
import storyelements.StoryCharacter;
import storygenerator.WorldProperty;
import storygenerator.WorldProperty.PropertyKey;

/**
 *
 * @author etienne
 */
public class TradeItemAction extends Action {
    
    public TradeItemAction(StoryCharacter pChar, StoryCharacter pTrader, Location pLoc, Item pGiven, Item pObtained) {
        super();
        
        //Text to be displayed when the action is performed in the story
        aNarrativeForm = "The " + pChar + " traded " + pChar.getPossessivePronoun()
                + " " + pGiven + " for the " + pTrader + "'s " + pObtained + ".";
                
        //Facts that need to be TRUE to fire the action
        aPreTrue.add(new WorldProperty(pChar, PropertyKey.IS_AT_LOCATION, pLoc));
        aPreTrue.add(new WorldProperty(pTrader, PropertyKey.IS_AT_LOCATION, pLoc, true));
        aPreTrue.add(new WorldProperty(pChar, PropertyKey.OWNS, pGiven));
        aPreTrue.add(new WorldProperty(pTrader, PropertyKey.OWNS, pObtained, true));
        
        //Facts that need to be FALSE to fire the action
        aPreFalse.add(new WorldProperty(pChar, PropertyKey.HAS_EQUIPPED, pGiven));
        aPreFalse.add(new WorldProperty(pChar, PropertyKey.HATES, pTrader));
        aPreFalse.add(new WorldProperty(pTrader, PropertyKey.HATES, pChar));
        aPreFalse.add(new WorldProperty(pTrader, PropertyKey.IS_CAPTIVE));
        aPreFalse.add(new WorldProperty(pTrader, PropertyKey.IS_DEAD, true));
        
        //Facts that BECOME TRUE as a result
        aPostTrue.add(new WorldProperty(pChar, PropertyKey.OWNS, pObtained));
        aPostTrue.add(new WorldProperty(pTrader, PropertyKey.OWNS, pGiven));
        
        //Facts that BECOME FALSE as a result
        aPostFalse.add(new WorldProperty(pChar, PropertyKey.OWNS, pGiven));
        aPostFalse.add(new WorldProperty(pTrader, PropertyKey.OWNS, pObtained));
                
    }

}
