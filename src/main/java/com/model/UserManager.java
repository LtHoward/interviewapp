package com.model;

import java.util.ArrayList;
import java.util.UUID;
import javafx.scene.chart.PieChart.Data;

public class UserManager {
    private static UserManager userManager;
    private ArrayList<User> users = new ArrayList<>();

    public UserManager() {} 

    public static UserManager getInstance() {
        if (userManager == null) {
            userManager = new UserManager();
        }
        return userManager;
    }

    public User createUser (String username, String email, String firstName, String lastName) {
        return null;
    }

    public boolean login (String username, String email, String password) {
        return true;
    }

    public boolean logout() {
        return true;
    }

    public boolean resetPassword (){
        return true;
    }

    public boolean resetAccount(User user) {
        return true;
    }

    public User getUser (String username, String password) {
        for(User user : users) {
            if(user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public ArrayList<User> getUser() {
        return users;
    }

    public boolean haveUser(String username){
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public boolean addUser(String username, String email, String password, String firstName, String lastName) {
        if(haveUser(username)) return false;

        users.add(new Contributor(UUID.randomUUID(), username, email, password, firstName, lastName, null));
        return true;
    }

    public boolean saveUser () {
        return true;
    }

    public boolean removeOtherAccount(Role administration) {
        return true;
    }
}