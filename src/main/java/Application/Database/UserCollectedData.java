package Application.Database;

import java.time.LocalDateTime;
import java.util.Date;
import java.time.LocalDate;

public class UserCollectedData {
    private String studentNumber;
    private String dateModified;
    private int gpa;
    private int gpaGoal;
    private String hoursStudied;
    private String hoursStudiedGoal;
    private double attendanceRate;
    private double attendanceRateGoal;
    private String unitsEnrolled;

    public UserCollectedData(String studentNumber, String dateModified, int gpa, int gpaGoal, String hoursStudied, String hoursStudiedGoal, double attendanceRate, double attendanceRateGoal, String unitsEnrolled) {
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
    public void setStudentNumber(String studentNumber) { this.studentNumber = studentNumber; }

    public String getDateModified() { return LocalDateTime.now().toString(); }

    public int getGpa() { return gpa; }
    public void setGpa(int gpa) { this.gpa = gpa; }

    public int getGpaGoal() { return gpaGoal; }
    public void setGpaGoal(int gpaGoal) { this.gpaGoal = gpaGoal; }

    public String getHoursStudied() { return hoursStudied; }
    public void setHoursStudied(String hoursStudied) { this.hoursStudied = hoursStudied; }

    public String getHoursStudiedGoal() { return hoursStudiedGoal; }
    public void setHoursStudiedGoal(String hoursStudiedGoal) { this.hoursStudiedGoal = hoursStudiedGoal; }

    public double getAttendanceRate() { return attendanceRate; }
    public void setAttendanceRate(double attendanceRate) { this.attendanceRate = attendanceRate; }

    public double getAttendanceRateGoal() { return attendanceRateGoal; }
    public void setAttendanceRateGoal(double attendanceRateGoal) { this.attendanceRateGoal = attendanceRateGoal; }

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