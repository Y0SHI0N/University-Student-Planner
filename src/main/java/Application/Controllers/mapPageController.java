package Application.Controllers;

import Application.AI_model;
import Application.Database.UserTimetableDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.Console;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class mapPageController extends sceneLoaderController {
    @FXML public Canvas heatMap;
    @FXML public ListView<String> busyLocationList;
    @FXML public ListView<String> quietLocationList;
    @FXML public TextArea AIMapSummary;
    @FXML public HBox dateContainer;
    @FXML public DatePicker predictedDate;
    @FXML public Button dateButton;

    public static ArrayList<Event> calenderEvents = new ArrayList<Event>();
    public enum feedType {LIVE, PREDICTED}
    public static feedType feedState = feedType.LIVE;
    public static LocalDate stored_date = java.time.LocalDate.now();

    public AI_model model = new AI_model();
    // stores all vital information regarding a building's code, x/y location and classes (in that order)
    /// PLACEHOLDER DATA JUST SO THERE'S SOMETHING TO SHOW
    // todo: WILL BE REPLACED ONCE "User_Signup_Data" IS POPULATED
    public static Building[] CampusBuildings = {
            new Building('P', 254, 100, new ArrayList<Event>()),
            new Building('S', 78, 164, new ArrayList<Event>()),
            new Building('Z', 128, 166, new ArrayList<Event>())
    };

    public static Circle CirclePreset =
            new Circle(25, 15, 51);

    public static class Building {
        Character letterID;
        Integer xPos;
        Integer yPos;
        ArrayList<Event> eventCount;

        public Building(Character letterID, Integer xPos, Integer yPos, ArrayList<Event> eventCount) {
            this.letterID = letterID;
            this.xPos = xPos;
            this.yPos = yPos;
            this.eventCount = eventCount;
        }

        public Character getBlockLetter() {
            return this.letterID;
        }

        public Integer getXPos() {
            return this.xPos;
        }

        public Integer getYPos() {
            return this.yPos;
        }

        public ArrayList<Event> getEventCount() {
            return this.eventCount;
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
        Integer eventID;
        String eventName;
        String eventType;
        String eventStartDatetime;
        String eventEndDatetime;
        String eventLocation;
        Integer eventAttendance;

        public Event(Integer eventID, String eventName, String eventType, String eventStartDatetime,
                     String eventEndDatetime, String eventLocation, Integer eventAttendance) {
            this.eventID = eventID;
            this.eventName = eventName;
            this.eventType = eventType;
            this.eventStartDatetime = eventStartDatetime;
            this.eventEndDatetime = eventEndDatetime;
            this.eventLocation = eventLocation;
            this.eventAttendance = eventAttendance;
        }

        public Integer getEventID() {
            return this.eventID;
        }

        public String getEventName() {
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

        // for wittle bubba gemini that can't chew it's fooood right awwww boo hoo

        /// yes, I'm unreasonably mad at this lol, and no I don't want to talk about it.
        public String pureeEventData() {
            ArrayList<String> input_list = new ArrayList<>();

            input_list.add(getEventID().toString());
            input_list.add(getEventName());
            input_list.add(getEventType());
            input_list.add(getEventStartDatetime());
            input_list.add(getEventEndDatetime());
            input_list.add(getEventLocation());
            input_list.add(getEventAttendance().toString());

            return String.join(", ", (input_list));
        }
    }


    public void renderHeatmap(ArrayList<Event> calender_events) {
        // Reset the canvas
        heatMap.getGraphicsContext2D().clearRect(0.0, 0.0, 302.0, 200.0);
        // The more rooms there are in the building booked, the more layers of circle there are, with each warmer colors.
        for (int i = 0; i < calender_events.size(); i++) {
            // room stores the current room increment, essentially how many layers deep the for loop is
            // starts on the highest heat (at the core of the ring, and slowly draws it's way outwards)

            // lookup letterID inside event from calender_events
            Building building = findBuildingByLetter(calender_events.get(i).eventLocation.charAt(0));
            drawCircle(building, heatMap.getGraphicsContext2D(), i, calender_events.get(i)); //todo: REPLACE WITH IT'S POSITION IN THE ORDERED CALENDER EVENTS SET
            System.out.println("6 6 6");
            System.out.println(building);
            System.out.println(calender_events.get(i));
            System.out.println("9 9 9");
        }
    }


    public void drawCircle(Building building, GraphicsContext graphics, int room_count, Event event) {
        // set draw color to 'calculateHeat's output
        graphics.setFill(calculateHeat(room_count, CirclePreset));
        // 30 is the intial circle size, chosen due to it being roughly the size of a building
        float circle_width = CirclePreset.circleWidth + (CirclePreset.circleStepValue * room_count);

        // removing half of the circle width from the coords is to compensate for the offset of the circle being in the top right corner (thanks javafx)
        graphics.fillOval(building.xPos - (circle_width / 2), building.yPos - (circle_width / 2), circle_width, circle_width); // first pair edits the x/y coords while the second edits the x/y size
        // add a label ontop of the circle with the event name/s
        graphics.setFill(Color.WHITE);
        graphics.setTextAlign(TextAlignment.CENTER);
        graphics.setFont(new Font(circle_width / 3));
        graphics.fillText(event.eventName, building.getXPos(), building.getYPos());
    }


    public static Building findBuildingByLetter(Character letter) {
        Building result = null;
        for (Building building : CampusBuildings) {
            if (building.letterID == letter)
                result = building;
        }
        return result;
    }


    public static Color calculateHeat(int room_number, Circle circleTemplate) {
        // The values range from 0 to 255 to encompass the full RGB scale of color.

        int heat_hue_value = Math.clamp(room_number * circleTemplate.hueStepValue, 0, 255);
        System.out.println(heat_hue_value);
        // The heat hue value works as an inverse relationship with both red and blue hues.
        // The deeper the red, the less the blue and vice versa. Initally starts with max blue and zero red
        return (Color.rgb(Math.abs(heat_hue_value - 255), 0, heat_hue_value, 0.5));
    }


    // todo: write at least 2 tests on the data validity / size
    public static void loadEvents(UserTimetableDAO userTimetableDAO, String UserNumber) {
        try {
            String profileInfoQuery = "SELECT * FROM User_Timetable_Data where StudentNumber = ? AND NOT EventLocation = ''";
            PreparedStatement statement = userTimetableDAO.getDBConnection().prepareStatement(profileInfoQuery);
            statement.setString(1, UserNumber);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Event newEvent = new Event(
                        resultSet.getInt("EventID"),
                        resultSet.getString("EventName"),
                        resultSet.getString("EventType"),
                        resultSet.getString("EventStartDatetime"),
                        resultSet.getString("EventEndDatetime"),
                        resultSet.getString("EventLocation"),
                        resultSet.getInt("EventAttendance"));
                // todo: check date
                //if LocalDate.now()
                String event_start = resultSet.getString("EventStartDatetime");
                String event_end = resultSet.getString("EventEndDatetime");

                event_start = event_start.substring(0, event_start.indexOf(' '));
                event_end = event_end.substring(0, event_end.indexOf(' '));

                if (isWithinDates(event_start, event_end, stored_date.toString()) && feedState == feedType.PREDICTED
                || isWithinDates(event_start, event_end, java.time.LocalDate.now().toString()) && feedState == feedType.LIVE) {
                    calenderEvents.add(newEvent);
                    // add a tally next to the event's corresponding building, makes it much easier to view event density later
                    findBuildingByLetter(newEvent.eventLocation.charAt(0)).eventCount.add(newEvent);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isWithinDates(String start_input, String end_input, String day_input) {
        LocalDate start_date = LocalDate.parse(start_input);
        LocalDate end_date = LocalDate.parse(end_input);
        LocalDate today = LocalDate.parse(day_input);

        return today.isAfter(start_date) || today.isEqual(start_date)
                && today.isBefore(end_date) || today.isEqual(end_date);
    }

    public void sortRooms(ArrayList<Event> calender_events) {
        // responsible for simply sorting and rendering rooms
        ArrayList<String> quiet_areas = new ArrayList<String>();
        ArrayList<String> busy_areas = new ArrayList<String>();

        // TODO: FIX THE BUG IN THIS LOOP WHERE IT DUPLICATES ALL LISTS
        /// confirmed to be inside quiet_areas & busy_areas
        /// they aren't clearing themselves properly. still don't know how to fix
        // todo: the database also appears to not clean out logged evens after they happen
        //  (e.g. view event on 19th, switch to 20th, event still present, haven't tested tho)

        for (Building building : CampusBuildings) {
            if (building.eventCount.size() > 2) {
                for (Event event : building.eventCount) {
                    busy_areas.add(event.eventName);
                }
            } else {
                for (Event event : building.eventCount) {
                    quiet_areas.add(event.eventName);
                }
            }
        }

        // create dynamic list that will update with changes, and use it to add the rooms onto the preferred list
        ObservableList<String> quiet_rooms = FXCollections.observableArrayList(quiet_areas);
        ObservableList<String> busy_rooms = FXCollections.observableArrayList(busy_areas);

        quietLocationList.getItems().setAll(quiet_rooms);
        busyLocationList.getItems().setAll(busy_rooms);
    }


    public void openLiveFeed() {
        feedState = feedType.LIVE;
        dateContainer.setVisible(false);
        reloadHeatmap();
    }

    public void openPredictedFeed() {
        feedState = feedType.PREDICTED;
        dateContainer.setVisible(true);
        ///monthText.setText(year+" "+java.time.Month.of(month));
    }

    public void confirmDate() {
        stored_date = predictedDate.getValue();
        dateContainer.setVisible(false);
        reloadHeatmap();
    }


    public void generateAIData(ArrayList<Event> event_data) {
        // prepare big bubba's pureed applesauce, convert all calender events into a readable string list of event details.
        // need to parse in context, giving it a template of what each value means
        String input_data = "";
        for (Event event : calenderEvents)
            input_data += input_data + event.pureeEventData() + ". ";
        String promptText = "Can you generate a summary of current activities including how clustered together the events are and their frequency given the following heatmap information for a university campus:" + input_data + " with a text limit of 200 characters including spaces and excluding any special characters." +
                "You are printing an informational readout for a user. Please do not talk about any backend details or ask for further information. The data you have given is sufficent for their needs." +
                "The formatting for each event (stored inside the input_data as nested lists) is as follows: " +
                " Integer eventID, " +
                " String eventName, " +
                " String eventType, " +
                " String eventStartDatetime, " +
                " String eventEndDatetime, " +
                " String eventLocation, " +
                " Integer eventAttendance;";
        // AI multi-threading
        Runnable AItask = new Runnable() {
            @Override
            public void run() {
                model.promptAI(promptText);
                AIMapSummary.setText(model.promptAI(promptText));
            }
        };
        Thread AIthread=new Thread(AItask);
        AIthread.start();
    }


    public void reloadHeatmap() {
        calenderEvents.clear();
        AIMapSummary.setText("Summary loading...");

        loadEvents(userTimetableDAO, currentUserNumber);
        generateAIData(calenderEvents); // pass in all event data for gemma to feast on

        renderHeatmap(calenderEvents);
        sortRooms(calenderEvents);
    }


    public void initialize() {
        try {
            reloadHeatmap();
            model.initialiseAIModel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
