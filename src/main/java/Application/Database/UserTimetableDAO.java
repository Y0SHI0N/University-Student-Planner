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
}