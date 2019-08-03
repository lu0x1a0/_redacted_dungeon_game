package unsw.dungeon;

import java.util.ArrayList;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

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
	
	
	
	
	


	public SimpleBooleanProperty getHasTreasure() {
		return hasTreasure;
	}

	public SimpleIntegerProperty getTreasureCount() {
		return treasureCount;
	}

	public Inventory(Player player) {
		this.inventory = new ArrayList<Collectible>();
		this.player = player;
		
	}

	public SimpleIntegerProperty getBombsCount() {
		return bombsCount;
	}


	public SimpleIntegerProperty getSwordHealth() {
		return swordHealth;
	}
	
	
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

	public SimpleBooleanProperty getHasSword() {
		return hasSword;
	}

	public SimpleBooleanProperty getHasBomb() {
		return hasBomb;
	}

	public SimpleBooleanProperty getHasKey() {
		return hasKey;
	}

	public SimpleBooleanProperty getHasPotion() {
		return hasPotion;
	}
	
	
}



