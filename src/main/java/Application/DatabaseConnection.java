package Application;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    public Connection databaseLink;

    public Connection getConnection(){
        String databaseName = "student_planner_db";
        String databaseUser = "student_user";
        String databasePassword = "SecurePass123";
        String url = "jdbc:mysql://localhost:3306/" + databaseName + "?serverTimezone=UTC";

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
        } catch (Exception e){
            System.out.println(e + "exception");
        }
        return databaseLink;
    }
}
