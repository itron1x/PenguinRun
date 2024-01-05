package at.ac.fhcampuswien.penguinrun.game;

import at.ac.fhcampuswien.penguinrun.Difficulty;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
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
    @FXML
    private Text end;
    @FXML
    private Label countdownLabel;
    @FXML
    private Slider volumeSlider;
    @FXML
    private Rectangle dimmBackground;
    @FXML
    private Rectangle pauseDimm;
    @FXML
    private Pane startText;

    private int secondsRemaining = 200;
    private Timeline timeline;
    private boolean isPaused = false;
    private boolean exitConfirmation = false;
    private boolean upPressed = false; //W + UP
    private boolean downPressed = false; //S + DOWN
    private boolean leftPressed = false; //A + LEFT
    private boolean rightPressed = false; //D + RIGHT
    private boolean won = false;
    private boolean stopTimer = false;
    private final int speed = GameSettings.speed; //Movement Speed Penguin
    private static final Image pgnStill = new Image("img/pgnStill.png",true);
    private static final Image pgnAnim = new Image("img/pgnAnim.gif",true);
    private MazeManager mazeM;

    public Rectangle getDimmBackground() {
        return dimmBackground;
    }

    public Pane getStartText() {
        return startText;
    }

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
        countdownLabel.setText(secondsRemaining + " seconds");
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if (!stopTimer && secondsRemaining > 1) {
                secondsRemaining--;
                countdownLabel.setText(secondsRemaining + " seconds");
            } else if(!stopTimer && !won) {
                countdownLabel.setText("Time's up!");
                timeline.stop();
            } else {
                timeline.stop();
                winScreen();
            }
        }));

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
    public void startTimer(KeyEvent event){
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void pauseGame() {
        // Zeige das Pause-Menü
        stopTimer = true;
        pauseDimm.setVisible(true);
        pauseMenu.setVisible(true);
        timeline.pause();
    }

    public void resumeGame() {
        stopTimer = false;
        pauseDimm.setVisible(false);
        tilePane.setVisible(true);
        pauseMenu.setVisible(false);
        safe.setVisible(false);
        exitConfirmation = false;
        timeline.play();
        isPaused = false;
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

    public void winScreen(){
        won = true;
        dimmBackground.setVisible(true);
        System.out.println("Win");
        end.setVisible(true);
        timeline.pause();
    }

    public void keyPressed(KeyEvent event) {
        if (!won && event.getCode() == KeyCode.ESCAPE) {
            if (!isPaused) {
                pauseGame();
                isPaused = true;
            } else {
                resumeGame();
                isPaused = false;
            }
        } else {
            if (!won && !isPaused) {
                switch (event.getCode()) {
                    //WASD + ARROW KEYS
                    case W, UP:
                        pgn.setImage(pgnAnim);
                        pgn.setRotate(0);
                        upPressed = true;
                        leftPressed = false;
                        downPressed = false;
                        rightPressed = false;
                        break;
                    case A, LEFT:
                        pgn.setImage(pgnAnim);
                        pgn.setRotate(-90);
                        upPressed = false;
                        leftPressed = true;
                        downPressed = false;
                        rightPressed = false;
                        break;
                    case S, DOWN:
                        pgn.setImage(pgnAnim);
                        pgn.setRotate(180);
                        upPressed = false;
                        downPressed = true;
                        leftPressed = false;
                        rightPressed = false;
                        break;
                    case D, RIGHT:
                        pgn.setImage(pgnAnim);
                        pgn.setRotate(90);
                        upPressed = false;
                        rightPressed = true;
                        leftPressed = false;
                        downPressed = false;
                        break;
                }
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

                if (secondsRemaining <= 0) {
                    return;
                }


                    double borderStart = pgn.getLayoutX();
                    double playerWidth = pgn.getFitWidth();

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
                    if (leftPressed && borderStart > -15){
                        possibleX -= speed;
                        possibleXWithPadding = possibleX - GameSettings.scale * 2;
                    }
                    if (rightPressed && borderStart < 1240) {
                        possibleX += speed;
                        possibleXWithPadding = possibleX + GameSettings.scale * 2;
                        if (borderStart > 1250 - playerWidth) {
                            winScreen();
                        }
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


