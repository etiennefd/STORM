
package planners;

import actions.Action;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import storygenerator.World;
import storygenerator.WorldProperty;

/**
 * This planner allows the hero and the villain to take turns picking actions. 
 * It is based on the forward planner.
 * @author etienne
 */
public class CompetitivePlanner extends Planner {
    
    //Lists of world properties to make true or achieve in the plan
    private ArrayList<WorldProperty> aHeroGoalsToMakeTrue;
    private ArrayList<WorldProperty> aHeroGoalsToMakeFalse;
    private ArrayList<WorldProperty> aAntiGoals;
    
    public CompetitivePlanner(World pWorld) {
        super(pWorld);
        aHeroGoalsToMakeTrue = new ArrayList<WorldProperty>();
        aHeroGoalsToMakeFalse = new ArrayList<WorldProperty>();
        aAntiGoals = new ArrayList<WorldProperty>();
        for (WorldProperty p : aWorld.getMainCharacter().getGoalsTrue()) {
            aHeroGoalsToMakeTrue.add(p);
        }
        for (WorldProperty p : aWorld.getMainCharacter().getGoalsFalse()) {
            aHeroGoalsToMakeFalse.add(p);
        }
        for (WorldProperty p : aWorld.getVillain().getAntiGoals()) {
            aAntiGoals.add(p);
        }
    }
    
    @Override
    public boolean makePlan() {
        aActionSequence = new LinkedList<Action>();
        
        //Get all possible actions
        ArrayList<Action> allHeroActions = aWorld.getHeroActions();
        ArrayList<Action> allVillainActions = aWorld.getVillainActions();
        
        //List to hold the world state and modify it
        ArrayList<WorldProperty> currentWorldState = new ArrayList<WorldProperty>();
        for (WorldProperty p : aWorld.getWorldState()) {
            currentWorldState.add(p);
        }
        
        //Loop to add actions
        //We add an action to the hero if i is even and to the villain if i is odd
        int i = 0;
        while (i < MAX_LENGTH 
                && !aWorld.getMainCharacter().checkGoalCompletion(currentWorldState)
                && !aWorld.getVillain().checkAntiGoalCompletion(currentWorldState)) {
            
            Action nextAction = allHeroActions.get(0);
            if (i % 2 == 0) {
                int distance = 99999;
                Collections.shuffle(allHeroActions, rand);
                for (Action a : allHeroActions) {
                    if (a.verifyPreconditions(currentWorldState)) {

                        ArrayList<WorldProperty> tempWorldState = new ArrayList<WorldProperty>();
                        for (WorldProperty p : currentWorldState) {
                            tempWorldState.add(p);
                        }
                        a.applyPostconditions(tempWorldState);

                        int d = computeDistanceFromGoal(a, tempWorldState, false);
                        if (d < distance) {
                            distance = d;
                            nextAction = a;
                        }
                    }
                }
            } 
            else {
                //Villain action
                int distance = 99999;
                Collections.shuffle(allVillainActions);
                for (Action a : allVillainActions) {
                    if (a.verifyPreconditions(currentWorldState)) {

                        ArrayList<WorldProperty> tempWorldState = new ArrayList<WorldProperty>();
                        for (WorldProperty p : currentWorldState) {
                            tempWorldState.add(p);
                        }
                        a.applyPostconditions(tempWorldState);

                        int d = computeDistanceFromGoal(a, tempWorldState, true);
                        if (d < distance) {
                            distance = d;
                            nextAction = a;
                        }
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
    public int computeDistanceFromGoal(Action pAction, ArrayList<WorldProperty> pWorldState, boolean pIsVillain) {
        int total = 0;
        if (!pIsVillain) total = total + (5*countUnsolvedGoals(pWorldState));
        else total = total + (5*countUnsolvedAntiGoals(pWorldState));
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
        for (WorldProperty tp : aHeroGoalsToMakeTrue) {
            if (!pWorldState.contains(tp)) {
                total++;
            }
        }
        for (WorldProperty fp : aHeroGoalsToMakeFalse) {
            if (pWorldState.contains(fp)) {
                total++;
            }
        }
        return total;
    }
    
    /**
     * Returns 1 if at least one antigoal is fulfilled, 0 otherwise. 
     * @return 
     */
    public int countUnsolvedAntiGoals(ArrayList<WorldProperty> pWorldState) {
        for (WorldProperty ag : aAntiGoals) {
            if (pWorldState.contains(ag)) {
                return 1;
            }
        }
        return 0;
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
