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

    @Test
    void testToStringIncludesAllValues() {
        UserCollectedData data = new UserCollectedData(
                "n12345678", "2025-04-20 05:10:30.333", 5, 6, 5.5f, 8.0f, 0.80f, 0.90f, "CS101, MATH202"
        );
        
        String out = data.toString();
        assertAll(
                () -> assertTrue(out.contains("n12345678")),
                () -> assertTrue(out.contains("2025-04-20 05:10:30.333")),
                () -> assertTrue(out.contains("5")),
                () -> assertTrue(out.contains("6")),
                () -> assertTrue(out.contains("5.5")),
                () -> assertTrue(out.contains("8.0")),
                () -> assertTrue(out.contains("0.80")),
                () -> assertTrue(out.contains("0.90")),
                () -> assertTrue(out.contains("CS101, MATH202"))
        );
    }
}
