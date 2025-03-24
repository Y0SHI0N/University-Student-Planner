package Application.Controllers;

import Application.Database.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;


import java.sql.*;

public class loginPageController extends sceneLoaderController {
    public Button loginButton;
    @FXML private Label loginLabel;
    @FXML private TextField loginTextField;
    @FXML private PasswordField loginPasswordField;

    public void loginButtonOnClick(MouseEvent click){
        if(loginTextField.getText().isBlank() == false && loginPasswordField.getText().isBlank() == false){
            validateLogin();
        }
        else{
            loginLabel.setText("Please enter a username and password.");
        }
    }

    public void validateLogin(){
        try {
            String verifyLoginQuery = "SELECT StudentNumber FROM User_Signup_Data where StudentNumber = ? AND LoginPassword = ?";
            PreparedStatement statement = userSignupDAO.getDBConnection().prepareStatement(verifyLoginQuery);
            statement.setString(1, loginTextField.getText());
            statement.setString(2, loginPasswordField.getText());
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.getString("StudentNumber") != null){
                // when the user is correctly logged in it will load the users home page and set the currentUserNumber
                currentUserNumber=resultSet.getString("StudentNumber");
                switchToHomePage();
            }else{
                loginLabel.setText("Invalid login. Please try again!");

            }
        }catch(Exception e){
            System.out.println(e + "exception");
        }
    }
}
