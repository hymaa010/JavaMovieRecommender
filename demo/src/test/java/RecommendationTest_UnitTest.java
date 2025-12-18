import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Vector;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.Entities.Movie;
import com.example.Entities.User;
import com.example.Writers.Recommendation;

public class RecommendationTest_UnitTest {

    private User user;
    private Vector<Movie> movies;

    @BeforeEach
    void setup() {
        user = mock(User.class);
        movies = new Vector<>();
    }

    private String captureOutput(Runnable action) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream original = System.out;
        System.setOut(new PrintStream(out));
        try {
            action.run();
        } finally {
            System.setOut(original);
        }
        return out.toString().trim();
    }

    @Test
    void testRecommendMovies_WithRecommendations() {

        Movie m1 = mock(Movie.class);
        Movie m2 = mock(Movie.class);
        Movie m3 = mock(Movie.class);

        when(m1.getId()).thenReturn("I001");
        when(m1.getGenres()).thenReturn(new String[]{"Sci-Fi", "Thriller"});

        when(m2.getId()).thenReturn("TG002");
        when(m2.getGenres()).thenReturn(new String[]{"Crime", "Drama"});

        when(m3.getId()).thenReturn("TM003");
        when(m3.getName()).thenReturn("The Matrix");
        when(m3.getGenres()).thenReturn(new String[]{"Sci-Fi", "Action"});

        movies.add(m1);
        movies.add(m2);
        movies.add(m3);

        when(user.getUserName()).thenReturn("ibrahim");
        when(user.getUserId()).thenReturn("123456789");
        when(user.getMoviesIds()).thenReturn(new String[]{"I001"});

        String output = captureOutput(() ->
            Recommendation.recommendMovies(user, movies)
        );

        assertEquals("ibrahim, 123456789\nThe Matrix", output);
    }

    @Test
    void testRecommendMovies_NoRecommendations() {

        Movie m1 = mock(Movie.class);
        when(m1.getId()).thenReturn("I001");
        when(m1.getGenres()).thenReturn(new String[]{"Sci-Fi"});

        movies.add(m1);

        when(user.getUserName()).thenReturn("ibrahim");
        when(user.getUserId()).thenReturn("123456789");
        when(user.getMoviesIds()).thenReturn(new String[]{"I001"});

        String output = captureOutput(() ->
            Recommendation.recommendMovies(user, movies)
        );

        assertEquals("ibrahim, 123456789", output);
    }

    @Test
    void testRecommendMovies_UserHasNoLikedMovies() {

        when(user.getUserName()).thenReturn("ibrahim");
        when(user.getUserId()).thenReturn("123456789");
        when(user.getMoviesIds()).thenReturn(new String[0]);

        String output = captureOutput(() ->
            Recommendation.recommendMovies(user, movies)
        );

        assertEquals("ibrahim, 123456789", output);
    }
}
