package unsw.dungeon;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class DungeonScreen {

	private Stage stage;
	private DungeonController controller;
	private Scene scene;
	
	public DungeonScreen(Stage stage, DungeonController controller) throws IOException{
		this.stage = stage;
		this.controller = controller;
		
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DungeonView.fxml"));

        loader.setController(this.controller);
        
        Parent root = loader.load();
        scene = new Scene(root);
		
	}

	public void start(String difficulty) {
        stage.setScene(scene);
        scene.getRoot().requestFocus();
        stage.show();
        controller.start(difficulty);
    }

    public DungeonController getController() {
        return controller;
    }

	
}
