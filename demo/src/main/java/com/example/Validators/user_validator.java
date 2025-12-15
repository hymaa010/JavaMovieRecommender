package com.example.Validators;

import java.util.HashSet;
import java.util.Set;

public class user_validator {

    private static Set<String> userIdsFound = new HashSet<String>();
    public static boolean checkName(String name) {
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

    public static boolean checkId(String id) {
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
    
    public static void reset() {
        userIdsFound.clear();
    }

    
}
