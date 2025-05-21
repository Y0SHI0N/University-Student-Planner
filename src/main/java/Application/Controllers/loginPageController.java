package Application.Controllers;

import Application.Database.DatabaseConnection;
import Application.Validation;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;


import java.sql.*;

public class loginPageController extends sceneLoaderController {
    @FXML public Button loginButton;
    @FXML public Button registerButton;
    @FXML private Label loginLabel;
    @FXML private TextField loginTextField;
    @FXML private PasswordField loginPasswordField;

    @FXML
    public void initialize() {
        // Pressing Enter on username moves to password
        loginTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                loginPasswordField.requestFocus();
            }
        });

        // Pressing Enter on password triggers login
        loginPasswordField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                loginButtonOnClick();
            }
        });

        // Pressing Enter on password triggers login
        loginButton.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                loginButtonOnClick();
            }
        });

        // Pressing Enter on password triggers login
        registerButton.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    switchToRegisterPage();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void loginButtonOnClick(){
        if(!loginTextField.getText().isBlank() && !loginPasswordField.getText().isBlank()){
            validateLogin();
        }
        else{
            loginLabel.setText("Please enter a username and password.");
        }
    }

    public void validateLogin(){
        try {
            String verifyLoginQuery = "SELECT count(1) FROM User_Signup_Data where StudentNumber = ? AND LoginPassword = ?";
            PreparedStatement statement = userSignupDAO.getDBConnection().prepareStatement(verifyLoginQuery);
            statement.setString(1, Validation.normaliseStudentNo(loginTextField.getText()));
            statement.setString(2, loginPasswordField.getText());
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                if(resultSet.getInt(1) == 1){
                    // when the user is correctly logged in it will load the users home page and set the currentUserNumber
                    currentUserNumber=Validation.normaliseStudentNo(loginTextField.getText());
                    switchToHomePage();
                }else{
                    loginLabel.setText("Invalid login. Please try again!");
                }
            }
        }catch(Exception e){
            System.out.println(e + "exception");
        }
    }
}
