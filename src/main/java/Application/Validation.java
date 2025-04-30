package Application;

import Application.Database.DatabaseConnection;
import Application.Database.UserSignupDAO;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.stream.Stream;

public class Validation {
    private static final UserSignupDAO userSignupDAO = DatabaseConnection.getUserSignupDAO();

    private static boolean checkDuplicateStudentNumber(String studentNumber) {
        String checkUnique = "SELECT count(1) FROM User_Signup_Data where StudentNumber = '" + studentNumber + "';";
        try {
            Statement statement = userSignupDAO.getDBConnection().createStatement();
            ResultSet queryResult = statement.executeQuery(checkUnique);
            queryResult.next();

            return queryResult.getInt(1) == 1; // returns true if the student number already exists
        } catch (Exception e){
            System.out.println(e + "exception");
        }
        return false; // will never execute but needed for no syntax error
    }

    /** Returns an error string if the student number is invalid, or null otherwise */
    public static String validateStudentNumber(String studentNumber) {
        // check if student number solely comprised of 'n' followed by 8 numeric characters
        if (!studentNumber.matches("^n[0-9]{8}$")) return "invalid student number.";

        if (checkDuplicateStudentNumber(studentNumber)) return "student number already registered.";

        return null;
    }

    /** Returns an error string if any of the provided strings are blank, or null otherwise */
    public static String anyBlank(String... strings) {
        for (String s : strings) if (s.isBlank()) return "don't leave fields empty.";
        return null;
    }

    /** Returns an error string if the passwords are invalid, or null otherwise */
    public static String validatePassword(String password, String confirmedPassword) {
        if (password.length() < 8) return "password must be at least 8 characters long.";

        if (!password.equals(confirmedPassword)) return "passwords don't match.";

        return null;
    }

    /** Returns an error string if the email is invalid, or null otherwise */
    public static String validateEmail(String email) {
        // check if email has an '@' with characters on either side
        if (!email.matches(".+@.+")) return "invalid email.";

        return null;
    }

    /** Returns an error string if the phone number is invalid, or null otherwise */
    public static String validatePhoneNumber(String phoneNumber) {
        if (!phoneNumber.matches("^[0-9]{1,10}$")) return "invalid phone number.";

        return null;
    }

    /** Prepends an 'n' to the student number if not present */
    public static String normaliseStudentNo(String studentNo) {
        if (studentNo.startsWith("n")) return studentNo;

        return "n" + studentNo;
    }
}
