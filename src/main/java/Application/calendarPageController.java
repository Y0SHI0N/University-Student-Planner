package Application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.SQLException;

public class calendarPageController extends sceneLoaderController{
protected Connection dBConnection;

@FXML
public void initialize() {
    try {
        setDbConnection();
    } catch (SQLException e) {
        System.err.println("Error initializing database connection: " + e.getMessage());
    }
}

public void setDbConnection() throws SQLException {
    dBConnection = DatabaseConnection.getInstance().getConnection();
}
}
