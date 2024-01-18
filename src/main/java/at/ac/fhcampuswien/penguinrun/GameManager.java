package at.ac.fhcampuswien.penguinrun;

import at.ac.fhcampuswien.penguinrun.game.*;
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
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.scene.control.Label;
import javafx.util.Duration;
import at.ac.fhcampuswien.penguinrun.game.Countdown;

public class GameManager implements Initializable {
    public double mapHeight = (10 * GameSettings.SCALE) * Difficulty.getDifficulty();
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
    private Pane gameWon;
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
    @FXML
    private ImageView volumeImage;
    private Timeline timeline;
    private boolean isPaused = false;
    private boolean exitConfirmation = false;
    private boolean upPressed = false; //W + UP
    private boolean downPressed = false; //S + DOWN
    private boolean leftPressed = false; //A + LEFT
    private boolean rightPressed = false; //D + RIGHT
    private boolean won = false;
    private boolean stopTimer = false;
    private static final Image pgnStill = new Image(Objects.requireNonNull(GameManager.class.getResource("img/pgnStill.png")).toExternalForm(), true);
    private static final Image pgnAnim = new Image(Objects.requireNonNull(GameManager.class.getResource("img/pgnAnim.gif")).toExternalForm(), true);
    private MazeManager mazeM;
    private Countdown countdownTimer;
    private Timeline labelUpdater;

    public GameManager() {
        volumeSlider = MediaManager.volumeSlider;
        volumeImage = MediaManager.volumeImage;
    }

    /**
     * Getter for the dim background.
     *
     * @return Rectangle for dimming the background.
     */
    public Rectangle getDimmBackground() {
        return dimmBackground;
    }

    /**
     * Getter for the text displayed in the beginning of a game.
     *
     * @return the pane with the start text.
     */
    public Pane getStartText() {
        return startText;
    }

    /**
     * Creates maze, sets the correct size of the player and its start position.
     *
     * @param sizeBoard the size of the maze.
     */
    public void generateMaze(int sizeBoard) {
        mazeM = new MazeManager(sizeBoard, tilePane);
        mazeM.generateMaze();
        pgn.setFitHeight(GameSettings.SCALE * 8);
        pgn.setFitWidth(GameSettings.SCALE * 8);
        newY = GameSettings.SCALE * 15;
        newX = GameSettings.SCALE * 5;
    }

    /**
     * Initializes the Camera Movement that follows the penguin (user) around the map,
     * the Continuous Movement method,
     * and the Countdown Timer used to let the player know how much time they have left.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     *                  the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        camera = new Camera(GameSettings.WINDOW_WIDTH, GameSettings.WINDOW_HEIGHT, mapHeight);
        continuousMovement();

        // initialize Countdown
        countdownTimer = new Countdown(200);
        countdownLabel.setText(countdownTimer.getSecondsRemaining() + " seconds");

        // Set up a Timeline to update the label every second
        labelUpdater = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    int secondsRemaining = countdownTimer.getSecondsRemaining();
                    if (secondsRemaining > 0) {
                        countdownLabel.setText(secondsRemaining + " seconds");
                    } else {
                        countdownLabel.setText("Time's up!");
                        labelUpdater.stop(); // Stop updating the label once the time is up
                    }
                })
        );
        labelUpdater.setCycleCount(Animation.INDEFINITE);

        volumeSlider.setValue(GameSettings.volume);
        if (GameSettings.volume == 0) volumeImage.setImage(MediaManager.volumeOff);
        else volumeImage.setImage(MediaManager.volumeOn);
        volumeSlider.valueProperty().
                addListener((observable, oldValue, newValue) ->
                {
                    MediaManager.updateVolume(newValue.doubleValue(), volumeImage);
                });
    }
    public void startTimer(KeyEvent event){
        countdownTimer.start();
        labelUpdater.play();
    }

    /**
     * Pauses the game by performing the following actions:
     * Sets the flag 'stopTimer' to true, indicating that the game timer should be paused.
     * Makes the pause dimming overlay visible, providing a visual indication of the game pause.
     * Displays the pause menu, allowing the player to access various options during the pause.
     * Pauses the timeline
     */
    private void pauseGame() {
        stopTimer = true;
        pauseDimm.setVisible(true);
        pauseMenu.setVisible(true);
        countdownTimer.pause();
    }

    /**
     * Resumes the game, setting it back to normal state by hiding the pause dimming overlay,
     * displaying the game TilePane, and hiding the pause menu. Additionally, any safety prompts
     * or exit confirmations are hidden, and the game timer is allowed to continue.
     */
    public void resumeGame() {
        stopTimer = false;
        pauseDimm.setVisible(false);
        tilePane.setVisible(true);
        pauseMenu.setVisible(false);
        safe.setVisible(false);
        exitConfirmation = false;
        countdownTimer.start();
        isPaused = false;
    }

    /**
     * Handles the transition back to the main menu. If the exit confirmation is activated, it performs
     * the actual return to the main menu. Otherwise, it displays the exit confirmation prompt.
     */
    public void backToMainMenu() {
        if (exitConfirmation) {
            performBackToMainMenu();
            safe.setVisible(false);
        } else {
            showExitConfirmation();
        }
    }

    /**
     * Displays the exit confirmation prompt, activating the exitConfirmation flag and showing the
     * confirmation message.
     */
    private void showExitConfirmation() {
        exitConfirmation = true;
        safe.setVisible(true);
    }

    /**
     * Performs the transition back to the main menu. Loads the start menu FXML file, creates a new
     * scene with the start menu, sets the CSS styling, and sets the scene on the current stage (window).
     * Shows the stage and resets the exit confirmation flag as the return to the main menu is completed.
     */
    private void performBackToMainMenu() {
        try {
            // Load the start menu FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("start-menu.fxml"));
            Pane startMenuPane = loader.load();

            Scene startMenuScene = new Scene(startMenuPane, GameSettings.WINDOW_WIDTH, GameSettings.WINDOW_HEIGHT);

            startMenuScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());

            Stage stage = (Stage) tilePane.getScene().getWindow();
            stage.setScene(startMenuScene);
            stage.show();

            exitConfirmation = false;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Displays the win screen when called. Sets the 'won' flag to true, makes the background dimmed,
     * shows the win screen, and pauses the timeline.
     */
    public void winScreen() {
        won = true;
        gameWon.setVisible(true);
        System.out.println("Win");
        countdownTimer.pause();
    }

    /**
     * Handles the key press events for game controls and pause functionality.
     * If ESCAPE is pressed, the game is either paused or resumed, depending on the current state.
     * Movement controls are processed if the game is not won and not paused.
     *
     * @param event The KeyEvent triggered by pressing a key.
     */
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

    /**
     * Handles key release events to stop movement in the respective direction.
     *
     * @param event The KeyEvent triggered by releasing a key.
     */
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

    /**
     * Continuously updates the character's position if movement keys are pressed.
     * This method is called in every frame while the AnimationTimer is active.
     */
    public void continuousMovement() {
        new AnimationTimer() {
            @Override
            public void handle(long now) {

                if (countdownTimer.getSecondsRemaining() <= 0) {
                    return;
                }
                double borderStart = pgn.getLayoutX();
                double playerWidth = pgn.getFitWidth();

                double possibleX = newX;
                double possibleY = newY;
                double possibleXWithPadding = possibleX;
                double possibleYWithPadding = possibleY;

                if (upPressed) {
                    possibleY -= GameSettings.SPEED;
                    possibleYWithPadding = possibleY - GameSettings.SCALE * 2;
                }
                if (downPressed) {
                    possibleY += GameSettings.SPEED;
                    possibleYWithPadding = possibleY + GameSettings.SCALE * 2;
                }
                if (leftPressed && borderStart > -15) {
                    possibleX -= GameSettings.SPEED;
                    possibleXWithPadding = possibleX - GameSettings.SCALE * 2;
                }
                if (rightPressed && borderStart < 1240) {
                    possibleX += GameSettings.SPEED;
                    possibleXWithPadding = possibleX + GameSettings.SCALE * 2;
                    if (borderStart > 1250 - playerWidth) {
                        winScreen();
                    }
                }

                if (canMoveTo(possibleXWithPadding, possibleYWithPadding)) {
                    newX = possibleX;
                    newY = possibleY;
                }

                camera.updateCamPos(newX, newY);

                pgn.setLayoutX(newX - (pgn.getFitHeight() / 2) - camera.getCameraX());
                pgn.setLayoutY(newY - (pgn.getFitHeight() / 2) - camera.getCameraY());

                tilePane.setTranslateX(-camera.getCameraX());
                tilePane.setTranslateY(-camera.getCameraY());
            }
        }.start();
    }

    /**
     * Determines if the character can move to the specified new X and Y positions based on tile type.
     *
     * @param newX The potential new X position for the character.
     * @param newY The potential new Y position for the character.
     * @return true if the character can move to the position, false otherwise.
     */
    public boolean canMoveTo(double newX, double newY) {
        return mazeM.getTileType((int) newX, (int) newY) == 0;
    }

    /**
     * Toggles the visibility and enablement of the volume slider.
     * If the volume slider is not visible (opacity is 0), it sets it to visible and enables it.
     * If the volume slider is visible, it disables and hides it by setting the opacity to 0.
     */
    public void changeVolume() {
        if (volumeSlider.getOpacity() == 0) {
            volumeSlider.setDisable(false);
            volumeSlider.setOpacity(1);
        } else {
            volumeSlider.setDisable(true);
            volumeSlider.setOpacity(0);
        }
    }
}