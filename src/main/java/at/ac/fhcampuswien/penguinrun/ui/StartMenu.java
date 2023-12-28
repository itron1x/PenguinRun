package at.ac.fhcampuswien.penguinrun.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class StartMenu {

    @FXML
    private Button startBtn;

    public void onStart(ActionEvent event) throws IOException {
        Stage stage = (Stage) startBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("difficulty.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 1280,720);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/at/ac/fhcampuswien/penguinrun/style.css")).toExternalForm());
        stage.setTitle("PenguinRun");
        stage.setScene(scene);
    }
}
