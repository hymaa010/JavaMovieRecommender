import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.example.Validators.movie_validator;

public class movieValidatorTest {    
    
    @Test
    void testValidTitle() {
        assertTrue(movie_validator.checkTitle("The Dark Knight"));
        assertTrue(movie_validator.checkTitle("T"));
        assertTrue(movie_validator.checkTitle("Se7en"));
    }

    @Test
    void testInvalidTitleFirstChar() {
        assertFalse(movie_validator.checkTitle("pulp Fiction"));
        assertFalse(movie_validator.checkTitle("7Seven"));
        assertFalse(movie_validator.checkTitle(" Wonder"));
        assertFalse(movie_validator.checkTitle("WALL -E"));
    }

    @Test
    void testInvalidTitleAfterSpace() {
        assertFalse(movie_validator.checkTitle("The god Father"));
    }

    @Test
    void testValidIdLetters() {
        assertTrue(movie_validator.checkIdLetters("The Curious Case Of Benjamin Button", "TCCOBB123"));
        assertTrue(movie_validator.checkIdLetters("Big Hero", "BH999"));
    }

    @Test
    void testInvalidIdLetters() {
        assertFalse(movie_validator.checkIdLetters("Mission Impossible", "IM123"));
        assertFalse(movie_validator.checkIdLetters("Final Destination", "MW678"));
    }

    @Test
    void testCheckIdNumberUnique() {
        assertTrue(movie_validator.checkIdNumber("APT123"));
        assertFalse(movie_validator.checkIdNumber("APT123"));
    }
    
}
