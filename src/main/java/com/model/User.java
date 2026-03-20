package com.model;
import java.util.UUID;

/**
 * Abstract class to repesent a user of the application, including student, contributor, and administrator users. 
 * It has method to get the user'd information like id, email, first and last name, password, and role. It also contain the method to reset the user's password.
 * @author Myila Howard
 */
public abstract class User {
    private final UUID userId;
    private final String username;
    private final String email;
    private String password;
    private final String firstName;
    private final String lastName;
    private final Role role;

    /**
     * Constructor for the User class that initicalizes the user with the given parameters.
     * @param id the id of the user to be created 
     * @param username the username of the user to be created
     * @param email the email of the user to be created 
     * @param password the password of the username to be created 
     * @param firstName the first name of the username to be created 
     * @param lastName the last name of the username to be created 
     * @param role the role of the username to be created
     */
    public User (UUID id, String username, String email, String password, String firstName, String lastName, Role role) {
        this.userId = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }


    /**
     * Method to the the id of the user if valid
     * @return the id of the user
     * @author Myila Howard
     */
    public UUID getId () {
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
     * Method to get the email of the user if vaild
     * @return email
     * @author myila Howard
     */
    public String getEmail() {
        return email;
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
     * @return true if the first name is not null or empty, false otherwise
     * @author Myila Howard
     */
    public String getFirstName() {
        if (firstName == null || firstName.isEmpty()) {
            return null;
        }
        return firstName;
    }

    /**
     * Method to get the last name of the user if valid.
     * @return true if the last name is not null or empty, false otherwise.
     * @author Myila Howard
     */
    public String getLastName() {
        if (lastName == null || lastName.isEmpty()) {
            return null;
        }
        return lastName;
    }

    public Role getStatus () {
        return role;
    }

    /**
     * Mehthod to reset the user's password if the new password is the same as the old password,
     * otherwise return false and do not change the password.
     * @param newPass the users new password
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
