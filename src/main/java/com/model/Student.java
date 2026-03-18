package com.model;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

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
        Progression progression, ArrayList<Reward> rewards, Date lastActivityDate, Role role) {
        super(userId, username, email, password, firstName, lastName, role);
        this.currentClasses = currentClasses;
        this.classesTaken = classesTaken;
        this.major = major;
        this.year = year;
        this.skillLevel = skillLevel;
        this.solvedQuestions = solvedQuestions;
        this.postedSolutions = postedSolutions;
        this.progression = progression;
        this.rewards = rewards;
        this.lastActivityDate = lastActivityDate;
        
    }

    public void recordSolvedQuestions(){

    }

    public int addPoints() {
        return 0;
    }

    public ArrayList<SolutionPost> getPostedSolutions() {
        return postedSolutions;
    }

    public String getCurrentClasses() {
        return currentClasses;
    }

    public String getClassesTaken() {
        return classesTaken;
    }

    public Major getMajor() {
        return major;
    }

    public Year getYear() {
        return year;
    }

    public SkillLevel getSkillLevel() {
        return skillLevel;
    }
    public int getSolvedQuestions() {
        return solvedQuestions;
    }

    public Date getLastActivityDate() {
        return lastActivityDate;
    }

    public ArrayList<Reward> getRewards() {
        return rewards;
    }

    public Progression getProgression() {
        return progression;
    }

    public void updateLevel() {

    }

    public int getCurrentStreak() {
        return progression.getCurrentStreak();
    }
    
}
