package unsw.dungeon;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class EndScreen {

	private Stage stage;
	private EndScreenController controller;
	private Scene scene;
	
	public EndScreen(Stage stage, EndScreenController controller) throws IOException{
		this.stage = stage;
		this.controller = controller;
		
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EndScreen.fxml"));

        loader.setController(this.controller);
        
        Parent root = loader.load();
        scene = new Scene(root);
		
	}

	public void start() {
        stage.setScene(scene);
        scene.getRoot().requestFocus();
        stage.show();
    }

    public EndScreenController getController() {
        return controller;
    }

	
}