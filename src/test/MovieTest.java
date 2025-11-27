/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.unittestmovie;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author ebrah
 */
public class MovieTest {

    @Test
    public void testDefaultConstructor() {
        Movie movie = new Movie();

        // Check default title and ID
        assertEquals("", movie.getMovieTitle(), "Default movie title should be empty string");
        assertEquals("", movie.getMovieId(), "Default movie ID should be empty string");

        // Check default genre array
        assertNotNull(movie.getMovieGenre(), "movieGenre array should not be null");
        assertEquals(10, movie.getMovieGenre().length, "movieGenre array length should be 10");
    }

    @Test
    public void testSetAndGetMovieTitle() {
        Movie movie = new Movie();
        movie.setMovieTitle("Inception");

        assertEquals("Inception", movie.getMovieTitle(), "getMovieTitle should return the value set");
    }

    @Test
    public void testSetAndGetMovieId() {
        Movie movie = new Movie();
        movie.setMovieId("M123");

        assertEquals("M123", movie.getMovieId(), "getMovieId should return the value set");
    }

    @Test
    public void testSetAndGetMovieGenre() {
        Movie movie = new Movie();
        String[] genres = {"Action", "Sci-Fi"};

        movie.setMovieGenre(genres);

        String[] result = movie.getMovieGenre();
        assertNotNull(result, "movieGenre should not be null after setting");
        assertEquals(2, result.length, "movieGenre length should match the array set");
        assertEquals("Action", result[0], "First genre should match");
        assertEquals("Sci-Fi", result[1], "Second genre should match");
    }
}
