package unsw.dungeon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

/**
 * A JavaFX controller for the dungeon.
 * @author Robert Clifton-Everest
 *
 */
public class DungeonController {

    @FXML
    public GridPane squares;

    private StartScreen startScreen;
    
    private EndScreen endScreen;

	private List<ImageView> initialEntities;
    private HashMap<String, Node> UIitems;

    private Player player;

    private Dungeon dungeon;

    public DungeonController(Dungeon dungeon, List<ImageView> initialEntities, HashMap<String, Node> UIitems) {
        this.dungeon = dungeon;
        this.player = dungeon.getPlayer();
        this.UIitems = UIitems;
        this.initialEntities = new ArrayList<>(initialEntities);
    }
    public Dungeon getDungeon() {
    	return dungeon;
    }
    
    public void setEndScreen(EndScreen endScreen) {
    	this.endScreen = endScreen;
    }
    
    public void setStartScreen(StartScreen startScreen) {
		this.startScreen = startScreen;
	}
	public void giveDungeonMyself() {
    	dungeon.setController(this);
    }
	
    public void removeEntityFromView(ImageView v) {
    	//initialEntities.remove(v);
    	squares.getChildren().remove(v);
    }
    public void removeNodeFromView(Node v) {
    	squares.getChildren().remove(v);
    }
    public void addEntityToView(ImageView v, int x, int y) {
    	//squares.getChildren().add(v, 3, 4);
    	squares.add(v, x, y);
    }
    public void changeEntityImage(Entity e, ImageView oldv) {
    	removeEntityFromView(oldv);
    	addEntityToView(e.getIv(),e.getX(),e.getY());
    }
    
    public void addNodeToView(Node v, int x, int y) {
    	//squares.getChildren().add(v, 3, 4);
    	squares.add(v, x, y);
    }
    
    
    @FXML
    public void initialize() {
        Image ground = new Image("/dirt_0_new.png");

        // Add the ground first so it is below all other entities
        for (int x = 0; x < dungeon.getWidth(); x++) {
            for (int y = 0; y < dungeon.getHeight(); y++) {
                squares.add(new ImageView(ground), x, y);
            }
        }

        for (ImageView entity : initialEntities)
            squares.getChildren().add(entity);
        
        for (Node item : UIitems.values())
            squares.getChildren().add(item);
        

    }

    public GridPane getSquares() {
		return squares;
	}
	@FXML
    public void handleKeyPress(KeyEvent event) {
    	switch (event.getCode()) {
        case UP:
            player.moveUp();
            break;
        case DOWN:
            player.moveDown();
            break;
        case LEFT:
            player.moveLeft();
            break;
        case RIGHT:
            player.moveRight();
            break;
        case Q:
        	player.waveSword();
        	break;
        case E:
        	player.litBomb();
        	break;
        default:
            break;
        }    
	}
    public void trackPosition(Entity entity, Node node) {
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
    public void start(String difficulty) {
    	
    	dungeon.startEnemies(difficulty);
    }
	public void endGame(boolean b) {
		// TODO Auto-generated method stub
		endScreen.start();
		
	}
    
}

