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
    ArrayList<UserTimetable> events=new ArrayList<UserTimetable>();
    @FXML private GridPane calendar;

    public void displayMonth(java.time.Month month){
        //add days to calendar
        Integer week = 1;
        Integer dayOfMonth=1;
        Integer dayOfWeek=LocalDate.of(LocalDate.now().getYear(),month,1).getDayOfWeek().getValue();
        for (int i=1; i <= LocalDate.now().getMonth().length(LocalDate.now().getYear()%4==0) ;i++){
            StackPane day = new StackPane();
            calendar.add(day, dayOfWeek-1, week);
            Text date = new Text(dayOfMonth.toString());
            day.getChildren().add(date);

            //this count is used to determine how low text will be placed so it does not overlap
            ArrayList<Text> eventTexts = new ArrayList<Text>();
            for (UserTimetable event : events){
                Integer startDay=Integer.parseInt(event.getEventStartDate().substring(8,10));
                Integer startMonth=Integer.parseInt(event.getEventStartDate().substring(5,7));
                if (startMonth==LocalDate.now().getMonth().getValue() && startDay==dayOfMonth){
                    Text eventText = new Text(event.getEventName());
                    day.getChildren().add(eventText);

                    eventTexts.add(eventText);

                    //only change the rowHeight if it's not already big enough
                    if ((eventTexts.size()+1)*20 > calendar.getRowConstraints().get(week).getMinHeight()) {
                        calendar.getRowConstraints().get(week).setMinHeight(
                                calendar.getRowConstraints().get(week).getMinHeight() + 20);
                    }
                }
            }
            if (eventTexts.size() >0){
                date.setTranslateY((-calendar.getRowConstraints().get(week).getMinHeight()/2) +9);
                Integer textCount=0;
                for (Text text: eventTexts){
                    text.setTranslateY(textCount * 20);
                    textCount+=1;
                }
                            }


            dayOfWeek+=1;
            if (dayOfWeek> 7){dayOfWeek=1;
                week+=1;
                calendar.getRowConstraints().add(new RowConstraints());
                calendar.getRowConstraints().get(week).setMinHeight(20);
            }
            dayOfMonth+=1;
        }

    }

    public void initialize(){
        try {
            //get events
            String profileInfoQuery = "SELECT * FROM User_Timetable_Data where StudentNumber = ?";
            PreparedStatement statement = userSignupDAO.getDBConnection().prepareStatement(profileInfoQuery);
            statement.setString(1,currentUserNumber);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                events.add(new UserTimetable(resultSet.getString("EventName")
                        ,currentUserNumber
                        ,resultSet.getString("EventType")
                        ,resultSet.getString("EventStartDatetime")
                        ,resultSet.getString("EventEndDatetime")
                        ,resultSet.getString("EventLocation")
                        ,resultSet.getInt("Event_Attendance")));
            }
            displayMonth(LocalDate.now().getMonth());
        }catch(Exception e){
            System.out.println(e + "exception");
        }
    }

}