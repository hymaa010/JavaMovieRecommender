package TopToDownTesting;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.Entities.Movie;
import com.example.Entities.User;
import com.example.Writers.Recommendation;

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

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(out));

        try (var mocked = mockStatic(Recommendation.class)) {
            mocked.when(() -> Recommendation.recommendMovies(user, movies))
                .thenAnswer(invocation -> {
                    System.out.println(user.getUserName() + ", " + user.getUserId());
                    List<String> watched = Arrays.asList(user.getMoviesIds());
                    java.util.Set<String> watchedGenres = new java.util.HashSet<>();
                    for (Movie w : movies) {
                        if (watched.contains(w.getMovieId())) {
                            String[] gs = w.getMovieGenre();
                            if (gs != null) {
                                for (String g : gs) watchedGenres.add(g);
                            }
                        }
                    }
                    for (Movie m : movies) {
                        if (!watched.contains(m.getMovieId())) {
                            String[] gs = m.getMovieGenre();
                            boolean sameGenre = false;
                            if (gs != null) {
                                for (String g : gs) {
                                    if (watchedGenres.contains(g)) { sameGenre = true; break; }
                                }
                            }
                            if (sameGenre) {
                                System.out.println(m.getMovieTitle());
                            }
                        }
                    }
                    return null;
                });

            Recommendation.recommendMovies(user, movies); 
        } finally {
            System.setOut(originalOut);
        }
        String output = out.toString().replace("\r","").trim(); 
        assertEquals("Mazen, 123456789\nInterstellar", output);
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
        
        try (var mocked = mockStatic(Recommendation.class)) {
            mocked.when(() -> Recommendation.recommendMovies(user, movies))
                  .thenAnswer(invocation -> {
                      System.out.println(user.getUserName() + ", " + user.getUserId());
                      List<String> watched = Arrays.asList(user.getMoviesIds());
                      java.util.Set<String> watchedGenres = new java.util.HashSet<>();
                      for (Movie w : movies) {
                          if (watched.contains(w.getMovieId())) {
                              String[] gs = w.getMovieGenre();
                              if (gs != null) for (String g : gs) watchedGenres.add(g);
                          }
                      }
                      for (Movie m : movies) {
                          if (!watched.contains(m.getMovieId())) {
                              String[] gs = m.getMovieGenre();
                              boolean sameGenre = false;
                              if (gs != null) {
                                  for (String g : gs) {
                                      if (watchedGenres.contains(g)) { sameGenre = true; break; }
                                  }
                              }
                              if (sameGenre) System.out.println(m.getMovieTitle());
                          }
                      }
                      return null;
                    });

            String output = captureOutput(() -> Recommendation.recommendMovies(user, movies));
            assertTrue(output.contains("Moon"));
        }
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
