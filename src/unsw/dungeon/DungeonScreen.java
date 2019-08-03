package unsw.dungeon;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class DungeonScreen extends Screen {

	private DungeonControllerLoader dungeonControllerLoader;
	/**
	 * constructor of the dungeon Screen.
	 * @param stage
	 * @param dungeonControllerLoader
	 * @throws IOException
	 */
	public DungeonScreen(Stage stage, DungeonControllerLoader dungeonControllerLoader) throws IOException{
		this.stage = stage;
		this.dungeonControllerLoader = dungeonControllerLoader;
        scene = initialiseScene();
	}
	
	/**
	 * create everything that is required for this screen
	 * @return
	 * @throws IOException
	 */
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


	/**
	 * tell the controller and model to start executing things 
	 * related to this screen
	 * @param difficulty
	 * @throws IOException
	 */
	public void start(String difficulty) throws IOException {
		
		if(((DungeonController ) controller).getSquares().getChildren().size() == 0) {
			scene = initialiseScene();
		}
        super.start();
        ((DungeonController ) controller).start(difficulty);
        
    }
	/**
	 * return the controller of the screen 
	 */
    public DungeonController getController() {
        return (DungeonController ) controller;
    }


	@Override
	public void start() {		
		if(((DungeonController ) controller).getSquares().getChildren().size() == 0) {
			try {
				scene = initialiseScene();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        super.start();
        ((DungeonController ) controller).start("Hard");
	}

	
}
