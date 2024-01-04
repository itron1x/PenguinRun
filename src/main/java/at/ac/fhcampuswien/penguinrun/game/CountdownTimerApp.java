package at.ac.fhcampuswien.penguinrun.game;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CountdownTimerApp extends Application {

    private int secondsRemaining = 15; // Set your desired countdown time in seconds

    public static Label countdownLabel;
    private Timeline timeline;

    public static Label getCountdownLabel() {
        return countdownLabel;
    }

    @Override
    public void start(Stage primaryStage) {
        countdownLabel = new Label("Time remaining: " + secondsRemaining + " seconds");

        // Create a Timeline that triggers an event every second
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (secondsRemaining > 0) {
                    secondsRemaining--;
                    countdownLabel.setText("Time remaining: " + secondsRemaining + " seconds");
                } else {
                    countdownLabel.setText("Time's up!");
                    timeline.stop(); // Stop the timeline when the countdown reaches zero
                }
            }
        }));



        timeline.setCycleCount(Animation.INDEFINITE); // Run the timeline indefinitely

        StackPane root = new StackPane();
        root.getChildren().add(countdownLabel);

        Scene scene = new Scene(root, 300, 200);

        primaryStage.setTitle("Countdown Timer");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Start the countdown when the application starts
        timeline.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


