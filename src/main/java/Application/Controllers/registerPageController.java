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

    public boolean checkDuplicateStudentNumbers(String studentNumber){
        String checkUnique = "SELECT count(1) FROM User_Signup_Data where StudentNumber = '" + studentNumber + "';";
        try {
            Statement statement = userSignupDAO.getDBConnection().createStatement();
            ResultSet queryResult = statement.executeQuery(checkUnique);
            queryResult.next();

            return queryResult.getInt(1) == 1; // returns true if the student number already exists
        } catch (Exception e){
            System.out.println(e + "exception");
        }
        return false; // will never execute but needed for no syntax error
    }

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

    private boolean inputValidation(
            String studentNumber,
            String firstName,
            String lastName,
            String email,
            String password,
            String confirmedPassword
    ){
        try {
            // Check for empty fields
            if (Stream.of(studentNumber, firstName, lastName, email, password, confirmedPassword).anyMatch(String::isEmpty)) {
                registerErrorMessagesText.setText("don't leave fields empty");
                return false;
            }

            if (!password.equals(confirmedPassword)) {
                registerErrorMessagesText.setText("passwords don't match");
                return false;
            }

            if (password.length() < 8) {
                registerErrorMessagesText.setText("password must be at least 8 characters long");
                return false;
            }

            // check if email has an '@' with characters on either side
            if (!email.matches(".+@.+")) {
                registerErrorMessagesText.setText("invalid email");
            }

            // check if student number solely comprised of 8 numeric digits
            if (!studentNumber.matches("^[0-9]{8}$")) {
                registerErrorMessagesText.setText("invalid student number");
                return false;
            }

            // check if record already exists within db
            if (checkDuplicateStudentNumbers(studentNumber)) {
                registerErrorMessagesText.setText("student number is already registered");
                return false;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Passed all checks (so far), return True
        return true;
    }

    public void registerUser(){
        String studentNumber = registerStudentNumber.getText();
        String firstName = registerFirstName.getText();
        String lastName = registerLastName.getText();
        String email = registerEmail.getText();
        String password = registerPassword.getText();
        String confirmedPassword = confirmRegisterPassword.getText();

        if (inputValidation(studentNumber,firstName,lastName,email,password,confirmedPassword)){
            try {
                UserSignup userToSignUp = new UserSignup(studentNumber,firstName,lastName,email, "", password);
                userSignupDAO.insertUser(userToSignUp);
                changeScene("/FXML/Login-Page.fxml");
            } catch (Exception e) {
                javafx.application.Platform.exit(); // exception exits platform
            }
        }
    }

}
