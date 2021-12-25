package unsw.dungeon;

import java.io.IOException;

import javafx.stage.Stage;

/**
 * Intial screen the user sees upon running the game
 * @author compu
 *
 */
public class StartScreen extends Screen {

	public StartScreen(Stage stage, StartScreenController controller) throws IOException{
		super(stage, controller, "StartScreen.fxml");		
	}

	/**
	 * Method to start the screen and display it
	 */
	public void start() {
        ((StartScreenController) controller).resetForViewing();
        super.start();
    }

	/**
	 * getter for this screen's controller
	 */
    public StartScreenController getController() {
        return (StartScreenController) controller;
    }

	
}
