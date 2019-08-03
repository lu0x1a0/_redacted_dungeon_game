package unsw.dungeon;

import java.io.IOException;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.util.Duration;

public class StartScreenController implements Controller {

	
	
	
    @FXML
    private Button easyButton;

    @FXML
    private Button mediumButton;

    @FXML
    private Button hardButton;
    
    private DungeonScreen dungeonScreen;
    
    private Screen instructionsScreen;

	public void setInstructionsScreen(Screen instructionsScreen) {
		this.instructionsScreen = instructionsScreen;
	}

	@FXML
	private Parent rootPane;
    

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

			instructionsScreen.start();
			
			
		});
		fadeTransition.play();	
	}

    
}
	
	
	
	
	
	
    