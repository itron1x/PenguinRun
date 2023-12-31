package at.ac.fhcampuswien.penguinrun.game;

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

    public void generateMaze(int sizeBoard){
        MazeManager maze = new MazeManager(sizeBoard,tilePane);
        maze.generateMaze();
        pgn.setFitHeight(GameSettings.scale * 8);
        pgn.setFitWidth(GameSettings.scale * 8);
        pgn.setLayoutX((pgn.getFitWidth() / 2) * -1);
        pgn.setLayoutY((pgn.getFitHeight() / 2) * - 1);
        pgn.setX(pgn.getFitWidth() / 2);
        pgn.setY(GameSettings.scale * 15);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        continuousMovement();
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

    private void continuousMovement() {
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                double newX = pgn.getLayoutX();
                double newY = pgn.getLayoutY();
                double playerWidth = pgn.getFitWidth();
                double playerHeight = pgn.getFitHeight();

                if (upPressed && newY > (-2 * playerHeight)) {
                    newY -= speed;
                }
                if (downPressed && newY < 720 - (2.7 * playerHeight)) {
                    newY += speed;
                }
                if (leftPressed && newX > (-0.7 * playerWidth)) {
                    newX -= speed;
                }
                if (rightPressed && newX < 1280 - (1.3 * playerWidth)) {
                    newX += speed;
                }

                pgn.setLayoutX(newX);
                pgn.setLayoutY(newY);
            }
        }.start();
    }
}

