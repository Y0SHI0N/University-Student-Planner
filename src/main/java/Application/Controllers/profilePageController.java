package Application.Controllers;

import Application.Database.DatabaseConnection;
import Application.Database.UserSignup;
import Application.Database.UserSignupDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class profilePageController extends sceneLoaderController {
    @FXML private Label firstName;
    @FXML private Label lastName;
    @FXML private Label Email;
    @FXML private Label phoneNumber;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField EmailField;
    @FXML private TextField phoneNumberField;
    @FXML private Button editButton;
    @FXML private Button cancelButton;
    @FXML private Button updateButton;
    @FXML private Text notice;
    private UserSignup userSignup;

    public void initialize(){
        try {
            //making a test student num var since there doesn't seem to be anything tracking the ID of the current user rn
            String testStudentNumber = "50";
            String profileInfoQuery = "SELECT * FROM User_Signup_Data where StudentNumber = ?";
            PreparedStatement statement = userSignupDAO.getDBConnection().prepareStatement(profileInfoQuery);
            statement.setString(1,testStudentNumber);
            ResultSet resultSet = statement.executeQuery();

            //make a UserSignup instance to use for this page
            userSignup = new UserSignup(testStudentNumber
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

    public void editButtonOnClick(MouseEvent click) {
        //hide labels and button
        firstName.setVisible(false);
        lastName.setVisible(false);
        Email.setVisible(false);
        phoneNumber.setVisible(false);
        editButton.setVisible(false);
        //show elements for editing form
        firstNameField.setVisible(true);
        lastNameField.setVisible(true);
        EmailField.setVisible(true);
        phoneNumberField.setVisible(true);
        cancelButton.setVisible(true);
        updateButton.setVisible(true);
        //set fields to saved values when opening (done everytime so changes made before hitting cancel are forgotten)
        firstNameField.setText(userSignup.getFirstName());
        lastNameField.setText(userSignup.getLastName());
        EmailField.setText(userSignup.getEmail());
        phoneNumberField.setText(userSignup.getPhoneNumber());
    }

    private  void hideForm(){
        //show labels and button
        firstName.setVisible(true);
        lastName.setVisible(true);
        Email.setVisible(true);
        phoneNumber.setVisible(true);
        editButton.setVisible(true);
        //hide elements for editing form
        firstNameField.setVisible(false);
        lastNameField.setVisible(false);
        EmailField.setVisible(false);
        phoneNumberField.setVisible(false);
        cancelButton.setVisible(false);
        updateButton.setVisible(false);
        notice.setVisible(false);
    }

    public void cancelButtonOnClick(MouseEvent click) {
        hideForm();
    }

    public Boolean validateChanges(){
        String[] fields = {firstNameField.getText(),lastNameField.getText(),EmailField.getText(),phoneNumberField.getText()};
        //check no fields are empty
        for (String value : fields){
            if (value.isEmpty()){
                notice.setText("don't leave fields empty");
                return false;
            }
        }
        //check fields have been changed
        String[] currentValues = {userSignup.getFirstName(),userSignup.getLastName()
                ,userSignup.getEmail(),userSignup.getPhoneNumber()};
        if (Arrays.equals(fields,currentValues)) {
            notice.setText("no fields have been changed");
            notice.setVisible(true);
            notice.setFill(Color.RED);
            return false;
        }


        return true;
    }

    public void updateButtonOnClick() {
        if (validateChanges()){
            userSignup.setFirstName(firstNameField.getText());
            userSignup.setLastName(lastNameField.getText());
            userSignup.setEmail(EmailField.getText());
            userSignup.setPhoneNumber(phoneNumberField.getText());

            try {
                userSignupDAO.updateUser(userSignup);

                hideForm();
                firstName.setText(userSignup.getFirstName());
                lastName.setText(userSignup.getLastName());
                Email.setText(userSignup.getEmail());
                phoneNumber.setText(userSignup.getPhoneNumber());

                notice.setText("changes saved successfully");
                notice.setVisible(true);
                notice.setFill(Color.GREEN);
            }catch (SQLException e) {
                notice.setText("an error has occurred");
                notice.setVisible(true);
                notice.setFill(Color.RED);
                System.out.println("Update failed: "+e);
            }
        }
    }
    

}