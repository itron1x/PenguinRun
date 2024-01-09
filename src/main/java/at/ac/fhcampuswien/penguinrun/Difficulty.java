package at.ac.fhcampuswien.penguinrun;

import at.ac.fhcampuswien.penguinrun.game.GameManager;
import at.ac.fhcampuswien.penguinrun.game.GameSettings;
import at.ac.fhcampuswien.penguinrun.game.MediaManager;
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
    private Button volumeBtn;

    /**
     * Initializes the controller class. This method is automatically called after the FXML file has been loaded.
     * It sets up the initial state of the volume slider based on the saved settings, configures it for proper
     * increments, and establishes a listener to update and save the volume setting whenever it is adjusted by the user.
     * It also updates the volume button's style to reflect the current volume state (on or off).
     */
    public void initialize() {
        // Load saved volume setting
        String volumeSetting = MediaManager.loadSetting("volume", "0.1");
        double volume = Double.parseDouble(volumeSetting);

        // Initial value of volume slider
        volumeSlider.setValue(volume);

        // Loaded volume setting
        MediaManager.setVolume(volume);

        // Slider configuring
        volumeSlider.setSnapToTicks(true);
        volumeSlider.setMajorTickUnit(0.25);

        // Update the volume button icon based on the current volume
        updateVolumeButtonIcon(volume);

        // listener to save the volume setting whenever it is changed by the user
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            MediaManager.setVolume(newValue.doubleValue()); // Adjust and save the volume setting
            updateVolumeButtonIcon(newValue.doubleValue());
        });
    }

    /**
     * Updates the volume button's background image based on the current volume level.
     * @param volume The current volume level.
     */
    private void updateVolumeButtonIcon(double volume) {
        String iconPath = (volume == 0) ? "/img/btn/volumeOff.png" : "/img/btn/volumeOn.png";
        volumeBtn.setStyle("-fx-background-image: url(" + iconPath + ")");
    }


    /**
     * Method to go back to the start screen.
     */
    public void onBack() throws IOException {
        Stage stage = (Stage) backBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("start-menu.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, GameSettings.windowWidth,GameSettings.windowHeight);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/at/ac/fhcampuswien/penguinrun/style.css")).toExternalForm());
        stage.setTitle("PenguinRun");
        stage.setScene(scene);
    }
    /**
     * Game stage is loaded, key events are initialized and frozen start screen for the game is enabled.
     * The difficulty is given to the game manager class.
     */
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
                controllerPlayer.startTimer(event);
                controllerPlayer.getDimmBackground().setVisible(false);
                controllerPlayer.getStartText().setVisible(false);
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

    /**
     * Difficulty set to easy.
     */
    public void setEasy() {
        startGame.setDisable(false);
        difficulty = GameSettings.easy;
    }
    /**
     * Difficulty set to middle.
     */
    public void setMiddle() {
        startGame.setDisable(false);
        difficulty = GameSettings.middle;
    }
    /**
     * Difficulty set to hard.
     */
    public void setHard() {
        startGame.setDisable(false);
        difficulty = GameSettings.hard;
    }

    public void changeVolume(){
        if (volumeSlider.getOpacity() == 0) {
            volumeSlider.setDisable(false);
            volumeSlider.setOpacity(1);
        }
        else {
            volumeSlider.setDisable(true);
            volumeSlider.setOpacity(0);
        }
    }


}
