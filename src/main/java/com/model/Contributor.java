package com.model;

import java.util.UUID;

/**
 * Class to represent a Contributor and extend the User class. It manages the experiance level, and the Administrator
 */
public class Contributor extends User {

    private String experience;

    /**
     * Constructor for the Contributor class that has the given parameters
     * @param userId the user's id
     * @param username the user's username
     * @param email the users email 
     * @param password the users password
     * @param firstName the users first name
     * @param lastName the user last name
     * @param experience the expreience of the user
     */
    public Contributor(UUID userId, String username, String email, String password,
                       String firstName, String lastName, String experience) {
        super(userId, username, email, password, firstName, lastName, Role.CONTRIBUTOR);
        this.experience = experience;
    }

    /**
     * second constructor of the Contributor class
     * @param userId the users id 
     * @param username the users username
     * @param email the users email
     * @param password the user password 
     * @param firstName the users first name
     * @param lastName the user last name
     * @param experience the users experiance
     * @param role the role of the user 
     */ 
    public Contributor(UUID userId, String username, String email, String password,
                       String firstName, String lastName, String experience, Role role) {
        super(userId, username, email, password, firstName, lastName, role);
        this.experience = experience;
    }

    /**
     * Method to get the experiance of the user 
     * @return experiance
     */
    public String getExperience() {
        return experience;
    }

    /**
     * Method to check if the user is an administrator
     * @return Administrator of the status equals Administrator, false otherwise(constributor)
     */
    public boolean isAdministrator() {
        return getStatus() == Role.ADMINISTRATOR;
    }
}