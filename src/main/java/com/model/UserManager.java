package com.model;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

    private static UserManager userManager;
    private ArrayList<User> users = new ArrayList<>();

<<<<<<< HEAD
    public UserManager() {} 
=======
    public UserManager() {
        users = DataLoader.getUsers();
    } 
>>>>>>> 1932ee516e694e60dc5b3e73567df77ad94b7b6b

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

<<<<<<< HEAD
    public User getUser (String username, String password) {
=======
    public User getUser (String username) {
        if (username == null) return null;

>>>>>>> 1932ee516e694e60dc5b3e73567df77ad94b7b6b
        for(User user : users) {
            if(user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
    public ArrayList<User> getUsers() 
    {
        return users;
    }

<<<<<<< HEAD
    public boolean haveUser(String username){
=======
    /**
     * Method to check if the username exists in the user list.
     * @param username the username to check
     * @return true if the username exist, false otherwise. 
     * @author 
     */
    public boolean userExist(String username){
        if (username == null) return false;
        
>>>>>>> 1932ee516e694e60dc5b3e73567df77ad94b7b6b
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

<<<<<<< HEAD
    public boolean addUser(String username, String email, String password, String firstName, String lastName) {
        if(haveUser(username)) return false;
=======
    public ArrayList<User> getAllUsers()
    {
        return users;
    }

    public boolean addUser(String username, String email, String password, String firstName, String lastName, Role role, Major major, Year year) 
    {
>>>>>>> 1932ee516e694e60dc5b3e73567df77ad94b7b6b

    if (username == null || email == null || password == null || firstName == null || lastName == null || role == null) return false;
    if (userExist(username)) return false;

    switch (role) 
    {
        case STUDENT:
            users.add(new Student(UUID.randomUUID(), username, email, password, firstName, lastName, major, year,
            "", "", SkillLevel.BEGINNER, 0, new ArrayList<>(),
            new Progression(), new ArrayList<>(), new Date(), Role.STUDENT));
            break;

        case ADMINISTRATOR:
            users.add(new Contributor(UUID.randomUUID(), username, email, password, firstName, lastName, null, Role.ADMINISTRATOR));
            break;

        default: // CONTRIBUTOR
            users.add(new Contributor(UUID.randomUUID(), username, email, password, firstName, lastName, null));
            break;
    }
    return true;
}

    public boolean saveUser () {
        return true;
    }

<<<<<<< HEAD
    public boolean removeOtherAccount(Role administration) {
=======
    public User createUser (String username, String email, String firstName, String lastName) {
        return null;
    }

    public boolean login(String username, String email, String password) {
        users = DataLoader.getUsers();

        for (User user : users) {
            if ((user.getUsername().equals(username) ||
                user.getEmail().equals(email)) &&
                user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public boolean logout() {
        DataWriter.saveUsers();
        users.clear();
        return true;
    }

    /**
     * Method to reset the password for a user if the username exists and the new password is different from the old password.
     * @param username the username of the user whose wants to reset their password
     * @param newPass the new password the user wants to set
     * @return true if the password was successfully reset, false otherwise.
     * @author Myila Howard
     */
    public boolean resetPassword (String username, String newPass) {
        if(userExist(username)) {
            return getUser(username).resetPassword(newPass);
        }
        return false;
    }

    /**
     * Method to remove a user account if the user exist in the user list.
     * @param user the user whose account is to be removed
     * @return true if the account was successfully removed, false otherwise.
     * @author Myila Howard
     */
    public boolean removeAccount(User user) {
        if (users.contains(user)) {
            users.remove(user);
        }
        return true;
    }

    public boolean removeOtherAccount(Role ADMINISTRATOR) {

>>>>>>> 1932ee516e694e60dc5b3e73567df77ad94b7b6b
        return true;
    }
}