package com.example;
import java.util.*;
import java.util.AbstractMap.SimpleEntry;
public class Recommendation {
    public static void recommendMovies( User user, Vector<Movie> movies) {
        System.out.println(user.getUserName()+", " + user.getUserId());
        Map <String,Vector<SimpleEntry<String, String>>> map2 = movieInput.getMap();
        boolean first = true;
        Set<String> recommendations = new HashSet<>();

        for (String likedMovieId : user.getMoviesIds()) {
            for (Movie movie : movies) {
                if (movie.getMovieId().equals(likedMovieId)) {
                    for (String genre : movie.getMovieGenre()) {
                        Vector<SimpleEntry<String, String>> recommendedMovies = map2.get(genre);
                        if (recommendedMovies != null) {
                            for (SimpleEntry<String, String> recommendedMovieId : recommendedMovies) {
                                if (!Arrays.asList(user.getMoviesIds()).contains(recommendedMovieId.getValue())) {
                                    recommendations.add(recommendedMovieId.getKey());
                                }
                            }
                        }
                    }
                }
            }
        }

        for (String recMovieId : recommendations) {
            if (first) {
                System.out.print(recMovieId);
                first = false;
            } else {
                System.out.print(", " + recMovieId);
            }
        }
        System.out.println();
    }
}
