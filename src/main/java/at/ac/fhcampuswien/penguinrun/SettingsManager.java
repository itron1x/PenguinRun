package at.ac.fhcampuswien.penguinrun;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

public class SettingsManager {

    private static final String SETTINGS_FILE = "settings.properties";
    private static MediaPlayer mediaPlayer;


    public static void saveSetting(String key, String value) {
        Properties prop = new Properties();
        try {
            File file = new File(SETTINGS_FILE);
            if (!file.exists()) {
                file.createNewFile();
            }

            try (FileOutputStream out = new FileOutputStream(file, true)) {
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

    public static void music(){
        String path = "C:\\Users\\noahp\\Downloads\\BGMusic.mp3";
        Media media = new Media(Paths.get(path).toUri().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Looping music
        mediaPlayer.play();
    }

    public static void setVolume(double volume) {
        if (mediaPlayer != null && volume >= 0.0 && volume <= 1.0) {
            mediaPlayer.setVolume(volume);
        }
    }
}
