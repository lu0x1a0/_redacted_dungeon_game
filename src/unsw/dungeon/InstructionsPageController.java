package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.util.Duration;


public class InstructionsPageController implements Controller {

    @FXML
    private Label swordInstructions;

    @FXML
    private Label BombInstructions;

    @FXML
    private Label arrowKeysInstructions;

    @FXML
    private Pane rootPane;
    
    private ArrayList<Label> labelsToLoad;
    
    private StartScreen startScreen;
    
    
    
    public void setStartScreen(StartScreen startScreen) {
		this.startScreen = startScreen;
	}

	@FXML
    public void initialize() {
    	labelsToLoad = new ArrayList<Label>();
    	labelsToLoad.add(swordInstructions);
    	labelsToLoad.add(BombInstructions);
    	labelsToLoad.add(arrowKeysInstructions);
    	
    	rootPane.setOpacity(0);
    }
    
    @FXML
    void handleBack(ActionEvent event) {
    	makeFadeOut();
    }
	/**
	 * fade in the instruction scene
	 */
    public void resetForViewing() {
    	makeFadeIn();
    	for (Label l: labelsToLoad) {
    		l.setOpacity(0);
    	}
    }
    /**
     * actual fade in procedure
     */
	private void makeFadeIn() {
		FadeTransition fadeTransition = new FadeTransition();
		fadeTransition.setDuration(Duration.millis(2000));
		fadeTransition.setNode(rootPane);
		fadeTransition.setFromValue(0);
		fadeTransition.setToValue(1);
		fadeTransition.setOnFinished((ActionEvent event) -> {
			if(labelsToLoad.size() == 1) return;
			loadInstructions(labelsToLoad);
			
		});
		fadeTransition.play();			
	}
    /**
     * display labels into the scene
     * @param labelsToLoad
     */
	private void loadInstructions(List<Label> labelsToLoad) {
		FadeTransition fadeTransition = new FadeTransition();
		fadeTransition.setDuration(Duration.millis(1000));
		fadeTransition.setNode(labelsToLoad.get(0));
		fadeTransition.setFromValue(0);
		fadeTransition.setToValue(1);
		fadeTransition.setOnFinished((ActionEvent event) -> {
			if(labelsToLoad.size() == 1) return;
			loadInstructions(labelsToLoad.subList(1, labelsToLoad.size()));
			
		});
		fadeTransition.play();
	}
	/**
	 * fade out the scene
	 */
	private void makeFadeOut() {
		FadeTransition fadeTransition = new FadeTransition();
		fadeTransition.setDuration(Duration.millis(1000));
		fadeTransition.setNode(rootPane);
		fadeTransition.setFromValue(1);
		fadeTransition.setToValue(0);
		fadeTransition.setOnFinished((ActionEvent event) -> {
			startScreen.start();
		});
		fadeTransition.play();	
	}


    
	
	
}