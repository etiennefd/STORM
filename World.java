
package storygenerator;

import storyelements.Location;
import storyelements.Item;
import storyelements.Creature;
import storyelements.StoryCharacter;
import storyelements.StoryElement;
import storyelements.PrimaryCharacter;
import storyelements.Weapon;
import actions.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import storygenerator.WorldProperty.PropertyKey;

/**
 *
 * @author etienne
 */
public class World {
    
    private static final int N_SECONDARY_CHARS = 10;
    private static final int N_WEAPONS = 5;
    private static final int N_ITEMS = 13; //excludes weapons
    private static final int N_CREATURES = 10;
    private static final int N_LOCATIONS = 5;
    
    //Random number generator
    static Random rand = new Random();//8 for the power point/report ambassador example; 50-59 for the report stories
    
    //Lists of things that can appear in a story
    static ArrayList<String> characters = new ArrayList(Arrays.asList(
        "Princess", "Prince", "King", "Queen", "Knight", "Lady", "Lord", 
        "Dame", "Sorcerer", "Sorceress", "Witch", "Wizard",
        "Peasant", "Mayor", "Traveller", "Pirate", "Captain", "Rebel",
        "Soldier", "Scholar", "Thief", "Rogue", "Orphan", "Student",
        "Beggar", "Widow", "Amazon", "Ninja", "Samurai", "Shogun", "Magician",
        "Emperor", "Empress", "General", "Apprentice", "Priest", "Monk",
        "Farmer", "Bandit", "Assassin", "Squire", "Page", "Servant",
        "Vampire", "Necromancer", "Fisherman", "Ambassador", "Ranger",
        "Sailor", "Doctor", "Writer", "Musician", "Poet", "Artist", 
        "Dancer", "Priestess", "Philosopher", "Nun"
    ));
    static ArrayList<String> relations = new ArrayList(Arrays.asList(
        "Son", "Daughter", "Father", "Mother", "Brother", "Sister", "Friend", 
        "Boyfriend", "Girlfriend", "Lover", "Grandfather", "Grandmother", 
        "Wife", "Husband"
    ));
    static ArrayList<String> locations = new ArrayList(Arrays.asList(
        "Castle", "Temple", "City", "Ruins", "Town", "Village", "Hamlet", 
        "Farm", "River", "Cave", "Church", "Palace", "House", "Dungeon", 
        "Hideout", "Road", "Fortress", "Inn", "University", "Tower", 
        "Metropolis", "Camp", "Mine", "Monastery", "Prison", "Mansion", 
        "Cottage", "Cemetery"
    ));
    static ArrayList<String> environments = new ArrayList(Arrays.asList(
        "Forest", "Lakeside", "Mountain", "Prairie", "Desert", "Field", 
        "Savanna", "Jungle", "Tundra", "Valley", "Island", "Seaside", "Hill"
    ));
    static ArrayList<String> items = new ArrayList(Arrays.asList(
        "Potion", "Amulet", "Jewel", "Key", "Seed", "Chest", "Pair of boots", 
        "Cloak", "Fossil", "Cake", "Scepter", "Fruit", "Flower", "Hat", 
        "Crown", "Belt", "Vest", "Purse", "Vial", "Teapot", "Mirror", 
        "Spyglass", "Gem", "Stick", "Plant", "Egg", "Perfume", "Violin", 
        "Flute", "Charm", "Pendant", "Bag", "Fang", "Scroll", "Wand", "Book", 
        "Tome", "Mask", "Shield", "Armour", "Compass", "Horn", "Music box", 
        "Broom", "Pearl", "Pair of gloves", "Necklace", "Fish", "Bread", 
        "Feather", "Diadem", "Tablet", "Talisman", "Rope", "Lamp", "Stone", 
        "Helmet", "Ring", "Watch", "Map", "Wheel", "Draught", "Wine", "Doll", 
        "Ruby", "Orb", "Cup", "Skull", "Bone"
    ));
    static ArrayList<String> weapons = new ArrayList(Arrays.asList(
        "Sword", "Knife", "Axe", "Staff", "Trident", "Mace", "Sabre",
        "Dagger", "Bow", "Crossbow", "Blade", "Spear", "Hammer", "Boomerang", 
        "Katana"
    ));
    static ArrayList<String> itemPreAdjectives = new ArrayList(Arrays.asList(
        "Dark", "Old", "Dusty", "Shiny", "Obscure", "Luminous", "Steel",
        "Oversized", "Golden", "Precious", "Helix", "Talking", "Cursed",
        "Silver", "Ancient", "Beautiful", "Secret", "Invisible", "Rare",
        "Expensive", "Magical", "White", "Sharp", "Lucky", "Unlucky", "Holy",
        "Fairy", "Rainbow", "Poisonous", "Forgotten", "Divine", "Sacred", 
        "Enchanted", "One-of-a-kind", "Miniature", "Otherworldly", "Amber", 
        "Indestructible", "Musical", "Platinum", "Ghostly", "Legendary", 
        "Mythical", "Universal", "Blessed", "Never-ending", "Frozen", "Evil", 
        "Epic", "Glow-in-the-dark", "Mysterious", "Jade", "Glass", "Incredible"
    ));
    static ArrayList<String> itemPostAdjectives = new ArrayList(Arrays.asList(
        "of Darkness", "of Power", "of Courage","of Despair", "of the Hero",
        "of Wisdom", "of the Gods", "of Love", "of Invisibility", "of Lore",
        "of Madness", "of Fire", "of Ice", "of Thunder", "of Youth", "of Time",
        "of Happiness", "of Sadness", "of Truth", "of Temptation", "from the Future", 
        "of Many Colours", "of the Prophecy", "of Invincibility", "of Long Life", 
        "of Death", "of Crystal", "of Wealth", "of Infinity", "of Friendship", 
        "of Knowledge", "of Yesteryear", "of Light", "of the Sun", "of the Moon", 
        "of the Stars", "of the Sky", "from Hell", "from Heaven", "of Doom", 
        "of Destiny", "of Dreams", "of Eternity", "of Vengeance", "of Nightmare"
    ));
    static ArrayList<String> creatures = new ArrayList(Arrays.asList(
        "Wolf", "Unicorn", "Dog", "Troll", "Ogre", "Salamander", "Gargoyle",
        "Eagle", "Snake", "Basilisk", "Dragon", "Serpent", "Worm", "Sasquatch", 
        "Yeti", "Wendigo", "Snail", "Lizard", "Tiger", "Lion", "Cat", "Squid",
        "Shark", "Cerberus", "Cockroach", "Dinosaur", "Toad", "Hydra", 
        "Vulture", "Anteater", "Insect", "Piranha", "Kraken", "Ghoul", "Bat",
        "Skeleton", "Sphinx", "Chimera", "Manticore", "Werewolf", "Mammoth", 
        "Boar", "Cobra", "Spider", "Tarantula", "Centipede", "Scorpion", 
        "Raven", "Orc", "Goblin", "Bear", "Demon", "Devil", "Crab",  "Dwarf",
        "Gorilla", "Ape", "Panther", "Gnome", "Griffin", "Phantom", "Parasite", 
        "Golem", "Lamia", "Crocodile", "Kappa", "Chupacabra", "Axolotl", "Moth",
        "Viper", "Centaur", "Reptile", "Arachnid", "Scorpion", "Beast", 
        "Guardian", "Horsehead"
    ));
    static ArrayList<String> creatureAdjectives = new ArrayList(Arrays.asList(
        "Wild", "Feral", "Rabid", "Venomous", "Vile", "Giant", "Man-eating", 
        "Hungry", "Starving", "Blood-thirsty", "Cursed", "Bicephalous", 
        "Three-headed", "Carnivorous", "Flesh-eating", "Undead", "Ghost", 
        "Armoured", "Vengeful", "Fire-breathing", "Boring", "Deadly", "Sneaky", 
        "Mutant", "Horrendous", "Evil", "Poison-breathing", "Shapeshifting", 
        "Savage", "Murderous", "Zombie", "Howling", "Monster", "Unearthly", 
        "Shadow", "Berserk", "Hateful", "Dreadful", "Stinking", "Colossal",
        "Hairy", "Tentacled", "Sword-wielding", "Hellish", "Wicked", "Infernal",
        "Nightmarish", "Ferocious", "Fiery", "One-eyed", "Voodoo"
    ));
    
    
    //Elements of the story
    private PrimaryCharacter aMainCharacter;
    private StoryCharacter aRelative;
    private PrimaryCharacter aVillain;
    private Location aHome;
    private final ArrayList<StoryCharacter> aSecondaryCharList = new ArrayList<StoryCharacter>();
    private final ArrayList<Item> aItemList = new ArrayList<Item>();
    private final ArrayList<Creature> aCreatureList = new ArrayList<Creature>();
    private final ArrayList<Location> aLocationList = new ArrayList<Location>();
    
    private final ArrayList<WorldProperty> aWorldState = new ArrayList<WorldProperty>();
    private final ArrayList<Action> aHeroActions = new ArrayList<Action>();
    private final ArrayList<Action> aVillainActions = new ArrayList<Action>();
    
    
    /**
     * Constructor! Initializes most game elements. 
     */
    public World() {
        
        createCharacters();
        createItems();
        createCreatures();
        createLocations();
        createWorldState();
        createAllActions(aMainCharacter, aHeroActions);
        createAllActions(aVillain, aVillainActions);
        createGoals(aMainCharacter, aVillain);
    }
    
    /*
     * Methods to create initial game elements
     */
    
    private void createCharacters() {
        //Create a main character
        aMainCharacter = new PrimaryCharacter (characters.remove(rand.nextInt(characters.size())));
        //Create a relative for the main character
        aRelative = new StoryCharacter(aMainCharacter.getOccupation(), relations.get(rand.nextInt(relations.size())));
        //Create a villain
        aVillain = new PrimaryCharacter (characters.remove(rand.nextInt(characters.size())));
        
        //Create a bunch of other secondary characters
        //Some of them might be relatives of others
        for (int i=0; i<N_SECONDARY_CHARS; i++) {
            String relation;
            String occupation;
            if (i>0 && aSecondaryCharList.get(i-1).getRelation().equals("") && rand.nextBoolean()) {
                relation = relations.get(rand.nextInt(relations.size()));
                occupation = aSecondaryCharList.get(i-1).getOccupation();
            }
            else {
                relation = "";
                occupation = characters.remove(rand.nextInt(characters.size()));
            }
            aSecondaryCharList.add(new StoryCharacter(occupation, relation));
        }
    }
    private void createItems() {
        //Weapons
        for (int i=0; i<N_WEAPONS; i++) {
            String weaponName = weapons.remove(rand.nextInt(weapons.size()));
            String pre = itemPreAdjectives.get(rand.nextInt(itemPreAdjectives.size()));
            String post = itemPostAdjectives.get(rand.nextInt(itemPostAdjectives.size()));
            //Randomly remove an adjective for some items
            if (rand.nextBoolean()) {
                if (rand.nextBoolean()) pre = "";
                else post = "";
            }
            aItemList.add(new Weapon(weaponName, pre, post));
        }
        //Other items
        for (int i=0; i<N_ITEMS; i++) {
            String itemName = items.remove(rand.nextInt(items.size()));
            String pre = itemPreAdjectives.get(rand.nextInt(itemPreAdjectives.size()));
            String post = itemPostAdjectives.get(rand.nextInt(itemPostAdjectives.size()));
            //Randomly remove an adjective for some items
            if (rand.nextBoolean()) {
                if (rand.nextBoolean()) pre = "";
                else post = "";
            }
            aItemList.add(new Item(itemName, pre, post));
        }
    }
    private void createCreatures() {
        for (int i=0; i<N_CREATURES; i++) {
            String creatureName = creatures.remove(rand.nextInt(creatures.size()));
            String adj = creatureAdjectives.remove(rand.nextInt(creatureAdjectives.size()));
            aCreatureList.add(new Creature(creatureName, adj));
        }
    }
    private void createLocations() {
        String env = environments.remove(rand.nextInt(environments.size()));
        ArrayList<String> tempRemoved = new ArrayList<String>();
        for (int i=0; i<N_LOCATIONS; i++) {
            String locationName = locations.remove(rand.nextInt(locations.size()));
            tempRemoved.add(locationName);
            aLocationList.add(new Location(locationName, env));
            
            //To put some variety in the environments: 
            if (rand.nextDouble() < 0.4) {
                env = environments.remove(rand.nextInt(environments.size()));
                locations.addAll(tempRemoved);
                tempRemoved.clear();
            }
        }
    }
    private void createWorldState() {
        /*
         * PLACING PEOPLE AND CREATURES IN LOCATIONS
         */
        //First, the protagonist and its relative
        aHome = aLocationList.get(rand.nextInt(aLocationList.size()));
        aWorldState.add(new WorldProperty(aMainCharacter, PropertyKey.IS_AT_LOCATION, aHome));
        aWorldState.add(new WorldProperty(aRelative, PropertyKey.IS_AT_LOCATION, aHome));
        //Then the villain
        Location villainHome = aLocationList.get(rand.nextInt(aLocationList.size()));
        aWorldState.add(new WorldProperty(aVillain, PropertyKey.IS_AT_LOCATION, villainHome));
        //Then all other characters, in random locations
        for (StoryCharacter c : aSecondaryCharList) {
            Location randomLoc = aLocationList.get(rand.nextInt(aLocationList.size()));
            aWorldState.add(new WorldProperty(c, PropertyKey.IS_AT_LOCATION, randomLoc));
        }
        //And finally creatures
        for (Creature c : aCreatureList) {
            Location randomLoc = aLocationList.get(rand.nextInt(aLocationList.size()));
            aWorldState.add(new WorldProperty(c, PropertyKey.IS_AT_LOCATION, randomLoc));
        }
        /*
         * PLACE ITEMS IN LOCATIONS OR IN PEOPLE/CREATURE OWNERSHIP
         */
        for (Item i : aItemList) {
            double r = rand.nextDouble();
            if (r < 0.4) {
                Location randomLoc = aLocationList.get(rand.nextInt(aLocationList.size()));
                aWorldState.add(new WorldProperty(i, PropertyKey.IS_AT_LOCATION, randomLoc));
            }
            else if (r < 0.7) {
                Creature randomCreature = aCreatureList.get(rand.nextInt(aCreatureList.size()));
                aWorldState.add(new WorldProperty(randomCreature, PropertyKey.OWNS, i));
            }
            else {
                ArrayList<StoryCharacter> allChars = getAllCharacters();
                StoryCharacter randomChar = allChars.get(rand.nextInt(allChars.size()));
                aWorldState.add(new WorldProperty(randomChar, PropertyKey.OWNS, i));
            }
        }
        /*
         * CREATE LOVE AND HATE RELATIONSHIPS
         */
        aWorldState.add(new WorldProperty(aMainCharacter, PropertyKey.LOVES, aRelative));
        aWorldState.add(new WorldProperty(aRelative, PropertyKey.LOVES, aMainCharacter));
        aWorldState.add(new WorldProperty(aMainCharacter, PropertyKey.HATES, aVillain));
        aWorldState.add(new WorldProperty(aVillain, PropertyKey.HATES, aMainCharacter));
        createLoveAndHate(aVillain, aRelative);
        for (StoryCharacter c1 : aSecondaryCharList) {
            createLoveAndHate(aMainCharacter, c1);
            createLoveAndHate(aVillain, c1);
            createLoveAndHate(aRelative, c1);
            for (StoryCharacter c2 : aSecondaryCharList) {
                createLoveAndHate(c1, c2);
            }
        }
        /*
         * CAPTIVES
         */
        for (StoryCharacter ch : aSecondaryCharList) {
            for (Creature cr : aCreatureList) {
                for (Location l : aLocationList) {
                    if (aWorldState.contains(new WorldProperty(ch, PropertyKey.IS_AT_LOCATION, l)) 
                            && aWorldState.contains(new WorldProperty(cr, PropertyKey.IS_AT_LOCATION, l))
                            && !aWorldState.contains(new WorldProperty(ch, PropertyKey.IS_CAPTIVE))) {
                        if (rand.nextDouble() < 0.3) {
                            aWorldState.add(new WorldProperty(ch, PropertyKey.IS_CAPTIVE));
                            aWorldState.add(new WorldProperty(ch, PropertyKey.IS_PRISONER_OF, cr));
                        }
                    }
                }
            }
        }
    }
    private void createLoveAndHate(StoryCharacter c1, StoryCharacter c2) {
        //Ignore cases that have already been done, or that are the character with itself
        if (c1.getID() >= c2.getID()) {
            return;
        }
        //Are the two characters relative?
        if (c1.getOccupation().equals(c2.getOccupation())) {
            //The two characters are relatives, so they high chance that they love each other
            double r = rand.nextDouble();
            if (r < 0.9) {
                aWorldState.add(new WorldProperty(c1, PropertyKey.LOVES, c2));
                aWorldState.add(new WorldProperty(c2, PropertyKey.LOVES, c1));
            } 
            else if (r < 0.95) {
                aWorldState.add(new WorldProperty(c1, PropertyKey.HATES, c2));
                aWorldState.add(new WorldProperty(c2, PropertyKey.HATES, c1));
            }
            return;
        }
        //if not relatives, small chance that there is a love/hate relation
        //now relationships are always symmetric: if A loves B, then B must love A. 
        double r = rand.nextDouble();
        if (r < 0.1) {
            aWorldState.add(new WorldProperty(c1, PropertyKey.LOVES, c2));
            aWorldState.add(new WorldProperty(c2, PropertyKey.LOVES, c1));
        } 
        else if (r < 0.25) {
            aWorldState.add(new WorldProperty(c1, PropertyKey.HATES, c2));
            aWorldState.add(new WorldProperty(c2, PropertyKey.HATES, c1));
        }
    }
    private void createAllActions(PrimaryCharacter pChar, ArrayList<Action> pAllActions) {
        //Action involving a location and a list of all location
        for (Location dest : aLocationList) {
            pAllActions.add(new TravelToAction(pChar, dest, aLocationList));
        }
        //Actions involving one location (true of most actions!)
        for (Location l : aLocationList) {
            //Actions involving an item
            for (Item i : aItemList) {
                pAllActions.add(new PickupAction(pChar, i, l));
            }
            //Actions involving a creature
            for (Creature c : aCreatureList) {
                pAllActions.add(new KillCreatureAction(pChar, c, l));
                for (Item i : aItemList) {
                    pAllActions.add(new LootTheDeadAction(pChar, c, i, l));
                }
            }
            //Action involving another character
            for (StoryCharacter c : getAllCharacters()) {
                if (!c.equals(pChar)) {
                    pAllActions.add(new KillCharacterAction(pChar, c, l));
                    pAllActions.add(new BecomeFriendsAction(pChar, c, l));
                    pAllActions.add(new BecomeEnemiesAction(pChar, c, l));
                    pAllActions.add(new EndFriendshipAction(pChar, c, l));
                    pAllActions.add(new EndHatredAction(pChar, c, l));
                    pAllActions.add(new DisarmOpponentAction(pChar, c, getWeapons(), l));
                    //Involving a character and an item: 
                    for (Item i : aItemList) {
                        pAllActions.add(new LootTheDeadAction(pChar, c, i, l));
                        pAllActions.add(new StealItemAction(pChar, c, i, l));
                        //And a second item: 
                        for (Item j : aItemList) {
                            if (!j.equals(i)) pAllActions.add(new TradeItemAction(pChar, c, l, i, j));
                        }
                    }
                    //Involving a character and a creature
                    for (Creature cr : aCreatureList) {
                        pAllActions.add(new LiberateCharacterAction(pChar, c, cr, l));
                    }
                }
            }
        }
        //Actions not involving a location
        for (Item i : aItemList) {
            if (i instanceof Weapon) {
                pAllActions.add(new EquipWeaponAction(pChar, (Weapon) i));
            }
            pAllActions.add(new DestroyItemAction(pChar, i));
        }
        pAllActions.add(new UnequipWeaponAction(pChar, getWeapons()));
    }
    private void createGoals(PrimaryCharacter pHero, PrimaryCharacter pAntagonist) {
        int goal = rand.nextInt(5);
        switch (goal) {
            case 0: //Kill the villain
                pHero.addGoalTrue(new WorldProperty(aVillain, PropertyKey.IS_DEAD));
                break;
            case 1: //Find 3-4 items
                Collections.shuffle(aItemList, rand);
                pHero.addGoalTrue(new WorldProperty(pHero, PropertyKey.OWNS, aItemList.get(0)));
                pAntagonist.addAntiGoal(new WorldProperty(aItemList.get(0), PropertyKey.IS_DESTROYED));
                pHero.addGoalTrue(new WorldProperty(pHero, PropertyKey.OWNS, aItemList.get(1)));
                pAntagonist.addAntiGoal(new WorldProperty(aItemList.get(1), PropertyKey.IS_DESTROYED));
                pHero.addGoalTrue(new WorldProperty(pHero, PropertyKey.OWNS, aItemList.get(2)));
                pAntagonist.addAntiGoal(new WorldProperty(aItemList.get(2), PropertyKey.IS_DESTROYED));
                if (rand.nextBoolean()) {
                    pHero.addGoalTrue(new WorldProperty(pHero, PropertyKey.OWNS, aItemList.get(3)));
                    pAntagonist.addAntiGoal(new WorldProperty(aItemList.get(3), PropertyKey.IS_DESTROYED));
                }
                break;
            case 2: //Liberate all captive characters
                for (StoryCharacter c : aSecondaryCharList) {
                    if (aWorldState.contains(new WorldProperty(c, PropertyKey.IS_CAPTIVE))) {
                        pHero.addGoalFalse(new WorldProperty(c, PropertyKey.IS_CAPTIVE));
                        //pAntagonist.addAntiGoal(new WorldProperty(c, PropertyKey.IS_DEAD)); Should this be added?
                    }
                }
                break;
            case 3: //Destroy an evil item
                Collections.shuffle(aItemList, rand);
                pHero.addGoalTrue(new WorldProperty(aItemList.get(0), PropertyKey.IS_DESTROYED));
                break;
            case 4: //Assassinate 3-4 people
                Collections.shuffle(aSecondaryCharList, rand);
                pHero.addGoalTrue(new WorldProperty(aSecondaryCharList.get(0), PropertyKey.IS_DEAD));
                pHero.addGoalTrue(new WorldProperty(aSecondaryCharList.get(1), PropertyKey.IS_DEAD));
                pHero.addGoalTrue(new WorldProperty(aSecondaryCharList.get(2), PropertyKey.IS_DEAD));
                if (rand.nextBoolean()) {
                    pHero.addGoalTrue(new WorldProperty(aSecondaryCharList.get(3), PropertyKey.IS_DEAD));
                }
                break;
            case 5: //Kill 3-4 creatures
                Collections.shuffle(aCreatureList, rand);
                pHero.addGoalTrue(new WorldProperty(aCreatureList.get(0), PropertyKey.IS_DEAD));
                pHero.addGoalTrue(new WorldProperty(aCreatureList.get(1), PropertyKey.IS_DEAD));
                pHero.addGoalTrue(new WorldProperty(aCreatureList.get(2), PropertyKey.IS_DEAD));
                if (rand.nextBoolean()) {
                    pHero.addGoalTrue(new WorldProperty(aCreatureList.get(3), PropertyKey.IS_DEAD));
                }
                break;
        }
        //50% chance that the hero wants to go back home after being done
        if (rand.nextBoolean()) {
            pHero.addGoalTrue(new WorldProperty(pHero, PropertyKey.IS_AT_LOCATION, aHome));
        }
        //One of the anti-goals of the antagonist is that the hero is dead
        pAntagonist.addAntiGoal(new WorldProperty(pHero, PropertyKey.IS_DEAD));
    }
    
    
    /*
     * Methods to execute actions
     */
    
    public void executeRandomAction() {
        Collections.shuffle(aHeroActions);
        int i = 0;
        boolean success = false;
        while(!success && i < aHeroActions.size()) {
            Action a = aHeroActions.get(i);
            success = a.execute(aWorldState);
            if (success) System.out.println(a.toString());
            else i++;
        }
    }
    public void executeActionSequence(List<Action> pSequence) {
        int i = 1;
        for (Action a : pSequence) {
            boolean success = (a.execute(aWorldState));
            if (success) System.out.println(i + ". " + a.toString());
            else System.out.println("FAILED TO EXECUTE ACTION: " + a);
            i++;
        }
    }
    public boolean checkGoalCompletion() {
        return aMainCharacter.checkGoalCompletion(aWorldState);
    }
    public boolean checkAntiGoalCompletion() {
        return aVillain.checkAntiGoalCompletion(aWorldState);
    }
    
    
    
    
    /*
     * Methods to query information about the world. 
     */
    
    /**
     * Makes a list containing all primary and secondary characters. 
     * @return 
     */
    public ArrayList<StoryCharacter> getAllCharacters() { 
        ArrayList<StoryCharacter> list = new ArrayList<StoryCharacter>();
        list.add(aMainCharacter);
        list.add(aVillain);
        list.add(aRelative);
        for (StoryCharacter c : aSecondaryCharList) {
            list.add(c);
        }
        return list;
    } 
    public ArrayList<Weapon> getWeapons() {
        ArrayList<Weapon> list = new ArrayList<Weapon>();
        for (Item i : aItemList) {
            if (i instanceof Weapon) list.add((Weapon) i);
        }
        return list;
    }
    public ArrayList<WorldProperty> getWorldState() {
        return aWorldState;
    }
    public ArrayList<Action> getHeroActions() {
        return aHeroActions;
    }
    public ArrayList<Action> getVillainActions() {
        return aVillainActions;
    }
    public PrimaryCharacter getMainCharacter() {
        return aMainCharacter;
    }
    public PrimaryCharacter getVillain() {
        return aVillain;
    }
    
    
    
    
    /*
     * Methods to print the contents of the world
     */
    
    public void printCharacters() {
        System.out.println("CHARACTERS\n");
        for (StoryCharacter c : getAllCharacters()) {
            System.out.println(c + " (" + c.getGender() + ")");
        }
        System.out.println();
    }
    public void printItems() {
        System.out.println("ITEMS\n");
        for (Item i : aItemList) {
            System.out.println(i);
        }
        System.out.println();
    }
    public void printCreatures() {
        System.out.println("CREATURES\n");
        for (Creature c : aCreatureList) {
            System.out.println(c);
        }
        System.out.println();
    }
    public void printLocations() {
        System.out.println("LOCATIONS\n");
        for (Location l : aLocationList) {
            System.out.println(l);
        }
        System.out.println();
    }
    public void printWorldState() {
        System.out.println("STATE OF THE WORLD (TRUE PROPERTIES)\n");
        for (WorldProperty p : aWorldState) {
            System.out.println(p);
        }
        System.out.println();
    }
    public void printGoals() {
        System.out.println("GOALS OF THE " + aMainCharacter + "\n");
        for (WorldProperty p : aMainCharacter.getGoalsTrue()) {
            System.out.println(p);
        }
        System.out.println();
        for (WorldProperty p : aMainCharacter.getGoalsFalse()) {
            System.out.println("NOT " + p);
        }
        System.out.println();
    }
    
    public void introduction() {
        System.out.println("Once upon a time, a" + StoryElement.addN(aMainCharacter.toString())
                + " " + aMainCharacter.toString() + " lived with " + aMainCharacter.getPossessivePronoun()
                + " " + aRelative.getRelation() + " near the " + aHome + ".");
    }

    public void conclusion() {
        if (checkGoalCompletion()) {
            System.out.println("Having completed all of " + aMainCharacter.getPossessivePronoun()
                    + " goals, the " + aMainCharacter.toString() + " lived happily for the rest of "
                    + aMainCharacter.getPossessivePronoun() + " existence.");
        } 
        else if (checkAntiGoalCompletion()) {
            System.out.println("The " + aMainCharacter + " could not succeed in " 
                    + aMainCharacter.getPossessivePronoun() + " quest: " + aMainCharacter.getPossessivePronoun()
                    + " foe, the " + aVillain + ", had prevailed.");
        }
        else {
            System.out.println("Suddenly, the " + aMainCharacter.toString() + " realized that a lot of time "
                    + "had past, and that " + aMainCharacter.getPossessivePronoun() + " lifelong wishes "
                    + "would never been fulfilled.");
            System.out.println(Character.toUpperCase(aMainCharacter.getSubjectPronoun().charAt(0))
                    + aMainCharacter.getSubjectPronoun().substring(1)
                    + " decided to go back home and spent " + aMainCharacter.getPossessivePronoun()
                    + " remaining days reflecting back at the failure of " + aMainCharacter.getPossessivePronoun()
                    + " quest.");
        }
    }
    
}
