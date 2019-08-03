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
    	makeFadeOut();
    }

    @FXML
    void handlePlayAgain(ActionEvent event) {
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
