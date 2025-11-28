package com.example;
public class User {
    private String userName;
    private String UserId;
    private String [] moviesIds;
    public User() {
        userName = "";
        UserId = "";
        moviesIds = new String[20];
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserId() {
        return UserId;
    }
    public void setUserId(String userId) {
        UserId = userId;
    }
    public String[] getMoviesIds() {
        return moviesIds;
    }
    public void setMoviesIds(String[] moviesIds) {
        this.moviesIds = moviesIds;
    }
}


