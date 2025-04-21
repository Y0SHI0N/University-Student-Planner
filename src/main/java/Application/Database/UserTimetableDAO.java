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
        String sql = "DELETE FROM User_Timetable_Data WHERE EventName = ? AND StudentNumber = ? AND EventType = ? AND EventStartDatetime = ? AND EventEndDatetime = ? AND EventLocation = ? AND Event_Attendance = ?";
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

    public void updateEvent(UserTimetable currentEvent, UserTimetable updatedEvent){
        try {
            String sql = "UPDATE User_Timetable_Data SET EventName = ?, EventType = ?, EventStartDatetime = ?, EventEndDatetime = ?," +
                    " EventLocation = ?, Event_Attendance = ? WHERE EventName = ? AND StudentNumber = ? AND EventType = ?" +
                    " AND EventStartDatetime = ? AND EventEndDatetime = ? AND EventLocation = ? AND Event_Attendance = ?";
            PreparedStatement updateEvent= connection.prepareStatement(sql);
            updateEvent.setString(1,updatedEvent.getEventName());
            updateEvent.setString(2,updatedEvent.getEventType());
            updateEvent.setString(3,updatedEvent.getEventStartDate());
            updateEvent.setString(4,updatedEvent.getEventEndDate());
            updateEvent.setString(5,updatedEvent.getEventLocation());
            updateEvent.setInt(6,updatedEvent.getEventAttendance());
            updateEvent.setString(7,currentEvent.getEventName());
            updateEvent.setString(8,currentEvent.getStudentNumber());
            updateEvent.setString(9,currentEvent.getEventType());
            updateEvent.setString(10,currentEvent.getEventStartDate());
            updateEvent.setString(11,currentEvent.getEventEndDate());
            updateEvent.setString(12,currentEvent.getEventLocation());
            System.out.println("1");
            updateEvent.setInt(13,currentEvent.getEventAttendance());
            System.out.println("2");
            updateEvent.execute();
            System.out.println("Event updated successfully!");
        } catch (SQLException e) {
            System.err.println("Update failed: " + e.getMessage());
        }

    }


}