
package storyelements;

import java.util.ArrayList;
import storygenerator.WorldProperty;

/**
 *
 * @author etienne
 */
public class PrimaryCharacter extends StoryCharacter {
    
    //The hero tries to make all of the properties in this list true:
    ArrayList<WorldProperty> aGoalsTrue = new ArrayList<WorldProperty>();
    //They also try to make all of the properties in this list false:
    ArrayList<WorldProperty> aGoalsFalse = new ArrayList<WorldProperty>();
    
    //If the character is an antagonist, then they try to make at least one of 
    //the following properties true, thus bringing the hero's quest to failure:
    ArrayList<WorldProperty> aAntiGoals = new ArrayList<WorldProperty>();
    
    public PrimaryCharacter(String pOcc) {
        super(pOcc);
    }
    
    public void addGoalTrue(WorldProperty pGoal) {
        aGoalsTrue.add(pGoal);
    }
    public void addGoalFalse(WorldProperty pGoal) {
        aGoalsFalse.add(pGoal);
    }
    public void addAntiGoal(WorldProperty pGoal) {
        aAntiGoals.add(pGoal);
    }
    
    public ArrayList<WorldProperty> getGoalsTrue() {
        return aGoalsTrue;
    }
    public ArrayList<WorldProperty> getGoalsFalse() {
        return aGoalsFalse;
    }
    public ArrayList<WorldProperty> getAntiGoals() {
        return aAntiGoals;
    }
    
    /**
     * Returns true if all the goals are in the world state; that is, all the
     * goals of the character have been completed. 
     * @param pWorldState
     * @return 
     */
    public boolean checkGoalCompletion(ArrayList<WorldProperty> pWorldState) {
        for (WorldProperty t : aGoalsTrue) {
            if (!pWorldState.contains(t)) {
                return false;
            }
        }
        for (WorldProperty f : aGoalsFalse) {
            if (pWorldState.contains(f)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Returns true if at least one anti-goal is realized. 
     * @param pWorldState
     * @return 
     */
    public boolean checkAntiGoalCompletion(ArrayList<WorldProperty> pWorldState) {
        for (WorldProperty g : aAntiGoals) {
            if (pWorldState.contains(g)) {
                return true;
            }
        }
        return false;
    } 
    
}
