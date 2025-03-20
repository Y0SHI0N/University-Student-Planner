package Application.Controllers;

import Application.sceneLoaderController;
import Application.DatabaseConnection;
import javafx.fxml.FXML;
import java.sql.Connection;
import java.sql.SQLException;

public class calendarPageController extends sceneLoaderController {
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
    dBConnection = DatabaseConnection.getInstance();
}
}
