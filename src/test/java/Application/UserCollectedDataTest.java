import Application.Database.UserCollectedData;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserCollectedDataTest {

    @Test
    void testStudentNumberField() {
        UserCollectedData data = new UserCollectedData("", "", 0, 0, 0f, 0f, 0f, 0f, "");

        data.setStudentNumber("n12345678");
        assertEquals("n12345678", data.getStudentNumber());
    }

    @Test
    void testGpaField() {
        UserCollectedData data = new UserCollectedData("", "", 0, 0, 0f, 0f, 0f, 0f, "");
        
        data.setGpa(5);
        assertEquals(5, data.getGpa());
    }

    @Test
    void testGpaGoalField() {
        UserCollectedData data = new UserCollectedData("", "", 0, 0, 0f, 0f, 0f, 0f, "");
        
        data.setGpaGoal(6);
        assertEquals(6, data.getGpaGoal());
    }

    @Test
    void testHoursStudiedField() {
        UserCollectedData data = new UserCollectedData("", "", 0, 0, 0f, 0f, 0f, 0f, "");
        
        data.setHoursStudied(5.0f);
        assertEquals(5.0f, data.getHoursStudied());
    }

    @Test
    void testHoursStudiedGoalField() {
        UserCollectedData data = new UserCollectedData("", "", 0, 0, 0f, 0f, 0f, 0f, "");

        data.setHoursStudiedGoal(10.0f);
        assertEquals(10.0f, data.getHoursStudiedGoal());
    }

    @Test
    void testAttendanceRateField() {
        UserCollectedData data = new UserCollectedData("", "", 0, 0, 0f, 0f, 0f, 0f, "");

        data.setAttendanceRate(0.85f);
        assertEquals(0.85f, data.getAttendanceRate());
    }

    @Test
    void testAttendanceRateGoalField() {
        UserCollectedData data = new UserCollectedData("", "", 0, 0, 0f, 0f, 0f, 0f, "");

        data.setAttendanceRateGoal(0.90f);
        assertEquals(0.90f, data.getAttendanceRateGoal());
    }

    @Test
    void testUnitsEnrolledField() {
        UserCollectedData data = new UserCollectedData("", "", 0, 0, 0f, 0f, 0f, 0f, "");

        data.setUnitsEnrolled("CS101, MATH202");
        assertEquals("CS101, MATH202", data.getUnitsEnrolled());
    }
}
