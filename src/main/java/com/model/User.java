package com.model;
import java.util.UUID;

public abstract class User {
    private UUID id;
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Role role;

    public User (UUID id, String username, String email, String password, String firstName, String lastName) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public boolean userExist (String username, String password) {
        return true;
    }

    public UUID getId () {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        if ( password == null || password.length() < 12) {
            return null;
        }
        return password;
    }

    public String getFirstName() {
        if (firstName == null || firstName.isEmpty()) {
            return null;
        }
        return firstName;
    }

    public String getLastName() {
        if (lastName == null || lastName.isEmpty()) {
            return null;
        }
        return lastName;
    }

    public Role getStatus () {
        return role;
    }

    public boolean resetPassword (String password ) {
        return true;
    }
}
