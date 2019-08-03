package unsw.dungeon;

import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;

public class DungeonApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Dungeon");

		DungeonControllerLoader dungeonLoader = new DungeonControllerLoader("advanced.json");


        StartScreenController startController = new StartScreenController();
        
        InstructionsPageController instructionsController = new InstructionsPageController();
        
        EndScreenController endScreenController = new EndScreenController();
        
        StartScreen startScreen = new StartScreen(primaryStage, startController);
        InstructionsScreen instructionsScreen = new InstructionsScreen(primaryStage, instructionsController);
        EndScreen endScreen = new EndScreen(primaryStage, endScreenController);        
        DungeonScreen dungeonScreen = new DungeonScreen(primaryStage, dungeonLoader);
        
        endScreen.getController().setDungeonScreen(dungeonScreen);
        endScreen.getController().setStartScreen(startScreen);
        instructionsScreen.getController().setStartScreen(startScreen);
        startScreen.getController().setDungeonScreen(dungeonScreen);
        startScreen.getController().setInstructionsScreen(instructionsScreen);
        dungeonScreen.getController().setStartScreen(startScreen);
        dungeonScreen.getController().setEndScreen(endScreen);
        
        startScreen.start();
        
    }

    public static void main(String[] args) {
        launch(args);
    }

}
