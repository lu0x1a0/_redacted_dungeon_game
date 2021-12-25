package unsw.dungeon;

import java.util.ArrayList;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Class to handle inventory of player. Uses variables that are observed by the controller to change
 * the inventory screen for the front end
 * @author compu
 *
 */
public class Inventory {

	private ArrayList<Collectible> inventory;
	private Player player;
	
	
	private SimpleIntegerProperty bombsCount = new SimpleIntegerProperty();
	private SimpleIntegerProperty swordHealth = new SimpleIntegerProperty();
	private SimpleBooleanProperty hasSword = new SimpleBooleanProperty();
	private SimpleBooleanProperty hasBomb = new SimpleBooleanProperty();
	private SimpleBooleanProperty hasKey = new SimpleBooleanProperty();
	private SimpleBooleanProperty hasPotion = new SimpleBooleanProperty();
	private SimpleBooleanProperty hasTreasure = new SimpleBooleanProperty();
	private SimpleIntegerProperty treasureCount = new SimpleIntegerProperty();
	private SimpleIntegerProperty potion_time = new SimpleIntegerProperty();

	/**
	 * getter for hasTreasure booleanProperty
	 * @return - BooleanProperty of whether treasure is in inventory
	 */
	public SimpleBooleanProperty getHasTreasure() {
		return hasTreasure;
	}
	
	/**
	 * getter for IntegerProperty about how many items of treasure are collected
	 * @return - IntegerProperty of the amount of treasure held
	 */
	public SimpleIntegerProperty getTreasureCount() {
		return treasureCount;
	}

	/**
	 * Contructor that attached the inventory to the player
	 * @param player - player who holds inventory
	 */
	public Inventory(Player player) {
		this.inventory = new ArrayList<Collectible>();
		this.player = player;
		
	}

	/**
	 * getter for IntegerProperty about how many bombs are held by player
	 * @return - Integer property for the number of bombs held by player
	 */
	public SimpleIntegerProperty getBombsCount() {
		return bombsCount;
	}

	/**
	 * getter for the IntegerProperty of how much health the sword has left
	 * @return - IntegerProperty for the abount of health the sword has left
	 */
	public SimpleIntegerProperty getSwordHealth() {
		return swordHealth;
	}
	
	/**
	 * Adds a collectible to the inventory
	 * @param c - collectable to add
	 */
	public void addToInventory(Collectible c) {
    	inventory.add(c);
    	if(c instanceof Sword) {
    		hasSword.setValue(true);
    		swordHealth.setValue(5);
    	}
    	else if(c instanceof Key) {
    		hasKey.setValue(true);

    	}
    	else if(c instanceof Bomb) {
    		hasBomb.setValue(true);
    		bombsCount.setValue(bombsCount.getValue()+1);
    	}
    	else if(c instanceof Treasure) {
    		hasTreasure.setValue(true);
    		treasureCount.setValue(treasureCount.getValue()+1);
    	}
    	else if(c instanceof Potion) {
    		hasPotion.setValue(true);
    		potion_time.setValue( ((Potion)c).getTime());
    	}
    }

	 /**
     * returns the key the player is holding
     * @return Key
     */
    public Key getKey() {
    	for( Collectible e: inventory) {
    		if(e instanceof Key) {
    			return (Key) e;    		
			}
    	}
    	return null;
    }
	
    /**
     * returns the key the player is holding
     * @return Sword
     */
    public Sword getSword() {
    	for( Collectible e: inventory) {
    		if(e instanceof Sword) {
    			return (Sword) e;    		
			}
    	}
    	return null;
    }
    
    /**
     * Action for player to way the sword
     */
    public void waveSword() {
    	for( Collectible e: inventory) {
    		if(e instanceof Sword) {
    			((Sword) e).use(player);
    			swordHealth.set(((Sword) e).getCount());
    			return;    		
			}
    	}
    	return;
    }
    
	/**
	 * returns whether or not the player is holding a sword
	 * @param player TODO
	 * @return - boolean
	 */
	public boolean hasSword() {
		return hasSword.getValue();
	}


	/**
	 * remove key from player's inventory
	 * @param player TODO
	 */
	public void removeKey() {
		for(Collectible c: inventory) {
			if (c instanceof Key) {
				inventory.remove(c);
				hasKey.setValue(false);
				break;
			}
		}
	}


	/**
	 * remove sword when it has been used 5 times
	 * @param player TODO
	 */
	public void removeSword() {
		for(Collectible c:inventory) {
			if (c instanceof Sword) {
				inventory.remove(c);
				hasSword.setValue(false);
				break;
			}
		}
	}


	/**
	 * count number bombs held by the player
	 * @param player TODO
	 * @return - int
	 */
	public int countBombs() {
		return bombsCount.getValue();
	}

	/**
	 * Used to get a bomb from the inventory of the player
	 * 
	 * @return Bomb - will return null when player has no bomb
	 */
	
	public Bomb getBomb() {
		if(countBombs() == 1) { //Set to 1 as you're about to use a bomb so it will decrement and end on 0
			hasBomb.setValue(false);
		}
		for(Collectible c:inventory) {
			if (c instanceof Bomb) {
				inventory.remove(c);
				bombsCount.setValue(bombsCount.getValue()-1);
				return (Bomb) c;
			}
		}
		return null;
	}


	/**
	 * returns whether or not the player has a potion
	 * @param player TODO
	 * @return - boolean
	 */
	public boolean hasPotion() {
		return hasPotion.getValue();
	}


	/**
	 * after the potion effect has expired
	 * @param player TODO
	 * @param p - potion to deactivate
	 */
	public void potionEffectOff(Potion p) {
		inventory.remove(p);
		hasPotion.setValue(false);
	}

	/**
	 * getter for if the player has a sword
	 * @return - BooleanProperty for if player has sword
	 */
	public SimpleBooleanProperty getHasSword() {
		return hasSword;
	}
	/**
	 * getter for if the player has a bomb
	 * @return - BooleanProperty for if player has bomb
	 */
	public SimpleBooleanProperty getHasBomb() {
		return hasBomb;
	}
	/**
	 * getter for if the player has a key
	 * @return - BooleanProperty for if player has key
	 */
	public SimpleBooleanProperty getHasKey() {
		return hasKey;
	}
	/**
	 * getter for if the player has a potion
	 * @return - BooleanProperty for if player has potion
	 */
	public SimpleBooleanProperty getHasPotion() {
		return hasPotion;
	}
	/**
	 * getter for if the remaining time left on a potion
	 * @return - IntegerProperty for the time left on the potion
	 */
	public SimpleIntegerProperty getPotionTimeLeft() {
		return potion_time;
	}
	
}



