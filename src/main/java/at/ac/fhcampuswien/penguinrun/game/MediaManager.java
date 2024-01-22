package at.ac.fhcampuswien.penguinrun.game;

import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.util.Objects;

/***
 * Class for the volume control.
 */
public class MediaManager {
    /***
     * Music Title: Happy And Joyful Children <a href="https://www.patreon.com/posts/62276355">...</a>
     * Released by: Oleg Mazur <a href="https://soundcloud.com/fm_freemusic">...</a>
     * Music promoted by <a href="https://www.chosic.com/free-music/all/">...</a>
     * Creative Commons CC BY 3.0
     * <a href="https://creativecommons.org/licenses/by/3.0/">...</a>
     */
    private static final Media media = new Media(Objects.requireNonNull(MediaManager.class.getResource("music/BGMusic.mp3")).toExternalForm());
    private static final MediaPlayer mediaPlayer = new MediaPlayer(media);
    public static final Slider volumeSlider = new Slider();
    public static final ImageView volumeImage = new ImageView();
    public static final Image volumeOff = new Image(Objects.requireNonNull(MediaManager.class.getResource("img/btn/volumeOff.png")).toExternalForm());
    public static final Image volumeOn = new Image(Objects.requireNonNull(MediaManager.class.getResource("img/btn/volumeOn.png")).toExternalForm());

    static{
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
        MediaManager.setVolume(GameSettings.volume);

        volumeSlider.setValue(GameSettings.volume);
        volumeSlider.setSnapToTicks(true);
        volumeSlider.setMajorTickUnit(0.25);
    }

    /***
     * Update volume and images.
     * @param newValue Updated volume.
     * @param volumeImage Speaker icon in relation to the current volume.
     */
    public static void updateVolume(double newValue, ImageView volumeImage){
        MediaManager.setVolume(newValue); // Adjust and save the volume setting
        MediaManager.setSetting(newValue);
        if (GameSettings.volume == 0) volumeImage.setImage(volumeOff);
        else volumeImage.setImage(volumeOn);
    }

    /***
     * Updates Volume volume variable in GameSettings.
     * @param value new Volume.
     */
    public static void setSetting(Double value){
        GameSettings.volume = value;
    }

    /**
     * Sets the volume of the currently playing media player. The volume value should be between
     * 0.0 (mute) and 1.0 (full volume).
     * @param volume The volume level to set for the media player.
     */
    public static void setVolume(double volume) {
        if (volume >= 0.0 && volume <= 1.0) {
            mediaPlayer.setVolume(volume);
        }
    }
}
