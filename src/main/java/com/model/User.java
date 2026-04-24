package com.model;
import java.util.UUID;

/**
 * Abstract class to represent a user of the application, including student, contributor, and administrator users. 
 * It has methods to get the user's information like id, email, first and last name, password, and role. It also contains the method to reset the user's password.
 * @author Myila Howard
 */
public abstract class User {
    private final UUID userId;
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private final Role role;

    /**
     * Constructor for the User class that initializes the user with the given parameters.
     * @param id the id of the user to be created 
     * @param username the username of the user to be created
     * @param email the email of the user to be created 
     * @param password the password of the username to be created 
     * @param firstName the first name of the username to be created 
     * @param lastName the last name of the username to be created 
     * @param role the role of the username to be created
     */
    public User(UUID id, String username, String email, String password, String firstName, String lastName, Role role) {
        this.userId = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    /**
     * Method to get the id of the user if valid
     * @return the id of the user
     * @author Myila Howard
     */
    public UUID getId() {
        return userId;
    }

    /**
     * Method to get the username of the user if valid
     * @return the username of the user
     * @author Myila Howard
     */
    public String getUsername() {
        return username;
    }

    /**
     * Method to set the username of the user
     * @param username the new username of the user
     * @author Jarris Lambert
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Method to get the email of the user if valid
     * @return email
     * @author Myila Howard
     */
    public String getEmail() {
        return email;
    }

    /**
     * Method to set the email of the user
     * @param email the new email of the user
     * @author Jarris Lambert
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Method to get the password of the user if valid.
     * @return the password of the user
     * @author Myila Howard
     */
    public String getPassword() { 
        return password;
    }

    /**
     * Method to get the first name of the user if valid.
     * @return the first name, or null if not set
     * @author Myila Howard
     */
    public String getFirstName() {
        if (firstName == null || firstName.isEmpty()) {
            return null;
        }
        return firstName;
    }

    /**
     * Method to set the first name of the user
     * @param firstName the new first name of the user
     * @author Jarris Lambert
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Method to get the last name of the user if valid.
     * @return the last name, or null if not set
     * @author Myila Howard
     */
    public String getLastName() {
        if (lastName == null || lastName.isEmpty()) {
            return null;
        }
        return lastName;
    }

    /**
     * Method to set the last name of the user
     * @param lastName the new last name of the user
     * @author Jarris Lambert
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Method to get the role/status of the user
     * @return the role of the user
     * @author Myila Howard
     */
    public Role getStatus() {
        return role;
    }

    /**
     * Method to reset the user's password if the new password is different from the old password,
     * otherwise return false and do not change the password.
     * @param newPass the user's new password
     * @return true if the password was successfully reset, false otherwise.
     * @author Myila Howard
     */
    public boolean resetPassword(String newPass) {
        if (!password.equals(newPass)) {
            password = newPass;
            return true;
        }
        return false;
    }
}