package unsw.dungeon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

/**
 * A JavaFX controller for the dungeon.
 * @author Robert Clifton-Everest
 *
 */
public class DungeonController implements Controller {


    @FXML
    public GridPane squares;

    private StartScreen startScreen;
    
    private EndScreen endScreen;

	private List<ImageView> initialEntities;
    private HashMap<String, Node> UIitems;

    private Player player;

    private Dungeon dungeon;


    private HashMap<Entity,Timeline> timelines;
    
    
    private ArrayList<Entity> entities;

    public DungeonController(Dungeon dungeon, List<ImageView> initialEntities, HashMap<String, Node> UIitems) {

        this.dungeon = dungeon;
        this.player = dungeon.getPlayer();
        this.UIitems = UIitems;
        this.initialEntities = new ArrayList<>(initialEntities);
        this.entities = dungeon.getEntities();
        this.timelines = new HashMap<Entity,Timeline>();
    }
    public Dungeon getDungeon() {
    	return dungeon;
    }
    
    public StartScreen getStartScreen() {
		return startScreen;
	}
	public void setEndScreen(EndScreen endScreen) {
    	this.endScreen = endScreen;
    }
    
    public EndScreen getEndScreen() {
		return endScreen;
	}
	public void setStartScreen(StartScreen startScreen) {
		this.startScreen = startScreen;
	}
	public void giveDungeonMyself() {
    	dungeon.setController(this);
    }
	
    public void removeEntityFromView(ImageView v) {
    	squares.getChildren().remove(v);
    }
    public void removeNodeFromView(Node v) {
    	squares.getChildren().remove(v);
    }
    public void addEntityToView(ImageView v, int x, int y) {
    	squares.add(v, x, y);
    }
    public void changeEntityImage(Entity e, ImageView oldv) {
    	removeEntityFromView(oldv);
    	addEntityToView(e.getIv(),e.getX(),e.getY());
    }
    
    public void addNodeToView(Node v, int x, int y) {
    	squares.add(v, x, y);
    }
    
    
    @Override
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
        for(Entity entity: entities) {
        	if(entity instanceof Bomb) {
        		timelines.put(entity, createTimeline((Bomb) entity));
        	}else if(entity instanceof Potion){
        		timelines.put(entity, createTimeline((Potion) entity));
        	}
    	}
        		

        
        for (Node item : UIitems.values())
            squares.getChildren().add(item);
        

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
    private Timeline createTimeline(Bomb bomb) {
    	GridPane grid = this.squares;
   	 	Timeline timeline = new Timeline();
		timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1),new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				bomb.lit2();
			}
		
		}));
		timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(2),new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				bomb.lit3();
			}
		
		}));
		timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(3),new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					bomb.notifyObservers(bomb, "explode");
					bomb.explode();
				}
    		
		}));
		timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(4),new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				bomb.removeFromView();
				bomb.setIv(null);
			}
		
		}));
		bomb.getIslit().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable,
                    Boolean oldValue, Boolean newValue) {
            	if(newValue) {
            		timelines.get(bomb).play();
            	}else {
            		timelines.get(bomb).pause();            		
            	}
            }
        });
		timeline.setOnFinished((ActionEvent event) -> {
			bomb.removeFromView();
		});
		timeline.setCycleCount(1);
		return timeline;
    }
    private Timeline createTimeline(Potion p) {
   	 	Timeline timeline = new Timeline();
    	KeyFrame ticking = new KeyFrame(Duration.seconds(1), e-> updateUIPotionTime());
    	timeline.setCycleCount(p.getTime());
    	timeline.getKeyFrames().add(ticking);
    	timeline.setOnFinished(e->p.wearoff());
    	p.getOnUse().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable,
                    Boolean oldValue, Boolean newValue) {
            	if(newValue) {
            		timelines.get(p).play();
            	}else {
            		timelines.get(p).pause();            		
            	}
            }
        });

		return timeline;
    }
    
    public void updateUIPotionTime() {
    	dungeon.getInventory().getPotionTimeLeft().set(
    			dungeon.getInventory().getPotionTimeLeft().get()-1
		);
    }

	public void start(String difficulty) {
    	dungeon.startEnemies(difficulty);
    }

	public void endGame(boolean b) {
		squares.getChildren().clear();
		initialEntities.clear();
		endScreen.start();
		
	}
	public GridPane getSquares() {
		return squares;
	}
	
}

