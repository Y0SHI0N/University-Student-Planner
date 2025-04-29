import Application.Database.UserSignupDAO;
import Application.Database.UserSignup;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

class UserSignupDAOTest {

    private Connection testConnection;
    private UserSignupDAO dao;

    @BeforeEach
    void setUp() throws SQLException {
        testConnection = DriverManager.getConnection("jdbc:sqlite::memory:");
        dao = new UserSignupDAO(testConnection); // uses clean, in-memory DB
    }

    @Test
    void testInsertUser_successfullyInserts() throws SQLException {
        UserSignup user = new UserSignup("S11111111", "Alice", "Nguyen", "alice@example.com", "0411222333", "securePass");

        dao.insertUser(user);

        PreparedStatement stmt = testConnection.prepareStatement("SELECT * FROM User_Signup_Data WHERE StudentNumber = ?");
        stmt.setString(1, "S11111111");
        ResultSet rs = stmt.executeQuery();

        assertTrue(rs.next());
        assertEquals("Alice", rs.getString("Firstname"));
        assertEquals("Nguyen", rs.getString("LastName"));
        assertEquals("alice@example.com", rs.getString("Email"));
        assertEquals("0411222333", rs.getString("PhoneNumber"));
    }

    @Test
    void testInsertUser_ignoresDuplicate() {
        UserSignup user = new UserSignup("n12345678", "admin", "admin", "admin@db", "0123456789", "password");
        assertDoesNotThrow(() -> dao.insertUser(user));  // already inserted by createTable, should be ignored
    }

    @Test
    void testUpdateUser_successfullyUpdates() throws SQLException {
        UserSignup user = new UserSignup("n12345678", "admin", "admin", "admin@db", "0123456789", "password");

        // Change fields
        user.setFirstName("Super");
        user.setLastName("User");
        user.setEmail("super@user.com");
        user.setPhoneNumber("0999999999");

        dao.updateUser(user);

        PreparedStatement stmt = testConnection.prepareStatement("SELECT * FROM User_Signup_Data WHERE StudentNumber = ?");
        stmt.setString(1, "n12345678");
        ResultSet rs = stmt.executeQuery();

        assertTrue(rs.next());
        assertEquals("Super", rs.getString("Firstname"));
        assertEquals("User", rs.getString("LastName"));
        assertEquals("super@user.com", rs.getString("Email"));
        assertEquals("0999999999", rs.getString("PhoneNumber"));
    }
}
