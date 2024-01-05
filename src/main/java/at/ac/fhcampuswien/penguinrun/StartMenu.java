package at.ac.fhcampuswien.penguinrun;

import at.ac.fhcampuswien.penguinrun.game.MediaManager;
import javafx.event.ActionEvent;
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

    public void onStart(ActionEvent event) throws IOException {
        Stage stage = (Stage) startBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("difficulty.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 1280,720);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/at/ac/fhcampuswien/penguinrun/style.css")).toExternalForm());
        stage.setTitle("PenguinRun");
        stage.setScene(scene);
    }

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

        // listener to save the volume setting whenever it is changed by the user
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            MediaManager.setVolume(newValue.doubleValue()); // Adjust and save the volume setting
            MediaManager.saveSetting("volume", String.valueOf(newValue.doubleValue()));
        });
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
