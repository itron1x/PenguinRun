package at.ac.fhcampuswien.penguinrun;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {
    /**
     * Startup of the game. Music is loaded and window settings are initialized.
     * @param stage is the start stage
     */
    @Override
    public void start(Stage stage) throws Exception {
        SceneManager sceneManager = SceneManager.getInstance(stage);
        Scene mainMenu = SceneManager.sceneList.get("mainMenu");
        Image icon = new Image(Objects.requireNonNull(SceneManager.class.getResource("game/img/pgnBig.png")).toExternalForm());

        sceneManager.getStage().setScene(mainMenu);
        sceneManager.getStage().setTitle("PenguinRun");
        sceneManager.getStage().setResizable(false);
        sceneManager.getStage().getIcons().add(icon);
        sceneManager.getStage().show();

        Font.loadFont(Objects.requireNonNull(getClass().getResource("Maze.ttf")).toExternalForm(),10);
    }

    public static void main(String[] args) {
        launch();
    }
}