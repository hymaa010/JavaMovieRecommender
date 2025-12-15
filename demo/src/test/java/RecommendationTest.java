import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.*;
import java.util.AbstractMap.SimpleEntry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.Movie;
import com.example.Recommendation;
import com.example.User;
import com.example.movieInput;

public class RecommendationTest {
    private User user;

    @BeforeEach
    public void resetBeforeEach() throws Exception {
        // Reset user
        user = new User();
        user.setUserName("ibrahim");
        user.setUserId("123456789");

        // Reset static fields in movieInput
        Field mapField = movieInput.class.getDeclaredField("map");
        mapField.setAccessible(true);
        mapField.set(null, new HashMap<String, Vector<SimpleEntry<String, String>>>());

        Field moviesFoundField = movieInput.class.getDeclaredField("moviesFound");
        moviesFoundField.setAccessible(true);
        moviesFoundField.set(null, new boolean[1000]);
    }

    @Test
    public void testRecommendMovies_WithRecommendations() {
        movieInput mi = new movieInput();
        mi.setFilePath("E:\\apps\\VS Code\\sw testing project\\demo\\src\\test\\java\\test1Movies.txt");
        Vector<Movie> movies = mi.getMovies();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(out));

        user.setMoviesIds(new String[] { "I001" });
        try {
            Recommendation.recommendMovies(user, movies);
        } finally {
            System.setOut(originalOut);
        }

        assertEquals("ibrahim, 123456789\r\nThe Matrix".trim(),
                     out.toString().trim());
    }

    @Test
    public void testRecommendMovies_NoRecommendedMovies() {
        movieInput mi = new movieInput();
        mi.setFilePath("E:\\apps\\VS Code\\sw testing project\\demo\\src\\test\\java\\test2Movies.txt");
        Vector<Movie> movies = mi.getMovies();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(out));

        user.setMoviesIds(new String[] { "I001" });

        try {
            Recommendation.recommendMovies(user, movies);
        } finally {
            System.setOut(originalOut);
        }

        assertEquals("ibrahim, 123456789",
                     out.toString().trim());
    }

    @Test
    public void testRecommendMovies_MultipleLikedMovies() {
        movieInput mi = new movieInput();
        mi.setFilePath("E:\\apps\\VS Code\\sw testing project\\demo\\src\\test\\java\\test1Movies.txt");
        Vector<Movie> movies = mi.getMovies();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(out));

        user.setMoviesIds(new String[] { "I001", "TG002" });

        try {
            Recommendation.recommendMovies(user, movies);
        } finally {
            System.setOut(originalOut);
        }

        String output = out.toString();
        assertTrue(output.contains("The Matrix"));
    }

    @Test
    public void testRecommendMovies_UserHasNoLikedMovies() {
        movieInput mi = new movieInput();
        mi.setFilePath("E:\\apps\\VS Code\\sw testing project\\demo\\src\\test\\java\\test1Movies.txt");
        Vector<Movie> movies = mi.getMovies();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(out));

        user.setMoviesIds(new String[0]);
        try {
            Recommendation.recommendMovies(user, movies);
        } finally {
            System.setOut(originalOut);
        }

        assertEquals("ibrahim, 123456789", out.toString().trim());
    }
    @Test
    public void testRecommendMovies_InvalidLikedMovieId() {
        movieInput mi = new movieInput();
        mi.setFilePath("E:\\apps\\VS Code\\sw testing project\\demo\\src\\test\\java\\test1Movies.txt");
        Vector<Movie> movies = mi.getMovies();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(out));

        user.setMoviesIds(new String[] { "INVALID_ID" });

        try {
            Recommendation.recommendMovies(user, movies);
        } finally {
            System.setOut(originalOut);
        }

        assertEquals("ibrahim, 123456789", out.toString().trim());
    }

    @Test
    public void testRecommendMovies_EmptyMovieList() {
        Vector<Movie> movies = new Vector<>();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(out));

        user.setMoviesIds(new String[] { "I001" });

        try {
            Recommendation.recommendMovies(user, movies);
        } finally {
            System.setOut(originalOut);
        }

        assertEquals("ibrahim, 123456789", out.toString().trim());
    }
}