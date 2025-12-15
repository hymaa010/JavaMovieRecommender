package com.example;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Vector;

import com.example.Entities.Movie;
import com.example.Entities.User;
import com.example.Readers.movieInput;
import com.example.Readers.userInput;
import com.example.Writers.Recommendation;
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
        mi.setFilePath("demo\\src\\main\\java\\com\\example\\Resources\\movies.txt");
        movies = mi.getMovies();

        userInput ui = new userInput();
        Vector<User> users;
        ui.setFilePath("demo\\src\\main\\java\\com\\example\\Resources\\users.txt");
        users = ui.getUsers();
        
        for (User user : users) {
            Recommendation.recommendMovies(user, movies);
        }
    }
}