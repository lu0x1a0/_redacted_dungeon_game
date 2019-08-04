package unsw.dungeon;

import java.io.IOException;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.util.Duration;

public class StartScreenController implements Controller {
    
    private DungeonScreen dungeonScreen;
    
    private Screen instructionsScreen;

    /**
     * Setter for instructions screen to which to transition to if the player clicks viewInstructions button
     * @param instructionsScreen
     */
	public void setInstructionsScreen(Screen instructionsScreen) {
		this.instructionsScreen = instructionsScreen;
	}

	@FXML
	private Parent rootPane;
    

	/**
	 * Starts the game with particular difficulty based on what button was clicked on by user
	 * @param event - event triggered when the button was pressed
	 * @throws IOException - if the FXML file for the dungeon screen is missing
	 */
    @FXML
    void startGame(ActionEvent event) throws IOException {
    	switch (((Button)event.getSource()).getText()) {
    	case "Easy":
    		dungeonScreen.start("Easy");
    		break;
    	case "Medium":
    		dungeonScreen.start("Medium");
    		break;
    	case "Hard":
    		dungeonScreen.start("Hard");
    		break;
    		
    	
    	}
    }
    
    /**
     * Setter for dungeon screen to proceed to upon clicking start game
     * @param dungeonScreen
     */
	public void setDungeonScreen(DungeonScreen dungeonScreen) {
		this.dungeonScreen = dungeonScreen;
	}

    
    @FXML
    void instructionsHandle(ActionEvent event) {
		makeFadeOut();

    }

    @FXML
    public void initialize() {
    	rootPane.setOpacity(0);
    	makeFadeIn();
    }

    /**
     * reset the screen for viewing by setting opacity etc
     */
    public void resetForViewing() {
    	makeFadeIn();
    }
    
    /**
     * Function for the screen to fade in with anamation
     */
	private void makeFadeIn() {
		FadeTransition fadeTransition = new FadeTransition();
		fadeTransition.setDuration(Duration.millis(2000));
		fadeTransition.setNode(rootPane);
		fadeTransition.setFromValue(0);
		fadeTransition.setToValue(1);
		fadeTransition.play();	
	}

	/** 
	 * Function to fade out and transition instructions screen 
	 */
	private void makeFadeOut() {
		FadeTransition fadeTransition = new FadeTransition();
		fadeTransition.setDuration(Duration.millis(1000));
		fadeTransition.setNode(rootPane);
		fadeTransition.setFromValue(1);
		fadeTransition.setToValue(0);
		fadeTransition.setOnFinished((ActionEvent event) -> {
			instructionsScreen.start();
			});
		fadeTransition.play();	
	}

    
}
	
	
	
	
	
	
    