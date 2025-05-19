package Application.Controllers;

import Application.Database.UserTimetable;
import Application.Database.UserTimetableDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ListView;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class mapPageController extends sceneLoaderController {
    @FXML public Canvas heatMap;
    @FXML public ListView<String> busyLocationList;
    @FXML public ListView<String> quietLocationList;
    public enum feedState {LIVE, PREDICTED};
    public feedState current_state = feedState.LIVE;

    // stores all vital information regarding a building's code, x/y location and classes (in that order)
    /// PLACEHOLDER DATA JUST SO THERE'S SOMETHING TO SHOW
    // todo: WILL BE REPLACED ONCE "User_Signup_Data" IS POPULATED
    public static Building[] CampusBuildings = {
            new Building('P', 254, 100, new String[]{}),
            new Building('S', 78, 164, new String[]{}),
            new Building('Z', 128, 166, new String[]{})
    };

    public static Circle CirclePreset =
            new Circle(25, 15, 51);

    public Event[] storedEvents = new Event[]{};


    public static class Building {
        Character blockLetter;
        Integer xPos;
        Integer yPos;
        String[] bookedEvents;

        public Building(Character BlockLetter, Integer xPos, Integer yPos, String[] bookedEvents) {
            this.blockLetter = BlockLetter;
            this.xPos = xPos;
            this.yPos = yPos;
            this.bookedEvents = bookedEvents;
        }

        public Character getBlockLetter() {
            return this.blockLetter;
        }
        public Integer getXPos() {
            return this.xPos;
        }
        public Integer getYPos() {
            return this.yPos;
        }
        public String[] getbookedEvents() {
            return this.bookedEvents;
        }
    }

    public static class Circle {
        // initial circle width and ring expansion size, respectively
        Integer circleWidth;
        Integer circleStepValue;
        // hue increase amount per ring (keep in mind that the maximum hue for RGB colors is 255)
        Integer hueStepValue;

        public Circle(Integer circleWidth, Integer circleStepValue, Integer hueStepValue) {
            this.circleWidth = circleWidth;
            this.circleStepValue = circleStepValue;
            this.hueStepValue = hueStepValue;
        }

        public int getWidth() {
            return this.circleWidth;
        }
        public int getStepValue() {
            return this.circleStepValue;
        }
        public int getHueValue() {
            return this.hueStepValue;
        }
    }

    public static class Event {
        Integer eventName;
        String eventType;
        String eventStartDatetime;
        String eventEndDatetime;
        String eventLocation;
        Integer eventAttendance;

        public Event(Integer eventName, String eventType, String eventStartDatetime,
                     String eventEndDatetime, String eventLocation, Integer eventAttendance) {
            this.eventName = eventName;
            this.eventType = eventType;
            this.eventStartDatetime = eventStartDatetime;
            this.eventEndDatetime = eventEndDatetime;
            this.eventLocation = eventLocation;
            this.eventAttendance = eventAttendance;
        }

        public Integer getEventName() {
            return this.eventName;
        }
        public String getEventType() {
            return this.eventType;
        }
        public String getEventStartDatetime() {
            return this.eventStartDatetime;
        }
        public String getEventEndDatetime() {
            return this.eventEndDatetime;
        }
        public String getEventLocation() {
            return this.eventLocation;
        }
        public Integer getEventAttendance() {
            return this.eventAttendance;
        }
    }


    public void renderHeatmap(Building building) {
        // Reset the canvas
        heatMap.getGraphicsContext2D().clearRect(0.0, 0.0, 302.0, 200.0);
        // The more rooms there are, the more layers of circle there are, with each warmer colors.
        for (int room_count = building.bookedEvents.length; room_count > 0; room_count--) {
            // room stores the current room increment, essentially how many layers deep the for loop is
            // starts on the highest heat (at the core of the ring, and slowly draws it's way outwards)
            drawCircle(building, heatMap.getGraphicsContext2D(), room_count);
        }
    }


    public void drawCircle(Building building, GraphicsContext graphics, int room_count) {
        // set draw color to 'calculateHeat's output
        graphics.setFill(calculateHeat(room_count, CirclePreset));
        // 30 is the intial circle size, chosen due to it being roughly the size of a building
        float circle_width = CirclePreset.circleWidth + (CirclePreset.circleStepValue * room_count);
        // removing half of the circle width from the coords is to compensate for the offset of the circle being in the top right corner (thanks javafx)
        graphics.fillOval(building.xPos - (circle_width / 2), building.yPos - (circle_width / 2), circle_width, circle_width); // first pair edits the x/y coords while the second edits the x/y size
    }


    public static Color calculateHeat(int room_number, Circle circleTemplate) {
        // The values range from 0 to 255 to encompass the full RGB scale of color.

        int heat_hue_value = Math.clamp(room_number * circleTemplate.hueStepValue, 0, 255);
        // The heat hue value works as an inverse relationship with both red and blue hues.
        // The deeper the red, the less the blue and vice versa. Initally starts with max blue and zero red
        return (Color.rgb(Math.abs(heat_hue_value - 255), 0, heat_hue_value, 0.5));
    }


    // todo: write at least 2 tests on the data validity / size
    public static ArrayList<UserTimetable> loadEvents(UserTimetableDAO userTimetableDAO, String UserNumber){
        ArrayList<UserTimetable> events = new ArrayList<UserTimetable>();
        try {
            String profileInfoQuery = "SELECT * FROM User_Timetable_Data where StudentNumber = ?";
            PreparedStatement statement = userTimetableDAO.getDBConnection().prepareStatement(profileInfoQuery);
            statement.setString(1,UserNumber);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                events.add(new UserTimetable(resultSet.getInt("EventID")
                        ,resultSet.getString("EventName")
                        ,UserNumber
                        ,resultSet.getString("EventType")
                        ,resultSet.getString("EventStartDatetime")
                        ,resultSet.getString("EventEndDatetime")
                        ,resultSet.getString("EventLocation")
                        ,resultSet.getInt("EventAttendance")));
            }
        }catch(Exception e){
            System.out.println(e + "exception");
        }
        // output the event as a list of classes filled with the data
        return events;
    }


    public void sortRooms(Building building) {
        // responsible for simply sorting and rendering rooms
        /// TEMP SORTING METHOD
        // todo: IMPROVE THIS AFTER CHECKPOINT
        ListView<String> prefered_list_type = null;
        if (building.bookedEvents.length > 2) {
            prefered_list_type = busyLocationList;
        } else {
            prefered_list_type = quietLocationList;
        }
        // create dynamic list that will update with changes, and use it to add the rooms onto the preferred list
        ObservableList<String> rooms = FXCollections.observableArrayList(building.bookedEvents);
        prefered_list_type.getItems().setAll(rooms);
    }


    public void openLiveFeed() {
        current_state = feedState.LIVE;
        reloadHeatmap();
    }


    public void openPredictedFeed() {
        current_state = feedState.PREDICTED;
        reloadHeatmap();
    }


    public void generateAIData() {
        /*
        todo: this. sample code from jake's page below
        String currentGoals = ""; // pulls the data in to parse to the ai
        String currentData = "";
        String promptText = "can you suggest what can be improved given the following information:" + currentGoals + currentData + "with a text limit of 200 characters including spaces";

        model.promptAI(promptText);
        AIGeneratedGoalSuggestion.setText(model.promptAI(promptText));
        */
    }


    public void reloadHeatmap() {
       /// event_data = loadEvents(userTimetableDAO,currentUserNumber);
        ///  do something with the data here
        ///storedEvents.append(*event_data); // trying to do that python '*' shortcut to autofill the data
        ///generateAIData(event_data); // pass in all event data for gemini2b:b to use
        for (Building building : CampusBuildings) {
            renderHeatmap(building);
            sortRooms(building);
        }
    }


    public void initialize() {
        super.initialize();
        try {
            reloadHeatmap();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
