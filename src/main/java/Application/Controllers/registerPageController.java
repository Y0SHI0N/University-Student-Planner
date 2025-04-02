package Application.Controllers;

import Application.Database.UserSignup;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.sql.*;
import java.util.stream.Stream;

public class registerPageController extends sceneLoaderController {

    @FXML private TextField registerFirstName;
    @FXML private TextField registerLastName;
    @FXML private TextField registerEmail;
    @FXML private PasswordField registerPassword;
    @FXML private PasswordField confirmRegisterPassword;
    @FXML private Label registerErrorMessagesText;
    @FXML private TextField registerStudentNumber;

//    public void registerUser() {
//        String studentNumber = registerStudentNumber.getText();
//        String firstName = registerFirstName.getText();
//        String lastName = registerLastName.getText();
//        String email = registerEmail.getText();
//        String password = registerPassword.getText();
//        String confirmedPassword = confirmRegisterPassword.getText();
//
//        if (studentNumber.equals("") || firstName.equals("") || lastName.equals("") || email.equals("") || password.equals("") || confirmedPassword.equals("")) {
//            registerErrorMessagesText.setText("don't leave fields empty");
//            return;
//        } else if (confirmedPassword.equals(password) == false) {
//            registerErrorMessagesText.setText("passwords don't match");
//            return;
//        } else {
//            boolean isDuplicate = checkDuplicateStudentNumbers(studentNumber);
//            if (isDuplicate == true) {
//                registerErrorMessagesText.setText("student number is already registered");
//                return;
//            }
//            else {
//                try {
//                    String addUserQuery = "INSERT INTO User_Signup_Data(StudentNumber, FirstName, LastName, Email, LoginPassword) VALUES(?, ?, ?, ?, ?);";
//                    PreparedStatement statement = userSignupDAO.getDBConnection().prepareStatement(addUserQuery);
//                    statement.setString(1,studentNumber);
//                    statement.setString(2,firstName);
//                    statement.setString(3,lastName);
//                    statement.setString(4,email);
//                    statement.setString(5,password);
//
//                    statement.execute();
//                    changeScene("/FXML/Login-Page.fxml");
//                } catch (Exception e) {
//                    javafx.application.Platform.exit(); // exception exits platform
//                }
//            }
//        }
//    }

    public void registerUser() {
        String studentNumber = normaliseStudentNo(registerStudentNumber.getText());
        String firstName = registerFirstName.getText();
        String lastName = registerLastName.getText();
        String email = registerEmail.getText();
        String password = registerPassword.getText();
        String confirmedPassword = confirmRegisterPassword.getText();

        String errorMessage = inputValidation(studentNumber,firstName,lastName,email,password,confirmedPassword);
        if (errorMessage != null){
            registerErrorMessagesText.setText(errorMessage);
            return;
        }

        try {
            UserSignup userToSignUp = new UserSignup(studentNumber,firstName,lastName,email, "", password);
            userSignupDAO.insertUser(userToSignUp);
            switchToLoginPage();
        } catch (Exception e) {
            javafx.application.Platform.exit(); // exception exits platform
        }
    }

}
