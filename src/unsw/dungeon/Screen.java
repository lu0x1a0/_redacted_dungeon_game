package unsw.dungeon;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class Screen  {

	protected Stage stage;
	protected Scene scene;
	protected Controller controller;

	
	public Screen() {
		super();
	}
	
	public Screen(Stage stage, Controller controller, String loaderName) throws IOException {
		super();
		this.controller = controller;
		this.stage = stage;
		FXMLLoader loader = new FXMLLoader(getClass().getResource(loaderName));
		loader.setController(this.controller);
        
        Parent root = loader.load();
        scene = new Scene(root);
		
	}

	public void start() {
		stage.setScene(scene);
        scene.getRoot().requestFocus();
        stage.show();
	}

	public Controller getController() {
	    return getController();
	}

}