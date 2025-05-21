package Application.Controllers;

import Application.AI_model;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;


public class homePageController extends sceneLoaderController {
    @FXML private Label studyLabel, gpaLabel, attLabel;
    @FXML private TextArea weeklyOverviewAI;
    @FXML private Text motivationalAI;
    @FXML private Text MondayOvr, TuesdayOvr, WedOvr, ThursOvr, FriOvr;
    @FXML private ProgressBar GPAProgress, StudyHourProgress, AttendanceProgress;
    public HashMap<String,String> weekDayHashMap = new HashMap<String, String>();

    public AI_model model = new AI_model();
    @FXML
    public void initialize() {
        super.initialize();
        try {
            weekDayHashMap.put("Monday", "");
            weekDayHashMap.put("Tuesday", "");
            weekDayHashMap.put("Wednesday", "");
            weekDayHashMap.put("Thursday", "");
            weekDayHashMap.put("Friday", "");

            weeklyOverviewAI.setEditable(false);
            model.initialiseAIModel();


            Thread AIthread=new Thread(){
                public void run(){
                    getWeeklyOverview();
                    if (!isInterrupted()) {
                        populateWidgets();
                    }
                    if (!Thread.interrupted()) {
                        getMotivationalQuote();
                    }
                }
            };
            AIthread.setName("AIthread");
            AIthread.start();
            ineruptableThreads.add(AIthread);


        } catch (Exception e) {
            System.out.println(e);
        }
        populateKPI();
    }

    public void getWeeklyOverview(){
        String weeklyInfo = "Monday: 2 hours studied; Tuesday: 3 tutorials attended, 1 practical attended"; // pulls the timetable data in json format to parse to the ai
        String promptText = "can you generate a weekly overview excluding any special characters, a heading and final query messages, including what went well and what can be improved given the following information:" + weeklyInfo + "with a text limit of 200 characters including spaces";

        model.promptAI(promptText);
        String response = model.promptAI(promptText);
        weeklyOverviewAI.setText(response);
    }

    public void getMotivationalQuote(){
        String promptText = "Can you give me a random motivational quote related to studying. Just give me the quote and the author if known, nothing else";
        model.promptAI(promptText);
        String response = model.promptAI(promptText);
        motivationalAI.setText(response);
    }

    public void populateWidgets(){
        var weekdays = weekDayHashMap.keySet();
        for (String weekday : weekdays) {
            String promptText = "give me a dot point breakdown of what i need to do this week on and keep it strictly academic. Limit to 3 dot points, 3-4 words per dot points, use - to start a dot point. Do not use emoji, unnecessary special symbols, no header. Just straight concise dot points.";
            String response = model.promptAI(promptText);

            if (weekday.equals("Monday")) {
                MondayOvr.setText(response);
            } else if (weekday.equals("Tuesday")) {
                TuesdayOvr.setText(response);
            } else if (weekday.equals("Wednesday")) {
                WedOvr.setText(response);
            } else if (weekday.equals("Thursday")) {
                ThursOvr.setText(response);
            } else if (weekday.equals("Friday")) {
                FriOvr.setText(response);
            }
        }
    }

    public void populateKPI(){
        try{
            String searchQuery = """
                          WITH latest AS (
                            SELECT *
                            FROM User_Collected_Data
                            WHERE studentNumber = ?
                            ORDER BY datetime(dateModified) DESC
                            LIMIT 1
                          )
                          SELECT
                            SUM(u.HoursStudied)             AS totalHours,
                            l.GPA,
                            l.GPAGoal,
                            l.HoursStudiedGoal,
                            l.AttendanceRate,
                            l.AttendanceRateGoal
                          FROM User_Collected_Data u
                          JOIN latest l
                            ON u.studentNumber = l.studentNumber
                          WHERE u.studentNumber = ?
                          """;
            PreparedStatement statement = userCollectedDataDAO.getDBConnection().prepareStatement(searchQuery);
            statement.setString(1, currentUserNumber);
            statement.setString(2, currentUserNumber);
            ResultSet rs = statement.executeQuery();

            // 1) GPA
            double gpaValue = rs.getDouble("GPA");
            double gpaGoal  = rs.getDouble("GPAGoal");
            double pctGpa   = (gpaGoal > 0) ? (gpaValue / gpaGoal) : 0;
            GPAProgress.setProgress(pctGpa);
            gpaLabel.setText(String.format("%.2f/%.2f", gpaValue, gpaGoal));
            applyThresholdColor(GPAProgress, pctGpa);

            // 2) Hours Studied
            double studyValue = rs.getDouble("totalHours");
            double studyGoal  = rs.getDouble("HoursStudiedGoal");
            double pctStudy   = (studyGoal > 0) ? (studyValue / studyGoal) : 0;
            StudyHourProgress.setProgress(pctStudy);
            studyLabel.setText(String.format("%.0f/%.0fh", studyValue, studyGoal));
            applyThresholdColor(StudyHourProgress, pctStudy);

            // 3) Attendance
            double attendValue = rs.getDouble("AttendanceRate");
            double attendGoal  = rs.getDouble("AttendanceRateGoal");
            double pctAttend   = (attendGoal > 0) ? (attendValue / attendGoal) : 0;
            AttendanceProgress.setProgress(pctAttend);
            attLabel.setText(String.format("%.0f/%.0f", attendValue, attendGoal));
            applyThresholdColor(AttendanceProgress, pctAttend);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void applyThresholdColor(ProgressIndicator pi, double frac) {
        String colour;
        if (frac < 0.3)       colour = "#f44336";  // red
        else if (frac < 0.75) colour = "#FFEB3B";  // yellow
        else                  colour = "#4CAF50";  // green

        // -fx-accent works on both ProgressBar and ProgressIndicator
        pi.setStyle("-fx-accent: " + colour + ";");
    }
}