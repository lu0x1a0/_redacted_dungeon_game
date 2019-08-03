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

	public void setDungeonScreen(DungeonScreen dungeonScreen) {
		this.dungeonScreen = dungeonScreen;
	}

	public void setStartScreen(StartScreen startScreen) {
		this.startScreen = startScreen;
	}

	@FXML
    public void initialize() {
    	rootPane.setOpacity(0);
    	makeFadeIn();
    }
    
	public void resetForViewing() {
    	makeFadeIn();
    }
	
	private void makeFadeIn() {
		FadeTransition fadeTransition = new FadeTransition();
		fadeTransition.setDuration(Duration.millis(2000));
		fadeTransition.setNode(rootPane);
		fadeTransition.setFromValue(0);
		fadeTransition.setToValue(1);
		fadeTransition.play();	
	}

	private void makeFadeOut(Screen destination) {
		FadeTransition fadeTransition = new FadeTransition();
		fadeTransition.setDuration(Duration.millis(1000));
		fadeTransition.setNode(rootPane);
		fadeTransition.setFromValue(1);
		fadeTransition.setToValue(0);
		fadeTransition.setOnFinished((ActionEvent event) -> {
//			destination.
			destination.start();
			
			
		});
		fadeTransition.play();	
	}



	public void setMessage(boolean value) {
		if(value == false) {
			winLossMessage.setText("You lost!!");
		}
		else {
			winLossMessage.setText("You win!!");
		}
		
	}
    
}
