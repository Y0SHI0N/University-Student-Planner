package Application.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;

public class OptionListController extends sceneLoaderController{
    @FXML public MenuItem viewProfileItem;
    @FXML public MenuItem updateDetailsItem;
    @FXML public MenuItem updateGoalsItem;
    @FXML public MenuItem logoutItem;
    @FXML public MenuItem closeAppItem;

    public void initialize() {
        viewProfileItem.setOnAction(e -> viewProfile());
        updateDetailsItem.setOnAction(e -> updateDetails());
        updateGoalsItem.setOnAction(e -> updateGoals());
        logoutItem.setOnAction(e -> logout());
        closeAppItem.setOnAction(e -> closeApp());
    }

    public void logout(){
        try{
            this.currentUserNumber = "";
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
