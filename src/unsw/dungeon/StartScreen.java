package unsw.dungeon;

import java.io.IOException;

import javafx.stage.Stage;


public class StartScreen extends Screen {

	public StartScreen(Stage stage, StartScreenController controller) throws IOException{
		super(stage, controller, "StartScreen.fxml");

//		this.stage = stage;
//		this.controller = controller;
//		
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("StartScreen.fxml"));
//        loader.setController(this.controller);
//        Parent root = loader.load();
//        scene = new Scene(root);
		
	}

	public void start() {
        ((StartScreenController) controller).resetForViewing();
        super.start();
    }

    public StartScreenController getController() {
        return (StartScreenController) controller;
    }

	
}
