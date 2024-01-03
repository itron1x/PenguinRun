package at.ac.fhcampuswien.penguinrun.game;

import at.ac.fhcampuswien.penguinrun.Difficulty;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.ResourceBundle;

public class GameManager implements Initializable {


    public double mapHeight = (10*GameSettings.scale)*Difficulty.getDifficulty();
    private double newX;
    private double newY;

    private Camera camera;
    private Label timerLabel;

    @FXML
    private VBox timerContainer;

    private Timer gameTimer;

    @FXML
    private TilePane tilePane;

    @FXML
    private ImageView pgn;

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
        newY = GameSettings.scale * 10 + 10;
        newX = GameSettings.scale * 10 + 10;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
        camera = new Camera(GameSettings.windowWidth, GameSettings.windowHeight,mapHeight);
        continuousMovement();
    }

    public void keyPressed(KeyEvent event) {
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


