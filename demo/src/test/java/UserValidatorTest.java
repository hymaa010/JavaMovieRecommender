import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.example.Validators.user_validator;

class UserValidatorTest {

    @Test
    void testCheckName_valid() {
        assertTrue(user_validator.checkName("Ziad Tamer"));
        assertTrue(user_validator.checkName("Ziad"));
    }

    @Test
    void testCheckName_startsWithSpace() {
        assertFalse(user_validator.checkName(" Mazen"));
    }

    @Test
    void testCheckName_containsNumbers() {
        assertFalse(user_validator.checkName("Rana2"));
    }

    @Test
    void testCheckName_containsSpecialChars() {
        assertFalse(user_validator.checkName("Shahd@"));
    }

    @Test
    void testCheckId_valid() {
        assertTrue(user_validator.checkId("12345678B"));
    }

    @Test
    void testCheckId_invalidLength() {
        assertFalse(user_validator.checkId("1234"));
    }

    @Test
    void testCheckId_invalidDigit() {
        assertFalse(user_validator.checkId("1234A678A"));
    }

    @Test
    void testCheckId_allNumbers() {
        assertTrue(user_validator.checkId("123456789"));
    }

    @Test
    void testCheckId_duplicate() {
        assertTrue(user_validator.checkId("12345678Z"));
        assertFalse(user_validator.checkId("12345678Z"));
    }
}
