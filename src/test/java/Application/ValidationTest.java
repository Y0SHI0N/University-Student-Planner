import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import Application.Validation;

public class ValidationTest {
    @Test
    void testInvalidStudentNumber() {
        assertNotEquals(null, Validation.validateStudentNumber("John"));
    }

    @Test
    void testAnyBlank() {
        assertNotEquals(null, Validation.anyBlank("John", "", "Doe"));
    }

    @Test
    void testPasswordsNotMatching() {
        assertNotEquals(null, Validation.validatePassword("establishment", "residence"));
    }

    @Test
    void testPasswordTooShort() {
        assertNotEquals(null, Validation.validatePassword("the", "the"));
    }

    @Test
    void testInvalidEmail() {
        assertNotEquals(null, Validation.validateEmail("john"));
    }

    @Test
    void testInvalidPhoneNumber() {
        assertNotEquals(null, Validation.validatePhoneNumber("zero one two three four"));
    }
}
