package com.model;

import java.util.UUID;

public class Contributor extends User {

    private String experience;

    public Contributor(UUID userId, String username, String email, String password,
                       String firstName, String lastName, String experience) {
        super(userId, username, email, password, firstName, lastName);
        this.experience = experience;
    }

    public Contributor(UUID userId, String username, String email, String password,
                       String firstName, String lastName, String experience, Role role) {
        super(userId, username, email, password, firstName, lastName);
        this.experience = experience;
    }

    public String getExperience() {
        return experience;
    }

    public boolean isAdministrator() {
        return false;
    }
}