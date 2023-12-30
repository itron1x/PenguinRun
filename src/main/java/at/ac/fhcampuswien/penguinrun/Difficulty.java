package at.ac.fhcampuswien.penguinrun;

import at.ac.fhcampuswien.penguinrun.game.GameManager;
import at.ac.fhcampuswien.penguinrun.game.GameSettings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Difficulty {
    @FXML
    private Button backBtn;
    public static int difficulty = 51;

    public void onBack() throws IOException {
        Stage stage = (Stage) backBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("start-menu.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 1280,720);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/at/ac/fhcampuswien/penguinrun/style.css")).toExternalForm());
        stage.setTitle("PenguinRun");
        stage.setScene(scene);
    }
    public void startGame() throws IOException {
        Stage stage = (Stage) backBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("game.fxml"));
        Parent root = loader.load();
        GameManager controllerPlayer = loader.getController();

        Scene scene = new Scene(root, 1280,720);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/at/ac/fhcampuswien/penguinrun/style.css")).toExternalForm());
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                controllerPlayer.keyPressed(event);
            }
        });
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                controllerPlayer.keyReleased(event);
            }
        });

        stage.setTitle("PenguinRun");
        stage.setScene(scene);

        GameManager game = loader.getController();
        game.generateMaze(difficulty);
    }
    public void setEasy(ActionEvent action) {
        difficulty = GameSettings.easy;
    }
    public void setMiddle(ActionEvent action) {
        difficulty = GameSettings.middle;
    }
    public void setHard(ActionEvent action) {
        difficulty = GameSettings.hard;
    }
}
