package unsw.dungeon;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class DungeonScreen extends Screen {

	private DungeonControllerLoader dungeonControllerLoader;

	public DungeonScreen(Stage stage, DungeonControllerLoader dungeonControllerLoader) throws IOException{
		this.stage = stage;
		this.dungeonControllerLoader = dungeonControllerLoader;
        scene = initialiseScene();
		
	}
	
	
	private Scene initialiseScene() throws IOException {
		if(this.controller != null && ((DungeonController )this.controller).getEndScreen() != null) {
			EndScreen endScreen = ((DungeonController ) this.controller).getEndScreen(); 
			StartScreen startScreen = ((DungeonController ) this.controller).getStartScreen();
			this.controller = this.dungeonControllerLoader.loadController();
			((DungeonController ) this.controller).setEndScreen(endScreen);
			((DungeonController ) this.controller).setStartScreen(startScreen);
		}
		else {
			this.controller = this.dungeonControllerLoader.loadController();
		}
		((DungeonController ) this.controller).giveDungeonMyself();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("DungeonView.fxml"));

        loader.setController(this.controller);
        
        Parent root = loader.load();
        scene = new Scene(root);
        return scene;
	}

	public void start(String difficulty) throws IOException {
		
		if(((DungeonController ) controller).getSquares().getChildren().size() == 0) {
			scene = initialiseScene();
		}
        super.start();

        ((DungeonController ) controller).start(difficulty);
        
    }
	
    public DungeonController getController() {
        return (DungeonController ) controller;
    }


	@Override
	public void start() {		
	}

	
}
