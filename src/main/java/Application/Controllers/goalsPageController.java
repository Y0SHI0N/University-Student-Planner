package Application.Controllers;

import Application.Database.DatabaseConnection;
import Application.Database.UserCollectedData;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class goalsPageController extends sceneLoaderController {
    protected UserCollectedData userCollectedData;
    //FXML page components
    @FXML Text hoursStudiedKPI;
    @FXML Text hoursStudiedGoal;
    @FXML Text GPAKPI;
    @FXML Text GPAGoal;
    @FXML Text attendanceKPI;
    @FXML Text attendanceRateGoal;
    @FXML Button editGoalButton;
    @FXML Button updateUserDataButton;


    public void initialize() throws SQLException {
        //get the info on the current user
        String currentStudentQuery = "SELECT * FROM User_Collected_Data where StudentNumber = ?";
        PreparedStatement statement = userCollectedDataDAO.getDBConnection().prepareStatement(currentStudentQuery);
        statement.setString(1,currentUserNumber);
        ResultSet resultSet = statement.executeQuery();

        //create a row instance to access the data
        userCollectedData = new UserCollectedData(
                resultSet.getString("studentNumber"),
                resultSet.getString("dateModified"),
                resultSet.getInt("gpa"),
                resultSet.getInt("gpaGoal"),
                resultSet.getString("hoursStudied"),
                resultSet.getString("hoursStudiedGoal"),
                resultSet.getDouble("attendanceRate"),
                resultSet.getDouble("attendanceRateGoal"),
                resultSet.getString("unitsEnrolled")
                );
        //set the labels to the data
        initialiseTextFields(userCollectedData);
    }

    public void initialiseTextFields(UserCollectedData userCollectedData){
        if(userCollectedData.getHoursStudied() != ""){
        hoursStudiedKPI.setText("acheived: " + userCollectedData.getHoursStudied());
        }
        if(userCollectedData.getHoursStudiedGoal() != "") {
            hoursStudiedGoal.setText("goal: " + userCollectedData.getHoursStudiedGoal());
        }
        if(userCollectedData.getGpa() != 0) {// defaults 0
            GPAKPI.setText("acheived: " + userCollectedData.getGpa());
        }
        if(userCollectedData.getGpaGoal() != 0) {
            GPAGoal.setText("goal: " + userCollectedData.getGpaGoal());
        }
        if(userCollectedData.getAttendanceRate() != 0) {
            attendanceKPI.setText("acheived: " + userCollectedData.getAttendanceRate());
        }
        if(userCollectedData.getAttendanceRateGoal() != 0) {
            attendanceRateGoal.setText("goal: " + userCollectedData.getAttendanceRateGoal());
        }
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
