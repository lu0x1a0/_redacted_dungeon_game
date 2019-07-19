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
public class Player extends Entity implements Movable{
	static int count = 0;
    private Dungeon dungeon;
    private ArrayList<Collectible> inventory;
    /**
     * Create a player positioned in square (x,y)
     * @param x
     * @param y
     */
    public Player(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
        this.inventory = new ArrayList<Collectible>();
    }
    @Override
    public void moveUp() {

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
        
        
        if (getY() > 0 && 
			dungeon.ispassable(getX(), getY() - 1) == true) {
    		int oldY = getY();
        	y().set(oldY - 1);
        	notifyObservers(this,new Coord(getX(),oldY));
        }
    }
    @Override
    public void moveDown() {
        if (getY() < dungeon.getHeight() - 1 && 
			dungeon.ispassable(getX(), getY() + 1) == true) {
    		int oldY = getY();
        	y().set(oldY + 1);
        	notifyObservers(this,new Coord(getX(),oldY));
        }
    }
    @Override
    public void moveLeft() {
    	if (getX() > 0 && 
			dungeon.ispassable(getX() - 1, getY()) == true) {
    		int oldx = getX();
	    	x().set(oldx - 1);
	    	notifyObservers(this,new Coord(oldx,getY()));
    	}
    }
    @Override
    public void moveRight() {
        if (getX() < dungeon.getWidth() - 1 && 
			dungeon.ispassable(getX() + 1, getY()) == true) {
        	collectItemAt(getX() + 1, getY());
        	int oldx = getX();
        	x().set(oldx + 1);
        	notifyObservers(this,new Coord(oldx,getY()));
        }
    }
    private void collectItemAt(int x, int y) {
    	Collectible c = dungeon.hasCollectibleAt(x,y);
    	if (c != null) {
    		dungeon.removeEntityAtCoord(x, y);
    		inventory.add(c);
    	}
    }
    @Override
    public void react(Entity e) {
    	if(e instanceof Enemy) {
    		react( (Enemy) e);
    	}
    	// TODO more cases
    }
    public void react(Enemy e) {
    	
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
