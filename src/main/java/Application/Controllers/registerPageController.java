package Application.Controllers;

import Application.Database.UserSignup;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.sql.*;

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
        try{
            Statement statement = userSignupDAO.getDBConnection().createStatement();
            ResultSet queryResult = statement.executeQuery(checkUnique);
            queryResult.next();
            if(queryResult.getInt(1) == 1){
                return true; // returns true because the student number already exists
            }else{
                return false; //
            }
        }catch(Exception e){
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

    private boolean inputValidation(String ... inputs){
        boolean ret = false;

        try {
            // Check for empty fields
            for (String value : inputs){
                if (value.equals("")){
                    registerErrorMessagesText.setText("don't leave fields empty");
                    return false;
                }
            }

            // check for password == confirmPassword
            if (!inputs[4].equals(inputs[5])){
                registerErrorMessagesText.setText("passwords don't match");
                return ret;
            }

            // check if record already exists within db
            if(checkDuplicateStudentNumbers(inputs[1])){
                registerErrorMessagesText.setText("student number is already registered");
                return ret;
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Passed all checks (so far), return True
        ret = true;
        return ret;
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
