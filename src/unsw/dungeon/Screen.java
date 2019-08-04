package unsw.dungeon;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Abstract class for screens, contains common variables and methods
 * @author compu
 *
 */
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

	/**
	 * Starts and displays the screen
	 */
	public void start() {
		stage.setScene(scene);
        scene.getRoot().requestFocus();
        stage.show();
	}

	/**
	 * Returns the controller for this screen
	 * @return
	 */
	public Controller getController() {
	    return getController();
	}

}