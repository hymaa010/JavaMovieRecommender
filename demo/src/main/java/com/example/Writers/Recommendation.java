package com.example.Writers;
import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import com.example.Entities.Movie;
import com.example.Entities.User;
import com.example.Readers.movieInput;
public class Recommendation {
    public static void recommendMovies( User user, Vector<Movie> movies) {
        System.out.println(user.getUserName()+", " + user.getUserId());
        Map <String,Vector<SimpleEntry<String, String>>> map2 = movieInput.getMap();
        boolean first = true;
        Set<String> recommendations = new HashSet<>();

        Set<String> likedIds = new HashSet<>(Arrays.asList(user.getMoviesIds()));
        Set<String> recommendedTitles = new LinkedHashSet<>(); 

        // Build lookup map once
        Map<String, Movie> movieById = new HashMap<>();
        for (Movie m : movies) {
            movieById.put(m.getMovieId(), m);
        }

        for (String likedMovieId : likedIds) {
            Movie likedMovie = movieById.get(likedMovieId);
            if (likedMovie == null) continue;

            for (String genre : likedMovie.getMovieGenre()) {
                Vector<SimpleEntry<String, String>> recs = map2.get(genre);
                if (recs == null) continue;

                for (SimpleEntry<String, String> rec : recs) {
                    if (!likedIds.contains(rec.getValue())) {
                        recommendedTitles.add(rec.getKey());
                    }
                }
            }
        }


recommendations.addAll(recommendedTitles);


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
