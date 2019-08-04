package unsw.dungeon;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
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
     * @return - Dungeon
     * @throws FileNotFoundException 
     * @throws JSONException 
     */
    public void changeDungeonFile(String filename) throws JSONException, FileNotFoundException {
    	json = new JSONObject(new JSONTokener(new FileReader("dungeons/" + filename)));
    }
    public Dungeon load() {
        int width = json.getInt("width");
        int height = json.getInt("height");

        Dungeon dungeon = new Dungeon(width, height);

        JSONArray jsonEntities = json.getJSONArray("entities");

        for (int i = 0; i < jsonEntities.length(); i++) {
            loadEntity(dungeon, jsonEntities.getJSONObject(i));
        }
        
        //Function here to initialise inventory
        
        JSONObject jsonGoals = json.getJSONObject("goal-condition");
        GoalComponent goals = goalAdder(dungeon, jsonGoals);
        dungeon.addGoal(goals);
        return dungeon;
    }
    /**
     * turn a group of goals into the corresponding java object that can
     * differ by and/or conditional logic and return this goal component
     * @param dungeon
     * @param jsonGoals
     * @return
     */
    private GoalComponent goalAdder(Dungeon dungeon, JSONObject jsonGoals) {
    	if(jsonGoals.getString("goal").equals("AND")) {
        	// This is an AND goal type. All the subgoals must be combined with AND
        	//Loop through JSON array
        	GoalCompositeComponent parentGoal = new GoalCompositeComponent(true);
        	JSONArray subGoals = jsonGoals.getJSONArray("subgoals");
        	for (int i = 0; i < subGoals.length(); i++) {
        		if(subGoals.getJSONObject(i).getString("goal").equals("AND")) {
        			parentGoal.addChild(goalAdder(dungeon, (JSONObject) subGoals.get(i)));
        		}
        		else if (subGoals.getJSONObject(i).getString("goal").equals("OR")) {
        			parentGoal.addChild(goalAdder(dungeon, (JSONObject) subGoals.get(i)));
        		}
        		else {
        			parentGoal.addChild(loadGoal(dungeon, subGoals.getJSONObject(i)));
        		}
            }
        	return parentGoal;
        	
        }
        else if (jsonGoals.getString("goal").equals("OR")) {
        	//This is an OR goal type. All the sub goals are combined with OR
        	// Loop through the JSON array "subgoals" :
        	
        	GoalCompositeComponent parentGoal = new GoalCompositeComponent(false);
        	JSONArray subGoals = jsonGoals.getJSONArray("subgoals");
        	for (int i = 0; i < subGoals.length(); i++) {
        		if(subGoals.getJSONObject(i).getString("goal").equals("AND")) {
        			parentGoal.addChild(goalAdder(dungeon, (JSONObject) subGoals.get(i)));
        		}
        		else if (subGoals.getJSONObject(i).getString("goal").equals("OR")) {
        			parentGoal.addChild(goalAdder(dungeon, (JSONObject) subGoals.get(i)));
        		}
        		else {
        			parentGoal.addChild(loadGoal(dungeon, subGoals.getJSONObject(i)));
        		}
//                parentGoal.addChild(loadGoal(dungeon, subGoals.getJSONObject(i)));
            }
        	return parentGoal;
        }
        else {
        	return loadGoal(dungeon, jsonGoals);
        	
        }
    }
    /**
     * interpret a specific goal from json into object.
     * @param dungeon
     * @param json
     * @return
     */
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
    
    /**
     * Takes a JSON object and parses it into an entity and adds it to dungeon object
     * @param dungeon
     * @param json
     */
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


    /**
     * Attaches player image to entity player
     * @param player
     */
    public abstract void onLoad(Player player);
    
    /**
     * Attaches enemy image to entity enemy
     * @param enemy
     */
    public abstract void onLoad(Enemy enemy);
    
    /**
     * Attaches boulder image to entity boulder
     * @param boulder
     */
    public abstract void onLoad(Boulder boulder);
    
    /**
     * Attaches bomb image to entity bomb
     * @param bomb
     */
    public abstract void onLoad(Bomb bomb);

    /**
     * Attaches sword image to entity sword
     * @param sword
     */
    public abstract void onLoad(Sword sword);    

    /**
     * Attaches player key to entity key
     * @param key
     */
    public abstract void onLoad(Key key);

    /**
     * Attaches treasure image to entity treasure
     * @param treasure
     */
    public abstract void onLoad(Treasure treasure);
    
    /**
     * Attaches potion image to entity potion
     * @param potion
     */
    public abstract void onLoad(Potion potion);

    /**
     * Attaches floorswitch image to entity floorswitch
     * @param floorswitch
     */
    public abstract void onLoad(FloorSwitch floorswitch);

    /**
     * Attaches door image to entity door
     * @param door
     */
    public abstract void onLoad(Door door);

    /**
     * Not used
     * @param goal
     */
    public abstract void onLoad(GoalComponent goal);

    /**
     * Not used
     * @param goal
     */
	public abstract void onLoad(Exit exit);
	
	/**
     * Not used
     * @param goal
     */
	public abstract void onLoad(Wall wall);
}
