package unsw.dungeon;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
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

	private DungeonController dungeonController;


    public DungeonControllerLoader(String filename)
            throws FileNotFoundException {
        super(filename);
        entities = new ArrayList<>();
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

    /**
     * Set a node in a GridPane to have its position track the position of an
     * entity in the dungeon.
     *
     * By connecting the model with the view in this way, the model requires no
     * knowledge of the view and changes to the position of entities in the
     * model will automatically be reflected in the view.
     * @param entity
     * @param node
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
     * @return
     * @throws FileNotFoundException
     */
    public DungeonController loadController() throws FileNotFoundException {
    	this.dungeonController = new DungeonController(load(), entities);
        return this.dungeonController;
    }

	@Override
	public void onLoad(GoalComponent goal) {
		// TODO Auto-generated method stub
		
	}
}
