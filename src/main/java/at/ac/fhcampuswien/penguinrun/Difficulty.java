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
import javafx.scene.control.Slider;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Difficulty {
    @FXML
    private Button backBtn;

    @FXML
    private Button startGame;

    public static int getDifficulty() {
        return difficulty;
    }

    private static int difficulty = 51;

    @FXML
    private Slider volumeSlider;

    @FXML
    public void initialize() {
        // Load saved volume setting
        String volumeSetting = SettingsManager.loadSetting("volume", "0.1");
        double volume = Double.parseDouble(volumeSetting);

        // Initial value of volume slider
        volumeSlider.setValue(volume);

        // Loaded volume setting
        SettingsManager.setVolume(volume);

        // Slider configuring
        volumeSlider.setSnapToTicks(true);
        volumeSlider.setMajorTickUnit(0.25);

        // listener to save the volume setting whenever it is changed by the user
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            SettingsManager.setVolume(newValue.doubleValue()); // Adjust and save the volume setting
            SettingsManager.saveSetting("volume", String.valueOf(newValue.doubleValue()));
        });
    }

    public void onBack() throws IOException {
        Stage stage = (Stage) backBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("start-menu.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, GameSettings.windowWidth,GameSettings.windowHeight);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/at/ac/fhcampuswien/penguinrun/style.css")).toExternalForm());
        stage.setTitle("PenguinRun");
        stage.setScene(scene);
    }
    public void startGame() throws IOException {
        Stage stage = (Stage) backBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("game.fxml"));
        Parent root = loader.load();
        GameManager controllerPlayer = loader.getController();

        Scene scene = new Scene(root, GameSettings.windowWidth,GameSettings.windowHeight);
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
        startGame.setDisable(false);
        difficulty = GameSettings.easy;
    }
    public void setMiddle(ActionEvent action) {
        startGame.setDisable(false);
        difficulty = GameSettings.middle;
    }
    public void setHard(ActionEvent action) {
        startGame.setDisable(false);
        difficulty = GameSettings.hard;
    }

}
