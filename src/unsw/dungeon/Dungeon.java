/**
 *
 */
package unsw.dungeon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;


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
    private Player player;
    private HashMap<Coord, ArrayList<Entity> > map;
	private DungeonController dc;
	private ArrayList<GoalComponent> goals = new ArrayList<GoalComponent>();
	
	/**
	 * Constructor for dungeon
	 * @param width - width of the map
	 * @param height - height of the map
	 */
    public Dungeon(int width, int height) {
        this.width = width;
        this.height = height;
        //this.entities = new ArrayList<>();
        this.player = null;
        this.map = new HashMap<Coord, ArrayList<Entity> >();
    }
    /**
     * getter for mapping of the dungeon's entity
     * @return the hashmap
     */
    public HashMap<Coord, ArrayList<Entity>> getMap() {
		return map;
	}
    /**
     * getter for width of dungeon
     * @return - int
     */
	public int getWidth() {
        return width;
    }
    /**
     * getter for height of dungeon
     * @return - int
     */
    public int getHeight() {
        return height;
    }

    /**
     * getter for player in the dungeon
     * @return - Player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * setter for player in the dungeon
     * @param player - player to set
     */
    public void setPlayer(Player player) {
        this.player = player;
    }
    /**
     * gets the player's Coord in the dungeon
     * @return Coord
     */
    public Coord getPlayerCoord() {
    	return new Coord(player.getX(), player.getY());
    }
    /**
     * Add an object to the view for the player
     * @param e - entity to add to view
     * @param v - view to add it to
     */
    public void addToView(Entity e, ImageView v) {
    	//dc.trackPosition(e,v);
    	if(v!=null) {
    		dc.addEntityToView(v,e.getX(),e.getY());
    	}
    }
    /**
     * removes a image from the JavaFX scene
     * @param v - image view
     */
	public void removeFromView(ImageView v) {
//		System.out.println("removin---"+v);
		
    	dc.removeEntityFromView(v);
    }
    /**
     * add objective to the dungeon
     * @param goal - GoalComponenet to add
     */
    public void addGoal(GoalComponent goal) {
    	goal.registerObserver(this);
    	goals.add(goal);
    }
    /**
     * add entity to the dungeon mapped to its coordinate
     * @param entity - Entity
     */
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
	/**
	 * get all accessible passable tile coords surrounding a specific coord
	 * @param c: the centre of all tiles being checked
	 * @return - LinkedList of possible locations
	 */
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
	/**
	 * check whether a given tile is passable
	 * @param x coord
	 * @param y coord
	 * @return  true/false
	 */
    public boolean ispassable(int x, int y) {
    	Coord coord = new Coord(x,y);
    	if (map.containsKey(coord)) {
    		//return map.get(coord).ispassable();
    		for(Entity e : map.get(coord)) {
    			if (!e.isPassable()) {
    				return false;
    			}
    		}
    	}
    	return true;
    }
    /**
     * remove the given entity at the specific coordinate in the dungeon
     * @param e entity to be removed
     * @param x coord
     * @param y coord
     */
    public void removeEntityAtCoord(Entity e,int x, int y) {
    	Coord coord = new Coord(x,y);
    	map.get(coord).remove(e);
    }
    /**
     * get all entities of given type in the dungeon
     * @param <T> variable type
     * @param fType: the type to be retrieved
     * @return - ArrayList<Entity>
     */
    public <T extends Entity> ArrayList<Entity> getEntitiesByType(Class<T> fType){
    	ArrayList<Entity> allEntities = new ArrayList<Entity>();
    	for (ArrayList<Entity> value : map.values()) {
    	    for (Entity e: value) {
    	    	if(e.getClass().equals(fType)) {
    	    		allEntities.add((Entity) e);
    	    	}
    	    	
    	    }
    		
    	}
    	return allEntities;
    }
    
    
    
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
			if(map.containsKey(newCoord)) {
				for(int i = 0; i<map.get(newCoord).size();i++) {
					map.get(newCoord).get(i).react(m);
				}
			}
			addEntity( (Entity) o);		
			}
		else if(o instanceof Player) {
			update((Player) o, (Direction) info);
		}
		else if(o instanceof GoalComponent) {
			System.out.print("You win!!!!");
		}
		//TODO add more cases
		else if(o instanceof Sword) {
			update((Sword) o,info);
		}
		else if(o instanceof Bomb){
			update((Bomb) o,info);
		}
		else if(o instanceof Potion) {
			update((Potion) o, info);
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
	/**
	 * case where player heads to an impassable tile before it and whether it is reactive
	 * @param p - Player
	 * @param d - Direction
	 */
	private void update(Player p, Direction d) {
		switch (d) {
		case UP:
			changeImpassable(p, new Coord(p.getX(),p.getY()-1));
			break;
		case DOWN:
			changeImpassable(p, new Coord(p.getX(),p.getY()+1));
			break;
		case LEFT:
			changeImpassable(p, new Coord(p.getX()-1,p.getY()));
			break;
		case RIGHT:
			changeImpassable(p, new Coord(p.getX()+1,p.getY()));
			break;
		default:
			break;
		}
	}
	/**
	 * change the appearance of an entity on the JavaFX Scene
	 * @param e entity
	 * @param oldv previous appearance
	 */
	public void changeEntityImage(Entity e, ImageView oldv) {
		dc.changeEntityImage(e, oldv);
	}
	/**
	 * this is what happens when player tries to walk into an impassable tile in front of it
	 * @param p player
	 * @param c coord of the tile tried to move into
	 */
	private void changeImpassable(Player p, Coord c) {
		ArrayList<Entity> stuff = map.get(c);
		for(int i=0; i<stuff.size();i++) {
			//if (stuff.get(i) instanceof Boulder) {
			stuff.get(i).react(p);
			//}
		}
	}
	/**
	 * this is what happens when a sword is used by the player
	 * all tiles surrounding the player are tried to hit by the sword
	 * @param s - Sword
	 * @param info - additional info passed in by notify
	 */
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
	/**
	 * object on the coord c will react to the sword
	 * @param s sword
	 * @param c coord to react
	 */
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
	/**
	 * This is what happens when the player use the bomb
	 * all movable in surround tiles will be affected by the bomb
	 * @param o bomb, 
	 * @param info info about the bomb
	 */
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
	
	private void update(Potion p, Object info) {
		if (info instanceof String) {
			if((String) info == "on") {
				for (ArrayList<Entity> arr: map.values()) {
					for(Entity e: arr) {
						if(e instanceof Enemy) {
							((Enemy) e).setPathMethod(true);
						}
					}
				}
			}
			if((String) info == "off") {
				for (ArrayList<Entity> arr: map.values()) {
					for(Entity e: arr) {
						if(e instanceof Enemy) {
							((Enemy) e).setPathMethod(false);
						}
					}
				}
			}
		}
	}
	/**
	 * if there are movable at the Coord c, destroy it
	 * @param c coord
	 */
	private void bombCell(Coord c) {
		if(map.containsKey(c)) {
			for(int i = 0; i<map.get(c).size() ;i++) {
				if(	map.get(c).get(i) instanceof Boulder) {
					map.get(c).get(i).removeFromView();
				}
				else if(map.get(c).get(i) instanceof Enemy) {
					((Enemy) map.get(c).get(i)).killed();
				}else if(map.get(c).get(i) instanceof Player) {
					((Player) map.get(c).get(i)).removeFromView();
				}
			}
		}
	}
	/**
	 * attach the controller of the dungeon to the dungeon, so it can call useful methods
	 * @param dc - DungeonController
	 */
	public void setController(DungeonController dc) {
		this.dc = dc;
	}
	/**
	 * tells the enemy in the dungeon to start moving and hunt the player
	 */
	public void startEnemies() {
		for(ArrayList<Entity> arr :map.values()) {
			for(Entity e: arr) {
				if(e instanceof Enemy) {
					((Enemy) e).start();
				}
			}
		}
	}
	public LinkedList<Coord> getSurroundTiles(Coord centre){
		LinkedList<Coord> ret = new LinkedList<Coord>();
		if(centre.getX()+1<this.width) {
			ret.add(new Coord(centre.getX()+1,centre.getY()));
		}
		if(centre.getX()-1>=0) {
			ret.add(new Coord(centre.getX()-1,centre.getY()));
		}
		if(centre.getY()+1<this.height) {
			ret.add(new Coord(centre.getX(),centre.getY()+1));
		}
		if(centre.getY()-1>=0) {
			ret.add(new Coord(centre.getX(),centre.getY()-1));
		}
		return ret;
	}
}
