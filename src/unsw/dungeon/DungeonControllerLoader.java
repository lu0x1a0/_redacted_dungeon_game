package unsw.dungeon;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

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
    
	private Image bombLit1Img;
	private Image bombLit2Img;
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
	private Image treasure_missing;
	private Image treasure_present;
	private Image potion_inactive;
	private Image potion_active;
	
	private ImageView key_missingIV;
	private ImageView key_presentIV;
	private ImageView sword_missingIV;
	private ImageView sword_presentIV;
	private ImageView bomb_missingIV;
	private ImageView bomb_presentIV;
	private ImageView treasure_missingIV;
	private ImageView treasure_presentIV;
	private ImageView potion_inactiveIV;
	private ImageView potion_activeIV;
	
	private DungeonController dungeonController;


	private HashMap<Entity, Timeline> timelines;
	
	private HashMap<String, Node> UIitems;


    public DungeonControllerLoader(String filename)
            throws FileNotFoundException {
        super(filename);
        entities = new ArrayList<>();
        UIitems = new HashMap<String,Node>();

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

     	exitImg = new Image("/exit.png"); 
     	treasure_present = new Image("/gold_pile.png");
     	treasure_missing = new Image("/gold_pile_missing.png");
     	key_missing = new Image("/key_inventory_missing.png");
    	key_present = new Image("/key.png");
    	sword_missing = new Image("/greatsword_1_inventory_missing.png");
    	sword_present = new Image("/greatsword_1_new.png");
    	bomb_missing = new Image("/bomb_unlit_missing.png");
    	bomb_present = new Image("/bomb_unlit.png");
    	potion_inactive = new Image("/potion_empty.png");
    	potion_active = new Image("/brilliant_blue_new.png");
    	
    	key_missingIV = new ImageView(key_missing);
    	key_presentIV = new ImageView(key_present);
    	sword_missingIV = new ImageView(sword_missing);
    	sword_presentIV = new ImageView(sword_present);
    	bomb_missingIV = new ImageView(bomb_missing);
    	bomb_presentIV = new ImageView(bomb_present);
    	treasure_missingIV = new ImageView(treasure_missing);
    	treasure_presentIV = new ImageView(treasure_present);
    	potion_inactiveIV = new ImageView(potion_inactive);
    	potion_activeIV = new ImageView(potion_active);
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
        ImageView lit1 = new ImageView(bombLit1Img);
        ImageView lit2 = new ImageView(bombLit2Img);
        ImageView lit3 = new ImageView(bombLit3Img);
        bomb.setImages(view, lit1, lit2, lit3, blow);
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
    	
    	GridPane.setRowIndex(treasure_missingIV, dungeonController.getDungeon().getHeight()+2);
    	GridPane.setColumnIndex(treasure_missingIV, 5);
    	UIitems.put("treasure_missing", treasure_missingIV);
    	
		Label treasureCountUI = new Label();
		GridPane.setRowIndex(treasureCountUI, dungeonController.getDungeon().getHeight()+2);
    	GridPane.setColumnIndex(treasureCountUI, 6);
    	UIitems.put("treasure_count", treasureCountUI);
    	
    	GridPane.setRowIndex(potion_inactiveIV, dungeonController.getDungeon().getHeight()+2);
    	GridPane.setColumnIndex(potion_inactiveIV, 7);
    	UIitems.put("potion", potion_inactiveIV);
    	
		Label potionTimeUI = new Label();
		GridPane.setRowIndex(potionTimeUI, dungeonController.getDungeon().getHeight()+2);
    	GridPane.setColumnIndex(potionTimeUI, 8);
    	UIitems.put("potion_time", potionTimeUI);
    	
    }
    

    /**
     * Set a node in a GridPane to have its position track the position of an
     * entity in the dungeon.
     *
     * By connecting the model with the view in this way, the model requires no
     * knowledge of the view and changes to the position of entities_img in the
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


    public void endGame(Boolean result) {
    	
    }

    
    /**
     * Create a controller that can be attached to the DungeonView with all the
     * loaded entities.
     * @return DungeonController
     * @throws FileNotFoundException - if file is not JSON available for reading
     */
    public DungeonController loadController() throws FileNotFoundException {
    	Dungeon ourDungeon = load();
    	this.dungeonController = new DungeonController(ourDungeon, entities, UIitems);
    	entities.clear();
    	UIitems.clear();
    	initialiseInventoryUIscreen();
    	Inventory playerInventory = ourDungeon.getInventory();
    	((Label)UIitems.get("sword_count")).textProperty().bind(playerInventory.getSwordHealth().asString());
    	((Label)UIitems.get("bomb_count")).textProperty().bind(playerInventory.getBombsCount().asString());
    	((Label)UIitems.get("treasure_count")).textProperty().bind(playerInventory.getTreasureCount().asString());
    	((Label)UIitems.get("potion_time")).textProperty().bind(playerInventory.getPotionTimeLeft().asString());
    	
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
    	
    	playerInventory.getHasSword().addListener(new ChangeListener<Boolean>() {
    		@Override
    		public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
    			if(newValue == true) {
    				dungeonController.removeNodeFromView(sword_missingIV);
    				dungeonController.addNodeToView(sword_presentIV, 1, dungeonController.getDungeon().getHeight()+2);
    			}
    			else {
    				dungeonController.removeNodeFromView(sword_presentIV);
    				dungeonController.addNodeToView(sword_missingIV, 1, dungeonController.getDungeon().getHeight()+2);
    			}
    		}
    	});
    	
    	
    	
    	
    	playerInventory.getHasKey().addListener(new ChangeListener<Boolean>() {
    		@Override
    		public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
    			if(newValue == true) {
    				dungeonController.removeNodeFromView(key_missingIV);
    				dungeonController.addNodeToView(key_presentIV, 0, dungeonController.getDungeon().getHeight()+2);
    				
    			}
    			else {
    				dungeonController.removeNodeFromView(key_presentIV);
    				dungeonController.addNodeToView(key_missingIV, 0, dungeonController.getDungeon().getHeight()+2);
    			}
    		}
    	});
    	
    	playerInventory.getHasTreasure().addListener(new ChangeListener<Boolean>() {
    		@Override
    		public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
    			if(newValue == true) {
    				dungeonController.removeNodeFromView(treasure_missingIV);
    				dungeonController.addNodeToView(treasure_presentIV, 5, dungeonController.getDungeon().getHeight()+2);
    				
    			}
    			else {
    				dungeonController.removeNodeFromView(treasure_presentIV);
    				dungeonController.addNodeToView(bomb_missingIV, 5, dungeonController.getDungeon().getHeight()+2);
    			}
    		}
    	});
    	
    	playerInventory.getHasPotion().addListener(new ChangeListener<Boolean>() {
    		@Override
    		public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
    			if(newValue == true) {
    				dungeonController.removeNodeFromView(potion_inactiveIV);
    				dungeonController.addNodeToView(potion_activeIV, 7, dungeonController.getDungeon().getHeight()+2);
    			}
    			else {
    				dungeonController.removeNodeFromView(potion_activeIV);
    				dungeonController.addNodeToView(potion_inactiveIV, 7, dungeonController.getDungeon().getHeight()+2);
    			}
    		}
    	});
    	
        return this.dungeonController;
    }
    public DungeonController getController() {
    	return this.dungeonController;
    }
    
	@Override
	public void onLoad(GoalComponent goal) {
		
	}
}
