package Application;

import Application.Database.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.stream.Stream;

import Application.Controllers.sceneLoaderController;

public class Main extends Application {
    protected FXMLLoader homePage;
    protected FXMLLoader loginPage;
    protected FXMLLoader registerPage;
    protected FXMLLoader goalsPage;
    protected FXMLLoader mapPage;
    protected FXMLLoader calendarPage;
    protected sceneLoaderController sceneLoaderController = new sceneLoaderController();
    protected StageController stageController = new StageController();

    public static void main(String[] args) {
        launch(args);
    }

    public void loadAllFXMLFiles(){
        try{
            loginPage = new FXMLLoader(getClass().getResource("/FXML/Login-Page.fxml"));
            registerPage = new FXMLLoader(getClass().getResource("/FXML/Register-Page.fxml"));
            homePage = new FXMLLoader(getClass().getResource("/FXML/Home-Page.fxml"));
            goalsPage = new FXMLLoader(getClass().getResource("/FXML/Goals-Page.fxml"));
            mapPage = new FXMLLoader(getClass().getResource("/FXML/Map-Page.fxml"));
            calendarPage = new FXMLLoader(getClass().getResource("/FXML/Calendar-Page.fxml"));

        } catch (Exception e) {
            System.out.println(e);
        }

    }
    @Override
    public void start(Stage applicationStage) throws Exception {
        loadAllFXMLFiles(); // stores all fxml files in a FXMLLoader protected variable for use in the sceneLoaderController
        sceneLoaderController.changeScene("/FXML/Login-Page.fxml"); // loads the login page as the first scene
    }

    @Override
    public void stop() {
        // Close DAOs when the app stops
        try {
            System.out.println("Application shutting down...");
            DatabaseConnection.closeConnection();  // Close DB connection on exit
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}