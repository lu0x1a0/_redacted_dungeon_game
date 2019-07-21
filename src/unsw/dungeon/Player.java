package unsw.dungeon;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javafx.scene.image.ImageView;

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
     * @param x
     * @param y
     */
    public Player(int x, int y, Dungeon dungeon) {
        super(x, y, dungeon);
        this.inventory = new ArrayList<Collectible>();
    }
    public boolean hasSword() {
    	for( Collectible e: inventory) {
    		if(e instanceof Sword) {
    			return true;
    		}
    	}
    	return false;
    }
    public Key getKey() {
    	for( Collectible e: inventory) {
    		if(e instanceof Key) {
    			return (Key) e;    		
			}
    	}
    	return null;
    }
    public int getKeyId(Key k) {
    	return k.getId();
    }
    public void removeKey() {
    	for(Collectible c:inventory) {
    		if (c instanceof Key) {
    			inventory.remove(c);
    			break;
    		}
    	}
    }
    public ArrayList<Collectible> getInventory() {
		return inventory;
	}
	@Override
    public void moveUp() {
    	/*
        final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        ScheduledFuture<?> result = executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.printf("%d,olalala\n",count++);
            }
        }, 0, 1, TimeUnit.SECONDS);
        
        if(count >= 10) {
        	executorService.shutdownNow();
        }
        */
        
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
    public void waveSword() {
    	for(Collectible c:inventory) {
    		if (c instanceof Sword) {
    			c.use(this);
    			break;
    		}
    	}
    }
    public void removeSword() {
    	for(Collectible c:inventory) {
    		if (c instanceof Sword) {
    			inventory.remove(c);
    			break;
    		}
    	}
    }
    public void litBomb() {
    	for(Collectible c:inventory) {
    		if (c instanceof Bomb) {
    			inventory.remove(c);
    			c.use(this);
    			break;
    		}
    	}
    }
    public boolean hasPotion() {
    	for(Collectible c:inventory) {
    		if (c instanceof Potion) {
    			return true;
    		}
    	}
    	return false;
    }
    public void potionEffectOff(Potion p) {
    	inventory.remove(p);
    }
    @Override
    public void react(Entity e) {
    	if(e instanceof Enemy) {
    		react( (Enemy) e);
    	}
    	// TODO more cases
    }
    public void react(Enemy e) {
    	e.react(this);
    }
    public void addToInventory(Collectible c) {
    	inventory.add(c);
    }
	@Override
	public boolean checkPositionAvail() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean ispassable() {
		// TODO Auto-generated method stub
		return true;
	}

}
