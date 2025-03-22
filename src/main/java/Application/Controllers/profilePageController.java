package Application.Controllers;

import Application.Database.DatabaseConnection;
import Application.Database.UserSignup;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class profilePageController extends sceneLoaderController {
    @FXML private Label firstName;
    @FXML private Label lastName;
    @FXML private Label Email;
    @FXML private Label phoneNumber;
    public void initialize(){
        try {
            //making a test student num var since there doesn't seem to be anything tracking the ID of the current user rn
            String testStudentNumber = "50";
            String profileInfoQuery = "SELECT * FROM User_Signup_Data where StudentNumber = ?";
            PreparedStatement statement = userSignupDAO.getDBConnection().prepareStatement(profileInfoQuery);
            statement.setString(1,testStudentNumber);
            ResultSet resultSet = statement.executeQuery();

            //make a UserSignup instance to use for this page
            UserSignup userSignup = new UserSignup(testStudentNumber
                    ,resultSet.getString("FirstName")
                    ,resultSet.getString("LastName")
                    ,resultSet.getString("Email")
                    ,resultSet.getString("PhoneNumber")
                    ,resultSet.getString("LoginPassword"));
            //set labels to user info
            firstName.setText("First name: "+userSignup.getFirstName());
            lastName.setText("Last Name: "+userSignup.getLastName());
            Email.setText("Email: "+userSignup.getEmail());
            phoneNumber.setText("Phone number: "+userSignup.getPhoneNumber());

        }catch(Exception e){
            System.out.println(e + "exception");
        }

    }
}
