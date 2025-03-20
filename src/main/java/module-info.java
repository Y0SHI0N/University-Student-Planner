module Application {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    // Open package to JavaFX runtime
    opens Application.Controllers to javafx.graphics, javafx.fxml;

    exports Application;
    exports Application.Controllers;

}