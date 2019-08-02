
package unsw.dungeon;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class InstructionsScreen {

	private Stage stage;
	private InstructionsPageController controller;
	private Scene scene;
	
	public InstructionsScreen(Stage stage, InstructionsPageController controller) throws IOException{
		this.stage = stage;
		this.controller = controller;
		
        FXMLLoader loader = new FXMLLoader(getClass().getResource("instructionsScreen.fxml"));
        loader.setController(this.controller);
        Parent root = loader.load();
        scene = new Scene(root);
		
	}

	public void start() {
		controller.resetForViewing();
        stage.setScene(scene);
        stage.show();
    }

    public InstructionsPageController getController() {
        return controller;
    }

	
}

