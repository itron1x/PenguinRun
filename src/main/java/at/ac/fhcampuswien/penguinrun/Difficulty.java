package at.ac.fhcampuswien.penguinrun;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Difficulty {
    @FXML
    private Button backBtn;
    public static int difficulty;

    public void onBack() throws IOException {
        Stage stage = (Stage) backBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("start-menu.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 1280,720);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/at/ac/fhcampuswien/penguinrun/style.css")).toExternalForm());
        stage.setTitle("PenguinRun");
        stage.setScene(scene);
    }
    public void setEasy(ActionEvent action) {
        difficulty = 50;
    }
    public void setMiddle(ActionEvent action) {
        difficulty = 150;
    }
    public void setHard(ActionEvent action) {
        difficulty = 300;
    }
}
