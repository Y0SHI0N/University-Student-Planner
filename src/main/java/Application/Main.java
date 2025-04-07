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

public class Main extends Application {
    private Parent root;
    private Stage applicationStage = new Stage(); //global stage reference
    protected FXMLLoader homePage;
    protected FXMLLoader loginPage;
    protected FXMLLoader registerPage;
    protected FXMLLoader goalsPage;
    protected FXMLLoader mapPage;
    protected FXMLLoader calendarPage;
    protected static String currentUserNumber; //used to track the user currently logged in
    protected final UserSignupDAO userSignupDAO = DatabaseConnection.getUserSignupDAO();
    protected final UserCollectedDataDAO userCollectedDataDAO = DatabaseConnection.getUserCollectedDataDAO();

    public void changeScene(String fxmlFilePath) throws IOException {
        try{
            FXMLLoader fxmlFile = new FXMLLoader(getClass().getResource(fxmlFilePath));
            root = fxmlFile.load();

            formatStage();

            applicationStage.setScene(new Scene(root));
            applicationStage.show();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage applicationStage) throws Exception {
        this.applicationStage = applicationStage; // syncs the 2 stages

        loadAllFXMLFiles(); // stores all fxml files in a FXMLLoader protected variable for use in the sceneLoaderController

        changeScene("/FXML/Login-Page.fxml"); // loads the login page as the first scene

        applicationStage.show(); // shows the stage
    }

    public void formatStage(){
        try {
            String imagePath = "/Img/QUT-Logo.jpg";
            URL imageUrl = getClass().getResource(imagePath);

            if (imageUrl == null) {
                throw new IllegalArgumentException("Image file not found: " + imagePath);
            }

            applicationStage.getIcons().add(new Image(imageUrl.toExternalForm()));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading stage icon.");
        }
    }
    public void closeActiveStage(){
        Platform.runLater(() -> {
            for (Window window : Stage.getWindows()) {
                if (window instanceof Stage) { // doesn't close every window open
                    ((Stage) window).close();
                }
            }
        });
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

    protected boolean checkDuplicateStudentNumbers(String studentNumber) {
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

    /** Returns an error message for invalid input or null when all arguments are valid */
    protected String inputValidation(
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
                return "don't leave fields empty.";
            }

            if (!password.equals(confirmedPassword)) {
                return "passwords don't match.";
            }

            if (password.length() < 8) {
                return "password must be at least 8 characters long.";
            }

            // check if email has an '@' with characters on either side
            if (!email.matches(".+@.+")) {
                return "invalid email.";
            }

            if (checkDuplicateStudentNumbers(studentNumber) == true){
                return "student number already registered.";
            }

            // check if student number solely comprised of 'n' followed by 8 numeric characters
            if (!studentNumber.matches("^n[0-9]{8}$")) {
                return "invalid student number.";
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Passed all checks (so far)
        return null;
    }

    /** Prepends an 'n' to the student number if not present */
    protected static String normaliseStudentNo(String studentNo) {
        if (studentNo.startsWith("n")) {
            return studentNo;
        }
        return "n" + studentNo;
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