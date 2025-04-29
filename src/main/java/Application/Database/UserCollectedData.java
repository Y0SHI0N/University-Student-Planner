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
    private Float hoursStudied;
    private Float hoursStudiedGoal;
    private Float attendanceRate;
    private Float attendanceRateGoal;
    private String unitsEnrolled;

    public UserCollectedData(String studentNumber, String dateModified, int gpa, int gpaGoal, Float hoursStudied, Float hoursStudiedGoal, Float attendanceRate, Float attendanceRateGoal, String unitsEnrolled) {
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

    public Float getHoursStudied() { return hoursStudied; }

    public Float getHoursStudiedGoal() { return hoursStudiedGoal; }

    public Float getAttendanceRate() { return attendanceRate * 100; }

    public Float getAttendanceRateGoal() { return attendanceRateGoal * 100; }

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