package Application.Controllers;

import Application.Database.DatabaseConnection;
import Application.Database.UserSignup;
import Application.Database.UserTimetable;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import javafx.scene.layout.StackPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class calendarPageController extends sceneLoaderController {
    @FXML private GridPane calendar;

    public void initialize(){
        try {
            ArrayList<UserTimetable> events=new ArrayList<UserTimetable>();
            //get events
            String profileInfoQuery = "SELECT * FROM User_Timetable_Data where StudentNumber = ?";
            PreparedStatement statement = userSignupDAO.getDBConnection().prepareStatement(profileInfoQuery);
            statement.setString(1,currentUserNumber);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                events.add(new UserTimetable(currentUserNumber
                        ,resultSet.getString("EventType")
                        ,resultSet.getString("EventStartDatetime")
                        ,resultSet.getString("EventEndDatetime")
                        ,resultSet.getString("EventLocation")
                        ,resultSet.getInt("Event_Attendance")));
            }
            //add days to calendar
            Integer week = 1;
            Integer dayOfMonth=1;
            Integer dayOfWeek=LocalDate.of(LocalDate.now().getYear(),LocalDate.now().getMonth(),1).getDayOfWeek().getValue();
            for (int i=1; i <= LocalDate.now().getMonth().length(LocalDate.now().getYear()%4==0) ;i++){
                StackPane day = new StackPane();
                day.setStyle("-fx-background-color: lightgrey");
                calendar.add(day, dayOfWeek-1, week);
                Text date = new Text(dayOfMonth.toString());
                day.getChildren().add(date);

                for (UserTimetable event : events){
                    Integer startDay=Integer.parseInt(event.getEventStartDate().substring(8,10));
                    Integer startMonth=Integer.parseInt(event.getEventStartDate().substring(5,7));
                    if (startMonth==LocalDate.now().getMonth().getValue() && startDay==dayOfMonth){
                        Text eventText = new Text("test");
                        day.getChildren().add(eventText);
                        date.setTranslateY(-8.0);
                        eventText.setTranslateY(+8.0);
                        System.out.println(calendar.getRowConstraints());
                        calendar.getRowConstraints().set(week,new RowConstraints(40));  //.set(,new RowConstraints(40));

                    }
                }

                dayOfWeek+=1;
                if (dayOfWeek> 7){dayOfWeek=1;
                    week+=1;
                    calendar.getRowConstraints().add(new RowConstraints(20));
                }
                dayOfMonth+=1;
            }

        }catch(Exception e){
            System.out.println(e + "exception");
        }
    }

}