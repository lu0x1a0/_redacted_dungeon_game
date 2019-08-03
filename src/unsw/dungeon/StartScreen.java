package unsw.dungeon;

import java.io.IOException;

import javafx.stage.Stage;


public class StartScreen extends Screen {

	public StartScreen(Stage stage, StartScreenController controller) throws IOException{
		super(stage, controller, "StartScreen.fxml");		
	}

	public void start() {
        ((StartScreenController) controller).resetForViewing();
        super.start();
    }

    public StartScreenController getController() {
        return (StartScreenController) controller;
    }

	
}
