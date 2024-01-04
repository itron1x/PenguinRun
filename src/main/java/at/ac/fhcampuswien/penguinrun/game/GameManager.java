package at.ac.fhcampuswien.penguinrun.game;

import at.ac.fhcampuswien.penguinrun.Difficulty;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.Label;
import javafx.util.Duration;

public class GameManager implements Initializable {

    public double mapHeight = (10*GameSettings.scale)*Difficulty.getDifficulty();
    private double newX;
    private double newY;
    private Camera camera;


    @FXML
    private TilePane tilePane;

    @FXML
    private ImageView pgn;


    @FXML
    private Pane pauseMenu;
    @FXML
    private Text safe;
    private boolean isPaused = false;
    private boolean exitConfirmation = false;

    @FXML
    private Label countdownLabel;
    private int secondsRemaining = 60;
    private Timeline timeline;


    private boolean upPressed = false; //W + UP
    private boolean downPressed = false; //S + DOWN
    private boolean leftPressed = false; //A + LEFT
    private boolean rightPressed = false; //D + RIGHT
    private final int speed = GameSettings.speed; //Movement Speed Penguin
    private static final Image pgnStill = new Image("img/pgnStill.png",true);
    private static final Image pgnAnim = new Image("img/pgnAnim.gif",true);
    private MazeManager mazeM;

    public void generateMaze(int sizeBoard){
        mazeM = new MazeManager(sizeBoard,tilePane);
        mazeM.generateMaze();
        pgn.setFitHeight(GameSettings.scale * 8);
        pgn.setFitWidth(GameSettings.scale * 8);
        newY = GameSettings.scale * 15;
        newX = GameSettings.scale * 5;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
        camera = new Camera(GameSettings.windowWidth, GameSettings.windowHeight,mapHeight);
        continuousMovement();

        // initialize Countdown
        countdownLabel.setText("Time remaining: " + secondsRemaining + " seconds");
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if (secondsRemaining > 0) {
                secondsRemaining--;
                countdownLabel.setText("Time remaining: " + secondsRemaining + " seconds");
            } else {
                countdownLabel.setText("Time's up!");
                timeline.stop();
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void pauseGame() {
        // Zeige das Pause-Menü
        pauseMenu.setVisible(true);
        //Blende, "Dimmen"
        tilePane.setBlendMode(BlendMode.MULTIPLY);
    }

    public void resumeGame() {
        tilePane.setVisible(true);
        pauseMenu.setVisible(false);
        tilePane.setBlendMode(BlendMode.SRC_OVER);
        safe.setVisible(false);
        exitConfirmation = false;
    }

    public void backToMainMenu() {
        if (exitConfirmation) {
            // Wenn die Exit-Bestätigung aktiviert ist, dann führe die Rückkehr zum Hauptmenü durch
            performBackToMainMenu();
            safe.setVisible(false);
        } else {
            // Wenn die Exit-Bestätigung nicht aktiviert ist, dann zeige die Bestätigungsfrage
            showExitConfirmation();
        }
    }

    private void showExitConfirmation() {
        exitConfirmation = true;
        //System.out.println("Möchten Sie das Spiel wirklich beenden? Wenn ja drücken Sie nochmal auf den Hauptmenu Button");
        safe.setVisible(true);
    }

    private void performBackToMainMenu() {
        try {
            // Lade die Startmenü-FXML-Datei
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/at/ac/fhcampuswien/penguinrun/start-menu.fxml"));
            Pane startMenuPane = loader.load();

            // Erstelle eine neue Szene mit dem Startmenü
            Scene startMenuScene = new Scene(startMenuPane, GameSettings.windowWidth, GameSettings.windowHeight);

            // Lade css
            startMenuScene.getStylesheets().add(getClass().getResource("/at/ac/fhcampuswien/penguinrun/style.css").toExternalForm());

            // Hole die Stage vom aktuellen Node, Tilepane
            Stage stage = (Stage) tilePane.getScene().getWindow();

            // Setze die Scene auf die Stage
            stage.setScene(startMenuScene);

            // Zeigt Stage
            stage.show();

            // Setze die Exit-Bestätigung zurück, da wir nun zum Hauptmenü zurückgekehrt sind
            exitConfirmation = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        //Initialize the countdown label and start the countdown
        countdownLabel.setText("Time remaining: " + secondsRemaining + " seconds");

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (secondsRemaining > 0) {
                    secondsRemaining--;
                    countdownLabel.setText("Time remaining: " + secondsRemaining + " seconds");
                } else {
                    countdownLabel.setText("Time's up!");
                    timeline.stop();
                }
            }
        }));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void keyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE) {
            if (!isPaused) {
                pauseGame();
                isPaused = true;
            } else {
                resumeGame();
                isPaused = false;
            }
        } else {
            pgn.setImage(pgnAnim);
            switch (event.getCode()) {
                //WASD + ARROW KEYS
                case W, UP:
                    pgn.setRotate(0);
                    upPressed = true;
                    leftPressed = false;
                    downPressed = false;
                    rightPressed = false;
                    break;
                case A, LEFT:
                    pgn.setRotate(-90);
                    upPressed = false;
                    leftPressed = true;
                    downPressed = false;
                    rightPressed = false;
                    break;
                case S, DOWN:
                    pgn.setRotate(180);
                    upPressed = false;
                    downPressed = true;
                    leftPressed = false;
                    rightPressed = false;
                    break;
                case D, RIGHT:
                    pgn.setRotate(90);
                    upPressed = false;
                    rightPressed = true;
                    leftPressed = false;
                    downPressed = false;
                    break;
            }
        }
    }

    public void keyReleased(KeyEvent event) {
        pgn.setImage(pgnStill);
        switch (event.getCode()) {
            //WASD + ARROW KEYS
            case W, UP:
                upPressed = false;
                break;
            case A, LEFT:
                leftPressed = false;
                break;
            case S, DOWN:
                downPressed = false;
                break;
            case D, RIGHT:
                rightPressed = false;
                break;
        }
    }
     public void continuousMovement() {
        new AnimationTimer() {
            @Override
            public void handle(long now) {

                    double possibleX = newX;
                    double possibleY = newY;
                    double possibleXWithPadding = possibleX;
                    double possibleYWithPadding = possibleY;

                    if (upPressed) {
                        possibleY -= speed;
                        possibleYWithPadding = possibleY - GameSettings.scale * 2;
                    }
                    if (downPressed) {
                        possibleY += speed;
                        possibleYWithPadding = possibleY + GameSettings.scale * 2;
                    }
                    if (leftPressed){
                        possibleX -= speed;
                        possibleXWithPadding = possibleX - GameSettings.scale * 2;
                    }
                    if (rightPressed) {
                        possibleX += speed;
                        possibleXWithPadding = possibleX + GameSettings.scale * 2;
                    }

                if (canMoveTo(possibleXWithPadding, possibleYWithPadding)){
                        newX = possibleX;
                        newY = possibleY;
                    }

                camera.updateCamPos(newX,newY);

                pgn.setLayoutX(newX - (pgn.getFitHeight() / 2) -camera.getCameraX());
                pgn.setLayoutY(newY - (pgn.getFitHeight() / 2) -camera.getCameraY());

                tilePane.setTranslateX(-camera.getCameraX());
                tilePane.setTranslateY(-camera.getCameraY());
            }
        }.start();
    }
    public boolean canMoveTo(double newX, double newY) {
        return mazeM.getTileType((int) newX , (int) newY) == 0;
    }
}


