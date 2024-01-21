package at.ac.fhcampuswien.penguinrun;

import at.ac.fhcampuswien.penguinrun.game.GameSettings;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SceneManager {
    private static SceneManager sceneManager;
    public static final Map<String, Scene> sceneList = new HashMap<>();
    private static Stage stage;

    public SceneManager(Stage stage){
        SceneManager.stage = stage;
        initializeScenes();
    }

    /***
     * Get the instance of the SceneManager and if no instance is existing create one.
     * @return - the instance of the SceneManager is returned.
     */
    public static SceneManager getInstance(){
        return getInstance(null);
    }
    public static SceneManager getInstance(Stage stage){
        if(sceneManager == null) sceneManager = new SceneManager(stage);
        return sceneManager;
    }

    /***
     * Get the stage for the program.
     * @return - the stage is returned.
     */
    public Stage getStage(){
        return stage;
    }

    /***
     * Create scenes for the start and difficulty screen and put them in the sceneList map.
     */
    public void initializeScenes(){
        try{
            //StartMenu
            FXMLLoader startMenuLoader = new FXMLLoader(Main.class.getResource("start-menu.fxml"));
            Scene mainScene = new Scene(startMenuLoader.load(), GameSettings.WINDOW_WIDTH, GameSettings.WINDOW_HEIGHT);
            mainScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
            SceneManager.sceneList.put("start",mainScene);

            //Difficulty
            FXMLLoader difficultyLoader = new FXMLLoader(Main.class.getResource("difficulty.fxml"));
            Scene difficultyScene = new Scene(difficultyLoader.load(), GameSettings.WINDOW_WIDTH, GameSettings.WINDOW_HEIGHT);
            difficultyScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
            SceneManager.sceneList.put("difficulty",difficultyScene);
        } catch (IOException e){
            System.out.println(e.getMessage());
        }


    }
}
