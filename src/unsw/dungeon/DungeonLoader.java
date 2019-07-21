package unsw.dungeon;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

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

        System.out.println(json);
        
        JSONArray jsonEntities = json.getJSONArray("entities");

        for (int i = 0; i < jsonEntities.length(); i++) {
            loadEntity(dungeon, jsonEntities.getJSONObject(i));
        }
        
        JSONObject jsonGoals = json.getJSONObject("goal-condition");
        
        System.out.println(jsonGoals);
        
        System.out.println(dungeon.getEntitiesByType(Enemy.class));
        
        
        System.out.println(dungeon.getEntitiesByType(Wall.class));
        
        
        
        if(jsonGoals.getString("goal").equals("AND")) {
        	// This is an AND goal type. All the subgoals must be combined with AND
        	//Loop through JSON array
        	GoalCompositeComponent parentGoal = new GoalCompositeComponent(true);
        	JSONArray subGoals = jsonGoals.getJSONArray("subgoals");
        	for (int i = 0; i < subGoals.length(); i++) {
                parentGoal.addChild(loadGoal(dungeon, subGoals.getJSONObject(i)));
            }
        	dungeon.addGoal(parentGoal);
        	
        }
        else if (jsonGoals.getString("goal").equals("OR")) {
        	//This is an OR goal type. All the sub goals are combined with OR
        	// Loop through the JSON array "subgoals" :
        	
        	GoalCompositeComponent parentGoal = new GoalCompositeComponent(false);
        	JSONArray subGoals = jsonGoals.getJSONArray("subgoals");
        	for (int i = 0; i < subGoals.length(); i++) {
                parentGoal.addChild(loadGoal(dungeon, subGoals.getJSONObject(i)));
            }
        	dungeon.addGoal(parentGoal);
        }
        else {
        	dungeon.addGoal(loadGoal(dungeon, jsonGoals));
        	
        }
        
        return dungeon;
    }

    private GoalLeafNode loadGoal(Dungeon dungeon, JSONObject json) {
    	if(json.getString("goal").equals("enemies")) {
    		ArrayList<Entity> enemies = dungeon.getEntitiesByType(Enemy.class);
    		GoalLeafEnemiesKilled enemyGoal = new GoalLeafEnemiesKilled();
    		for (Entity e: enemies) {
    			((Enemy) e).registerObserver(enemyGoal);
    			enemyGoal.addEnemy(((Enemy) e));
    			
    		}
    		return enemyGoal;
    	}
    	else if(json.getString("goal").equals("treasure")) {
    		ArrayList<Entity> treasures = dungeon.getEntitiesByType(Treasure.class);
    		GoalLeafTreasureCollected treasureGoal = new GoalLeafTreasureCollected();
    		for (Entity t: treasures) {
    			((Treasure) t).registerObserver(treasureGoal);
    			treasureGoal.addTreasure(((Treasure) t));
    			
    		}
    		return treasureGoal;
    	}
    	else if(json.getString("goal").equals("boulders")) {
    		ArrayList<Entity> floorSwitches = dungeon.getEntitiesByType(FloorSwitch.class);
    		System.out.println("Checking for floor switches");
    		System.out.println(floorSwitches);
    		GoalLeafFloorSwitch floorSwitchGoal = new GoalLeafFloorSwitch();
    		for (Entity f: floorSwitches) {
    			((FloorSwitch) f).registerObserver(floorSwitchGoal);
    			floorSwitchGoal.addFloorSwitch(((FloorSwitch) f));
    			
    		}
    		return floorSwitchGoal;
    	}
    	else {
    		ArrayList<Entity> exit = dungeon.getEntitiesByType(Exit.class);
    		GoalLeafExit exitGoal = new GoalLeafExit((Exit) exit.get(0));
    		return exitGoal;
    		


    	}
    }
    
    
    private void loadEntity(Dungeon dungeon, JSONObject json) {
        String type = json.getString("type");
        int x = json.getInt("x");
        int y = json.getInt("y");
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
        	Exit exit = new Exit(x,y,dungeon);
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
	        int id = json.getInt("key");

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
	        int did = json.getInt("key");
			Door d = new Door(x,y,dungeon,did);
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

	public abstract void onLoad(Exit exit);
	
	public abstract void onLoad(Wall wall);
}
