package com.example.Readers;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import com.example.Entities.User;
import com.example.Validators.user_validator;
public class userInput {
    private String filePath = "";
    public void setFilePath(String filePath) {
        this.filePath = filePath;
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
                    if (!user_validator.checkName(parts[0])) {
                        throw new Exception("ERROR: User Name {" + parts[0] + "} is wrong");
                    }
                    else if (!user_validator.checkId(parts[1])) {
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
