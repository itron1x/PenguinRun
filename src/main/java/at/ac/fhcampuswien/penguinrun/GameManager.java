package at.ac.fhcampuswien.penguinrun;

import at.ac.fhcampuswien.penguinrun.game.*;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
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

import java.util.*;

import javafx.scene.control.Label;
import javafx.util.Duration;
import at.ac.fhcampuswien.penguinrun.game.Countdown;

public class GameManager{
    @FXML
    private TilePane tilePane;
    @FXML
    private Pane itemPane;
    @FXML
    private ImageView pgn;
    @FXML
    private Pane pauseMenu;
    @FXML
    private Text safe;
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
    @FXML
    private Pane gameOver;
    @FXML
    private Pane dimOverlay;
    @FXML
    private ImageView fogImage;
    @FXML
    private ImageView itemsCollected;
    private MazeManager mazeM;
    private Entities entitiesClass;
    private Countdown countdownTimer;
    private Timeline labelUpdater;
    private Camera camera;
    public double mapHeight = (10 * GameSettings.SCALE) * Difficulty.getDifficulty();
    private double playerX;
    private double playerY;
    private boolean isPaused = false;
    private boolean exitConfirmation = false;
    private boolean upPressed = false; //W + UP
    private boolean downPressed = false; //S + DOWN
    private boolean leftPressed = false; //A + LEFT
    private boolean rightPressed = false; //D + RIGHT
    private boolean won = false;
    private static final Image pgnStill = new Image(Objects.requireNonNull(GameManager.class.getResource("img/pgnStill.png")).toExternalForm(), true);
    private static final Image pgnAnim = new Image(Objects.requireNonNull(GameManager.class.getResource("img/pgnAnim.gif")).toExternalForm(), true);

    private static final Image keyCount0 = new Image(Objects.requireNonNull(GameManager.class.getResource("game/icons/keyCount0.png")).toExternalForm(),true);
    private static final Image keyCount1 = new Image(Objects.requireNonNull(GameManager.class.getResource("game/icons/keyCount1.png")).toExternalForm(),true);
    private static final Image keyCount2 = new Image(Objects.requireNonNull(GameManager.class.getResource("game/icons/keyCount2.png")).toExternalForm(),true);
    private static final Image keyCount3 = new Image(Objects.requireNonNull(GameManager.class.getResource("game/icons/keyCount3.png")).toExternalForm(),true);

    public GameManager() {
        volumeSlider = MediaManager.volumeSlider;
        volumeImage = MediaManager.volumeImage;
    }

    /**
     * Initializes the Camera Movement that follows the penguin (user) around the map,
     * the Continuous Movement method,
     * and the Countdown Timer used to let the player know how much time they have left.
     * The labelUpdater ensures that the countdown label displays the correct time.
     */
    public void initialize() {
        camera = new Camera(GameSettings.WINDOW_WIDTH, GameSettings.WINDOW_HEIGHT, mapHeight);
        continuousMovement();
        itemsCollected.setImage(keyCount0);
        // initialize Countdown
        countdownTimer = new Countdown(Countdown.getAccordingTime());
        countdownLabel.setText(countdownTimer.getSecondsRemaining() + " seconds");

        // Set up a Timeline to update the label every second
        labelUpdater = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    int secondsRemaining = countdownTimer.getSecondsRemaining();
                    if (secondsRemaining > 0) {
                        countdownLabel.setText(secondsRemaining + " seconds");
                    } else {
                        countdownLabel.setText("Time's up!");
                        gameOverScreen();
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
        mazeM = new MazeManager(sizeBoard, tilePane, itemPane);
        mazeM.generateMaze();
        entitiesClass = mazeM.getItems();
        pgn.setFitHeight(GameSettings.SCALE * 8);
        pgn.setFitWidth(GameSettings.SCALE * 8);
        playerY = GameSettings.SCALE * 15;
        playerX = GameSettings.SCALE * 5;
        setFogWindowSize();
    }

    public void tryAgain() {
        Scene difficulty = SceneManager.sceneList.get("difficulty");
        SceneManager.getInstance().initializeScenes();
        Stage stage = SceneManager.getInstance().getStage();
        stage.setScene(difficulty);

        gameOver.setVisible(false);
    }

    /**
     * Performs the transition back to the main menu. Loads the start menu FXML file, creates a new
     * scene with the start menu, sets the CSS styling, and sets the scene on the current stage (window).
     * Shows the stage and resets the exit confirmation flag as the return to the main menu is completed.
     */
    public void backToMainMenu() {
        Scene mainMenu = SceneManager.sceneList.get("mainMenu");
        SceneManager.getInstance().initializeScenes();
        Stage stage = SceneManager.getInstance().getStage();
        stage.setScene(mainMenu);

        exitConfirmation = false;
    }

    /**
     * Handles the transition back to the main menu. If the exit confirmation is activated, it performs
     * the actual return to the main menu. Otherwise, it displays the exit confirmation prompt.
     */
    public void confirmationMainMenu() {
        if (exitConfirmation) {
            backToMainMenu();
            safe.setVisible(false);
        } else {
            showExitConfirmation();
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

    public void gameOverScreen() {
        dimOverlay.setVisible(true);
        gameOver.setVisible(true);
    }

    /**
     * Pauses the game by performing the following actions:
     * Makes the pause dimming overlay visible, providing a visual indication of the game pause.
     * Displays the pause menu, allowing the player to access various options during the pause.
     * Pauses the timeline
     */
    private void pauseGame() {
        pauseDimm.setVisible(true);
        pauseMenu.setVisible(true);
        countdownTimer.pause();
        labelUpdater.pause();
    }

    /**
     * Resumes the game, setting it back to normal state by hiding the pause dimming overlay,
     * displaying the game TilePane, and hiding the pause menu. Additionally, any safety prompts
     * or exit confirmations are hidden, and the game timer is allowed to continue.
     */
    public void resumeGame() {
        pauseDimm.setVisible(false);
        tilePane.setVisible(true);
        pauseMenu.setVisible(false);
        safe.setVisible(false);
        exitConfirmation = false;
        countdownTimer.resume();
        labelUpdater.play();
        isPaused = false;
    }

    /**
     * Displays the exit confirmation prompt, activating the exitConfirmation flag and showing the
     * confirmation message.
     */
    private void showExitConfirmation() {
        exitConfirmation = true;
        safe.setVisible(true);
    }

    public void startTimer(KeyEvent event) {
        countdownTimer.start();
        labelUpdater.play();
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
        if (!upPressed && !leftPressed &&!downPressed &&!rightPressed) pgn.setImage(pgnStill);
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

                double possibleX = playerX;
                double possibleY = playerY;
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
                    playerX = possibleX;
                    playerY = possibleY;
                }


                camera.updateCamPos(playerX, playerY);

                updateFog(playerX, playerY, camera.getCameraX(), camera.getCameraY());

                pgn.setLayoutX(playerX - (pgn.getFitHeight() / 2) - camera.getCameraX());
                pgn.setLayoutY(playerY - (pgn.getFitHeight() / 2) - camera.getCameraY());

                tilePane.setTranslateX(-camera.getCameraX());
                tilePane.setTranslateY(-camera.getCameraY());

                itemPane.setTranslateX(-camera.getCameraX());
                itemPane.setTranslateY(-camera.getCameraY());
                entitiesClass.itemCollision(playerY, playerX);

                if (entitiesClass.getItemCount() == 3) {
                    itemsCollected.setImage(keyCount3);
                    mazeM.finishTile();
                    mazeM.setWon();
                }if (entitiesClass.getItemCount()==2){
                    itemsCollected.setImage(keyCount2);
                }if (entitiesClass.getItemCount() == 1){
                    itemsCollected.setImage(keyCount1);
                }
            }
        }.start();
    }

    /***
     * This method updates the position of the fog. It is supposed to follow the character at all times.
     * @param pgnX this is the x coordinate of the character
     * @param pgnY this is the y coordinate of the character
     * @param camX this is the x coordinate of the camera
     * @param camY this is the y coordinate of the camera
     */
    private void updateFog(double pgnX, double pgnY, double camX, double camY) {
        fogImage.setTranslateX(pgnX - fogImage.getFitWidth() / 2 - camX);
        fogImage.setTranslateY(pgnY - fogImage.getFitHeight() / 2 - camY);
    }

    public void setFogWindowSize() {
        int difficulty = Difficulty.getDifficulty();
        if (difficulty == GameSettings.EASY) {
            fogImage.setFitWidth(6000);
            fogImage.setFitHeight(3500);
        } else if (difficulty == GameSettings.MIDDLE) {
            fogImage.setFitWidth(5500);
            fogImage.setFitHeight(3200);
        } else {
            fogImage.setFitWidth(3500);
            fogImage.setFitHeight(2000);
        }
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
