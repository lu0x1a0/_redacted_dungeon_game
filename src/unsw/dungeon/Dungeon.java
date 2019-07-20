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
    public void addToView(Entity e, ImageView v) {
    	//dc.trackPosition(e,v);
    	dc.addEntityToView(v,e.getX(),e.getY());
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
    public void removeEntityAtCoord(Entity e,int x, int y) {
    	Coord coord = new Coord(x,y);
    	map.get(coord).remove(e);
    }
    
//    public ArrayList<Entity> getEntitiesAtCoord(int x, int y) {
//    	Coord coord = new Coord(x,y);
//    	return map.get(coord);
//    	
//    }
    
	@Override
	public void update(Observable o, Object info) {
		if(o instanceof Movable && info instanceof Coord) {
			Coord oldCoord = (Coord) info;
			Entity m = (Entity) o;
			map.get(oldCoord).remove(m);
			if(map.get(oldCoord).size()==0) {
				map.remove(oldCoord);
			}
			Coord newCoord = new Coord(m.getX(), m.getY());
			System.out.printf("%d,%d:new coord outsideloop\n", m.getX(),m.getY()); 
			if(map.containsKey(newCoord)) {
				for(int i = 0; i<map.get(newCoord).size();i++) {
					map.get(newCoord).get(i).react(m);
				}
			}
			addEntity( (Entity) o);		
			System.out.println("finishedUPDATE\n\n");
		}
		else if(o instanceof Player) {
			update((Player) o, (Direction) info);
		}
		else if(o instanceof Sword) {
			update((Sword) o,info);
		}
		else if(o instanceof Bomb){
			update((Bomb) o,info);
		}
		else if(o instanceof Boulder) {
			Coord oldCoord = (Coord) info;
			Entity m = (Entity) o;
			map.get(oldCoord).remove(m);
			if(map.get(oldCoord).size()==0) {
				map.remove(oldCoord);
			}
			Coord newCoord = new Coord(m.getX(), m.getY());
			if(map.containsKey(newCoord)) {
				for(int i = 0; i<map.get(newCoord).size();i++) {
					map.get(newCoord).get(i).react(m);
				}
			}
			addEntity( (Entity) o);	
		}
		
	}
	private void update(Player p, Direction d) {
		switch (d) {
		case UP:
			pushBoulder(p, new Coord(p.getX(),p.getY()-1));
			break;
		case DOWN:
			pushBoulder(p, new Coord(p.getX(),p.getY()+1));
			break;
		case LEFT:
			pushBoulder(p, new Coord(p.getX()-1,p.getY()));
			break;
		case RIGHT:
			pushBoulder(p, new Coord(p.getX()+1,p.getY()));
			break;
		default:
			break;
		}
	}
	private void pushBoulder(Player p, Coord c) {
		ArrayList<Entity> stuff = map.get(c);
		for(int i=0; i<stuff.size();i++) {
			//if (stuff.get(i) instanceof Boulder) {
			stuff.get(i).react(p);
			//}
		}
	}
	private void update(Sword s, Object info) {
		Coord pCoord = (Coord) info;
		stab(s, new Coord(pCoord.getX()-1,pCoord.getY()));
		stab(s, new Coord(pCoord.getX(),pCoord.getY()-1));
		stab(s, new Coord(pCoord.getX()+1,pCoord.getY()));
		stab(s, new Coord(pCoord.getX(),pCoord.getY()+1));

		if(s.getCount()==0) {
			s.swordBroken(player);
		}
	}
	private void stab(Sword s,Coord c) {
		if(s.getCount()>0) {
			if(map.containsKey(c)) {
				//for (Entity e : map.get(c)) {
				for(int i = 0; i<map.get(c).size() ;i++) {
					map.get(c).get(i).react(s);
				}
			}
		}
	}
	private void update(Bomb o, Object info) {
		Coord centre = new Coord(((Bomb) o).getX(),((Bomb) o).getY());

		bombCell(new Coord(centre.getX()-1,centre.getY()-1));
		bombCell(new Coord(centre.getX()  ,centre.getY()-1));
		bombCell(new Coord(centre.getX()+1,centre.getY()-1));
		bombCell(new Coord(centre.getX()-1,centre.getY()));
		bombCell(new Coord(centre.getX()  ,centre.getY()));
		bombCell(new Coord(centre.getX()+1,centre.getY()));
		bombCell(new Coord(centre.getX()-1,centre.getY()+1));
		bombCell(new Coord(centre.getX()  ,centre.getY()+1));
		bombCell(new Coord(centre.getX()+1,centre.getY()+1));
	}
	private void bombCell(Coord c) {
		if(map.containsKey(c)) {
			for(int i = 0; i<map.get(c).size() ;i++) {
				if(	map.get(c).get(i) instanceof Boulder) {
					map.get(c).get(i).removeFromView();
				}
				else if(map.get(c).get(i) instanceof Enemy) {
					((Enemy) map.get(c).get(i)).killed();
				}
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
