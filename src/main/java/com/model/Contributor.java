package com.model;

import java.util.UUID;

public class Contributor extends User {

    private String experience;

    public Contributor(UUID id, String username, String email, String password,
                       String firstName, String lastName, String experience) {
        super(id, username, email, password, firstName, lastName);
    }

    public Contributor(UUID id, String username, String email, String password,
                       String firstName, String lastName, String experience, Role role) {
        super(id, username, email, password, firstName, lastName);
    }

    public String getExperience() {
        return null;
    }

    public boolean isAdministrator() {
        return false;
    }
}