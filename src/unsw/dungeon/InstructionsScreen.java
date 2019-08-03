
package unsw.dungeon;

import java.io.IOException;

import javafx.stage.Stage;


public class InstructionsScreen extends Screen {

	public InstructionsScreen(Stage stage, InstructionsPageController controller) throws IOException{
		super(stage, controller, "instructionsScreen.fxml");

//		this.stage = stage;
//		this.controller = controller;
//		
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("instructionsScreen.fxml"));
//        loader.setController(this.controller);
//        Parent root = loader.load();
//        scene = new Scene(root);
		
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

