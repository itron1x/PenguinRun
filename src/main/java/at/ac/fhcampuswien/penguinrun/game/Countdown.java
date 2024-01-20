package at.ac.fhcampuswien.penguinrun.game;


import at.ac.fhcampuswien.penguinrun.Difficulty;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;

/**This class manages the Countdown Timer used in the game
 * It initializes a timer that counts down depending on the seconds given that will differ depending on the difficulty.
 * The class also includes the ability to start, stop, and resume the timer when needed.
 */
public class Countdown {
    private int secondsRemaining;
    private Timeline timeline;
    private boolean paused;
    private static int TimeLeft;


    public Countdown(int initialSeconds) {
        this.secondsRemaining = initialSeconds;
        initializeTimeline();
        this.paused = false;
    }

    private void initializeTimeline() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if (!paused) {
                if (secondsRemaining > 1) {
                    secondsRemaining--;
                } else {
                    secondsRemaining = 0;
                    timeline.stop();
                }
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    public void start() {
        timeline.play();
    }

    public void pause() {
        paused = true;
    }

    public void resume() {
        paused = false;
    }

    public void reset(int initialSeconds) {
        this.secondsRemaining = initialSeconds;
        initializeTimeline();
    }

    public int getSecondsRemaining() {
        return secondsRemaining;
    }

    /**
     * Sets the time depending on which difficulty the player has chosen.
     * @return
     */
    public static int getAccordingTime() {
        if (Difficulty.getDifficulty() == GameSettings.EASY) {
            TimeLeft = 90;
        }
        else if (Difficulty.getDifficulty() == GameSettings.MIDDLE) {
            TimeLeft = 150;
        }
        else if (Difficulty.getDifficulty() == GameSettings.HARD) {
            TimeLeft = 240;
        }
        return TimeLeft;
    }

}


