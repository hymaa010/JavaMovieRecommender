package com.example;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
public class userInput {
    private String filePath = "";
    private static Set<String> userIdsFound = new HashSet<String>();
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public boolean checkName(String name) {
        if(name.charAt(0) ==' '){
            return false;
        }
        for(int i = 0; i < name.length(); i++) {
            if(!((name.charAt(i) >= 'A' && name.charAt(i) <= 'Z') || (name.charAt(i) >= 'a' && name.charAt(i) <= 'z') || name.charAt(i) == ' ')){
                return false;
            }
        }
        return true;
    }

    public boolean checkId(String id) {
        if(id.length() != 9){
            return false;
        }
        for(int i = 0; i < id.length(); i++) {
            if(i!=8 && !(id.charAt(i) >= '0' && id.charAt(i) <= '9')){
                return false;
            }
            if (i==8 && !(((id.charAt(i) >= 'A' && id.charAt(i) <= 'Z') || (id.charAt(i) >= 'a' && id.charAt(i) <= 'z') || (id.charAt(i) >= '0' && id.charAt(i) <= '9')))) {
                return false;
            }
        }
        if(userIdsFound.contains(id)){
            return false;
        }
        else {
            userIdsFound.add(id);
            return true;
        }
    }

    public Vector <User> getUsers(){
        Vector<User> users = new Vector<>();
        if(filePath == null){
            System.out.println("File path is not set!");
            return users;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while (true) {
                String UserLine = br.readLine();
                if (UserLine == null) break;

                String likedMoviesLine = br.readLine();
                if (likedMoviesLine == null) break;

                User user = new User();
                String [] parts = UserLine.split(",", 2);
                try {
                    if (!checkName(parts[0])) {
                        throw new Exception("ERROR: User Name {" + parts[0] + "} is wrong");
                    }
                    else if (!checkId(parts[1])) {
                        throw new Exception("ERROR: User Id {" + parts[1] + "} is wrong");
                    }

                    user.setUserName(parts[0].trim());
                    user.setUserId(parts[1].trim());
                    String [] likedMovies = likedMoviesLine.split(",", 10);
                    user.setMoviesIds(likedMovies);
                    users.add(user);
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return users;
    }
}
