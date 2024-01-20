package at.ac.fhcampuswien.penguinrun;

import at.ac.fhcampuswien.penguinrun.game.GameSettings;
import at.ac.fhcampuswien.penguinrun.game.MediaManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;


public class StartMenu {

    @FXML
    private Button startBtn;
    @FXML
    private Slider volumeSlider;
    @FXML
    private ImageView volumeImage;

    public StartMenu(){
        volumeSlider = MediaManager.volumeSlider;
        volumeImage = MediaManager.volumeImage;
    }

    /**
     * Method for loading the difficulty screen.
     */
    public void onStart() throws IOException {
        Stage stage = (Stage) startBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("difficulty.fxml"));
        Parent root = loader.load();

        Scene difficulty = new Scene(root, GameSettings.WINDOW_WIDTH, GameSettings.WINDOW_HEIGHT);
        difficulty.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());

        GameManager.sceneManager.put("difficulty",difficulty);

        stage.setTitle("PenguinRun");
        stage.setScene(difficulty);
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
                });
    }

    /**
     * Toggles the visibility and interactivity of the volume slider in the UI.
     * If the slider is currently invisible (opacity is 0), it is made visible and interactive.
     * If it is visible, it is made invisible and non-interactive.
     */
    public void changeVolume() {
        if (volumeSlider.getOpacity() == 0) {
            volumeSlider.setDisable(false);
            volumeSlider.setOpacity(1);
        } else {
            volumeSlider.setDisable(true);
            volumeSlider.setOpacity(0);
        }
    }
}
