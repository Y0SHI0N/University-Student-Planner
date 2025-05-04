package Application.Controllers;

import Application.Database.DatabaseConnection;
import Application.Database.UserSignup;
import Application.Database.UserSignupDAO;
import Application.Validation;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Random;
import static java.util.Locale.filter;

public class profilePageController extends sceneLoaderController {
    @FXML private Label firstName;
    @FXML private Label lastName;
    @FXML private Label Email;
    @FXML private Label phoneNumber;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField EmailField;
    @FXML private TextField phoneNumberField;
    @FXML private Button editButton;
    @FXML private Button cancelButton;
    @FXML private Button updateButton;
    @FXML private Text notice;
    @FXML private StackPane profilePlace;
    private UserSignup userSignup;

    //making this a function so it can be reused in other pages
    //when given a stackPane it will add a background and text to it
    public static void setProfileImage(UserSignup user,StackPane pane,Float size){
        //set profileImg placeholder
        //get a random seed from the userNumber so the color is different for each user
        Random random = new Random(Long.parseLong(user.getStudentNumber().replaceAll("[^0-9]","")));
        int[] colors={random.nextInt(1,255),random.nextInt(1,255),random.nextInt(1,255)};
        //if all three are too high make one lower so the background doesn't hide the text
        int brightColors=0;
        for (int color :colors){if (color > 220){brightColors+=1;}}
        if (brightColors == 3){colors[random.nextInt(1,4)]=random.nextInt(1,200);}
        //add elements to pane
        Circle profileBackGround=new Circle();
        profileBackGround.setRadius(size);
        profileBackGround.setStrokeType(StrokeType.INSIDE);
        profileBackGround.setFill(Color.rgb(colors[0],colors[1],colors[2]));
        System.out.println(colors[0]);
        System.out.println(colors[1]);
        System.out.println(colors[2]);

        Text profileText=new Text();
        profileText.setText(user.getFirstName().substring(0,1).toUpperCase()+user.getLastName().substring(0,1).toUpperCase());
        profileText.setFill(Color.WHITE);
        profileText.setStrokeType(StrokeType.OUTSIDE);
        profileText.setStrokeWidth(0f);
        Font font=new Font(size);
        profileText.fontProperty().setValue(font);

        pane.getChildren().add(profileBackGround);
        pane.getChildren().add(profileText);
    }

    public void initialize(){
        super.initialize();
        try {
            //get the info on the current user
            String profileInfoQuery = "SELECT * FROM User_Signup_Data where StudentNumber = ?";
            PreparedStatement statement = userSignupDAO.getDBConnection().prepareStatement(profileInfoQuery);
            statement.setString(1,currentUserNumber);
            ResultSet resultSet = statement.executeQuery();

            //make a UserSignup instance to use for this page
            userSignup = new UserSignup(currentUserNumber
                    ,resultSet.getString("FirstName")
                    ,resultSet.getString("LastName")
                    ,resultSet.getString("Email")
                    ,resultSet.getString("PhoneNumber")
                    ,resultSet.getString("LoginPassword"));
            //set labels to user info
            firstName.setText("First name: "+userSignup.getFirstName());
            lastName.setText("Last Name: "+userSignup.getLastName());
            Email.setText("Email: "+userSignup.getEmail());
            phoneNumber.setText("Phone number: "+userSignup.getPhoneNumber());

            setProfileImage(userSignup,profilePlace,55f);
        }catch(Exception e){
            System.out.println(e + "exception");
        }
    }

    public void editButtonOnClick(MouseEvent click) {
        //hide labels and button
        firstName.setVisible(false);
        lastName.setVisible(false);
        Email.setVisible(false);
        phoneNumber.setVisible(false);
        editButton.setVisible(false);
        //show elements for editing form
        firstNameField.setVisible(true);
        lastNameField.setVisible(true);
        EmailField.setVisible(true);
        phoneNumberField.setVisible(true);
        cancelButton.setVisible(true);
        updateButton.setVisible(true);
        //set fields to saved values when opening (done everytime so changes made before hitting cancel are forgotten)
        firstNameField.setText(userSignup.getFirstName());
        lastNameField.setText(userSignup.getLastName());
        EmailField.setText(userSignup.getEmail());
        phoneNumberField.setText(userSignup.getPhoneNumber());
    }

    private  void hideForm(){
        //show labels and button
        firstName.setVisible(true);
        lastName.setVisible(true);
        Email.setVisible(true);
        phoneNumber.setVisible(true);
        editButton.setVisible(true);
        //hide elements for editing form
        firstNameField.setVisible(false);
        lastNameField.setVisible(false);
        EmailField.setVisible(false);
        phoneNumberField.setVisible(false);
        cancelButton.setVisible(false);
        updateButton.setVisible(false);
        notice.setVisible(false);
    }

    public void cancelButtonOnClick(MouseEvent click) {
        hideForm();
    }

    public Boolean validateChanges(){
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = EmailField.getText();
        String phoneNumber = phoneNumberField.getText();

        //check no fields are empty
        String blankError = Validation.anyBlank(firstName, lastName, email, phoneNumber);
        if (blankError != null) {
            notice.setText(blankError);
            notice.setVisible(true);
            notice.setFill(Color.RED);
            return false;
        }

        //check for correct email format
        String emailError = Validation.validateEmail(EmailField.getText());
        if (emailError != null) {
            notice.setText(emailError);
            notice.setVisible(true);
            notice.setFill(Color.RED);
            return false;
        }

        //check for correct phone number format
        String phoneNumberError = Validation.validatePhoneNumber(phoneNumberField.getText());
        if (phoneNumberError != null) {
            notice.setText(phoneNumberError);
            notice.setVisible(true);
            notice.setFill(Color.RED);
            return false;
        }

        return true;
    }

    public void updateButtonOnClick() {
        if (validateChanges()){
            userSignup.setFirstName(firstNameField.getText());
            userSignup.setLastName(lastNameField.getText());
            userSignup.setEmail(EmailField.getText());
            userSignup.setPhoneNumber(phoneNumberField.getText());

            try {
                userSignupDAO.updateUser(userSignup);

                hideForm();
                firstName.setText("First name: "+userSignup.getFirstName());
                lastName.setText("Last Name: "+userSignup.getLastName());
                Email.setText("Email: "+userSignup.getEmail());
                phoneNumber.setText("Phone number: "+userSignup.getPhoneNumber());
                setProfileImage(userSignup,profilePlace,55f);

                notice.setText("changes saved successfully.");
                notice.setVisible(true);
                notice.setFill(Color.GREEN);
            }catch (SQLException e) {
                notice.setText("an error has occurred.");
                notice.setVisible(true);
                notice.setFill(Color.RED);
                System.out.println("Update failed: "+e);
            }
        }
    }


}