package com.example;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.AbstractMap.SimpleEntry;
public class movieInput {
    private static Map<String, Vector<SimpleEntry<String, String>>> map = new HashMap<>();
    private String filePath = "";
    private static boolean [] moviesFound = new boolean[1000];
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public boolean checkTitle(String title) {
        if(title.charAt(0) < 'A' || title.charAt(0) > 'Z' ) {
            return false;
        }
        for(int i = 1; i < title.length(); i++) {
            if( (title.charAt(i) < 'A' || title.charAt(i) > 'Z') && (title.charAt(i-1) == ' ')){
                return false;
            }
        }
        return true;
    }
    
    public boolean checkIdLetters(String title, String id) {
        int idx = 0;
        for (int i = 0; i < title.length(); i++) {
            if (title.charAt(i) >= 'A' && title.charAt(i) <= 'Z') {
                if (id.charAt(idx) == title.charAt(i)) {
                    idx++;
                } else return false;
            }
        }
        return true;
    }
    public boolean checkIdNumber(String id) {
        if(moviesFound [Integer.valueOf(id.substring(id.length()-3, id.length()))])
            return false;
        else moviesFound [Integer.valueOf(id.substring(id.length()-3, id.length()))] = true;

        return true;
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
                    if (!checkTitle(parts[0])) {
                        throw new Exception("ERROR: Movie Title {" + parts[0] + "} is wrong");
                    }
                    else if (!checkIdLetters(parts[0],parts[1])) {
                        throw new Exception("ERROR: Movie ID letters {" + parts[1] + "} are wrong");
                    }
                    else if (!checkIdNumber(parts[1])){
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
}
