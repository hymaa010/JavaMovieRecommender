package integrationTesting;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.Entities.Movie;
import com.example.Entities.User;
import com.example.Readers.movieInput;
import com.example.Readers.userInput;

public class ReaderEntityIntegrationTest {
    private movieInput mi;
    private userInput ui;

    @BeforeEach
    void setup() {
        mi = new movieInput();
        ui = new userInput();
        movieInput.resetMap();

    }
    private String createTestFile(String content) throws Exception {
        File temp = File.createTempFile("testFile", ".txt");
        temp.deleteOnExit();
        try (FileWriter writer = new FileWriter(temp)) {
            writer.write(content);
        }
        return temp.getAbsolutePath();
    }

    @Test
    void testGetMoviesValidInput() throws Exception {

        String content= "Forrest Gump,FG001\n"+
                        "Historical,Drama\n"+
                        "The Karate Kid,TKK002\n"+
                        "Drama,Action\n"+
                        "The Mask,TM005\n"+
                        "Comedy,Musical\n";

        mi.setFilePath(createTestFile(content));
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

        String content =    "she's The Man,STM001\n"+
                            "Romantic,Teen Fiction,Comedy\n";

        mi.setFilePath(createTestFile(content));
        Vector<Movie> movies = mi.getMovies();

        assertEquals(0, movies.size());
    }

    @Test
    void testGetMoviesInvalidIdLetters() throws Exception {

        String content =    "Scarface,SF001\n"+
                            "Action,Crime\n";

        mi.setFilePath(createTestFile(content));
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
        String content =    "The Pirates Of The Caribbeans,TPOTC999\n"+
                            "Adventure,Fantasy\n" +
                            "The Notebook,TN999\n" +
                            "Romantic,Drama\n";

        mi.setFilePath(createTestFile(content));
        Vector<Movie> movies = mi.getMovies();

        assertEquals(1, movies.size());
    }

    @Test
    void testGetUsers_validFile() throws Exception {
        String content =
                "Menna Mohamed,12345678A\n" +
                "M001,M002\n" +
                "Mazen Mohamed,98765432B\n" +
                "M010,M020,M030\n";

        ui.setFilePath(createTestFile(content));
        Vector<User> users = ui.getUsers();

        assertEquals(2, users.size());
    }

    @Test
    void testGetUsers_invalidName() throws Exception {
        String content =
                "$Mazen Mohamed,12345678B\n" +
                "M001\n";

        ui.setFilePath(createTestFile(content));
        assertTrue(ui.getUsers().isEmpty());
    }

    @Test
    void testGetUsers_invalidId() throws Exception {
        String content =
                "Mohamed Allam,1234AB\n" +
                "M001\n";

        ui.setFilePath(createTestFile(content));
        assertTrue(ui.getUsers().isEmpty());
    }

    @Test
    void testGetUsers_duplicateIds() throws Exception {
        String content =
                "Shahd Bassem,12345678F\n" +
                "M001,M002\n" +
                "Rana Essam,12345678F\n" +
                "M005\n";

        ui.setFilePath(createTestFile(content));
        Vector<User> users = ui.getUsers();

        assertEquals(1, users.size());
        assertEquals("Shahd Bassem", users.get(0).getUserName());
    }

    @Test
    void testGetUsers_filePathNotSet() {
        ui.setFilePath(null);
        assertTrue(ui.getUsers().isEmpty());
    }

    @Test
    void testGetUsers_emptyFile() throws Exception {
        ui.setFilePath(createTestFile(""));
        assertTrue(ui.getUsers().isEmpty());
    }

 
}