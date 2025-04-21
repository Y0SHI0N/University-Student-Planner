package Application.Database;

import java.sql.*;

public class UserTimetableDAO extends BaseDAO {

    public UserTimetableDAO() {
        createTable();
    }

    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS User_Timetable_Data (" +
                "EventName VARCHAR NOT NULL, " +
                "StudentNumber VARCHAR NOT NULL, " +
                "EventType VARCHAR NOT NULL, " +
                "EventStartDatetime DATETIME NOT NULL, "+
                "EventEndDatetime DATETIME NOT NULL, "+
                "EventLocation VARCHAR, "+
                "Event_Attendance INT)";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //the bellow function is pretty much just copy and pasted from the UserSignupDAO
    public void insertEvent(UserTimetable event){
        String sql = "INSERT OR IGNORE INTO User_Timetable_Data (EventName, StudentNumber, EventType, EventStartDatetime, EventEndDatetime, EventLocation, Event_Attendance) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Define indexes of required (non-nullable) fields
            int[] requiredFields = {1, 2, 3, 4, 5, 6, 7}; // EventName, StudentNumber, EventType, EventStartDatetime, EventEndDatetime, EventLocation, EventAttendance

            // Use the improved StatementPrep() with validation
            DatabaseConnection.StatementPrep(stmt, requiredFields,
                    event.getEventName(),
                    event.getStudentNumber(),
                    event.getEventType(),
                    event.getEventStartDate(),
                    event.getEventEndDate(),
                    event.getEventLocation(),
                    event.getEventAttendance()    );

            stmt.executeUpdate();
            System.out.println("Event inserted successfully!");
        } catch (SQLException e) {
            System.err.println("Insert failed: " + e.getMessage());
        }
    }

    public void deleteEvent(UserTimetable event){
        String sql = "DELETE FROM User_Timetable_Date WHERE EventName = ?, StudentNumber = ?, EventType = ?, EventStartDatetime = ?, EventEndDatetime = ?, EventLocation = ?, Event_Attendance = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            int[] requiredFields = {1, 2, 3, 4, 5, 6, 7};
            // Use the improved StatementPrep() with validation
            DatabaseConnection.StatementPrep(stmt, requiredFields,
                    event.getEventName(),
                    event.getStudentNumber(),
                    event.getEventType(),
                    event.getEventStartDate(),
                    event.getEventEndDate(),
                    event.getEventLocation(),
                    event.getEventAttendance()    );

            stmt.executeUpdate();
            System.out.println("Event deleted successfully!");
        } catch (SQLException e) {
            System.err.println("Deletion failed: " + e.getMessage());
        }
    }


}