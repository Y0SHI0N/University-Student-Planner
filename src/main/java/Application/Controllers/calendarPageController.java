package Application.Controllers;

import Application.Database.UserTimetable;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.control.Tooltip;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import javax.swing.*;
import javax.tools.Tool;

public class calendarPageController extends sceneLoaderController {
    ArrayList<UserTimetable> events=new ArrayList<UserTimetable>();
    @FXML private GridPane calendar;
    @FXML private ComboBox monthSelect;

    public void displayMonth(Integer month){
        //clear the days from the calendar
        ArrayList<javafx.scene.Node> children=new ArrayList<javafx.scene.Node>(calendar.getChildren());
        for (javafx.scene.Node i: children){
            if (i.getTypeSelector().equals("StackPane")){
                calendar.getChildren().remove(i);
            }
        }
        //reset rowConstraints
        calendar.getRowConstraints().clear();
        calendar.getRowConstraints().add(new RowConstraints());
        calendar.getRowConstraints().get(0).setMinHeight(20);
        calendar.getRowConstraints().add(new RowConstraints());
        calendar.getRowConstraints().get(1).setMinHeight(20);

        //add days to calendar
        Integer week = 1;
        Integer dayOfMonth=1;
        Integer dayOfWeek=LocalDate.of(LocalDate.now().getYear(),month,1).getDayOfWeek().getValue();
        for (int i=1; i <= java.time.Month.of(month).length(LocalDate.now().getYear()%4==0) ;i++){
            StackPane day = new StackPane();
            calendar.add(day, dayOfWeek-1, week);
            Text date = new Text(dayOfMonth.toString());
            day.getChildren().add(date);

            //this count is used to determine how low text will be placed so it does not overlap
            ArrayList<StackPane> eventBlocks = new ArrayList<StackPane>();
            for (UserTimetable event : events){
                Integer startDay=Integer.parseInt(event.getEventStartDate().substring(8,10));
                Integer startMonth=Integer.parseInt(event.getEventStartDate().substring(5,7));
                Integer endDay=Integer.parseInt(event.getEventEndDate().substring(8,10));
                Integer endMonth=Integer.parseInt(event.getEventEndDate().substring(5,7));

                if (startMonth==month && startDay==dayOfMonth
                    || endMonth==month && endDay==dayOfMonth){

                    StackPane eventBlock=new StackPane();
                    Rectangle background=new Rectangle();
                    switch (event.getEventType()){
                        case "Study period":
                            background.setFill(Color.BLUE);
                            break;
                        case "Work schedule":
                            background.setFill(Color.GREEN);
                            break;
                        case "Food break":
                            background.setFill(Color.ORANGE);
                            break;
                        case "Assignment":
                            background.setFill(Color.RED);
                            break;
                    }
                    background.setHeight(20);
                    background.setWidth(76);
                    eventBlock.getChildren().add(background);

                    Text eventText = new Text(event.getEventName());
                    eventText.setFill(Color.WHITE);
                    eventBlock.getChildren().add(eventText);

                    String location="";
                    if (event.getEventLocation()!=null && event.getEventLocation()!=""){
                        location="\nLocation: "+event.getEventLocation();
                    }
                    String attendance;
                    if (event.getEventAttendance()==0){
                        attendance="\nAttendance: no";
                    }
                    else{
                        attendance="\nAttendance: yes";
                    }

                    Tooltip details=new Tooltip("Name: "+event.getEventName()
                            +"\nType: "+event.getEventType()
                            +"\nStart Datetime: "+event.getEventStartDate()
                            +"\nEnd Datetime: "+event.getEventEndDate()
                            +attendance+location);
                    details.setMinWidth(100);
                    details.setHideDelay(Duration.ZERO);
                    details.setShowDelay(Duration.ZERO);
                    Tooltip.install(eventBlock,details);
                    eventBlock.setMaxHeight(20);

                    day.getChildren().add(eventBlock);
                    eventBlocks.add(eventBlock);

                    //only change the rowHeight if it's not already big enough
                    if ((eventBlocks.size()+1)*20 > calendar.getRowConstraints().get(week).getMinHeight()) {
                        calendar.getRowConstraints().get(week).setMinHeight(
                                calendar.getRowConstraints().get(week).getMinHeight() + 20);
                    }
                }
            }
            if (eventBlocks.size() >0){
                date.setTranslateY((-calendar.getRowConstraints().get(week).getMinHeight()/2) +9);
                Integer textCount=1;
                for (StackPane eventBlock: eventBlocks){
                    eventBlock.setTranslateY(((-calendar.getRowConstraints().get(week).getMinHeight()/2) +9)
                            +(textCount * 20));
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

            monthSelect.setItems(FXCollections.observableArrayList("January","February","March","April","May","June"
                                                        ,"July","August","September","October","November","December"));

            displayMonth(LocalDate.now().getMonth().getValue());

        }catch(Exception e){
            System.out.println(e + "exception");
        }
    }

    public void selectMonth() {
        switch (monthSelect.getValue().toString()){
            case "January": displayMonth(1); break;
            case "February": displayMonth(2); break;
            case "March": displayMonth(3); break;
            case "April": displayMonth(4); break;
            case "May": displayMonth(5); break;
            case "June": displayMonth(6); break;
            case "July": displayMonth(7); break;
            case "August": displayMonth(8); break;
            case "September": displayMonth(9); break;
            case "October": displayMonth(10); break;
            case "November": displayMonth(11); break;
            case "December": displayMonth(12); break;
        }
    }
}