package com.model;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Class to represent a student user of the application, which extends the User class. 
 * It has methods to get the student's information like current classes, classes taken, major, year, skill level,
 * solved questions, posted solutions, progression, rewards, and last activity date.
 * It also has method to record solved questions, add points, update level, and get current streak.
 * @author Myila Howard
 */
public class Student extends User{
    private String currentClasses;
    private String classesTaken;
    private Major major;
    private Year year;
    private SkillLevel skillLevel;
    private int level;
    private int solvedQuestions;
    private Progression progression;
    private ArrayList<Reward> rewards;
    private LocalDate lastActivityDate;

    /**
     * Constructor for the Student class that initializes the student with the given parameters.
     * @param userId the id of the student to be created
     * @param username the username of the student to be created 
     * @param email the email of the student to be created 
     * @param password the password of the student to be created 
     * @param firstName the first name of the student to be created 
     * @param lastName the last name of the student to be created 
     * @param major the major of the student to be created
     * @param year the year of the student to be created 
     * @param currentClasses the current classes of the student to be created 
     * @param classesTaken the current classes taken of the student to be created 
     * @param skillLevel the skill level of the student to be created 
     * @param solvedQuestions the number of solved questions of the student to be created 
     * @param progression the progression of the student to be created 
     * @param rewards the list of rewards of the student to be created 
     * @param lastActivityDate the last activity date of the student to be created 
     * @param role the role of the student to be created 
     * @author Myila Howard 
     */
    public Student (UUID userId, String username, String email, String password, String firstName, 
        String lastName, Major major, Year year, String currentClasses, String classesTaken, 
        SkillLevel skillLevel, int solvedQuestions, 
        Progression progression, ArrayList<Reward> rewards, LocalDate lastActivityDate, Role role) {
        super(userId, username, email, password, firstName, lastName, role);
        this.currentClasses = currentClasses;
        this.classesTaken = classesTaken;
        this.major = major;
        this.year = year;
        this.skillLevel = skillLevel;
        this.solvedQuestions = solvedQuestions;
        this.progression = progression;
        this.rewards = rewards;
        this.lastActivityDate = lastActivityDate;
        
    }

    /**
     * Method to record a solved question by incrementing the number of solved question by 1.
     */
    public void recordSolvedQuestions(){    
         solvedQuestions++;                                                         
    }

    /**
     * Method to add points to the student by incrementing the level by 1 and updating the skill level based on the new level.
     * @return the new level of the student after adding the pointes.
     */
    public int addPoints() {
        return progression.getPoints();
    }

    /**
     * Method to get the current classes of the student.
     * @return the current classes of the student.
     */
    public String getCurrentClasses() {
        return currentClasses;
    }

    /**
     * Method to get the classes taken of the student
     * @return the classes taken of the student
     */
    public String getClassesTaken() {
        return classesTaken;
    }

    /**
     * Method to get the major of the student
     * @return the major of the student 
     */
    public Major getMajor() {
        return major;
    }

    /**
     * Method to get the year of the student 
     * @return the year of the student 
     */
    public Year getYear() {
        return year;
    }

    /**
     * Method to get the level of the student 
     * @return the level of the student 
     */
    public int getLevel() {
        return level;
    }

    /**
     * Method to get the skill level of the student based on the current level of the student
     * @return the skill level of the student
     */
    public SkillLevel getSkillLevel() {
        return skillLevel;
    }

    /**
     * Method to get the number of solved questions of the student 
     * @return the number of solved questions of the student 
     */
    public int getSolvedQuestions() {
        return solvedQuestions;
    }

    /**
     * Method to get the last activity date of the student 
     * @return the last activity date of the student 
     */
    public LocalDate getLastActivityDate() {
        return lastActivityDate;
    }

    /**
     * Method to get the list of rewards of the student
     * @return the list of rewards the student has gained
     */
    public ArrayList<Reward> getRewards() {
        if (rewards == null) {
            rewards = new ArrayList<>();
        }
        return rewards;
    }

    /**
     * Method to get the progression of the student
     * @return the progression of the student
     */
    public Progression getProgression() {
        return progression;
    }

    /**
     * Method to update the level of the student by checking if the student has enough points to level up and updating that student's skill level based on the new level.
     */
    public void updatelevel() 
    {    
        progression.checkLevelUp();                                       
    }

    /**
     * Method to get the current streak of the student by getting the current streak from the student's progression.
     * @return the current streak of the student
     */
    public int getCurrentStreak() {
        return progression.getCurrentStreak();
    }

    public Title getEquippedTitle(){
        return progression.getEquippedTitle();
    }
}
