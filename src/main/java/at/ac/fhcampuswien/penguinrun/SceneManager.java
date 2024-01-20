package at.ac.fhcampuswien.penguinrun;

import at.ac.fhcampuswien.penguinrun.game.GameSettings;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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

    public static SceneManager getInstance(){
        return getInstance(null);
    }
    public static SceneManager getInstance(Stage stage){
        if(sceneManager == null) sceneManager = new SceneManager(stage);
        return sceneManager;
    }

    public Stage getStage(){
        return stage;
    }

    public void initializeScenes(){
        try{
            //StartMenu
            FXMLLoader startMenuLoader = new FXMLLoader(Main.class.getResource("start-menu.fxml"));
            Scene mainScene = new Scene(startMenuLoader.load(), GameSettings.WINDOW_WIDTH, GameSettings.WINDOW_HEIGHT);
            mainScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
            SceneManager.sceneList.put("mainMenu",mainScene);

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
