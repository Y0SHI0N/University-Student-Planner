package Application.Database;

import java.sql.*;

public class UserSignupDAO extends BaseDAO{

    public UserSignupDAO() {
        createTable();
    }

    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS User_Signup_Data (" +
                "StudentNumber TEXT PRIMARY KEY, " +
                "Firstname TEXT NOT NULL, " +
                "LastName TEXT NOT NULL, " +
                "Email TEXT NOT NULL, " +
                "PhoneNumber TEXT, " +
                "LoginPassword TEXT NOT NULL)";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);

            // create default admin user
            UserSignup adminUser = new UserSignup("admin","admin","admin","admin","","1");
            insertUser(adminUser);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertUser(UserSignup user) {
        String sql = "INSERT OR IGNORE INTO User_Signup_Data (StudentNumber, Firstname, LastName, Email, PhoneNumber, LoginPassword) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Define indexes of required (non-nullable) fields
            int[] requiredFields = {1, 2, 3, 4, 6}; // StudentNumber, Firstname, LastName, Email, LoginPassword

            // Use the improved StatementPrep() with validation
            DatabaseConnection.StatementPrep(stmt, requiredFields,
                    user.getStudentNumber(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getPhoneNumber(),  // Optional field
                    user.getLoginPassword()
            );

            stmt.executeUpdate();
            System.out.println("User inserted successfully!");
        } catch (SQLException e) {
            System.err.println("Insert failed: " + e.getMessage());
        }
    }
}
