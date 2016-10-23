
package planners;

import actions.Action;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import storygenerator.World;

/**
 *
 * @author etienne
 */
public abstract class Planner {
    
    protected Random rand = new Random();//8 for the power point /report example
    
    //maximum number of actions in a sequence
    protected final static int MAX_LENGTH = 1000;
    
    //Action sequence that should lead to making the two lista above empty
    protected LinkedList<Action> aActionSequence;
    
    protected World aWorld;
    
    protected Planner(World pWorld) {
        aWorld = pWorld;
    }
    
    /**
     * Prints the current action sequence to the console. 
     */
    public void printSequence() {
        for (Action a : aActionSequence) {
            System.out.println(a.toString());
        }
        System.out.println();
    }
    
    public abstract void executePlan();
    
    public abstract boolean makePlan();
    
    public int getSequenceLength() {
        return aActionSequence.size();
    }
    
    /**
     * Computes n/l, where n is the number of differently named actions 
     * (such as pickUp, travelTo, etc.) and l is the total number of actions
     * in the sequence. The higher the ratio, the less repetition there is. 
     * @return 
     */
    public double getUniquenessScore() {
        ArrayList<String> kinds = new ArrayList<String>();
        
        for (Action a : aActionSequence) {
            String name = a.getClass().getName();
            if (!kinds.contains(name)) {
                kinds.add(name);
            }
        }
        
        return (double) kinds.size() / (double) aActionSequence.size();
    }
    
}
