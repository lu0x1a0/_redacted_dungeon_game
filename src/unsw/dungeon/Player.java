package unsw.dungeon;

import java.util.ArrayList;


/**
 * The player entity
 * @author Robert Clifton-Everest
 *
 */
public class Player extends Movable{
	//static int count = 0;
    ArrayList<Collectible> inventory;
    Inventory playerInventory = null;




	/**
     * Create a player positioned in square (x,y)
     * @param x - int
     * @param y - int
     * @param dungeon - dungeon to be added to
     */
    public Player(int x, int y, Dungeon dungeon) {
        super(x, y, dungeon);
        playerInventory = new Inventory(this);
    }
    
    
    
    
    public Inventory getPlayerInventory() {
		return playerInventory;
	}




	public void setPlayerInventory(Inventory playerInvenvtory) {
    	this.playerInventory = playerInvenvtory;
    }
    
    public boolean hasSword() {
    	return playerInventory.hasSword();
    } 
    
    /**
     * returns the key the player is holding
     * @return Key
     */
    public Key getKey() {
    	return playerInventory.getKey();
    }
    
    /**
     * get the ID of the key held by player
     * @param k - key to return ID of
     * @return - int ID of key
     */
    public int getKeyId(Key k) {
    	return k.getId();
    }
    
    /**
	 * remove key from player's inventory
	 * 
	 */
	public void removeKey() {
		playerInventory.removeKey();
	}
    
    /**
     * returns the inventory held by the player
     * @return ArrayList<Collectible> - Inventory of player
     */
//    public ArrayList<Collectible> getInventory() {
//		return inventory;
//	}
	@Override
    public void moveUp() {
        if (getY() > 0 ) { 
			if(dungeon.ispassable(getX(), getY() - 1) == true) {
	    		int oldY = getY();
	        	y().set(oldY - 1);
	        	notifyObservers(this,new Coord(getX(),oldY));
			}
        	else {
	        	notifyObservers(this,Direction.UP);
	        }
        }
    }
    @Override
    public void moveDown() {
        if (getY() < dungeon.getHeight() - 1) {
	        if(dungeon.ispassable(getX(), getY() + 1) == true) {
        		int oldY = getY();
	        	y().set(oldY + 1);
	        	notifyObservers(this,new Coord(getX(),oldY));
	        }else {
	        	notifyObservers(this,Direction.DOWN);
	        }
        }
    }
    @Override
    public void moveLeft() {
    	if (getX() > 0) { 
			if(dungeon.ispassable(getX() - 1, getY()) == true) {
	    		int oldx = getX();
		    	x().set(oldx - 1);
		    	notifyObservers(this,new Coord(oldx,getY()));
			}
	    	else {
	        	notifyObservers(this,Direction.LEFT);
	        }
    	}
    }
    @Override
    public void moveRight() {
        if (getX() < dungeon.getWidth() - 1) { 
			if(dungeon.ispassable(getX() + 1, getY()) == true) {
	        	int oldx = getX();
	        	x().set(oldx + 1);
	        	notifyObservers(this,new Coord(oldx,getY()));
			}
        	else {
	        	notifyObservers(this,Direction.RIGHT);
	        }
        }
        
    }
    
    /**
     * use sword to kill enemy
     */
    public void waveSword() {
    	playerInventory.waveSword();
    }
    
    /**
	 * remove sword when it has been used 5 times
	 * 
	 */
	public void removeSword() {
		playerInventory.removeSword();
	}
    
    /**
	 * count number bombs held by the player
	 * @return - int
	 * 
	 */
	public int countBombs() {
		return playerInventory.countBombs();
	}
    
    /**
     * light bomb in inventory and place it on map
     */
    public void litBomb() {
    	Bomb bomb = playerInventory.getBomb();
    	if (bomb != null) bomb.use(this);
    }
    
    /**
	 * returns whether or not the player has a potion
	 * @return - boolean
	 */
	public boolean hasPotion() {
		return playerInventory.hasPotion();
	}
    
    /**
	 * after the potion effect has expired
	 * @param p - potion to deactivate
	 */
	public void potionEffectOff(Potion p) {
		playerInventory.potionEffectOff(p);
	}
    @Override
    public void react(Entity e) {
    	if(e instanceof Enemy) {
    		react( (Enemy) e);
    	}
    }
    
    /**
     * Overloaded method for when the player has to react to an enemy
     * @param e - enemy to react to
     */
    public void react(Enemy e) {
    	e.react(this);
    }
    
    /**
     * Add item to inventory
     * @param c - collectible to add to inventory
     */
    public void addToInventory(Collectible c) {
    	playerInventory.addToInventory(c);
    }

	@Override
	public boolean isPassable() {
		return true;
	}
	

}
