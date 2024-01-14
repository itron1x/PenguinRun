package at.ac.fhcampuswien.penguinrun;

import at.ac.fhcampuswien.penguinrun.game.GameSettings;
import at.ac.fhcampuswien.penguinrun.game.MediaManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
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
        MediaManager.music();
        MediaManager.setVolume(0.1);
        Font.loadFont(Objects.requireNonNull(getClass().getResource("Maze.ttf")).toExternalForm(),10);
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("start-menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), GameSettings.windowWidth, GameSettings.windowHeight);

        Image icon = new Image(Objects.requireNonNull(getClass().getResource("img/pgnBig.png")).toExternalForm());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
        stage.setTitle("PenguinRun");
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}