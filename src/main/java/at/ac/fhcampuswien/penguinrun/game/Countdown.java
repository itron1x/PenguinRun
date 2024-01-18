package at.ac.fhcampuswien.penguinrun.game;


import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;


public class Countdown {
    private int secondsRemaining;
    private Timeline timeline;

    public Countdown(int initialSeconds) {
        this.secondsRemaining = initialSeconds;
        initializeTimeline();
    }

    private void initializeTimeline() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if (secondsRemaining > 1) {
                secondsRemaining--;
            } else {
                secondsRemaining = 0;
                timeline.stop();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    public void start() {
        timeline.play();
    }

    public void pause() {
        timeline.pause();
    }

    public void reset(int initialSeconds) {
        this.secondsRemaining = initialSeconds;
        initializeTimeline();
    }

    public int getSecondsRemaining() {
        return secondsRemaining;
    }

    /**
     * This is a class used to test the Countdown Timer code.
     * It executes a separate window with a different timer. Was used to debug the
     * countdown timer code.
     *
     * @param primaryStage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     */

}


