package unsw.dungeon;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.ImageView;
/**
 * An entity in the dungeon.
 * @author Robert Clifton-Everest
 *
 */
public abstract class Entity {

    // IntegerProperty is used so that changes to the entities position can be
    // externally observed.
    private IntegerProperty x, y;
    private ImageView iv;
	protected Dungeon dungeon;

    /**
     * Create an entity positioned in square (x,y)
     * @param x - int
     * @param y - int
     * @param dungeon the dungeon object which the entity is placed at
     */
    public Entity(int x, int y, Dungeon dungeon) {
        this.x = new SimpleIntegerProperty(x);
        this.y = new SimpleIntegerProperty(y);
        this.dungeon = dungeon;
    }
    /**
     * how it will react to other objects
     * @param e - Entity 
     */
    public abstract void react(Entity e);
    /**
     * get javafx property x of Entity
     * @return - IntegerProperty
     */
    public IntegerProperty x() {
        return x;
    }
    /**
     * get javafx property y of Entity
     * @return - IntegerProperty
     */
    public IntegerProperty y() {
        return y;
    }
    /**
     * get y coord of Entity
     * @return  - IntegerProperty
     */
    public int getY() {
        return y().get();
    }
	/**
	 * get x coord of Entity
	 * @return - IntegerProperty
	 */
    public int getX() {
        return x().get();
    }
    /**
     * check whether this object is a passable
     * @return boolean
     */
    public abstract boolean isPassable();
    
    /**
     * set the active JavaFX image of this Entity
     * @param iv - ImageView
     */
    public void setIv(ImageView iv) {
		this.iv = iv;
	}
    /**
     * return the current active JavaFX image of this Entity
     * @return ImageView
     */
	public ImageView getIv() {
		return iv;
	}
	
	/**
	 * remove this Entity from the game view(javaFX view and the Dungeon's HashMap)
	 */
    public void removeFromView() {
    	dungeon.removeEntityAtCoord(this,getX(), getY());
    	if (iv!=null) {
    		dungeon.removeFromView(iv);
    	}
		return;

	}
	@Override
	public String toString() {
		return ""+this.getClass();
	}
	/**
	 * getter for Dungeon
	 * @return - Dungeon
	 */
	public Dungeon getDungeon() {
		return dungeon;
	}

    
    
}
