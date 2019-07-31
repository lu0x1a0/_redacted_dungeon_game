package unsw.dungeon;

import java.util.ArrayList;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.NumberStringConverter;

public class Inventory {

	private ArrayList<Collectible> inventory;
	private GridPane squares;
	private Dungeon dungeon;
	private Image key_missing = new Image("/key_inventory_missing.png");
	private Image key_present = new Image("/key.png");
	private Image sword_missing = new Image("/greatsword_1_inventory_missing.png");
	private Image sword_present = new Image("/greatsword_1_new.png");
	private Image bomb_missing = new Image("/bomb_unlit_missing.png");
	private Image bomb_present = new Image("/bomb_unlit.png");
	private ImageView key_missingIV = new ImageView(key_missing);
	private ImageView key_presentIV = new ImageView(key_present);
	private ImageView sword_missingIV = new ImageView(sword_missing);
	private ImageView sword_presentIV = new ImageView(sword_present);
	private ImageView bomb_missingIV = new ImageView(bomb_missing);
	private ImageView bomb_presentIV = new ImageView(bomb_present);
	private SimpleIntegerProperty bombs = new SimpleIntegerProperty();
	private SimpleIntegerProperty swordHealth = new SimpleIntegerProperty();
	
	public Inventory(GridPane squares, Dungeon dungeon) {
		// TODO Auto-generated constructor stub
		this.inventory = new ArrayList<Collectible>();
		this.squares = squares;
		this.dungeon = dungeon;
		squares.add(key_missingIV, 0, dungeon.getHeight());
		squares.add(sword_missingIV, 1, dungeon.getHeight());
		Label swordCountUI = new Label();
		squares.add(swordCountUI,2, dungeon.getHeight());
		swordCountUI.textProperty().bind(swordHealth.asString());
		squares.add(bomb_missingIV, 3, dungeon.getHeight());
		Label bombCountUI = new Label();
		squares.add(bombCountUI,4, dungeon.getHeight());
		bombCountUI.textProperty().bind(bombs.asString());
	}

	
	public void addToInventory(Collectible c) {
    	inventory.add(c);
    	if(c instanceof Sword) {
    		System.out.println("Collected a sword!");
    		squares.getChildren().remove(sword_missingIV);
    		squares.add(sword_presentIV, 1, dungeon.getHeight());
    		swordHealth.setValue(5);
    	}
    	else if(c instanceof Key) {
    		squares.getChildren().remove(key_missingIV);
    		squares.add(key_presentIV, 0, dungeon.getHeight());
    	}
    	else if(c instanceof Bomb) {
    		squares.getChildren().remove(bomb_missingIV);
    		squares.add(bomb_presentIV, 3, dungeon.getHeight());
    		bombs.setValue(bombs.getValue()+1);
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
    			((Sword) e).use(dungeon.getPlayer());
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
		for( Collectible e: inventory) {
			if(e instanceof Sword) {
				return true;
			}
		}
		return false;
	}


	/**
	 * remove key from player's inventory
	 * @param player TODO
	 */
	public void removeKey() {
		for(Collectible c: inventory) {
			if (c instanceof Key) {
				inventory.remove(c);
				squares.getChildren().remove(key_presentIV);
	    		squares.add(key_missingIV, 1, dungeon.getHeight());
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
				squares.getChildren().remove(sword_presentIV);
	    		squares.add(sword_missingIV, 1, dungeon.getHeight());
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
		return bombs.getValue();
	}

	/**
	 * Used to get a bomb from the inventory of the player
	 * 
	 * @return Bomb - will return null when player has no bomb
	 */
	
	public Bomb getBomb() {
		if(countBombs() == 1) { //Set to 1 as you're about to use a bomb so it will decrement and end on 0
			squares.getChildren().remove(bomb_presentIV);
    		squares.add(bomb_missingIV, 2, dungeon.getHeight());
		}
		for(Collectible c:inventory) {
			if (c instanceof Bomb) {
				inventory.remove(c);
				bombs.setValue(bombs.getValue()-1);
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
		for(Collectible c:inventory) {
			if (c instanceof Potion) {
				return true;
			}
		}
		return false;
	}


	/**
	 * after the potion effect has expired
	 * @param player TODO
	 * @param p - potion to deactivate
	 */
	public void potionEffectOff(Potion p) {
		inventory.remove(p);
	}
}



