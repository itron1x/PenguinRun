package at.ac.fhcampuswien.penguinrun.game;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class MediaManager {

    private static final String SETTINGS_FILE = "settings.properties";
    private static MediaPlayer mediaPlayer;

    public static void initialize(Slider volumeSlider) {
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

    /**
     * Saves a setting to a properties file. If the file doesn't exist, it creates one.
     * @param key The key under which the setting will be saved.
     * @param value The value of the setting.
     */
    public static void saveSetting(String key, String value) {
        Properties prop = new Properties();
        try {
            File file = new File(SETTINGS_FILE);
            if (!file.exists()) {
                file.createNewFile();
            }

            try (FileOutputStream out = new FileOutputStream(file)) {
                // Load existing properties
                prop.load(new FileInputStream(file));
                // Set property and save
                prop.setProperty(key, value);
                prop.store(out, null);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads a setting from a properties file. If the file or the specified key doesn't exist,
     * it returns a default value.
     * @param key The key of the setting to load.
     * @param defaultValue The default value to return if the key isn't found.
     * @return The value of the setting or the default value.
     */
    public static String loadSetting(String key, String defaultValue) {
        Properties prop = new Properties();
        try {
            File file = new File(SETTINGS_FILE);
            if (!file.exists()) {
                return defaultValue; // File doesn't exist, return default value
            }

            try (FileInputStream in = new FileInputStream(file)) {
                prop.load(in);
                return prop.getProperty(key, defaultValue);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    /**
     * Starts playing the background music on a loop.
     */
    public static void music(){
        String path = "/music/BGMusic.mp3";
        Media media = new Media(Objects.requireNonNull(MediaManager.class.getResource(path)).toExternalForm());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Looping music
        mediaPlayer.play();
    }

    /**
     * Sets the volume of the currently playing media player. The volume value should be between
     * 0.0 (mute) and 1.0 (full volume).
     * @param volume The volume level to set for the media player.
     */
    public static void setVolume(double volume) {
        if (mediaPlayer != null && volume >= 0.0 && volume <= 1.0) {
            mediaPlayer.setVolume(volume);
        }
    }
}
