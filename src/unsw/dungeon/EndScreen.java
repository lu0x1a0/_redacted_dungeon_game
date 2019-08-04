package unsw.dungeon;

import java.io.IOException;
import javafx.stage.Stage;

/**
 * Wrapper class for the EndScreen that contains the FXML and controller.
 * The screen is called from here
 * @author compu
 *
 */
public class EndScreen extends Screen {

	/**
	 * constructor for the screen when a dungeon game finishes
	 * @param stage - takes stage that the game will play on
	 * @param controller - takes the controller for this screen
	 * @throws IOException - Throws error when the coresponding FXML file is missing
	 */
	public EndScreen(Stage stage, EndScreenController controller) throws IOException{
		super(stage, controller, "EndScreen.fxml");
	}
	/**
	 * start off all necessary operation in this screen
	 * @param value  indication of the result of the game
	 */
	public void start(boolean value) {
		controller.initialize();
		((EndScreenController) controller).setMessage(value);
		super.start();
    }
		
	/**
	 * return the controller of this screen
	 */
    public EndScreenController getController() {
        return (EndScreenController) controller;
    }

	
}