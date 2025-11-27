package com.example;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.*;
import java.util.AbstractMap.SimpleEntry;
public class Main {
    public static void main(String[] args) {
        try {
            PrintStream fileOut = new PrintStream(new FileOutputStream("recommendations.txt"));
            System.setOut(fileOut);
        } catch (Exception e) {
            System.out.println("Error redirecting output to file: " + e.getMessage());
        }

        movieInput mi = new movieInput();
        Vector<Movie> movies;
        mi.setFilePath("E:/apps/VS Code/sw testing project/demo/src/main/java/com/example/movies.txt");
        movies = mi.getMovies();

        userInput ui = new userInput();
        Vector<User> users;
        ui.setFilePath("E:/apps/VS Code/sw testing project/demo/src/main/java/com/example/users.txt");
        users = ui.getUsers();
        
        Map<String, Vector<SimpleEntry<String,String>>> movieMap = movieInput.getMap();
        for (User user : users) {
            Recommendation.recommendMovies(user, movies);
        }
    }
}
