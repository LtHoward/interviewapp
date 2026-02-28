package com.model;
import java.util.ArrayList;

public class UserManager {
    private UserManager userManager;
    private ArrayList<User> users = new ArrayList<>();

    public UserManager() {} 

    public static UserManager getInstance() {
        return UserManager.getInstance();
    }

    public User createUser (String username, String email, String firstName, String lastName) {
        r
    }

    public boolean login (String username, String email, String password) {
        return true;
    }

    public boolean resetPassword (){
        return true;
    }

    public boolean resetAccount(User user) {
        return true;
    }

    public User getUser (String username, String password) {
        
    }

    public boolean saveUser () {
        return true;
    }

    public boolean removeOtherAccount(Administration administration) {
        return true;
    }
}
