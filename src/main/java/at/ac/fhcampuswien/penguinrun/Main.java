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
        Font.loadFont(Objects.requireNonNull(getClass().getResource("Maze.ttf")).toExternalForm(),10);
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("start-menu.fxml"));
        Scene mainMenu = new Scene(fxmlLoader.load(), GameSettings.WINDOW_WIDTH, GameSettings.WINDOW_HEIGHT);

        Image icon = new Image(Objects.requireNonNull(getClass().getResource("img/pgnBig.png")).toExternalForm());
        mainMenu.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());

        GameManager.sceneManager.put("mainMenu",mainMenu);

        stage.setTitle("PenguinRun");
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.setScene(mainMenu);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}