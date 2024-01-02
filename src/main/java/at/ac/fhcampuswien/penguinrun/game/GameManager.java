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
    public double newX;
    public double newY;

    private Camera camera;

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

    @Override
    public void initialize(URL location, ResourceBundle resources){
        camera = new Camera(GameSettings.windowWidth, GameSettings.windowHeight,mapHeight);
        continuousMovement();
    }
    public void generateMaze(int sizeBoard){
        MazeManager maze = new MazeManager(sizeBoard,tilePane);
        maze.generateMaze();
        pgn.setFitHeight(GameSettings.scale * 8);
        pgn.setFitWidth(GameSettings.scale * 8);
        newX = (pgn.getFitWidth() / 2) * -1;
        newY = (pgn.getFitWidth() / 2) * -1;
        pgn.setLayoutX((pgn.getFitWidth() / 2) * -1);
        pgn.setLayoutY((pgn.getFitHeight() / 2) * - 1);
        pgn.setX(pgn.getFitWidth() / 2);
        pgn.setY(GameSettings.scale * 15);
    }


    public void keyPressed(KeyEvent event) {
        pgn.setImage(pgnAnim);
        switch (event.getCode()) {
            //WASD + ARROW KEYS
            case W, UP:
                pgn.setRotate(0);
                upPressed = true;
                break;
            case A, LEFT:
                pgn.setRotate(-90);
                leftPressed = true;
                break;
            case S, DOWN:
                pgn.setRotate(180);
                downPressed = true;
                break;
            case D, RIGHT:
                pgn.setRotate(90);
                rightPressed = true;
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

                    double positionX = newX - camera.getCameraX();
                    double positionY = newY - camera.getCameraY();
                    double playerHeight = pgn.getFitHeight();
                    double playerWidth = pgn.getFitWidth();

                    if (upPressed && positionY > (-2 * playerHeight)) {
                        newY -= speed;
                    }
                    if (downPressed && positionY < GameSettings.windowHeight - (2.7 * playerHeight)) {
                        newY += speed;
                    }
                    if (leftPressed && positionX > (-0.7 * playerWidth)) {
                        newX -= speed;
                    }
                    if (rightPressed && positionX < GameSettings.windowWidth - (1.3 * playerWidth)) {
                        newX += speed;
                    }

                    camera.updateCamPos(newX,newY);

                pgn.setLayoutX(newX-camera.getCameraX());
                pgn.setLayoutY(newY-camera.getCameraY());

                tilePane.setTranslateX(-camera.getCameraX());
                tilePane.setTranslateY(-camera.getCameraY());

            }
        }.start();
    }
}


