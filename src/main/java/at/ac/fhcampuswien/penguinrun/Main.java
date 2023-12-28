package at.ac.fhcampuswien.penguinrun;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Font.loadFont(getClass().getResource("Maze.ttf").toExternalForm(),10);
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("start-menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);

        Image icon = new Image("/img/pgnBig.png");
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/at/ac/fhcampuswien/penguinrun/style.css")).toExternalForm());
        stage.setTitle("PenguinRun");
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}