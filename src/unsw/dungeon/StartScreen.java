package unsw.dungeon;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class StartScreen {

	private Stage stage;
	private StartScreenController controller;
	private Scene scene;
	
	public StartScreen(Stage stage, StartScreenController controller) throws IOException{
		this.stage = stage;
		this.controller = controller;
		
        FXMLLoader loader = new FXMLLoader(getClass().getResource("StartScreen.fxml"));
        loader.setController(this.controller);
        Parent root = loader.load();
        scene = new Scene(root);
		
	}

	public void start() {
        stage.setScene(scene);
        controller.resetForViewing();
        stage.show();
    }

    public StartScreenController getController() {
        return controller;
    }

	
}
