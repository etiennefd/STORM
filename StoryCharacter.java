
package storyelements;

/**
 *
 * @author etienne
 */
public class StoryCharacter extends StoryElement {

    private String aOccupation;
    private String aRelation;
    private boolean isFemale = true;

    public StoryCharacter(String pOcc, String pRel) {
        aOccupation = pOcc;
        aRelation = pRel;
        setGender();
    }

    public StoryCharacter(String pOcc) {
        aOccupation = pOcc;
        aRelation = "";
        setGender();
    }

    private void setGender() {
        if (!aRelation.equals("")) {
            if (aRelation.contains("Daughter") || aRelation.contains("Mother")
                    || aRelation.contains("Girlfriend") || aRelation.contains("Sister")
                    || aRelation.contains("Grandmother") || aRelation.contains("Wife")) {
                isFemale = true;
            } else if (aRelation.contains("Son") || aRelation.contains("Father")
                    || aRelation.contains("Boyfriend") || aRelation.contains("Brother")
                    || aRelation.contains("Grandfather") || aRelation.contains("Husband")) {
                isFemale = false;
            } else {
                isFemale = Math.random() < 0.5;
            }
        }
        else {
            if (aOccupation.contains("Princess") || aOccupation.contains("Queen")
                    || aOccupation.contains("Dame") || aOccupation.contains("Lady")
                    || aOccupation.contains("Sorceress") || aOccupation.contains("Witch")
                    || aOccupation.contains("Widow") || aOccupation.contains("Amazon")
                    || aOccupation.contains("Priestess") || aOccupation.contains("Empress")
                    || aOccupation.contains("Nun")) {
                isFemale = true;
            } else if (aOccupation.contains("Prince") || aOccupation.contains("King")
                    || aOccupation.contains("Lord") || aOccupation.contains("Sorcerer")
                    || aOccupation.contains("Wizard") || aOccupation.contains("Priest")
                    || aOccupation.contains("Emperor") || aOccupation.contains("Fisherman")
                    || aOccupation.contains("Monk")) {
                isFemale = false;
            } else {
                isFemale = Math.random() < 0.5;
            }
        }
    }

    @Override
    public String toString() {
        if (aRelation.equals("")) {
            return aOccupation;
        }
        else {
            return aRelation + " of the " + aOccupation;
        }
    }

    public String getSubjectPronoun() {
        if (isFemale) return "she";
        else return "he";
    }
    
    public String getObjectPronoun() {
        if (isFemale) return "her";
        else return "him";
    }
    
    public String getPossessivePronoun() {
        if (isFemale) return "her";
        else return "his";
    }
    
    public String getOccupation() {
        return aOccupation;
    }
    
    public String getRelation() {
        return aRelation;
    }
    
    public String getGender() {
        if (isFemale) return "F";
        else return "M";
    }
}
