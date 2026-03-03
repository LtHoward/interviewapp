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
        return null;
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
        return null;
    }

    public boolean saveUser () {
        return true;
    }

    public boolean removeOtherAccount(Role administration) {
        return true;
    }
}