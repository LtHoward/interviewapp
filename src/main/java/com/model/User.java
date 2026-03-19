package com.model;
import java.util.UUID;

public abstract class User {
    private UUID userId;
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Role role;

    public User (UUID id, String username, String email, String password, String firstName, String lastName, Role role) {
        this.userId = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }


    public UUID getId () {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    /**
     * Method to get the email if it is valid, otherwise returns null.
     * A valid email contains an "@" and a "." and is not null or empty.
     * @return true if the email id valid, false otherwise.
     * @auhtor Myila Howard
     */
    public String getEmail() {
        return email;
    }

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
