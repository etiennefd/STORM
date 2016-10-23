
package planners;

import actions.Action;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import storygenerator.World;
import storygenerator.WorldProperty;

/**
 * This planner picks random actions at any given time. 
 * @author etienne
 */
public class RandomPlanner extends Planner {
    
    public RandomPlanner(World pWorld) {
        super(pWorld);
    }
    
    @Override
    public boolean makePlan() {
        aActionSequence = new LinkedList<Action>();
        
        //Get all possible actions
        ArrayList<Action> allActions = aWorld.getHeroActions();
        
        //List to hold the world state and modify it
        ArrayList<WorldProperty> currentWorldState = new ArrayList<WorldProperty>();
        for (WorldProperty p : aWorld.getWorldState()) {
            currentWorldState.add(p);
        }
        
        //Loop to add random actions
        int i = 0;
        while (i < MAX_LENGTH && !aWorld.getMainCharacter().checkGoalCompletion(currentWorldState)) {
            Collections.shuffle(allActions, rand);
            for (Action a : allActions) {
                if (a.verifyPreconditions(currentWorldState)) {
                    aActionSequence.add(a);
                    i++;
                    a.applyPostconditions(currentWorldState);
                    break;
                }
            }
        }
        return true;
    }
    
    @Override
    public void executePlan() {
        aWorld.executeActionSequence(aActionSequence);
    }
}
