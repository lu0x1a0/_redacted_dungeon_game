/**
 *
 */
package unsw.dungeon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
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
    //private List<Entity> entities;
    private Player player;
    private HashMap<Coord, ArrayList<Entity> > map;
	private DungeonController dc;
    public Dungeon(int width, int height) {
        this.width = width;
        this.height = height;
        //this.entities = new ArrayList<>();
        this.player = null;
        this.map = new HashMap<Coord, ArrayList<Entity> >();
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

    public Coord getPlayerCoord() {
    	return new Coord(player.getX(), player.getY());
    }
    public void addEntity(Entity entity) {
        //entities.add(entity);
        //map.put(new Coord(entity.getX(),entity.getY()), entity);
        Coord newCoord = new Coord(entity.getX(), entity.getY());
		if(map.containsKey(newCoord)) {
			map.get(newCoord).add(entity);
		}else {
			ArrayList<Entity> arr = new ArrayList<Entity>();
			arr.add(entity);
			map.put( newCoord, arr);
		}
    }
	public LinkedList<Coord> getSurroundPassable(Coord c) {
		LinkedList<Coord> ret = new LinkedList<Coord>();
		if(c.getX()!=0) {
			if( ispassable(c.getX()-1,c.getY()) ) {
				ret.add(new Coord(c.getX()-1,c.getY()));
			}
		}
		if(c.getY()!=0) {
			if( ispassable(c.getX(),c.getY()-1) ) {
				ret.add(new Coord(c.getX(),c.getY()-1));
			}
		}
		if(c.getX()!=width){
			if( ispassable(c.getX()+1,c.getY()) ) {
				ret.add(new Coord(c.getX()+1,c.getY()));
			}
		}
		if(c.getY()!=height){
			if( ispassable(c.getX(),c.getY()+1) ) {
				ret.add(new Coord(c.getX(),c.getY()+1));
			}			
		}
		return ret;
	}
    public boolean ispassable(int x, int y) {
    	Coord coord = new Coord(x,y);
    	if (map.containsKey(coord)) {
    		//return map.get(coord).ispassable();
    		for(Entity e : map.get(coord)) {
    			if (!e.ispassable()) {
    				return false;
    			}
    		}
    	}
		System.out.println("Not");
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
    		for(Entity e : map.get(coord)) {
    			if ( e instanceof Collectible)
    				return (Collectible) e;
    		}
		} 
    	return null;
    }
    public void removeEntityAtCoord(int x, int y) {
    	Coord coord = new Coord(x,y);
    	map.remove(coord);
    }
    
	@Override
	public void update(Observable o, Object info) {
		if(o instanceof Movable) {
			Coord oldCoord = (Coord) info;
			Entity m = (Entity) o;
			map.remove(oldCoord);
			Coord newCoord = new Coord(m.getX(), m.getY());
			for (Entity e : map.get(newCoord)) {
				e.react(m);
			}
			addEntity( (Entity) o);		
		}
		//TODO add more cases
	}
	public void setController(DungeonController dc) {
		this.dc = dc;
	}
}
