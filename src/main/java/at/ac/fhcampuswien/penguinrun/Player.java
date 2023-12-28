package at.ac.fhcampuswien.penguinrun;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class Player implements Initializable {

    @FXML
    private ImageView pgn;

    private boolean upPressed = false; //W + UP
    private boolean downPressed = false; //S + DOWN
    private boolean leftPressed = false; //A + LEFT
    private boolean rightPressed = false; //D + RIGHT
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupKeyHandlers();
    }

    public void handleKeyPressed(KeyEvent event) {
        switch (event.getCode()) {
            //WASD + ARROW KEYS
            case W, UP:
                upPressed = true;
                break;
            case A, LEFT:
                leftPressed = true;
                break;
            case S, DOWN:
                downPressed = true;
                break;
            case D, RIGHT:
                rightPressed = true;
                break;
        }
    }

    public void handleKeyReleased(KeyEvent event) {
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

    private void setupKeyHandlers() {
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (upPressed) { pgn.setLayoutY(pgn.getLayoutY() - 2); }
                if (downPressed) { pgn.setLayoutY(pgn.getLayoutY() + 2); }
                if (leftPressed) { pgn.setLayoutX(pgn.getLayoutX() - 2); }
                if (rightPressed) { pgn.setLayoutX(pgn.getLayoutX() + 2); }
            }
        }.start();
    }
}
