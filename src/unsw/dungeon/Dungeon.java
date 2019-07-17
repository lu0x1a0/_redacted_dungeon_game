/**
 *
 */
package unsw.dungeon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A dungeon in the interactive dungeon player.
 *
 * A dungeon can contain many entities, each occupy a square. More than one
 * entity can occupy the same square.
 *
 * @author Robert Clifton-Everest
 *
 */
public class Dungeon implements Observer{

    private int width, height;
    private List<Entity> entities;
    private Player player;
    private HashMap<Coord, Entity> map;
    public Dungeon(int width, int height) {
        this.width = width;
        this.height = height;
        this.entities = new ArrayList<>();
        this.player = null;
        this.map = new HashMap<Coord, Entity>();
    }
    
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
        map.put(new Coord(entity.getX(),entity.getY()), entity);
    }
    /*
    public Entity getEntityAtCoord(int x, int y) {
    	Coord coord = new Coord(x,y);
    	if (map.containsKey(coord)) {
    		return map.get(coord);
    	}
    	return null;
    }*/
    public boolean ispassable(int x, int y) {
    	Coord coord = new Coord(x,y);
    	if (map.containsKey(coord)) {
    		hasEntity(x,y);
    		return map.get(coord).ispassable();
    	}
		System.out.println("Note");
    	return true;
    }
    private void hasEntity(int x, int y) {
    	Coord coord = new Coord(x,y);
    	if (map.containsKey(coord)) {
    		System.out.printf("%d,%d hasEntity: ",x,y);
    		System.out.println(map.get(coord).getClass());
    	}
		
    }
    public Collectible hasCollectibleAt(int x, int y) {
    	Coord coord = new Coord(x,y);
    	if (map.containsKey(coord)) {
    		if (map.get(coord) instanceof Collectible)
    			return (Collectible) map.get(coord);
    	} 
    	return null;
    }
    public void removeEntityAtCoord(int x, int y) {
    	Coord coord = new Coord(x,y);
    	map.remove(coord);
    }
    
	@Override
	public void update(Observable o) {
		// TODO Auto-generated method stub
		
	}
}
