import Application.Database.UserCollectedData;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserCollectedDataTest {

    @Test
    void testStudentNumberField() {
        UserCollectedData data = new UserCollectedData(null, 0, 0, null, null, 0f, 0f, null);

        data.setStudentNumber("12345678");
        assertEquals("12345678", data.getStudentNumber());
    }

    @Test
    void testGpaField() {
        UserCollectedData data = new UserCollectedData(null, 0, 0, null, null, 0f, 0f, null);

        data.setGpa(5);
        assertEquals(5, data.getGpa());
    }

    @Test
    void testGpaGoalField() {
        UserCollectedData data = new UserCollectedData(null, 0, 0, null, null, 0f, 0f, null);

        data.setGpaGoal(6);
        assertEquals(6, data.getGpaGoal());
    }

    @Test
    void testHoursStudiedField() {
        UserCollectedData data = new UserCollectedData(null, 0, 0, null, null, 0f, 0f, null);

        data.setHoursStudied("20");
        assertEquals("20", data.getHoursStudied());
    }

    @Test
    void testHoursStudiedGoalField() {
        UserCollectedData data = new UserCollectedData(null, 0, 0, null, null, 0f, 0f, null);

        data.setHoursStudiedGoal("30");
        assertEquals("30", data.getHoursStudiedGoal());
    }

    @Test
    void testAttendanceRateField() {
        UserCollectedData data = new UserCollectedData(null, 0, 0, null, null, 0f, 0f, null);

        data.setAttendanceRate(85.0f);
        assertEquals(85.0f, data.getAttendanceRate());
    }

    @Test
    void testAttendanceRateGoalField() {
        UserCollectedData data = new UserCollectedData(null, 0, 0, null, null, 0f, 0f, null);

        data.setAttendanceRateGoal(90.0f);
        assertEquals(90.0f, data.getAttendanceRateGoal());
    }

    @Test
    void testUnitsEnrolledField() {
        UserCollectedData data = new UserCollectedData(null, 0, 0, null, null, 0f, 0f, null);

        data.setUnitsEnrolled("CS101, MATH202");
        assertEquals("CS101, MATH202", data.getUnitsEnrolled());
    }

    @Test
    void testToStringIncludesAllValues() {
        UserCollectedData data = new UserCollectedData(
                "12345678", 5, 6, "20", "30", 80.0f, 90.0f, "CS101, MATH202"
        );

        String out = data.toString();
        assertAll(
                () -> assertTrue(out.contains("12345678")),
                () -> assertTrue(out.contains("5")),
                () -> assertTrue(out.contains("6")),
                () -> assertTrue(out.contains("20")),
                () -> assertTrue(out.contains("30")),
                () -> assertTrue(out.contains("80.0")),
                () -> assertTrue(out.contains("90.0")),
                () -> assertTrue(out.contains("CS101, MATH202"))
        );
    }
}
