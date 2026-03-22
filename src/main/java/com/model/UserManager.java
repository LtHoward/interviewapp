package com.model;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

    /**
     * Class to manage usr accounts, including creating new accounts, logging in and out, resseting passwords, and removing accounts.
     * It also has method to check if a username exist and if a password is valid.
     * @author Myila Howard
     */
    public class UserManager {

        private Role role;
        private static UserManager userManager;
        private ArrayList<User> users = new ArrayList<>();

        /**
         * Constructor for the UseManager class that initializes the user list by loading the user data from the data loader.
         */
        public UserManager() {
            users = DataLoader.getUsers();
        } 

        /**
         * Method to get the instance of the UserManager class
         * @return the instance of the UserManager class
         * @author myila Howard
         */
        public static UserManager getInstance() {
            if (userManager == null) {
                userManager = new UserManager();
            }
            return userManager;
        }

        /**
         * Method to get a user by their username.
         * @param username the username of the user to get
         * @return the user with the given username, or null if the username does not exist in the user list.
         * @author Myila Howard
         */
        public User getUser (String username) {
            if (username == null) return null;

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

        /**
         * Method to check if the password is valid
         * @param password the password to check
         * @return true if the password is at least 8 characters long and contains at least one letter, one number, and one special character, false otherwise.
         * @author Myila Howard
         */
        public boolean isPasswordValid(String password) {
            return "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$".equals(password);
        }

        /**
         * Method to check if the username exists in the user list.
         * @param username the username to check
         * @return true if the username exist, false otherwise. 
         * @author 
         */
        public boolean userExist(String username){
            if (username == null) return false;
            
            for (User user : users) {
                if (user.getUsername().equals(username)) {
                    return true;
                }
            }
            return false;
        }

        /**
         * Method to get all users in the userlist.
         * @return an arraylist of all users in the user list.
         * @author Myila Howard
         */
        public ArrayList<User> getAllUsers()
        {
            return users;
        }

        /**
         * Method to add a user to the user list if the username does not exist and the password is valid.
         * @param username the username of the user to be added
         * @param email the email of the user to be added
         * @param password the password of the user to be added 
         * @param firstName the first name of the user to be added 
         * @param lastName the last name of the user to be added
         * @param role the role of the user to be added 
         * @param major the major of the user to be added
         * @param year the year of the user to be added 
         * @return true if the user was successfuly added to the user list, false otherwise.
         * 
         */
        public boolean addUser(String username, String email, String password, String firstName, String lastName, Role role, Major major, Year year) 
        {

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

    /**
     * Method to save the user data to the data writter
     * @return true if the user data was successfuly saved, false otherwise.
     * @author Myila Howard
     */
        public boolean saveUser () {
            return true;
        }

        /**
         * Method to log in a user if the username or email and password match a user in the user list.
         * @param username the username of the user to log including
         * @param email the email of the user to log in
         * @param password the password of the user to log in
         * @returtn trur if the login was successful, false otherwise
         */
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

        /**
         * Method to remove another user's account if the current user is an administrator and 
         * the user whose account is to be removed exist in the user list.
         * @param user the user to be removed
         * @return true if the account was successfuly removed, false otherwise.
         * @author Myila Howard
         */
        public boolean removeOtherAccount(User user) {
            if (this.role != Role.ADMINISTRATOR) {
               return false;
            }
            if (users.contains(user)) {
                users.remove(user);
                return true;
            }
            return false;
        }
}