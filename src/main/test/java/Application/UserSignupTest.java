import org.junit.jupiter.api.Test;
import Application.Database.UserSignup;

import static org.junit.jupiter.api.Assertions.*;

class UserSignupTest {

    @Test
    void testStudentNumberField() {
        UserSignup user = new UserSignup(null, null, null, null, null, null);
        user.setStudentNumber("S1234567");
        assertEquals("S1234567", user.getStudentNumber());
    }

    @Test
    void testFirstNameField() {
        UserSignup user = new UserSignup(null, null, null, null, null, null);
        user.setFirstName("Alice");
        assertEquals("Alice", user.getFirstName());
    }

    @Test
    void testLastNameField() {
        UserSignup user = new UserSignup(null, null, null, null, null, null);
        user.setLastName("Nguyen");
        assertEquals("Nguyen", user.getLastName());
    }

    @Test
    void testEmailField() {
        UserSignup user = new UserSignup(null, null, null, null, null, null);
        user.setEmail("alice@example.com");
        assertEquals("alice@example.com", user.getEmail());
    }

    @Test
    void testPhoneNumberField() {
        UserSignup user = new UserSignup(null, null, null, null, null, null);
        user.setPhoneNumber("0412345678");
        assertEquals("0412345678", user.getPhoneNumber());
    }

    @Test
    void testLoginPasswordField() {
        UserSignup user = new UserSignup(null, null, null, null, null, null);
        user.setLoginPassword("securePass123");
        assertEquals("securePass123", user.getLoginPassword());
    }

    @Test
    void testToStringMasksPassword() {
        UserSignup user = new UserSignup(
                "S7654321",
                "Bob",
                "Smith",
                "bob@example.com",
                "0498765432",
                "mySecretPassword"
        );

        String output = user.toString();
        assertTrue(output.contains("studentNumber='S7654321'"));
        assertTrue(output.contains("firstName='Bob'"));
        assertTrue(output.contains("lastName='Smith'"));
        assertTrue(output.contains("email='bob@example.com'"));
        assertTrue(output.contains("phoneNumber='0498765432'"));
        assertTrue(output.contains("loginPassword='*****'"));  // Masked, not the real password
        assertFalse(output.contains("mySecretPassword"));       // Should not expose real password
    }
}