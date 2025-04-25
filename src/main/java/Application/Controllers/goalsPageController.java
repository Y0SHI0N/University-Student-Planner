package Application.Controllers;

import Application.Database.DatabaseConnection;
import Application.Database.UserCollectedData;
import Application.Database.UserCollectedDataDAO;
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


    public void initialize() {
        try{
        //get the info on the current user
        String currentStudentQuery = "SELECT * FROM User_Collected_Data where StudentNumber = ?";
            PreparedStatement statement = userCollectedDataDAO.getDBConnection().prepareStatement(currentStudentQuery); // error happening here
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
        userCollectedDataDAO.insertData(userCollectedData);
        //set the labels to the data
        initialiseTextFields(userCollectedData);
        }catch(Exception e) {
        System.out.println(e);
        }
    }

    public void initialiseTextFields(UserCollectedData userCollectedData){
        // if statements check whether there is data collected for respective components to be shown
        if(userCollectedData.getHoursStudied() != ""){
        hoursStudiedKPI.setText("acheived: " + userCollectedData.getHoursStudied());
        }
        if(userCollectedData.getHoursStudiedGoal() != "") {
            hoursStudiedGoal.setText("goal: " + userCollectedData.getHoursStudiedGoal());
        }
        if(userCollectedData.getGpa() != 0) {
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

    public void displayGPAChanges(){

    }
    public void displayStudyChanges(){

    }
    public void displayAttendanceChanges(){

    }
    public void displayDataChangesOverTime(){
        displayGPAChanges();
        displayStudyChanges();
        displayAttendanceChanges();
    }

    public void generateAIRecommendedGoals(){

    }
}
