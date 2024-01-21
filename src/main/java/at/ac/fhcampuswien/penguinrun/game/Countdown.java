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

    /**
     * Creates a timer that acts as the countdown for the game. This will be called in the GameManager class.
     * @param initialSeconds - Give time for the level.
     */
    public Countdown(int initialSeconds) {
        this.secondsRemaining = initialSeconds;
        initializeTimeline();
        this.paused = false;
    }

    /**
     * Creates and starts the timer letting the time tick down if the game isn't paused. If the time reaches zero the timer stops, ensuring that
     * the timer doesn't display negative numbers.
     */
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

    /**
     * Method that starts the timer.
     */
    public void start() {
        timeline.play();
    }

    /**
     * Turns the boolean "paused" to true to let the timer know if the game is paused or not. If "paused" is true,
     * then the timer stops ticking.
     */
    public void pause() {
        paused = true;
    }

    /**
     * Turns the boolean "paused" to false. Lets the timer know that the player has unpaused the game and resumed
     * playing, letting the timer continue to tick down from where it left off.
     */
    public void resume() {
        paused = false;
    }

    /**
     * A getter method for secondsRemaining.
     * @return - Getter for the variable seconds remaining.
     */
    public int getSecondsRemaining() {
        return secondsRemaining;
    }

    /**
     * Sets the time depending on which difficulty the player has chosen.
     * @return - Returns the start time for the right difficulty.
     */
    public static int getAccordingTime() {
        if (Difficulty.getDifficulty() == GameSettings.EASY) {
            TimeLeft = 120;
        }
        else if (Difficulty.getDifficulty() == GameSettings.MIDDLE) {
            TimeLeft = 240;
        }
        else if (Difficulty.getDifficulty() == GameSettings.HARD) {
            TimeLeft = 720;
        }
        return TimeLeft;
    }

}


