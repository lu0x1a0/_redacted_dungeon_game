package unsw.dungeon;

import java.io.IOException;

import javafx.stage.Stage;


public class EndScreen extends Screen {

	public EndScreen(Stage stage, EndScreenController controller) throws IOException{
		super(stage, controller, "EndScreen.fxml");
		
//		this.stage = stage;
//		this.controller = controller;
//		
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("EndScreen.fxml"));

		
	}

	public void start() {
		controller.initialize();
        super.start();
    }

    public EndScreenController getController() {
        return (EndScreenController) controller;
    }

	
}