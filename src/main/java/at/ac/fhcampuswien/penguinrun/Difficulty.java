package at.ac.fhcampuswien.penguinrun;

import at.ac.fhcampuswien.penguinrun.game.GameSettings;
import at.ac.fhcampuswien.penguinrun.game.MediaManager;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class Difficulty {
    @FXML
    private Button startGame;
    @FXML
    private Slider volumeSlider;
    @FXML
    private ImageView volumeImage;
    private static int difficulty = 51;
    private final Image volumeOff = new Image(Objects.requireNonNull(this.getClass().getResource("img/btn/volumeOff.png")).toExternalForm());
    private final Image volumeOn = new Image(Objects.requireNonNull(this.getClass().getResource("img/btn/volumeOn.png")).toExternalForm());

    public Difficulty() {
        volumeSlider = MediaManager.volumeSlider;
        volumeImage = MediaManager.volumeImage;
    }

    public static int getDifficulty() {
        return difficulty;
    }

    /**
     * Initializes the controller class. This method is automatically called after the FXML file has been loaded.
     * It sets up the initial state of the volume slider based on the saved settings.
     * And it establishes a listener to update and save the volume setting whenever it is adjusted by the user.
     * It also updates the volume button's style to reflect the current volume state (on or off).
     */
    public void initialize() {
        volumeSlider.setValue(GameSettings.volume);
        if (GameSettings.volume == 0) volumeImage.setImage(MediaManager.volumeOff);
        else volumeImage.setImage(MediaManager.volumeOn);
        volumeSlider.valueProperty().
                addListener((observable, oldValue, newValue) ->
                {
                    MediaManager.updateVolume(newValue.doubleValue(), volumeImage);
                    updateVolumeButtonIcon(newValue.doubleValue());
                });
    }

    /**
     * Updates the volume button's background image based on the current volume level.
     * @param volume The current volume level.
     */
    private void updateVolumeButtonIcon(double volume) {
        if(volume == 0) volumeImage.setImage(volumeOff);
        else volumeImage.setImage(volumeOn);
    }


    /**
     * Method to go back to the start screen.
     */
    public void onBack() throws IOException {
        Stage stage = SceneManager.getInstance().getStage();
        SceneManager.getInstance().initializeScenes();
        Scene mainMenu = SceneManager.sceneList.get("mainMenu");
        stage.setScene(mainMenu);
    }

    /**
     * Game stage is loaded, key events are initialized and frozen start screen for the game is enabled.
     * The difficulty is given to the game manager class.
     */
    public void startGame() throws IOException {
        Stage stage = SceneManager.getInstance().getStage();
        FXMLLoader gameLoader = new FXMLLoader(this.getClass().getResource("game.fxml"));

        Scene gameScene = new Scene(gameLoader.load(), GameSettings.WINDOW_WIDTH,GameSettings.WINDOW_HEIGHT);
        gameScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());

        GameManager controllerPlayer = gameLoader.getController();
        controllerPlayer.generateMaze(difficulty);
        gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                controllerPlayer.keyPressed(event);
                controllerPlayer.startTimer(event);
                controllerPlayer.getDimmBackground().setVisible(false);
                controllerPlayer.getStartText().setVisible(false);
            }
        });
        gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                controllerPlayer.keyReleased(event);
            }
        });

        stage.setTitle("PenguinRun");
        stage.setScene(gameScene);
    }

    /**
     * Difficulty set to easy.
     */
    public void setEasy() {
        startGame.setDisable(false);
        difficulty = GameSettings.EASY;
    }
    /**
     * Difficulty set to middle.
     */
    public void setMiddle() {
        startGame.setDisable(false);
        difficulty = GameSettings.MIDDLE;
    }
    /**
     * Difficulty set to hard.
     */
    public void setHard() {
        startGame.setDisable(false);
        difficulty = GameSettings.HARD;
    }

    /**
     * Toggles the visibility and interactivity of the volume slider in the UI.
     * If the slider is currently invisible (opacity is 0), it is made visible and interactive.
     * If it is visible, it is made invisible and non-interactive.
     */
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
