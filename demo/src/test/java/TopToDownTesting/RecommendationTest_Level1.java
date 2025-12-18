package TopToDownTesting;
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
import com.example.Readers.movieInput;

public class RecommendationTest_Level1 {

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
    void testRecommendMovies_WithRecommendations_Flexible() {
        
        Movie m1 = mock(Movie.class);
        Movie m2 = mock(Movie.class);
        Movie m3 = mock(Movie.class);

        when(m1.getMovieId()).thenReturn("M123");
        when(m1.getMovieTitle()).thenReturn("Moon");
        when(m1.getMovieGenre()).thenReturn(new String[]{"Sci-Fi", "Action"});

        when(m2.getMovieId()).thenReturn("Z123");
        when(m2.getMovieTitle()).thenReturn("Zoro");
        when(m2.getMovieGenre()).thenReturn(new String[]{"Crime", "Drama"});

        when(m3.getMovieId()).thenReturn("I123");
        when(m3.getMovieTitle()).thenReturn("Interstellar");
        when(m3.getMovieGenre()).thenReturn(new String[]{"Sci-Fi", "Action"});

        movies.add(m1);
        movies.add(m2);
        movies.add(m3);

        when(user.getUserName()).thenReturn("Mazen");
        when(user.getUserId()).thenReturn("123456789");
        when(user.getMoviesIds()).thenReturn(new String[]{"M123"}); 

        movieInput.resetMap();

        for (Movie m : movies) {
            String title = m.getMovieTitle();
            String id = m.getMovieId();
            for (String genre : m.getMovieGenre()) {
                movieInput.getMap().putIfAbsent(genre, new Vector<>());
                movieInput.getMap().get(genre).add(new java.util.AbstractMap.SimpleEntry<>(title, id));
            }
        }

        String output = captureOutput(() -> Recommendation.recommendMovies(user, movies));
        String out = output.toString().replace("\r", "").trim();
        assertEquals("Mazen, 123456789\nInterstellar", out);
    }   

    @Test
    void testRecommendMovies_NoRecommendations() {

        Movie m1 = mock(Movie.class);
        when(m1.getMovieId()).thenReturn("M123");
        when(m1.getMovieTitle()).thenReturn("Moon");
        when(m1.getMovieGenre()).thenReturn(new String[]{"Sci-Fi"});

        movies.add(m1);

        when(user.getUserName()).thenReturn("Mazen");
        when(user.getUserId()).thenReturn("123456789");
        when(user.getMoviesIds()).thenReturn(new String[]{"M123"});

        String output = captureOutput(() ->
            Recommendation.recommendMovies(user, movies)
        );

        assertEquals("Mazen, 123456789", output);
    }

    @Test
    void testRecommendMovies_MultipleLikedMovies() {
        Movie m1 = mock(Movie.class);
        Movie m2 = mock(Movie.class);
        Movie m3 = mock(Movie.class);

        when(m1.getMovieId()).thenReturn("I123");
        when(m1.getMovieTitle()).thenReturn("Interstellar");
        when(m1.getMovieGenre()).thenReturn(new String[]{"Sci-Fi", "Thriller"});

        when(m2.getMovieId()).thenReturn("M123");
        when(m2.getMovieTitle()).thenReturn("Moon");
        when(m2.getMovieGenre()).thenReturn(new String[]{"Sci-Fi", "Thriller"});

        when(m3.getMovieId()).thenReturn("Z123");
        when(m3.getMovieTitle()).thenReturn("Zoro");
        when(m3.getMovieGenre()).thenReturn(new String[]{"Crime", "Drama"});

        movies.add(m1);
        movies.add(m2);
        movies.add(m3);

        when(user.getUserName()).thenReturn("Mazen");
        when(user.getUserId()).thenReturn("123456789");
        when(user.getMoviesIds()).thenReturn(new String[]{"I123", "Z123"});

        movieInput.resetMap();

        for (Movie m : movies) {
            String title = m.getMovieTitle();
            String id = m.getMovieId();
            for (String genre : m.getMovieGenre()) {
                movieInput.getMap().putIfAbsent(genre, new Vector<>());
                movieInput.getMap().get(genre).add(new java.util.AbstractMap.SimpleEntry<>(title, id));
            }
        }

        String output = captureOutput(() -> Recommendation.recommendMovies(user, movies));
        assertTrue(output.contains("Moon"));
    }

    @Test
    void testRecommendMovies_UserHasNoLikedMovies() {
        Movie m1 = mock(Movie.class);

        when(m1.getMovieId()).thenReturn("I123");
        when(m1.getMovieTitle()).thenReturn("Interstellar");
        when(m1.getMovieGenre()).thenReturn(new String[]{"Sci-Fi", "Thriller"});

        movies.add(m1);

        when(user.getUserName()).thenReturn("Mazen");
        when(user.getUserId()).thenReturn("123456789");
        when(user.getMoviesIds()).thenReturn(new String[0]);

        String output = captureOutput(() ->Recommendation.recommendMovies(user, movies));

        assertEquals("Mazen, 123456789", output);
    }

    @Test
    public void testRecommendMovies_EmptyMovieList() {
        when(user.getUserName()).thenReturn("Mazen");
        when(user.getUserId()).thenReturn("123456789");
        when(user.getMoviesIds()).thenReturn(new String[]{"M123", "Z123"});

        String output = captureOutput(() ->Recommendation.recommendMovies(user, movies));

        assertEquals("Mazen, 123456789", output);
    }
}
