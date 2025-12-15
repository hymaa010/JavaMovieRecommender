package com.example.Entities;
public class Movie {
    private String movieTitle;
    private String movieId;
    private String [] movieGenre;
    public Movie() {
        movieTitle = "";
        movieId = "";
        movieGenre = new String[10];
    }
    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }
    public String getMovieTitle() {
        return movieTitle;
    }
    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }
    public String getMovieId() {
        return movieId;
    }
    public void setMovieGenre(String [] movieGenre) {
        this.movieGenre = movieGenre;
    }
    public String [] getMovieGenre() {
        return movieGenre;
    }
}