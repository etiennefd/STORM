
package planners;

import actions.Action;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import storygenerator.World;
import storygenerator.WorldProperty;

/**
 * This planner uses a heuristic algorithm to get a path from the start state to the goal state. 
 * @author etienne
 */
public class ForwardPlanner extends Planner {
    
    //Lists of world properties to make true or achieve in the plan
    private ArrayList<WorldProperty> aGoalsToMakeTrue;
    private ArrayList<WorldProperty> aGoalsToMakeFalse;
    
    public ForwardPlanner(World pWorld) {
        super(pWorld);
        aGoalsToMakeTrue = new ArrayList<WorldProperty>();
        aGoalsToMakeFalse = new ArrayList<WorldProperty>();
        for (WorldProperty p : aWorld.getMainCharacter().getGoalsTrue()) {
            aGoalsToMakeTrue.add(p);
        }
        for (WorldProperty p : aWorld.getMainCharacter().getGoalsFalse()) {
            aGoalsToMakeFalse.add(p);
        }
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
        
        //Loop to add actions
        int i = 0;
        while (i < MAX_LENGTH && !aWorld.getMainCharacter().checkGoalCompletion(currentWorldState)) {
            Collections.shuffle(allActions, rand);
            Action nextAction = allActions.get(0);
            int distance = 99999;
            //for every allowed action, compute the resulting world state
            //then find the world state that leads to the most goals solved
            //add the corresponding action to the sequence, and update the currentWorldState
            for (Action a : allActions) {
                if (a.verifyPreconditions(currentWorldState)) {
                    
                    ArrayList<WorldProperty> tempWorldState = new ArrayList<WorldProperty>();
                    for (WorldProperty p : currentWorldState) {
                        tempWorldState.add(p);
                    }
                    a.applyPostconditions(tempWorldState);
                    
                    int d = computeDistanceFromGoal(a, tempWorldState);
                    if (d < distance) {
                        distance = d;
                        nextAction = a;
                    }
                }
            }
            aActionSequence.add(nextAction);
            nextAction.applyPostconditions(currentWorldState);
            i++;
        }
        return true;
    }
    
    @Override
    public void executePlan() {
        aWorld.executeActionSequence(aActionSequence);
    }
    
    /**
     * Heuristic to help choose an action. The lowest it is, the likeliest the 
     * action is to be taken. Things taken into account are: 
     * - number of unsolved goals (weight: 5)
     * - number of times this exact action has been taken (weight: 2)
     * - number of times an action with the same type has been taken (weight: 1)
     * @param pAction
     * @param pWorldState
     * @return 
     */
    public int computeDistanceFromGoal(Action pAction, ArrayList<WorldProperty> pWorldState) {
        int total = 0;
        total = total + (5*countUnsolvedGoals(pWorldState));
        total = total + (2*countExactOccurrences(pAction));
        total = total + (1*countTypeOccurrences(pAction));
        return total;
    }
    
    /**
     * Heuristic to determine how close a world state is to goal completion. 
     * It just counts the number of goals that are not satisfied in the world
     * state. 
     * @return 
     */
    public int countUnsolvedGoals(ArrayList<WorldProperty> pWorldState) {
        int total = 0;
        for (WorldProperty tp : aGoalsToMakeTrue) {
            if (!pWorldState.contains(tp)) {
                total++;
            }
        }
        for (WorldProperty fp : aGoalsToMakeFalse) {
            if (pWorldState.contains(fp)) {
                total++;
            }
        }
        return total;
    }
    
    /**
     * Counts occurrences of pAction in the action sequence (completely equal 
     * actions only). 
     * @param pAction
     * @return 
     */
    public int countExactOccurrences(Action pAction) {
        int total = 0;
        for (Action a : aActionSequence) {
            if (a.equals(pAction)) {
                total++;
            }
        }
        return total;
    }
    
    /**
     * Counts occurrences of pAction in the action sequence (same action type).
     * @param pAction
     * @return 
     */
    public int countTypeOccurrences(Action pAction) {
        int total = 0;
        for (Action a : aActionSequence) {
            if (a.getClass().getName().equals(pAction.getClass().getName())) {
                total++;
            }
        }
        return total;
    }
}
