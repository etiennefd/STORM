
package planners;

import actions.Action;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import storygenerator.World;
import storygenerator.WorldProperty;
import storygenerator.WorldProperty.PropertyKey;

/**
 * This planner finds a plan by going from the goals to the start state. 
 * Then it runs a forward verification to remove any inconsistencies or errors
 * in the plan. 
 * @author etienne
 */
public class BackwardPlanner extends Planner {
        
    //Lists of world properties to make true or achieve in the makePlan
    private ArrayList<WorldProperty> aGoalsToMakeTrue;
    private ArrayList<WorldProperty> aGoalsToMakeFalse;
    
    //Boolean set to true when changes are made during forwardVerification. 
    boolean aDirty = true;
    
    /**
     * Constructor. 
     */
    public BackwardPlanner(World pWorld) {
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
    
    /**
     * Builds a sequence of actions to make the goals fulfilled. 
     * @return 
     */
    @Override
    public boolean makePlan() {
        aActionSequence = new LinkedList<Action>();
        
        ArrayList<WorldProperty> initialWorldState = aWorld.getWorldState();
        
        //Get all possible actions
        ArrayList<Action> allActions = aWorld.getHeroActions();
        
        //Now we pick a random goal to make true (or false). 
        //Then we try to find an action that can make this goal happen in the world state. 
        //This modifies the local worldState and the goals to make true/false list. 
        //Then repeat until the goals to make true/false lists are empty. 
        
        while (!aGoalsToMakeTrue.isEmpty() || !aGoalsToMakeFalse.isEmpty()) {

            //First, remove any goals that are initially completed. 
            ArrayList<WorldProperty> toRemoveTrue = new ArrayList<WorldProperty>();
            ArrayList<WorldProperty> toRemoveFalse = new ArrayList<WorldProperty>();
            for (WorldProperty t : aGoalsToMakeTrue) {
                if (initialWorldState.contains(t)) {
                    toRemoveTrue.add(t);
                }
            }
            for (WorldProperty t : aGoalsToMakeFalse) {
                if (!initialWorldState.contains(t)) {
                    toRemoveFalse.add(t);
                }
            }
            aGoalsToMakeTrue.removeAll(toRemoveTrue);
            aGoalsToMakeFalse.removeAll(toRemoveFalse);
            
            //If the goal lists are now empty, the makePlan is done
            if (aGoalsToMakeTrue.isEmpty() && aGoalsToMakeFalse.isEmpty()) {
                return true;
            }
            
            //If not, we seek th next action to add to our makePlan
            Action nextAction = null;
            
            //Since the location goals are prone to bugs, we try to solve them first
            WorldProperty locationGoal = null;
            for (WorldProperty goal : aGoalsToMakeTrue) {
                if (goal.aKey == PropertyKey.IS_AT_LOCATION) {
                    locationGoal = goal;
                    break;
                }
            }
            if (locationGoal != null) {
                for (Action a : allActions) {
                    if (a.postTrueContains(locationGoal)) {
                        if (!unreachableContext(a, initialWorldState)) {
                            nextAction = a;
                            break;
                        }
                    }
                }
            } 
            else {
                //trying to solve any arbitrary goal
                //We get a goal at random from either list
                Collections.shuffle(allActions, rand);
                int sizeT = aGoalsToMakeTrue.size();
                int sizeF = aGoalsToMakeFalse.size();


                if (rand.nextDouble() < (double) sizeT / (double) (sizeT + sizeF)) {
                    //try to fulfill a goal that must be true
                    WorldProperty goal = aGoalsToMakeTrue.get(rand.nextInt(sizeT));
                    for (Action a : allActions) {
                        if (a.postTrueContains(goal)) {
                            if (!unreachableContext(a, initialWorldState)) {
                                nextAction = a;
                                break;
                            }
                        }
                    }
                } else {
                    //try to fulfill a goal that must be false
                    WorldProperty goal = aGoalsToMakeFalse.get(rand.nextInt(sizeF));
                    for (Action a : allActions) {
                        if (a.postFalseContains(goal)) {
                            if (!unreachableContext(a, initialWorldState)) {
                                nextAction = a;
                                break;
                            }
                        }
                    }
                }
            }

            //If an action was found, add it to the sequence 
            //and update the lists
            if (nextAction != null) {
                aActionSequence.addFirst(nextAction);
                for (WorldProperty p : nextAction.getPreTrue()) {
                    if (!aGoalsToMakeTrue.contains(p)) {
                        aGoalsToMakeTrue.add(p);
                    }
                }
                for (WorldProperty p : nextAction.getPreFalse()) {
                    if (!aGoalsToMakeFalse.contains(p)) {
                        aGoalsToMakeFalse.add(p);
                    }
                }
                for (WorldProperty p : nextAction.getPostTrue()) {
                    if (aGoalsToMakeTrue.contains(p)) {
                        aGoalsToMakeTrue.remove(p);
                    }
                }
                for (WorldProperty p : nextAction.getPostFalse()) {
                    if (aGoalsToMakeFalse.contains(p)) {
                        aGoalsToMakeFalse.remove(p);
                    }
                }
            }
            else {
                return false; 
            }
        }
        
        return true;
    }
    
    
    /**
     * Called to execute the plan (i.e., each action) onto the world. 
     * But first, the method verifyForward is called to correct any 
     * inconsistencies in the plan.
     */
    @Override
    public void executePlan() {
        int iterations = 0;
        while (aDirty) {

//            printSequence(); //Uncomment this to print every version of the story during forward verification
            verifyForward();
            
            //This is just to make sure the loop isn't infinite
            iterations++;
            if (iterations > 50) {
                break;
            }
        }
        aWorld.executeActionSequence(aActionSequence);
    }

    /**
     * Goes through the plan from the beginning, adding actions when there are
     * preconditions that are not satisfied (because an action added later 
     * removed the precondition that was true when the action was chosen). 
     */
    public void verifyForward() {
        aDirty = false; //until otherwise stated
        
        //List to contain the modified sequence of actions
        LinkedList<Action> newSequence = new LinkedList<Action>();
        
        //List to hold the world state and modify it
        ArrayList<WorldProperty> currentWorldState = new ArrayList<WorldProperty>();
        for (WorldProperty p : aWorld.getWorldState()) {
            currentWorldState.add(p);
        }
        
        //Now we loop for each action in the sequence. 
        //Whenever one has preconditions that are not satisfied, we must add 
        //some new actions before it to make them satisfied (the same way we 
        //did the original plan, by backward planning). 
        //At the end, the action a is added to the newSequence, and its effecs
        //are applied to the currentWorldState. 
        for (Action a : aActionSequence) {
            if (a.uselessAction(currentWorldState)) {
                //The action won't have any effect, so it can be removed. 
                //That's likely due to the action having already been taken earlier. 
                continue;
            }
            if (unreachableContext(a, currentWorldState)) {
                //It is impossible to accomplish this action, so remove it. 
                continue;
            }
            if (!a.verifyPreconditions(currentWorldState)) {
                //Now is the case where the action can't be taken because of 
                //missing preconditions.
                
                //get all preconditions
                //keep only those that are not true in currentWorldState
                //they constitute the goal list for the mini-makePlan
                ArrayList<WorldProperty> goalsTrue = a.getPreTrue();
                ArrayList<WorldProperty> goalsFalse = a.getPreFalse();
                ArrayList<WorldProperty> goalsTrueToSatisfy = new ArrayList<WorldProperty>();
                ArrayList<WorldProperty> goalsFalseToSatisfy = new ArrayList<WorldProperty>();

                for (WorldProperty g : goalsTrue) {
                    if (!currentWorldState.contains(g)) {
                        goalsTrueToSatisfy.add(g);
                    }
                }
                for (WorldProperty g : goalsFalse) {
                    if (currentWorldState.contains(g)) {
                        goalsFalseToSatisfy.add(g);
                    }
                }
                planCorrection(currentWorldState, goalsTrueToSatisfy, goalsFalseToSatisfy, newSequence, newSequence.size());
                aDirty = true;
            }
            newSequence.add(a);
            a.applyPostconditions(currentWorldState);
        }

        //Also add a check for missing goals at the end of the game
        //(Most likely goals that were thought to be true because they are in 
        //the initial world state)
        ArrayList<WorldProperty> goalsTrue = aWorld.getMainCharacter().getGoalsTrue();
        ArrayList<WorldProperty> goalsFalse = aWorld.getMainCharacter().getGoalsFalse();
        ArrayList<WorldProperty> goalsTrueToSatisfy = new ArrayList<WorldProperty>();
        ArrayList<WorldProperty> goalsFalseToSatisfy = new ArrayList<WorldProperty>();
        
        for (WorldProperty g : goalsTrue) {
            if (!currentWorldState.contains(g)) {
                goalsTrueToSatisfy.add(g);
            }
        }
        for (WorldProperty g : goalsFalse) {
            if (!currentWorldState.contains(g)) {
                goalsFalseToSatisfy.add(g);
            }
        }
        if (!goalsTrueToSatisfy.isEmpty() || !goalsFalseToSatisfy.isEmpty()) {
           planCorrection(currentWorldState, goalsTrueToSatisfy, goalsFalseToSatisfy, newSequence, newSequence.size());
            aDirty = true; 
        }
        
        //Finally we replace the action sequence with the updated one
        aActionSequence = newSequence;
    }
    
    
    /**
     * Returns true if a precondition of an action is context (cannot be
     * influenced by the character) and is not fulfilled given the world state. 
     * @param a
     * @param pWorldState
     * @return 
     */
    private boolean unreachableContext(Action a, ArrayList<WorldProperty> pWorldState) {
        for (WorldProperty p : a.getPreTrue()) {
            if (p.aIsContext && !pWorldState.contains(p)) {
                return true; //unreachable
            }
        }
        for (WorldProperty p : a.getPreFalse()) {
            if (p.aIsContext && pWorldState.contains(p)) {
                return true; //unreachable
            }
        }
        return false;
    }
    
    /**
     * Adds actions backward from pActionIndex backwards to satisfy all pGoals.
     * This method is similar but different in details to the main makePlan().
     *
     * @param pActionIndex
     */
    private void planCorrection(ArrayList<WorldProperty> pWorldState,
            ArrayList<WorldProperty> pGoalsTrue, ArrayList<WorldProperty> pGoalsFalse,
            LinkedList<Action> pActionSequence, int pActionIndex) {

        //Get all possible actions
        ArrayList<Action> allActions = aWorld.getHeroActions();

        while (!pGoalsTrue.isEmpty() || !pGoalsFalse.isEmpty()) {
            //First, remove any goals that are initially completed. 
            ArrayList<WorldProperty> toRemoveTrue = new ArrayList<WorldProperty>();
            ArrayList<WorldProperty> toRemoveFalse = new ArrayList<WorldProperty>();
            for (WorldProperty t : pGoalsTrue) {
                if (pWorldState.contains(t)) {
                    toRemoveTrue.add(t);
                }
            }
            for (WorldProperty t : pGoalsFalse) {
                if (!pWorldState.contains(t)) {
                    toRemoveFalse.add(t);
                }
            }
            pGoalsTrue.removeAll(toRemoveTrue);
            pGoalsFalse.removeAll(toRemoveFalse);
            
            //If the goal lists are now empty, the makePlan is done
            if (pGoalsTrue.isEmpty() && pGoalsFalse.isEmpty()) {
                return;
            }
            
            //If not, we seek th next action to add to our makePlan
            Action nextAction = null;
            
            //Since the location goals are prone to bugs, we try to solve them first
            WorldProperty locationGoal = null;
            for (WorldProperty goal : pGoalsTrue) {
                if (goal.aKey == PropertyKey.IS_AT_LOCATION) {
                    locationGoal = goal;
                    break;
                }
            }
            if (locationGoal != null) {
                for (Action a : allActions) {
                    if (a.postTrueContains(locationGoal)) {
                        if (!unreachableContext(a, pWorldState)) {
                            nextAction = a;
                            break;
                        }
                    }
                }
            } 
            else {
                //trying to solve any arbitrary goal
                //We get a goal at random from either list
                Collections.shuffle(allActions, rand);
                int sizeT = pGoalsTrue.size();
                int sizeF = pGoalsFalse.size();


                if (rand.nextDouble() < (double) sizeT / (double) (sizeT + sizeF)) {
                    //try to fulfill a goal that must be true
                    WorldProperty goal = pGoalsTrue.get(rand.nextInt(sizeT));
                    for (Action a : allActions) {
                        if (a.postTrueContains(goal)) {
                            if (!unreachableContext(a, pWorldState)) {
                                nextAction = a;
                                break;
                            }
                        }
                    }
                } else {
                    //try to fulfill a goal that must be false
                    WorldProperty goal = pGoalsFalse.get(rand.nextInt(sizeF));
                    for (Action a : allActions) {
                        if (a.postFalseContains(goal)) {
                            if (!unreachableContext(a, pWorldState)) {
                                nextAction = a;
                                break;
                            }
                        }
                    }
                }
            }

            //If an action was found, add it to the sequence 
            //and update the lists
            if (nextAction != null) {
                pActionSequence.add(pActionIndex, nextAction);
                for (WorldProperty p : nextAction.getPreTrue()) {
                    if (!pGoalsTrue.contains(p)) {
                        pGoalsTrue.add(p);
                    }
                }
                for (WorldProperty p : nextAction.getPreFalse()) {
                    if (!pGoalsFalse.contains(p)) {
                        pGoalsFalse.add(p);
                    }
                }
                for (WorldProperty p : nextAction.getPostTrue()) {
                    if (pGoalsTrue.contains(p)) {
                        pGoalsTrue.remove(p);
                    }
                }
                for (WorldProperty p : nextAction.getPostFalse()) {
                    if (pGoalsFalse.contains(p)) {
                        pGoalsFalse.remove(p);
                    }
                }
            }
            else {
                return;
            }
        }
    }
    
    
}
