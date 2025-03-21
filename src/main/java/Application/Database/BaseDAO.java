package Application.Database;

import java.sql.*;


public class BaseDAO {

    protected final Connection connection;

    public BaseDAO() {
        this.connection = DatabaseConnection.getInstance();
    }

    public Connection getDBConnection() {
        return this.connection;
    }
}
