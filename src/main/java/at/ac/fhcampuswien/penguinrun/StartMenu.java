package at.ac.fhcampuswien.penguinrun;

import at.ac.fhcampuswien.penguinrun.game.MediaManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class StartMenu {

    @FXML
    private Button startBtn;

    @FXML
    private Slider volumeSlider;

    @FXML
    private Button volumeBtn;

    /**
     * Method for loading the difficulty screen.
     */
    public void onStart() throws IOException {
        Stage stage = (Stage) startBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("difficulty.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 1280,720);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/at/ac/fhcampuswien/penguinrun/style.css")).toExternalForm());
        stage.setTitle("PenguinRun");
        stage.setScene(scene);
    }

    /**
     * Initializes the controller class. This method is automatically called after the FXML file has been loaded.
     * It sets up the initial state of the volume slider based on the saved settings, configures it for proper
     * increments, and establishes a listener to update and save the volume setting whenever it is adjusted by the user.
     * It also updates the volume button's style to reflect the current volume state (on or off).
     */
    public void initialize() {
        String volumeSetting = MediaManager.loadSetting("volume", "0.1");
        double volume = Double.parseDouble(volumeSetting);

        volumeSlider.setValue(volume);
        MediaManager.setVolume(volume);
        volumeSlider.setSnapToTicks(true);
        volumeSlider.setMajorTickUnit(0.25);
        updateVolumeButtonIcon(volume);

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
