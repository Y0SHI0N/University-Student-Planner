module Application {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.zaxxer.hikari;

    // Open package to JavaFX runtime
    exports Application;
    opens Application to javafx.graphics, javafx.fxml;
}