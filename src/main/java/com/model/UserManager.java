package com.model;
import java.util.ArrayList;
import java.util.UUID;

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
        
        User createdUser = new User(username, email, firstName, lastName);
        return createdUser;
    }

    public boolean login (String username, String email, String password) {
        User user = getUser(username,password);
        if(user != null)
        return  true;
    else 
        return false;
    
    }

    public boolean logout(User user)
    {
       if(users.contains(user))
        return true;
    else 
        return false;
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