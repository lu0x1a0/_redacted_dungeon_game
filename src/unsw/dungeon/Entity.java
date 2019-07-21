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
     * @param x
     * @param y
     */
    public Entity(int x, int y, Dungeon dungeon) {
        this.x = new SimpleIntegerProperty(x);
        this.y = new SimpleIntegerProperty(y);
        this.dungeon = dungeon;
    }
    public abstract void react(Entity e);
    public IntegerProperty x() {
        return x;
    }
    public IntegerProperty y() {
        return y;
    }

    public int getY() {
        return y().get();
    }

    public int getX() {
        return x().get();
    }

    public abstract boolean ispassable();
    
    public void setIv(ImageView iv) {
		this.iv = iv;
	}
	public ImageView getIv() {
		return iv;
	}
	
    public void removeFromView() {
    	dungeon.removeEntityAtCoord(this,getX(), getY());
    	return;
//		dungeon.removeFromView(iv);
	}
	@Override
	public String toString() {
		return ""+this.getClass();
	}
	public Dungeon getDungeon() {
		return dungeon;
	}

    
    
}
