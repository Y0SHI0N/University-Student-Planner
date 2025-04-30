package Application.Database;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.time.LocalDate;

public class UserCollectedData {
    private String studentNumber;
    private String dateModified;
    private int gpa;
    private int gpaGoal;
    private float hoursStudied;
    private float hoursStudiedGoal;
    private float attendanceRate;
    private float attendanceRateGoal;
    private String unitsEnrolled;

    //UserCollectedData constructs a row in the database schema
    public UserCollectedData(String studentNumber, String dateModified, int gpa, int gpaGoal, float hoursStudied, float hoursStudiedGoal, float attendanceRate, float attendanceRateGoal, String unitsEnrolled) {
        this.studentNumber = studentNumber;
        this.dateModified = dateModified;
        this.gpa = gpa;
        this.gpaGoal = gpaGoal;
        this.hoursStudied = hoursStudied;
        this.hoursStudiedGoal = hoursStudiedGoal;
        this.attendanceRate = attendanceRate;
        this.attendanceRateGoal = attendanceRateGoal;
        this.unitsEnrolled = unitsEnrolled;
    }

    public String getStudentNumber() { return studentNumber; }

    public String getDateModified() { return dateModified; }

    public int getGpa() { return gpa; }

    public int getGpaGoal() { return gpaGoal; }

    public float getHoursStudied() { return hoursStudied; }

    public float getHoursStudiedGoal() { return hoursStudiedGoal; }

    public float getAttendanceRate() { return attendanceRate * 100; }

    public float getAttendanceRateGoal() { return attendanceRateGoal * 100; }

    public String getUnitsEnrolled() { return unitsEnrolled; }
    public void setUnitsEnrolled(String unitsEnrolled) { this.unitsEnrolled = unitsEnrolled; }

    @Override
    public String toString() {
        return "UserCollectedData{" +
                "studentNumber='" + studentNumber + '\'' +
                ", dateModified='" + dateModified + '\'' +
                ", gpa=" + gpa + '\'' +
                ", gpaGoal=" + gpaGoal + '\'' +
                ", hoursStudied='" + hoursStudied + '\'' +
                ", hoursStudiedGoal='" + hoursStudiedGoal + '\'' +
                ", attendanceRate=" + attendanceRate + '\'' +
                ", attendanceRateGoal=" + attendanceRateGoal + '\'' +
                ", unitsEnrolled='" + unitsEnrolled + '\'' +
                '}';
    }


}
