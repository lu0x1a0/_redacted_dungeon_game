package unsw.dungeon;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class EndScreen extends Screen {

	
	public EndScreen(Stage stage, EndScreenController controller) throws IOException{
		super(stage, controller, "EndScreen.fxml");
			
	}

	public void start(boolean value) {
		controller.initialize();
		((EndScreenController) controller).setMessage(value);
		super.start();
    }
		

    public EndScreenController getController() {
        return (EndScreenController) controller;
    }

	
}