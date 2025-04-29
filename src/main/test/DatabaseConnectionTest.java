import Application.Database.DatabaseConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.*;


class DatabaseConnectionTest {

    private Connection testconn;

    @BeforeEach
    void setUp() throws SQLException {
        testconn = DriverManager.getConnection("jdbc:sqlite::memory:");

        // Create dummy table before each test
        testconn.createStatement().execute(
                "CREATE TABLE IF NOT EXISTS dummy_table (col1 TEXT, col2 TEXT)"
        );
    }

    @AfterEach
    void tearDown() throws SQLException {
        if (testconn != null && !testconn.isClosed()) {
            // Clean up dummy table after each test
            testconn.createStatement().execute("DROP TABLE IF EXISTS dummy_table");
        }
        DatabaseConnection.closeConnection();
    }

    @Test
    void testGetInstance_NotNull() {
        Connection conn = DatabaseConnection.getInstance();
        assertNotNull(conn, "Database connection should not be null");
    }

    @Test
    void testGetInstance_Singleton() {
        Connection conn1 = DatabaseConnection.getInstance();
        Connection conn2 = DatabaseConnection.getInstance();
        assertSame(conn1, conn2, "Both instances should be the same object (singleton)");
    }

    @Test
    void testCloseConnection() {
        Connection conn = DatabaseConnection.getInstance();
        assertNotNull(conn);

        DatabaseConnection.closeConnection();

        // After closing, trying to close again should not throw exception
        assertDoesNotThrow(() -> DatabaseConnection.closeConnection());
    }

    @Test
    void testGetUserSignupDAO() {
        assertNotNull(DatabaseConnection.getUserSignupDAO(), "UserSignupDAO should not be null");
    }

    @Test
    void testGetUserTimetableDAO() {
        assertNotNull(DatabaseConnection.getUserTimetableDAO(), "UserTimetableDAO should not be null");
    }

    @Test
    void testStatementPrep_Success() throws SQLException {
        String sql = "INSERT INTO dummy_table (col1, col2) VALUES (?, ?)";

        try (PreparedStatement stmt = testconn.prepareStatement(sql)) {
            DatabaseConnection.StatementPrep(stmt, new int[]{1,2}, "value1", "value2");
            // No exception = success
        }
    }

    @Test
    void testStatementPrep_ThrowsExceptionOnMissingRequiredField() {
        String sql = "INSERT INTO dummy_table (col1, col2) VALUES (?, ?)";

        assertThrows(SQLException.class, () -> {
            try (PreparedStatement stmt = testconn.prepareStatement(sql)) {
                DatabaseConnection.StatementPrep(stmt, new int[]{1,2}, "value1", null);
            }
        });
    }
}

