package com.example.Validators;

public class movie_validator {
    private static boolean [] moviesFound = new boolean[1000];

     public static boolean checkTitle(String title) {
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
    
    public static boolean checkIdLetters(String title, String id) {
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
    public static boolean checkIdNumber(String id) {
        if(moviesFound [Integer.valueOf(id.substring(id.length()-3, id.length()))])
            return false;
        else moviesFound [Integer.valueOf(id.substring(id.length()-3, id.length()))] = true;

        return true;
    }

    public static void reset() {
        moviesFound = new boolean[1000];
    }
}
