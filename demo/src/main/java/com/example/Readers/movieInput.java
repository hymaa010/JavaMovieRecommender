package com.example.Readers;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import com.example.Entities.Movie;
import com.example.Validators.movie_validator;

public class movieInput {
    private static Map<String, Vector<SimpleEntry<String, String>>> map = new HashMap<>();
    private String filePath = "";
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Vector <Movie> getMovies(){
        Vector<Movie> movies = new Vector<>();
        if(filePath == null){
            System.out.println("File path is not set!");
            return movies;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while (true) {
                String titleLine = br.readLine();
                if (titleLine == null) break;

                String genreLine = br.readLine();
                if (genreLine == null) break;

                Movie movie = new Movie();
                String [] parts = titleLine.split(",", 2);
                try {
                    if (!movie_validator.checkTitle(parts[0])) {
                        throw new Exception("ERROR: Movie Title {" + parts[0] + "} is wrong");
                    }
                    else if (!movie_validator.checkIdLetters(parts[0],parts[1])) {
                        throw new Exception("ERROR: Movie ID letters {" + parts[1] + "} are wrong");
                    }
                    else if (!movie_validator.checkIdNumber(parts[1])){
                        throw new Exception("ERROR: Movie ID numbers {" + parts[1] + "} aren't unique");
                    }

                    movie.setMovieTitle(parts[0].trim());
                    movie.setMovieId(parts[1].trim());
                    String [] genres = genreLine.split(",", 10);
                    movie.setMovieGenre(genres);
                    movies.add(movie);
                    for(String genre : genres){
                        map.putIfAbsent(genre, new Vector<SimpleEntry<String, String>>());
                        map.get(genre).add(new SimpleEntry<>(parts[0], parts[1]));
                    }
                    
                    
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return movies;
    }
    public static Map<String, Vector<SimpleEntry<String,String>>> getMap() {
        return map;
    }
    public static void resetMap() {
        map.clear();
    }
}
