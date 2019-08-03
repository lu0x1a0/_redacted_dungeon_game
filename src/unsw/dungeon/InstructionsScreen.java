
package unsw.dungeon;

import java.io.IOException;

import javafx.stage.Stage;


public class InstructionsScreen extends Screen {

	public InstructionsScreen(Stage stage, InstructionsPageController controller) throws IOException{
		super(stage, controller, "instructionsScreen.fxml");
	}
	@Override
	public void start() {
		((InstructionsPageController) controller).resetForViewing();
	    super.start();
	}
	
	public InstructionsPageController getController() {
		return (InstructionsPageController) controller;
	}
	
	
	
}

