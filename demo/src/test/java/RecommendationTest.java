import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.Readers.movieInput;
import com.example.Entities.Movie;
import com.example.Entities.User;
import com.example.Validators.movie_validator;
import com.example.Validators.user_validator;
import com.example.Writers.Recommendation;

public class RecommendationTest {
    private User user;

    @BeforeEach
    public void BeforeEach() throws Exception {
        user = new User();
        user.setUserName("ibrahim");
        user.setUserId("123456789");
        movie_validator.reset();
        user_validator.reset();
        movieInput.resetMap();

        
    }
   
    private String createTestFile(String content) throws IOException {
        File temp = File.createTempFile("testFile", ".txt");
        temp.deleteOnExit();
        try (FileWriter writer = new FileWriter(temp)) {
            writer.write(content);
        }
        return temp.getAbsolutePath();
    }

    @Test
    public void testRecommendMovies_WithRecommendations() throws IOException {

        String content =

            "Inception,I001\n" +
            "Sci-Fi,Thriller\n" +
            "The Godfather,TG002\n" +
            "Crime,Drama\n" +
            "The Matrix,TM003\n" +
            "Sci-Fi,Action\n";
                        
        String path = createTestFile(content);
        movieInput mi = new movieInput();
        mi.setFilePath(path);
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
    public void testRecommendMovies_NoRecommendedMovies() throws IOException {
        String content =
            "Inception,I001\n" +
            "Sci-Fi,Thriller\n" +
            "The Godfather,TG002\n" +
            "Crime,Drama\n";
                        
        String path = createTestFile(content);
        movieInput mi = new movieInput();
        mi.setFilePath(path);
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
    public void testRecommendMovies_MultipleLikedMovies() throws IOException {
        movieInput mi = new movieInput();
        String content =
          "Inception,I001\n" +
            "Sci-Fi,Thriller\n" +
            "The Godfather,TG002\n" +
            "Crime,Drama\n" +
            "The Matrix,TM003\n" +
            "Sci-Fi,Action\n";
        String path = createTestFile(content);
        mi.setFilePath(path);
        
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
    public void testRecommendMovies_UserHasNoLikedMovies() throws IOException {
        String content =

            "Inception,I001\n" +
            "Sci-Fi,Thriller\n" +
            "The Godfather,TG002\n" +
            "Crime,Drama\n" +
            "The Matrix,TM003\n" +
            "Sci-Fi,Action\n";
                        
        String path = createTestFile(content);
        movieInput mi = new movieInput();
        mi.setFilePath(path);
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
    public void testRecommendMovies_InvalidLikedMovieId() throws IOException {
        String content =

            "Inception,I001\n" +
            "Sci-Fi,Thriller\n" +
            "The Godfather,TG002\n" +
            "Crime,Drama\n" +
            "The Matrix,TM003\n" +
            "Sci-Fi,Action\n";
                        
        String path = createTestFile(content);
        movieInput mi = new movieInput();
        mi.setFilePath(path);
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