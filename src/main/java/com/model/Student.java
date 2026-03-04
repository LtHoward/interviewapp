package com.model;
import java.util.ArrayList;
import java.util.UUID;
import java.util.Date;

public class Student extends User{
    private String currentClasses;
    private String classesTaken;
    private Major major;
    private Year year;
    private SkillLevel skillLevel;
    private int solvedQuestions;
    private ArrayList<SolutionPost> postedSolutions;
    private Progression progression;
    private ArrayList<Reward> rewards;
    private Date lastActivityDate;

    public Student (UUID userId, String username, String email, String password, String firstName, 
        String lastName, Major major, Year year, String currentClasses, String classesTaken, 
        SkillLevel skillLevel, int solvedQuestions, ArrayList<SolutionPost> postedSolutions, 
        Progression progression, ArrayList<Reward> rewards, Date lastActivityDate) {
        super(userId, username, email, password, firstName, lastName);
        
    }

    public void recordSolvedQuestions(){

    }

    public int addPoints() {
        return 0;
    }

    public ArrayList<SolutionPost> getPostedSolutions() {
        return null;
    }

    public String getCurrentClasses() {
        return null;
    }

    public String getClassesTaken() {
        return null;
    }

    public Major getMajor() {
        return null;
    }

    public Year getYear() {
        return null;
    }

    public SkillLevel getSkillLevel() {
        return null;
    }
    public int getSolvedQuestions() {
        return 0;
    }

    public Date getLastActivityDate() {
        return null;
    }

    public ArrayList<Reward> getRewards() {
        return null;
    }

    public String getType() {
        return null;
    }

    public int getAmount() {
        return 0;
    }

    public boolean getRedeemed() {
        return false;
    }

    public void updateLevel() {

    }

    public Date getCurrentStreak() {
        return null;
    }
    
}
