package Application.Controllers;


import Application.Main;
import Application.Database.*;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.fxml.FXML;


import java.sql.ResultSet;
import java.sql.Statement;
import java.util.stream.Stream;

public class sceneLoaderController extends Main {
    protected final UserSignupDAO userSignupDAO = DatabaseConnection.getUserSignupDAO();
    protected final UserTimetableDAO userTimetableDAO = DatabaseConnection.getUserTimetableDAO();
    protected final UserCollectedDataDAO userCollectedDataDAO = DatabaseConnection.getUserCollectedDataDAO();

    @FXML protected MenuItem viewProfileItem;
    @FXML protected MenuItem updateDetailsItem;
    @FXML protected MenuItem updateGoalsItem;
    @FXML protected MenuItem logoutItem;
    @FXML protected MenuItem closeAppItem;

    @FXML
    public void initialize() {
        try {
            viewProfileItem.setOnAction(e -> viewProfile());
            updateDetailsItem.setOnAction(e -> updateDetails());
            updateGoalsItem.setOnAction(e -> updateGoals());
            logoutItem.setOnAction(e -> logout());
            closeAppItem.setOnAction(e -> closeApp());

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void switchToLoginPage() throws Exception {
        try{
            changeScene("/FXML/Login-Page.fxml");
            closeActiveStage();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void switchToRegisterPage() throws Exception {
        try{
            changeScene("/FXML/Register-Page.fxml");
            closeActiveStage();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void switchToHomePage() throws Exception {
        try{
            changeScene("/FXML/Home-Page.fxml");
            closeActiveStage();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void switchToMapPage() throws Exception {
        try{
            changeScene("/FXML/Map-Page.fxml");
            closeActiveStage();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void switchToGoalsPage() throws Exception {
        try{
            changeScene("/FXML/Goals-Page.fxml");
            closeActiveStage();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void switchToCalendarPage() throws Exception {
        try{
            changeScene("/FXML/Calendar-Page.fxml");
            closeActiveStage();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void switchToProfilePage() throws Exception{
        try{
            changeScene("/FXML/Profile-Page.fxml");
            closeActiveStage();
        } catch (Exception e){
            System.out.println(e);
        }
    }

    public void logout(){
        try{
            currentUserNumber = "";
            switchToLoginPage();
        } catch (Exception e){
            System.out.println(e);
        }
    }

    public void closeApp() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Confirmation");
        alert.setHeaderText("Are you sure you want to close the app?");
        alert.setContentText("Any unsaved changes will be lost.");

        // Show dialog and wait for user response
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                Platform.exit();
                System.exit(0);
            }
        });
    }

    public void updateGoals() {
        try{
            switchToGoalsPage();
        } catch (Exception e){
            System.out.println(e);
        }
    }

    public void updateDetails() {
        try{
            switchToCalendarPage();
        } catch (Exception e){
            System.out.println(e);
        }
    }

    public void viewProfile() {
        try{
            switchToProfilePage();
        } catch (Exception e){
            System.out.println(e);
        }
    }
}
