package Application.Controllers;

import Application.Database.DatabaseConnection;
import Application.Database.UserCollectedData;
import javafx.fxml.FXML;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class goalsPageController extends sceneLoaderController {
    protected UserCollectedData userCollectedData;

    public void initialize() throws SQLException {
        //get the info on the current user
        String currentStudentQuery = "SELECT * FROM User_Collected_Data where StudentNumber = ?";
        PreparedStatement statement = userCollectedDataDAO.getDBConnection().prepareStatement(currentStudentQuery);
        statement.setString(1,currentUserNumber);
        ResultSet resultSet = statement.executeQuery();
    }

    public void hideGoals(){

    }
    public void hideData(){

    }
    public void updateData(){

    }
    public void editGoals(){

    }
    public void displayDataChangesOverTime(){

    }
    public void generateAIRecommendedGoals(){

    }
}
