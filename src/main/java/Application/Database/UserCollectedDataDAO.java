package Application.Database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

public class UserCollectedDataDAO extends BaseDAO{
    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS User_Collected_Data (" +
                "StudentNumber TEXT PRIMARY KEY, " +
                "dateModified TIME PRIMARY KEY," +
                "GPA INT DEFAULT 0," +
                "GPAGoal INT DEFAULT 0," +
                "HoursStudied VARCHAR," +
                "HoursStudiedGoal VARCHAR," +
                "AttendanceRate FLOAT DEFAULT 0," +
                "AttendanceRateGoal FLOAT DEFAULT 0," +
                "UnitsEnrolled VARCHAR,";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);

            // create default admin user
            UserCollectedData adminUserData = new UserCollectedData("n12345678", LocalDateTime.now().toString(), 7,7,"5", "8", 0.8, 1.0, "someUnits" );
            insertData(adminUserData);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertData(UserCollectedData usersData){
        String sql = "INSERT OR IGNORE INTO User_Collected_Data UserCollectedData(studentNumber, dateModified, gpa, gpaGoal, hoursStudied, hoursStudiedGoal, attendanceRate, attendanceRateGoal, unitsEnrolled) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Define indexes of required (non-nullable) fields
            int[] requiredFields = {1, 2}; // StudentNumber, dateModified

            // Use the improved StatementPrep() with validation
            DatabaseConnection.StatementPrep(stmt, requiredFields,
                    usersData.getStudentNumber(),
                    usersData.getDateModified(),
                    usersData.getGpa(),
                    usersData.getGpaGoal(),
                    usersData.getHoursStudied(),
                    usersData.getHoursStudiedGoal(),
                    usersData.getAttendanceRate(),
                    usersData.getAttendanceRateGoal(),
                    usersData.getUnitsEnrolled()
            );

            stmt.executeUpdate();
            System.out.println("User inserted successfully!");
        } catch (SQLException e) {
            System.err.println("Insert failed: " + e.getMessage());
        }
    }
    public void updateUserData(UserCollectedData userData)throws SQLException {
        PreparedStatement updateUserData= connection.prepareStatement(
                "UPDATE User_Collected_Data SET dateModified=?, GPA=?, GPAGoal=?, HoursStudied=?, HoursStudiedGoal=?, AttendanceRate=?, AttendanceRateGoal=?, UnitsEnrolled=? WHERE StudentNumber=?");
        updateUserData.setString(1,userData.getDateModified().toString());
        updateUserData.setString(2,Integer.toString(userData.getGpa()));
        updateUserData.setString(3,Integer.toString(userData.getGpaGoal()));
        updateUserData.setString(4,userData.getHoursStudied());
        updateUserData.setString(5,userData.getHoursStudiedGoal());
        updateUserData.setString(6,Double.toString(userData.getAttendanceRate()));
        updateUserData.setString(7,Double.toString(userData.getAttendanceRateGoal()));
        updateUserData.setString(8,userData.getUnitsEnrolled());
        updateUserData.setString(9,userData.getStudentNumber());
        updateUserData.execute();
    }

}
