package unsw.dungeon;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Loads a dungeon from a .json file.
 *
 * By extending this class, a subclass can hook into entity creation. This is
 * useful for creating UI elements with corresponding entities.
 *
 * @author Robert Clifton-Everest
 *
 */
public abstract class DungeonLoader {

    private JSONObject json;

    public DungeonLoader(String filename) throws FileNotFoundException {
        json = new JSONObject(new JSONTokener(new FileReader("dungeons/" + filename)));
    }

    /**
     * Parses the JSON to create a dungeon.
     * @return
     */
    public Dungeon load() {
        int width = json.getInt("width");
        int height = json.getInt("height");

        Dungeon dungeon = new Dungeon(width, height);

        JSONArray jsonEntities = json.getJSONArray("entities");

        for (int i = 0; i < jsonEntities.length(); i++) {
            loadEntity(dungeon, jsonEntities.getJSONObject(i));
        }
        return dungeon;
    }

    private void loadEntity(Dungeon dungeon, JSONObject json) {
        String type = json.getString("type");
        int x = json.getInt("x");
        int y = json.getInt("y");
        int id = json.getInt("id");
        Entity entity = null;
        switch (type) {
        case "player":
            Player player = new Player(x, y, dungeon);
            dungeon.setPlayer(player);
            onLoad(player);
            entity = player;
            break;
        case "wall":
            Wall wall = new Wall(x, y,dungeon);
            onLoad(wall);
            entity = wall;
            break;
        // TODO Handle other possible entities
        case "exit":
        	ExitGoal exit = new ExitGoal(x,y,dungeon);
            onLoad(exit);
            entity = exit;
        	break;
        case "enemy":
        	Enemy enemy = new Enemy(x,y,dungeon);
        	onLoad(enemy);
        	entity = enemy;
        	break;
        case "boulder":
        	Boulder boulder = new Boulder(x,y,dungeon);
        	onLoad(boulder);
        	entity = boulder;
        	break;
        case "bomb":
        	Bomb bomb = new Bomb(x,y,dungeon);
        	onLoad(bomb);
        	entity = bomb;
        	break;
        case "sword":
        	Sword sword = new Sword(x,y,dungeon);
        	onLoad(sword);
        	entity = sword;
        	break;      	
	    case "key":
	    	Key key = new Key(x,y,dungeon,id);
        	onLoad(key);
        	entity = key;
        	break;
		case "treasure":
        	Treasure t = new Treasure(x,y,dungeon);
        	onLoad(t);
        	entity = t;
        	break;
		case "invincibility":
        	Potion p = new Potion(x,y,dungeon);
        	onLoad(p);
        	entity = p;
			break;      	
		case "switch":
	    	FloorSwitch s = new FloorSwitch(x,y,dungeon);
	    	onLoad(s);
	    	entity = s;
			break;      	
		case "door":
			Door d = new Door(x,y,dungeon,id);
			onLoad(d);
			entity = d;
			break;     	
		}
        // FIXME
        if(entity != null) {
        	dungeon.addEntity(entity);
        }
    }

    public abstract void onLoad(Player player);
    // TODO Create additional abstract methods for the other entities
    
    public abstract void onLoad(Enemy enemy);
    
    public abstract void onLoad(Boulder boulder);
    
    public abstract void onLoad(Bomb bomb);

    public abstract void onLoad(Sword sword);    

    public abstract void onLoad(Key key);

    public abstract void onLoad(Treasure treasure);
    
    public abstract void onLoad(Potion potion);

    public abstract void onLoad(FloorSwitch floorswitch);

    public abstract void onLoad(Door door);

    public abstract void onLoad(GoalComponent goal);

	public abstract void onLoad(ExitGoal exit);
	
	public abstract void onLoad(Wall wall);
}
