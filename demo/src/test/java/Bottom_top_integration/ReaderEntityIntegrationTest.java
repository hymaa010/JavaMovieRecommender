package Bottom_top_integration;
import java.io.File;
import java.io.PrintWriter;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.Entities.Movie;
import com.example.Readers.movieInput;

public class ReaderEntityIntegrationTest {
    private movieInput mi;

    @BeforeEach
    void setup() {
        mi = new movieInput();
        movieInput.resetMap();

    }

    @Test
    void testGetMoviesValidInput() throws Exception {

        File temp = File.createTempFile("movies", ".txt");
        try (PrintWriter pw = new PrintWriter(temp)) {
            pw.println("Forrest Gump,FG001");
            pw.println("Historical,Drama");

            pw.println("The Karate Kid,TKK002");
            pw.println("Drama,Action");

            pw.println("The Mask,TM005");
            pw.println("Comedy,Musical");
        }

        mi.setFilePath(temp.getAbsolutePath());
        Vector <Movie> movies = mi.getMovies();

        assertEquals(3, movies.size());

        Movie m1 = movies.get(0);
        assertEquals("Forrest Gump", m1.getMovieTitle());
        assertEquals("FG001", m1.getMovieId());

        Movie m2 = movies.get(1);
        assertEquals("The Karate Kid", m2.getMovieTitle());
        assertEquals("TKK002", m2.getMovieId());

        Movie m3 = movies.get(2);
        assertEquals("The Mask", m3.getMovieTitle());
        assertEquals("TM005", m3.getMovieId());

        Map<String, Vector<SimpleEntry<String, String>>> map = movieInput.getMap();

        assertTrue(map.containsKey("Action"));
        assertTrue(map.containsKey("Drama"));
        assertTrue(map.containsKey("Comedy"));
        assertTrue(map.containsKey("Musical"));
        assertTrue(map.containsKey("Historical"));

        assertEquals(2, map.get("Drama").size());
    }

    @Test
    void testGetMoviesInvalidTitle() throws Exception {
        File temp = File.createTempFile("movies", ".txt");
        try (PrintWriter pw = new PrintWriter(temp)) {
            pw.println("she's The Man,STM001");
            pw.println("Romantic,Teen Fiction,Comedy");
        }

        mi.setFilePath(temp.getAbsolutePath());
        Vector<Movie> movies = mi.getMovies();

        assertEquals(0, movies.size());
    }

    @Test
    void testGetMoviesInvalidIdLetters() throws Exception {
        File temp = File.createTempFile("movies", ".txt");
        try (PrintWriter pw = new PrintWriter(temp)) {
            pw.println("Scarface,SF001");
            pw.println("Action,Crime");
        }

        mi.setFilePath(temp.getAbsolutePath());
        Vector<Movie> movies = mi.getMovies();

        assertEquals(0, movies.size());
    }

    @Test
    void testGetMoviesDuplicateIdNumbers() throws Exception {
        File temp = File.createTempFile("movies", ".txt");
        try (PrintWriter pw = new PrintWriter(temp)) {
            pw.println("The Pirates Of The Caribbeans,TPOTC999");
            pw.println("Adventure,Fantasy");

            pw.println("The Notebook,TN999");
            pw.println("Romantic,Drama");
        }

        mi.setFilePath(temp.getAbsolutePath());
        Vector<Movie> movies = mi.getMovies();

        assertEquals(1, movies.size());
    }

 
}
