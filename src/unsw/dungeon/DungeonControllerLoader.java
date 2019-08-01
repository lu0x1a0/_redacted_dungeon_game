package unsw.dungeon;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.sun.javafx.collections.MappingChange.Map;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

/**
 * A DungeonLoader that also creates the necessary ImageViews for the UI,
 * connects them via listeners to the model, and creates a controller.
 * @author Robert Clifton-Everest
 *
 */
public class DungeonControllerLoader extends DungeonLoader {

    private List<ImageView> entities;
    

    //Images
    private Image playerImg;
    private Image wallImg;
    private Image enemyImg;
    private Image boulderImg;
    private Image bombUnlitImg;
    
	@SuppressWarnings("unused")
	private Image bombLit1Img;
	@SuppressWarnings("unused")
	private Image bombLit2Img;
	@SuppressWarnings("unused")
	private Image bombLit3Img;
    private Image bombExplodeImg;
    private Image swordImg;
    private Image keyImg;
    private Image treasureImg;
    private Image potionImg;
    private Image switchImg;
    private Image closedDoorImg;
    private Image openedDoorImg;
    private Image exitImg;
    private Image key_missing;
	private Image key_present;
	private Image sword_missing;
	private Image sword_present;
	private Image bomb_missing;
	private Image bomb_present;
	private ImageView key_missingIV;
	private ImageView key_presentIV;
	private ImageView sword_missingIV;
	private ImageView sword_presentIV;
	private ImageView bomb_missingIV;
	private ImageView bomb_presentIV;

	private DungeonController dungeonController;
	private HashMap<String, Node> UIitems;

    public DungeonControllerLoader(String filename)
            throws FileNotFoundException {
        super(filename);
        entities = new ArrayList<>();
        UIitems = new HashMap< String,Node>();
        playerImg = new Image("/human_new.png");
        wallImg = new Image("/brick_brown_0.png");
        enemyImg = new Image("/gnome.png");
        boulderImg = new Image("/boulder.png");
        bombUnlitImg = new Image("/bomb_unlit.png");
        bombLit1Img = new Image("/bomb_lit_1.png");
        bombLit2Img = new Image("/bomb_lit_2.png");
        bombLit3Img = new Image("/bomb_lit_3.png");
        bombExplodeImg = new Image("/bomb_lit_4.png");
     	swordImg = new Image("/greatsword_1_new.png");
     	keyImg = new Image("/key.png");
     	treasureImg = new Image("/gold_pile.png");
     	potionImg = new Image("/brilliant_blue_new.png");
     	switchImg = new Image("/pressure_plate.png");
     	closedDoorImg = new Image("/closed_door.png");
     	openedDoorImg = new Image("/open_door.png");
     	exitImg = new Image("/exit.png"); 
     	key_missing = new Image("/key_inventory_missing.png");
    	key_present = new Image("/key.png");
    	sword_missing = new Image("/greatsword_1_inventory_missing.png");
    	sword_present = new Image("/greatsword_1_new.png");
    	bomb_missing = new Image("/bomb_unlit_missing.png");
    	bomb_present = new Image("/bomb_unlit.png");
    	key_missingIV = new ImageView(key_missing);
    	key_presentIV = new ImageView(key_present);
    	sword_missingIV = new ImageView(sword_missing);
    	sword_presentIV = new ImageView(sword_present);
    	bomb_missingIV = new ImageView(bomb_missing);
    	bomb_presentIV = new ImageView(bomb_present);
//    	entities.add(key_missingIV);
//    	entities.add(key_presentIV);
//    	entities.add(sword_missingIV);
//    	entities.add(sword_presentIV);
//    	entities.add(bomb_missingIV);
//    	entities.add(bomb_presentIV);

    }

    @Override
    public void onLoad(Player player) {
        ImageView view = new ImageView(playerImg);
        addEntity(player, view);
    }

    @Override
    public void onLoad(Wall wall) {
        ImageView view = new ImageView(wallImg);
        addEntity(wall, view);
    }
    
	@Override
	public void onLoad(Enemy enemy) {
        ImageView view = new ImageView(enemyImg);
        addEntity(enemy, view);
	}
	@Override
	public void onLoad(Boulder boulder) {
        ImageView view = new ImageView(boulderImg);
        addEntity(boulder, view);
	}

	@Override
	public void onLoad(Bomb bomb) {
        ImageView view = new ImageView(bombUnlitImg);
        ImageView blow = new ImageView(bombExplodeImg);
        bomb.setImages(view, blow);
        addEntity(bomb, view);
	}

	@Override
	public void onLoad(Sword sword) {
        ImageView view = new ImageView(swordImg);
        addEntity(sword, view);
	}

	@Override
	public void onLoad(Key key) {
        ImageView view = new ImageView(keyImg);
        addEntity(key, view);
	}

	@Override
	public void onLoad(Treasure treasure) {
        ImageView view = new ImageView(treasureImg);
        addEntity(treasure, view);
	}

	@Override
	public void onLoad(Potion potion) {
        ImageView view = new ImageView(potionImg);
        addEntity(potion, view);
	}

	@Override
	public void onLoad(FloorSwitch floorswitch) {
        ImageView view = new ImageView(switchImg);
        addEntity(floorswitch, view);
	}

	@Override
	public void onLoad(Door door) {
        ImageView view = new ImageView(closedDoorImg);
        ImageView unlock = new ImageView(openedDoorImg); 
        door.setViews(view, unlock);
        addEntity(door, view);
	}
	
	@Override
	public void onLoad(Exit exit) {
        ImageView view = new ImageView(exitImg);
        addEntity(exit, view);
	}
	
    private void addEntity(Entity entity, ImageView view) {
        trackPosition(entity, view);
        entity.setIv(view);
        entities.add(view);
    }
    public void removeEntity() {
    }

    public void initialiseInventoryUIscreen() {
    	GridPane.setRowIndex(key_missingIV, dungeonController.getDungeon().getHeight()+2);
    	
    	GridPane.setColumnIndex(key_missingIV, 0);
    	UIitems.put("key_missing", key_missingIV);
    	
    	GridPane.setRowIndex(sword_missingIV, dungeonController.getDungeon().getHeight()+2);
    	GridPane.setColumnIndex(sword_missingIV, 1);
    	UIitems.put("sword_missing", sword_missingIV);

		Label swordCountUI = new Label();
		GridPane.setRowIndex(swordCountUI, dungeonController.getDungeon().getHeight()+2);
    	GridPane.setColumnIndex(swordCountUI, 2);
    	UIitems.put("sword_count", swordCountUI);


    	GridPane.setRowIndex(bomb_missingIV, dungeonController.getDungeon().getHeight()+2);
    	GridPane.setColumnIndex(bomb_missingIV, 3);
    	UIitems.put("bomb_missing", bomb_missingIV);
    	
		Label bombCountUI = new Label();
		GridPane.setRowIndex(bombCountUI, dungeonController.getDungeon().getHeight()+2);
    	GridPane.setColumnIndex(bombCountUI, 4);
    	UIitems.put("bomb_count", bombCountUI);
    	
    	

    }
    
    /**
     * Set a node in a GridPane to have its position track the position of an
     * entity in the dungeon.
     *
     * By connecting the model with the view in this way, the model requires no
     * knowledge of the view and changes to the position of entities in the
     * model will automatically be reflected in the view.
     * @param entity - Entity to track
     * @param node - Node
     */
    private void trackPosition(Entity entity, Node node) {
        GridPane.setColumnIndex(node, entity.getX());
        GridPane.setRowIndex(node, entity.getY());
        entity.x().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                GridPane.setColumnIndex(node, newValue.intValue());
            }
        });
        entity.y().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                GridPane.setRowIndex(node, newValue.intValue());
            }
        });
    }

    /**
     * Create a controller that can be attached to the DungeonView with all the
     * loaded entities.
     * @return DungeonController
     * @throws FileNotFoundException - if file is not JSON available for reading
     */
    public DungeonController loadController() throws FileNotFoundException {
//    	System.out.println("Creating dungeon controller with UIitems "+UIitems);
    	Dungeon ourDungeon = load();
    	this.dungeonController = new DungeonController(ourDungeon, entities, UIitems);
    	initialiseInventoryUIscreen();
    	Inventory playerInventory = ourDungeon.getInventory();
//    	System.out.println(playerInventory.getSwordHealth().asString());
    	((Label)UIitems.get("sword_count")).textProperty().bind(playerInventory.getSwordHealth().asString());
    	((Label)UIitems.get("bomb_count")).textProperty().bind(playerInventory.getBombsCount().asString());
    	playerInventory.getHasBomb().addListener(new ChangeListener<Boolean>() {
    		@Override
    		public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
    			if(newValue == true) {
    				dungeonController.removeNodeFromView(bomb_missingIV);
    				dungeonController.addNodeToView(bomb_presentIV, 3, dungeonController.getDungeon().getHeight()+2);
    				
    			}
    			else {
    				dungeonController.removeNodeFromView(bomb_presentIV);
    				dungeonController.addNodeToView(bomb_missingIV, 3, dungeonController.getDungeon().getHeight()+2);
    			}
    		}
    	});
    	
    	playerInventory.getHasKey().addListener(new ChangeListener<Boolean>() {
    		@Override
    		public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
    			if(newValue == true) {
    				dungeonController.removeNodeFromView(key_missingIV);
    				dungeonController.addNodeToView(key_presentIV, 3, dungeonController.getDungeon().getHeight()+2);
    				
    			}
    			else {
    				dungeonController.removeNodeFromView(key_presentIV);
    				dungeonController.addNodeToView(key_missingIV, 3, dungeonController.getDungeon().getHeight()+2);
    			}
    		}
    	});
    	
        return this.dungeonController;
    }

	@Override
	public void onLoad(GoalComponent goal) {
		// TODO Auto-generated method stub
		
	}
}
