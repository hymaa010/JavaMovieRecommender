import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.Entities.Movie;

import static org.junit.jupiter.api.Assertions.*;

public class MovieTest {
    private Movie movie;

    @BeforeEach
    void setUp() {
        movie = new Movie();
    }

    @Test
    public void testDefaultConstructor() {
        assertEquals("", movie.getMovieTitle());
        assertEquals("", movie.getMovieId());

        assertNotNull(movie.getMovieGenre());
        assertEquals(10, movie.getMovieGenre().length);
    }

    @Test
    public void testSetAndGetMovieTitle() {
        Movie movie = new Movie();
        movie.setMovieTitle("Inception");

        assertEquals("Inception", movie.getMovieTitle());
    }

    @Test
    public void testSetAndGetMovieId() {
        Movie movie = new Movie();
        movie.setMovieId("M123");

        assertEquals("M123", movie.getMovieId());
    }

    @Test
    public void testSetAndGetMovieGenre() {
        Movie movie = new Movie();
        String[] genres = {"Action", "Sci-Fi"};

        movie.setMovieGenre(genres);

        String[] result = movie.getMovieGenre();
        assertNotNull(result);
        assertEquals(2, result.length);
        assertEquals("Action", result[0]);
        assertEquals("Sci-Fi", result[1]);
    }
}
