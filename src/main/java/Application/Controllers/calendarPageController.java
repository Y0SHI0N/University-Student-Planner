package Application.Controllers;

import Application.Database.UserTimetable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.util.converter.NumberStringConverter;

import javax.swing.*;
import javax.tools.Tool;

public class calendarPageController extends sceneLoaderController {
    ArrayList<UserTimetable> events=new ArrayList<UserTimetable>();
    private Integer month;
    private Integer year;
    @FXML private GridPane calendar;
    @FXML private StackPane monthDecrementButton;
    @FXML private StackPane monthIncrementButton;
    @FXML private Text monthText;
    @FXML private StackPane addEvent;
    @FXML private Rectangle background;
    @FXML private TextField addEventNameField;
    @FXML private ComboBox addEventTypeSelect;
    @FXML private DatePicker addEventStartDate;
    @FXML private Spinner addEventStartHour;
    @FXML private Spinner addEventStartMinute;
    @FXML private DatePicker addEventEndDate;
    @FXML private Spinner addEventEndHour;
    @FXML private Spinner addEventEndMinute;
    @FXML private TextField addEventLocation;

    public void displayMonth(){
        //update month text
        monthText.setText(year+" "+java.time.Month.of(month));

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
        Integer dayOfWeek=LocalDate.of(year,month,1).getDayOfWeek().getValue();
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
                Integer startYear=Integer.parseInt(event.getEventStartDate().substring(0,4));
                Integer endDay=Integer.parseInt(event.getEventEndDate().substring(8,10));
                Integer endMonth=Integer.parseInt(event.getEventEndDate().substring(5,7));
                Integer endYear=Integer.parseInt(event.getEventEndDate().substring(0,4));

                if (startMonth.equals(month) && startDay.equals(dayOfMonth) && startYear.equals(year)
                    || endMonth.equals(month) && endDay.equals(dayOfMonth) && endYear.equals(year)){

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

            year=LocalDate.now().getYear();
            month=LocalDate.now().getMonth().getValue();
            displayMonth();


        }catch(Exception e){
            System.out.println(e + "exception");
        }
    }

    public void decrementMonth(){
        if (month == 1){
            month=12;
            year-=1;
        }
        else{month-=1;}
        displayMonth();
    }

    public void incrementMonth(){
        if (month == 12){
            month=1;
            year+=1;
        }
        else{month+=1;}
        displayMonth();
    }

    public void showAddEventForm(){
        //reset form
        addEventNameField.setText("");
        addEventTypeSelect.setValue("");
        addEventStartDate.setValue(null);
        addEventStartHour.decrement(24);
       addEventStartMinute.decrement(60);
        addEventEndDate.setValue(null);
        addEventEndHour.decrement(24);
        addEventEndMinute.decrement(60);
        addEventLocation.setText("");

        addEvent.setVisible(true);
        background.setVisible(true);

    }
    public void cancelAddEvent(){
        addEvent.setVisible(false);
        background.setVisible(false);
    }

}