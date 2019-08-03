package unsw.dungeon;

import java.util.ArrayList;


/**
 * The player entity
 * @author Robert Clifton-Everest
 *
 */
public class Player extends Movable{
	//static int count = 0;
    private ArrayList<Collectible> inventory;
    /**
     * Create a player positioned in square (x,y)
     * @param x - int
     * @param y - int
     * @param dungeon - dungeon to be added to
     */
    public Player(int x, int y, Dungeon dungeon) {
        super(x, y, dungeon);
        this.inventory = new ArrayList<Collectible>();
    }
    
    /**
     * returns whether or not the player is holding a sword
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
     * get the ID of the key held by player
     * @param k - key to return ID of
     * @return - int ID of key
     */
    public int getKeyId(Key k) {
    	return k.getId();
    }
    
    /**
     * remove key from player's inventory
     */
    public void removeKey() {
    	for(Collectible c:inventory) {
    		if (c instanceof Key) {
    			inventory.remove(c);
    			break;
    		}
    	}
    }
    
    /**
     * returns the inventory held by the player
     * @return ArrayList<Collectible> - Inventory of player
     */
    public ArrayList<Collectible> getInventory() {
		return inventory;
	}
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
    	System.out.println(dungeon.getMap().get(new Coord(13,4)));
    	for(Collectible c:inventory) {
    		if (c instanceof Sword) {
    			c.use(this);
    			break;
    		}
    	}
    }
    
    /**
     * remove sword when it has been used 5 times
     */
    public void removeSword() {
    	for(Collectible c:inventory) {
    		if (c instanceof Sword) {
    			inventory.remove(c);
    			break;
    		}
    	}
    }
    
    /**
     * count number bombs held by the player
     * @return - int
     */
    public int countBombs() {
    	int count = 0;
    	for(Collectible c:inventory) {
    		if (c instanceof Bomb) {
    			count++;
    		}
    	}
    	return count;
    }
    
    /**
     * light bomb in inventory and place it on map
     */
    public void litBomb() {
    	for(Collectible c:inventory) {
    		if (c instanceof Bomb) {
    			inventory.remove(c);
    			c.use(this);
    			break;
    		}
    	}
    }
    
    /**
     * returns whether or not the player has a potion
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
     * @param p - potion to deactivate
     */
    public void potionEffectOff(Potion p) {
    	inventory.remove(p);
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
    	inventory.add(c);
    }

	@Override
	public boolean isPassable() {
		return true;
	}
	

}
