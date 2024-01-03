package at.ac.fhcampuswien.penguinrun.game;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.scene.control.Label;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class Timer {
    private Timeline timeline;
    private Label timerLabel;
    private int secondsLeft;
    private int initialTime;
    private boolean timeRunning;

    public Timer(int initialTime){
        this.initialTime = initialTime;
        this.secondsLeft = initialTime;
        this.timeRunning = false;
        initializeTimerLabel();
        initializeTimeline();
    }
    public Label getTimerLabel(){
        return timerLabel;
    }
    private void initializeTimerLabel(){
        timerLabel = new Label(formatTime(secondsLeft));
        timerLabel.getStyleClass().add("timer-label");
    }

    private void initializeTimeline(){
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if  (secondsLeft>0){
                    secondsLeft--;
                    updateTimerLabel();
                }else{
                    stopTimer();
                }
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }
    private void updateTimerLabel(){
        timerLabel.setText(formatTime(secondsLeft));
    }
    private void updateConsole() {
        System.out.println(formatTime(secondsLeft));
    }
    private String formatTime(int seconds){
        int minutes =  seconds/60;
        int remainingSeconds = seconds%60;
        return String.format("%02d:%02d", minutes,remainingSeconds);
    }
    public void stopTimer(){
        if  (!timeRunning){
            timeRunning = true;
            timeline.play();
        }
    }
    public void startTimer(){
        if (!timeRunning){
            timeRunning = true;
            timeline.play();
        }
    }
    public void resetTimer(){
        stopTimer();
        secondsLeft = initialTime;
        updateTimerLabel();
        updateConsole();
    }
}
