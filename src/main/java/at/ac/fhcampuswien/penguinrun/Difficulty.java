package at.ac.fhcampuswien.penguinrun;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Difficulty {
    @FXML
    private Button backBtn;

    public void onBack() throws IOException {
        Stage stage = (Stage) backBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("start-menu.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 1270,720);
        scene.getStylesheets().add(getClass().getResource("/at/ac/fhcampuswien/penguinrun/style.css").toExternalForm());
        stage.setTitle("PenguinRun");
        stage.setScene(scene);
    }
}
