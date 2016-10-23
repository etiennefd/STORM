
package actions;

import storyelements.Location;
import storyelements.StoryCharacter;
import java.util.List;
import storygenerator.WorldProperty;
import storygenerator.WorldProperty.PropertyKey;

/**
 *
 * @author etienne
 */
public class TravelToAction extends Action {
    
    public TravelToAction(StoryCharacter pTraveller, Location pDestination, List<Location> pAllLocs) {
        super();
        
        //Text to be displayed when the action is performed in the story
        aNarrativeForm = "The " + pTraveller + " travelled to the " + pDestination + ".";
        
        //Facts that need to be TRUE to fire the action
        //none
        
        //Facts that need to be FALSE to fire the action
        aPreFalse.add(new WorldProperty(pTraveller, PropertyKey.IS_AT_LOCATION, pDestination));
        
        //Facts that BECOME TRUE as a result
        aPostTrue.add(new WorldProperty(pTraveller, PropertyKey.IS_AT_LOCATION, pDestination));

        //Facts that BECOME FALSE as a result
        for (Location l : pAllLocs) {
            if (!l.equals(pDestination)) {
                aPostFalse.add(new WorldProperty(pTraveller, PropertyKey.IS_AT_LOCATION, l));
            }
        }
        
    }
    
//     public TravelToAction(StoryCharacter pTraveller, Location pFrom, Location pTo) {
//        super();
//        
//        //Text to be displayed when the action is performed in the story
//        aNarrativeForm = "The " + pTraveller + " travelled from the " + pFrom + 
//                " to the " + pTo + ".";
//        
//        //Facts that need to be TRUE to fire the action
//        aPreTrue.add(new WorldProperty(pTraveller, PropertyKey.IS_AT_LOCATION, pFrom));
//        
//        //Facts that need to be FALSE to fire the action
//        aPreFalse.add(new WorldProperty(pTraveller, PropertyKey.IS_AT_LOCATION, pTo));
//        
//        //Facts that BECOME TRUE as a result
//        aPostTrue.add(new WorldProperty(pTraveller, PropertyKey.IS_AT_LOCATION, pTo));
//        
//        //Facts that BECOME FALSE as a result
//        aPostFalse.add(new WorldProperty(pTraveller, PropertyKey.IS_AT_LOCATION, pFrom));
//    }

}
