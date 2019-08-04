package unsw.dungeon;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

public class EndScreenController implements Controller {

    @FXML
    private Label winLossMessage;
    
    @FXML
    private BorderPane rootPane;
    
    private DungeonScreen dungeonScreen;
    
    private StartScreen startScreen;
    

    
    @FXML
    void handleMainMenu(ActionEvent event) {
    	makeFadeOut(startScreen);
    }

    
    
    @FXML
    void handlePlayAgain(ActionEvent event) {
    	makeFadeOut(dungeonScreen);
    }
    
    /**
     * Set the dungeon screen, so we can swap to it after a user selected "Play again"
     * @param dungeonScreen
     */
	public void setDungeonScreen(DungeonScreen dungeonScreen) {
		this.dungeonScreen = dungeonScreen;
	}

	/**
     * Set the dungeon screen, so we can swap to it after a user selected "Main Menu"
     * @param dungeonScreen
     */
	public void setStartScreen(StartScreen startScreen) {
		this.startScreen = startScreen;
	}

	@FXML
    public void initialize() {
    	rootPane.setOpacity(0);
    	makeFadeIn();
    }
    
	/**
	 * Fade the screen in and reset opacity so the screen is viewable
	 */
	public void resetForViewing() {
    	makeFadeIn();
    }
	
	/**
	 * Stores animation for fade in
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
	 * Fades this screen out and transition to next screen
	 * @param destination - next screen to transition to
	 */
	private void makeFadeOut(Screen destination) {
		FadeTransition fadeTransition = new FadeTransition();
		fadeTransition.setDuration(Duration.millis(1000));
		fadeTransition.setNode(rootPane);
		fadeTransition.setFromValue(1);
		fadeTransition.setToValue(0);
		fadeTransition.setOnFinished((ActionEvent event) -> {
			destination.start();
		});
		fadeTransition.play();	
	}


	/**
	 * Sets the message to display depending on whether the game was a win or less
	 * @param value - win or loss boolean
	 */
	public void setMessage(boolean value) {
		if(value == false) {
			winLossMessage.setText("You lost!!");
		}
		else {
			winLossMessage.setText("You win!!");
		}
		
	}
    
}
