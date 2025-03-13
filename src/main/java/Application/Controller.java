package Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;

public class Controller {
    //the following code is responsible for controlling the Login screen
    @FXML private Button closeApp;
    @FXML private Label loginLabel;
    @FXML private TextField loginTextField;
    @FXML private PasswordField loginPasswordField;

    public void closeAppOnClick(MouseEvent click){
        Stage stage = (Stage) closeApp.getScene().getWindow();
        stage.close();
    }
    public void closeOpenStage(){
        Platform.runLater(() -> {
            for (Window window : Stage.getWindows()) {
                if (window instanceof Stage) { // doesn't close every window open
                    ((Stage) window).close();
                }
            }
        });
    }
    public void loginButtonOnClick(MouseEvent click){
        if(loginTextField.getText().isBlank() == false && loginPasswordField.getText().isBlank() == false){
            validateLogin();
        }
        else{
            loginLabel.setText("Please enter a username and password.");
        }
    }
    public void validateLogin(){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String verifyLogin = "SELECT count(1) FROM User_Signup_Data where StudentNumber = '" + loginTextField.getText() + "' AND LoginPassword = '" + loginPasswordField.getText() + "' ";
        try{
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);
            while(queryResult.next()){
                if(queryResult.getInt(1) == 1){
                    closeOpenStage(); // will close the stages open in all windows ensuring only 1 window is ever used at 1 time
                    loadHomePage(); // when the user is correctly logged in it will load the users home page
                }else{
                    loginLabel.setText("Invalid login. Please try again!");
                }
            }
        }catch(Exception e){
            System.out.println(e + "exception");
        }
    }
    public void loadHomePage() throws IOException {
        Stage HomePageStage = new Stage();
        Parent HomePageRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Home-Page.fxml")));
        HomePageStage.initStyle(StageStyle.UNDECORATED);
        HomePageStage.setScene(new Scene(HomePageRoot));
        HomePageStage.getIcons().add(new Image("Img/QUT-Logo.jpg"));
        HomePageStage.show();
    }
    public void loadRegisterPage() throws IOException{
        closeOpenStage(); // used to close the Login stage
        Stage RegisterPageStage = new Stage();
        Parent RegisterRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Register-Page.fxml")));
        RegisterPageStage.initStyle(StageStyle.UNDECORATED);
        RegisterPageStage.setScene(new Scene(RegisterRoot));
        RegisterPageStage.getIcons().add(new Image("Img/QUT-Logo.jpg"));
        RegisterPageStage.show();
    }
    public void ReturnToLoginPage() throws IOException{
        closeOpenStage();
        Stage LoginPageStage = new Stage();
        Parent LoginRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Login-Page.fxml")));
        LoginPageStage.initStyle(StageStyle.UNDECORATED);
        LoginPageStage.setScene(new Scene(LoginRoot));
        LoginPageStage.getIcons().add(new Image("Img/QUT-Logo.jpg"));
        LoginPageStage.show();
    }
}