module at.ac.fhcampuswien.penguinrun {
    requires javafx.controls;
    requires javafx.fxml;


    opens at.ac.fhcampuswien.penguinrun to javafx.fxml;
    exports at.ac.fhcampuswien.penguinrun;
    exports at.ac.fhcampuswien.penguinrun.game;
    opens at.ac.fhcampuswien.penguinrun.game to javafx.fxml;
}