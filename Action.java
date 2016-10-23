
package actions;

import java.util.ArrayList;
import storygenerator.WorldProperty;

/**
 *
 * @author etienne
 */
public abstract class Action {
        
    protected ArrayList<WorldProperty> aPreTrue; //These properties must be in the world's state in order to execute the action
    protected ArrayList<WorldProperty> aPreFalse; //These properties must NOT be in the world state
    
    protected ArrayList<WorldProperty> aPostTrue; //These are added to the world's state as a result of the action
    protected ArrayList<WorldProperty> aPostFalse; //These are removed from the world's state as a result of the action
    
    protected String aNarrativeForm; //To show the text of the action
    
    /**
     * Constructor. Initializes the condition lists. 
     */
    protected Action() {
        aPreTrue = new ArrayList<WorldProperty>();
        aPreFalse = new ArrayList<WorldProperty>();
        aPostTrue = new ArrayList<WorldProperty>();
        aPostFalse = new ArrayList<WorldProperty>();
    }
    
    
    /**
     * Applies the postconditions of this Action to the world state, but only
     * if the preconditions are verified. 
     * @param pWorldState
     * @return 
     */
    public boolean execute(ArrayList<WorldProperty> pWorldState) {

        if (!verifyPreconditions(pWorldState)) {
            return false;
        }
        
        applyPostconditions(pWorldState);
        return true;
    }
    
    /**
     * Returns true if all of this Actions's true preconditions are found in 
     * the world's state, and all of this Action's false preconditions are NOT
     * found in the world's state. 
     * @param pWorld
     * @return 
     */
    public boolean verifyPreconditions(ArrayList<WorldProperty> pWorldState) {
        //Check that all preconditions that must be true are found in the world state
        for (WorldProperty tp : aPreTrue) {
            if (!pWorldState.contains(tp)) {
                return false;
            }
        }
        //Check that all preconditions that must be false are found in the world state
        for (WorldProperty fp : aPreFalse) {
            if (pWorldState.contains(fp)) {
                return false;
            }
        }
        //If all true preconditions were found and all false preconditions were
        //not found, then we can execute this action. 
        return true;
    }
    
    /**
     * Adds the true postconditions to, and removes the false postconditions 
     * from the world state. 
     * @param pWorldState 
     */
    public void applyPostconditions(ArrayList<WorldProperty> pWorldState) {
        for (WorldProperty tp : aPostTrue) {
            pWorldState.add(tp);
        }
        for (WorldProperty fp : aPostFalse) {
            pWorldState.remove(fp);
        }
    }
    
    /**
     * Returns true if all of the postconditions of the action are already 
     * present (or absent if it's false) from the world state. 
     * @param pWorldState 
     */
    public boolean uselessAction(ArrayList<WorldProperty> pWorldState) {
        for (WorldProperty tp : aPostTrue) {
            if (!pWorldState.contains(tp)) {
                return false;
            }
        }
        for (WorldProperty fp : aPostFalse) {
            if (pWorldState.contains(fp)) {
                return false;
            }
        }
        //If all the true postconditions are already there, and all the 
        //false preconditions already absent, then the action is useless. 
        return true;
    }
    
    /**
     * Determines whether the positive effects of this actions include 
     * property p. 
     * @param p
     * @return 
     */
    public boolean postTrueContains(WorldProperty p) {
        return aPostTrue.contains(p);
    }
    /**
     * Determines whether the negative effects of this actions include 
     * property p. 
     * @param p
     * @return 
     */
    public boolean postFalseContains(WorldProperty p) {
        return aPostFalse.contains(p);
    }
    
    /*
     * Getters
     */
    public ArrayList<WorldProperty> getPreTrue() {
        return aPreTrue;
    }
    public ArrayList<WorldProperty> getPreFalse() {
        return aPreFalse;
    }
    public ArrayList<WorldProperty> getPostTrue() {
        return aPostTrue;
    }
    public ArrayList<WorldProperty> getPostFalse() {
        return aPostFalse;
    }
    
    @Override
    public String toString() {
        return aNarrativeForm;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o == this) return true;
        if (!(o instanceof Action)) return false;
        Action a = (Action) o;
        if (a.aNarrativeForm.equals(this.aNarrativeForm)){
            return true;
        }
        else return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + (this.aNarrativeForm != null ? this.aNarrativeForm.hashCode() : 0);
        return hash;
    }
    
}
