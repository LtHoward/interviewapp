package com.model;
import java.util.ArrayList;
import java.util.UUID;

public class UserManager {
    private static UserManager userManager;
    private ArrayList<User> users = new ArrayList<>();

    public UserManager() {
        users = new ArrayList<>();
    } 

    public static UserManager getInstance() {
        if (userManager == null) {
            userManager = new UserManager();
        }
        return userManager;
    }

    public User getUserById(UUID userId){
        if (userId == null) return null;

        for (User user : users) {
            if (user.getId().equals(userId)) {
                return user;
            }
        }
        return null;
    }

    public User getUser (String username, String password) {
        if (username == null || password == null) return null;

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
        if (username == null) return false;
        
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<User> getAllUsers()
    {
        return null;
    }
    
    public boolean addUser(String username, String email, String password, String firstName, String lastName) {
        if(haveUser(username)) return false;

        users.add(new Contributor(UUID.randomUUID(), username, email, password, firstName, lastName, null));
        return true;
    }

    public boolean removeUser(User user) {
        if (user == null) return false;
        return users.remove(user);
    }

    public boolean saveUser () {
        return true;
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

    public boolean resetPassword (String password ) {
        return true;
    }

    public boolean resetAccount(User user) {
        return true;
    }

    public boolean removeOtherAccount(Role ADMINISTRATOR) {
        return true;
    }
}