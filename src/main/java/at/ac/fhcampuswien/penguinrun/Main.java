package at.ac.fhcampuswien.penguinrun;

import at.ac.fhcampuswien.penguinrun.game.CountdownTimerApp;
import at.ac.fhcampuswien.penguinrun.game.GameManager;
import at.ac.fhcampuswien.penguinrun.game.GameSettings;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Font.loadFont(getClass().getResource("Maze.ttf").toExternalForm(),10);
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("start-menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), GameSettings.windowWidth, GameSettings.windowHeight);

        Image icon = new Image("/img/pgnBig.png");
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/at/ac/fhcampuswien/penguinrun/style.css")).toExternalForm());
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