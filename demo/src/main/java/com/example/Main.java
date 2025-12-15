package com.example;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.*;
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
        mi.setFilePath("demo\\src\\main\\java\\com\\example\\movies.txt");
        movies = mi.getMovies();

        userInput ui = new userInput();
        Vector<User> users;
        ui.setFilePath("demo\\src\\main\\java\\com\\example\\users.txt");
        users = ui.getUsers();
        
        for (User user : users) {
            Recommendation.recommendMovies(user, movies);
        }
    }
}