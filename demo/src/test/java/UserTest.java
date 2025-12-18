
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import com.example.Entities.User;

public class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void testDefaultConstructor() {
        assertEquals("", user.getUserName());
        assertEquals("", user.getUserId());
        assertNotNull(user.getMoviesIds());
        assertEquals(20, user.getMoviesIds().length);
    }

    @Test
    void testSetAndGetUserName() {
        user.setUserName("Mazen Mohamed");
        assertEquals("Mazen Mohamed", user.getUserName());
    }

    @Test
    void testSetAndGetUserId() {
        user.setUserId("123456789");
        assertEquals("123456789", user.getUserId());
    }

    @Test
    void testSetAndGetMoviesIds() {
        String[] movies = {"DSD123", "KWR456", "MJQ789"};
        user.setMoviesIds(movies);
        assertArrayEquals(movies, user.getMoviesIds());
    }

    @Test
    void testMoviesIdsDefaultValuesAreNull() {
        String[] arr = user.getMoviesIds();
        for (String s : arr) {
            assertNull(s);
        }
    }
}
