/**
 *
 */
package unsw.dungeon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javafx.scene.image.ImageView;

/**
 * A dungeon in the interactive dungeon player.
 *
 * A dungeon can contain many entities, each occupy a square. More than one
 * entity can occupy the same square.
 *
 * @author Robert Clifton-Everest
 *
 */
public class Dungeon implements Observer {

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
    
    public void removeFromView(ImageView v) {
    	dc.removeEntityFromView(v);
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
		//System.out.println("Not");
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
    
//    public ArrayList<Entity> getEntitiesAtCoord(int x, int y) {
//    	Coord coord = new Coord(x,y);
//    	return map.get(coord);
//    	
//    }
    
	@Override
	public void update(Observable o, Object info) {
		System.out.println("----");
		System.out.println(map.get(new Coord(6,1)));
		System.out.println("----");
		
		if(o instanceof Movable && info instanceof Coord) {
			System.out.println(map.get(new Coord(6,1)));
			Coord oldCoord = (Coord) info;
			Entity m = (Entity) o;
			map.get(oldCoord).remove(m);
			if(map.get(oldCoord).size()==0) {
				map.remove(oldCoord);
			}
			Coord newCoord = new Coord(m.getX(), m.getY());
			System.out.printf("%d,%d:new coord outsideloop\n", m.getX(),m.getY()); 
			System.out.println(map.get(newCoord));
			if(map.containsKey(newCoord)) {
				for (Entity e : map.get(newCoord)) {
					e.react(m);
				}
			}
			addEntity( (Entity) o);		
			System.out.println("finishedUPDATE\n\n");
		}
		//TODO add more cases
		else if(o instanceof Sword) {
			Sword s = (Sword) o;
			Coord pCoord = (Coord) info;
			if(s.getCount()>0) {
				//s.setCount(s.getCount()-1);
				Coord newCoord = new Coord(pCoord.getX()-1,pCoord.getY());
				if(map.containsKey(newCoord)) {
					for (Entity e : map.get(newCoord)) {
						e.react(s);
					}
				}
			}
			if(s.getCount()>0) {
				//s.setCount(s.getCount()-1);
				Coord newCoord = new Coord(pCoord.getX(),pCoord.getY()-1);
				if(map.containsKey(newCoord)) {
					for (Entity e : map.get(newCoord)) {
						e.react(s);
					}
				}
			}
			if(s.getCount()>0) {
				//s.setCount(s.getCount()-1);
				Coord newCoord = new Coord(pCoord.getX()+1,pCoord.getY());
				if(map.containsKey(newCoord)) {
					for (Entity e : map.get(newCoord)) {
						e.react(s);
					}
				}
			}
			if(s.getCount()>0) {
				//s.setCount(s.getCount()-1);
				Coord newCoord = new Coord(pCoord.getX(),pCoord.getY()+1);
				if(map.containsKey(newCoord)) {
					for (Entity e : map.get(newCoord)) {
						e.react(s);
					}
				}
			}
			if(s.getCount()==0) {
				s.swordBroken(player);
			}
		}
	}
	public HashMap<Coord, ArrayList<Entity> > getEntities() {
		return map;
	}
	
	public void setController(DungeonController dc) {
		this.dc = dc;
	}
}
