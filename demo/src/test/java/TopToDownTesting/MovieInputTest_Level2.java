package TopToDownTesting;

import com.example.Entities.Movie;
import com.example.Validators.movie_validator;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.*;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

import com.example.Readers.movieInput;

class MovieInputTest_Level2 {

    private movieInput mi;

    @BeforeEach
    void setUp() {
        mi = new movieInput();
        movieInput.resetMap(); 
    }

    @Test
    void testGetMovies_FilePathNotSet() {
        mi.setFilePath(null);
        Vector<Movie> movies = mi.getMovies();
        assertTrue(movies.isEmpty());
    }

    @Test
    void testGetMovies_ValidFile() throws IOException {
        File tempFile = File.createTempFile("testMovies", ".txt");
        tempFile.deleteOnExit();

        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
        writer.write("Interstellar,I123\n");
        writer.write("Sci-Fi,Action\n");
        writer.write("Moon,M123\n");
        writer.write("Sci-Fi,Thriller\n");
        writer.close();

        mi.setFilePath(tempFile.getAbsolutePath());

        MockedStatic<movie_validator> mv = Mockito.mockStatic(movie_validator.class);
        mv.when(() -> movie_validator.checkTitle(anyString())).thenReturn(true);
        mv.when(() -> movie_validator.checkIdLetters(anyString(), anyString())).thenReturn(true);
        mv.when(() -> movie_validator.checkIdNumber(anyString())).thenReturn(true);

        Vector<Movie> movies = mi.getMovies();

        assertEquals(2, movies.size());
        assertEquals("Interstellar", movies.get(0).getMovieTitle());
        assertEquals("I123", movies.get(0).getMovieId());
        assertArrayEquals(new String[]{"Sci-Fi","Action"}, movies.get(0).getMovieGenre());

        assertEquals("Moon", movies.get(1).getMovieTitle());
        assertEquals("M123", movies.get(1).getMovieId());
        assertArrayEquals(new String[]{"Sci-Fi","Thriller"}, movies.get(1).getMovieGenre());

        mv.close();
    }

    @Test
    void testGetMovies_InvalidTitleOrId() throws IOException {
        File tempFile = File.createTempFile("testMoviesInvalid", ".txt");
        tempFile.deleteOnExit();

        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
        writer.write("BadBoys,B123\n");
        writer.write("Comedy,Drama\n");
        writer.close();

        mi.setFilePath(tempFile.getAbsolutePath());

        MockedStatic<movie_validator> mv = Mockito.mockStatic(movie_validator.class);
        mv.when(() -> movie_validator.checkTitle(anyString())).thenReturn(false);
        mv.when(() -> movie_validator.checkIdLetters(anyString(), anyString())).thenReturn(true);
        mv.when(() -> movie_validator.checkIdNumber(anyString())).thenReturn(true);

        Vector<Movie> movies = mi.getMovies();

        assertTrue(movies.isEmpty());

        mv.close();
    }

    @Test
    void testMapPopulation() throws IOException {
        File tempFile = File.createTempFile("testMap", ".txt");
        tempFile.deleteOnExit();

        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
        writer.write("Wonder,W123\n");
        writer.write("Drama,Family\n");
        writer.close();

        mi.setFilePath(tempFile.getAbsolutePath());

        MockedStatic<movie_validator> mv = Mockito.mockStatic(movie_validator.class);
        mv.when(() -> movie_validator.checkTitle(anyString())).thenReturn(true);
        mv.when(() -> movie_validator.checkIdLetters(anyString(), anyString())).thenReturn(true);
        mv.when(() -> movie_validator.checkIdNumber(anyString())).thenReturn(true);

        mi.getMovies();

        assertTrue(movieInput.getMap().containsKey("Drama"));
        assertTrue(movieInput.getMap().containsKey("Family"));
        var entries = movieInput.getMap().get("Drama");
        assertEquals("Wonder", entries.get(0).getKey());
        assertEquals("W123", entries.get(0).getValue());

        mv.close();
    }
}
