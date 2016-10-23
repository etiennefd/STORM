
package storygenerator;

import planners.Planner;
import planners.RandomPlanner;
import planners.ForwardPlanner;
import planners.BackwardPlanner;
import java.util.Random;
import planners.CompetitivePlanner;

/**
 *
 * @author etienne
 */
public class STORM {
    
    static Random rand = new Random();

    public static void main(String[] args) {
        
        World w = new World();
        
        Planner p;
        if (args[0].equals("RANDOM")) {
            p = new RandomPlanner(w);
        }
        else if (args[0].equals("BACKWARD")) {
            p = new BackwardPlanner(w);
        }
        else if (args[0].equals("FORWARD")) {
            p = new ForwardPlanner(w);
        }
        else if (args[0].equals("COMPETITIVE")) {
            p = new CompetitivePlanner(w);
        }
        else {
            System.out.println("Need to specify planner type "
                    + "(RANDOM, BACKWARD, FORWARD or COMPETITIVE) "
                    + "as the first argument");
            System.exit(0);
            p = new RandomPlanner(w);
        }
        
        w.printCharacters();
        w.printItems();
        w.printCreatures();
        w.printLocations();
        w.printWorldState();
        w.printGoals();
            
        generateStory(w, p);
        
        
    }
    
    public static void generateStory(World w, Planner p) {
        boolean success = p.makePlan();
        
        if (success) {
            w.introduction();
            p.executePlan();
            w.conclusion();
            
            System.out.println();
            System.out.println("Success of the main character: " + w.checkGoalCompletion());
            System.out.println("Success of the villain: " + w.checkAntiGoalCompletion());
            System.out.println("Length of story: " + p.getSequenceLength());
            System.out.println("Uniqueness score: " + p.getUniquenessScore());
        }
        else {
            System.out.println("Failure to find a plan.");
        }
    }
   
    
}
