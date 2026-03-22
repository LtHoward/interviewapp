package com.model;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Class to represents the user progression in the application. It manages the points, level, streaks, and unlocked titles.
 * It provides functionality to award Xp, check for level ups, track streaks, and manage titles.
 */
public class Progression 
{
    private int points;
    private int level;
    private int currentStreak;
    private int longestStreak;
    private Title equippedTitle;
    private ArrayList<Title> unlockedTitles;
<<<<<<< HEAD

    /**
     * Constructor for the Progression class to intialized with the given parameters.
     * @param points the points the user has 
     * @param level the current level the user is on 
     * @param longestStreak the longest streak the user has 
     * @param currentStreak the current streak for the user
     * @param unlockedTitles all the unlocked titles to user has
     */
=======
/**
 * Constructs a new Progression instance with default starting values,
 * initializing points, level, and streaks to zero and an empty list of unlocked titles.
 */
>>>>>>> 03256e56598440fddbf6a74bcb2855a829f4e7c2
    public Progression() {
    this.points = 0;
    this.level = 1;
    this.currentStreak = 0;
    this.longestStreak = 0;
    this.unlockedTitles = new ArrayList<>();
}
<<<<<<< HEAD

    /**
     * Method for awards XP to the user and check for level-up
     * @param amount the amount of the XP to award, has to be positive
     */
=======
/**
 * Awards the specified number of experience points to the progression.
 *
 * @param amount the number of points to add
 */
>>>>>>> 03256e56598440fddbf6a74bcb2855a829f4e7c2
    public void awardXP(int amount) {
        if (amount > 0 ) {
            this.points += amount;
            checkLevelUp();
        }
    }
<<<<<<< HEAD

    /**
     * Method to check if the user has enough Xp to level up
     * @return true if the user leveled up, false otherwise
     */
=======
/**
 * Checks whether the current points are sufficient to level up.
 * If so, increments the level and deducts the required experience points.
 * The experience points needed to level up is the current level multiplied by 100.
 *
 * @return {@code true} if a level up occurred, {@code false} otherwise
 */
>>>>>>> 03256e56598440fddbf6a74bcb2855a829f4e7c2
    public boolean checkLevelUp() {
    int xpNeeded = level * 100; // e.g. level 1 needs 100xp, level 2 needs 200xp, etc.
    if (points >= xpNeeded) {
        level++;
        points -= xpNeeded; // reset points after leveling up
        return true;
    }
    return false;
}
<<<<<<< HEAD
    

    /**
     * Method to get the current XP points
     * @return the user's XP, never is negative
     */
=======
/**
 * Returns the current points, ensuring the value is never negative.
 *
 * @return the current points, or {@code 0} if points have fallen below zero
 */  
>>>>>>> 03256e56598440fddbf6a74bcb2855a829f4e7c2
    public int getPoints() {
       return Math.max(points, 0);
    }
<<<<<<< HEAD

    /**
     * Method to get the user's current level
     * @return the current level
     */
=======
/**
 * Returns the current level.
 *
 * @return the current level
 */
>>>>>>> 03256e56598440fddbf6a74bcb2855a829f4e7c2
    public int getLevel() {
        return level;
    }

    /**
     * Method to get the current streak of the user
     * @return the current streak 
     */
    public int getCurrentStreak() {
        return currentStreak;
    }
<<<<<<< HEAD

    /**
     * Method to get the longest streak of the user
     * @return the longest streak
     */
=======
/**
 * Returns the longest streak ever achieved.
 *
 * @return the longest streak
 */
>>>>>>> 03256e56598440fddbf6a74bcb2855a829f4e7c2
    public int getLongestStreak() {

        return longestStreak;
    }
<<<<<<< HEAD

    /**
     * Method to get the current equipped title of the use 
     * @return the equipped title
     */
    public Title getEquippedTitle() {
        return equippedTitle;
    }

    /**
     * Method to calculate how much XP is need to reach the next level
     * @return XP required for next level
     */
=======
/**
 * Returns the currently equipped title.
 *
 * @return the currently equipped {@link Title}, or {@code null} if no title is equipped
 */
    public Title getEquippedTitle() {
        return equippedTitle;
    }
/**
 * Calculates the number of experience points needed to reach the next level.
 *
 * @return the remaining experience points required to level up
 */
>>>>>>> 03256e56598440fddbf6a74bcb2855a829f4e7c2
    public int xpToNextLevel() {
    return (level * 100) - points;
}
 /**
 * Updates the current and longest streaks based on the date a problem was solved.
 *
 * @param solvedOn the {@link LocalDate} on which the problem was solved
 */   

    /**
     * Method to update the user's activity streak based on the last activity date
     * @param solvedOn the date of the current activity 
     * @param lastActivityDate the previous activity date
     */
    public void updateStreak(LocalDate solvedOn, LocalDate lastActivityDate){ 
        if (lastActivityDate == null) {
            currentStreak = 1;
        } else if (solvedOn.equals(lastActivityDate.plusDays(1))) {
            currentStreak++;
        } else if (solvedOn.equals(lastActivityDate)) {
            return;
        } else {
            currentStreak = 1;
        }
        if (currentStreak > longestStreak) {
            longestStreak = currentStreak;
        }            
    }
<<<<<<< HEAD
    
    /**
     * Method to get the list of unlocked titles 
     * @return the list of unlocked titles 
     */
    public ArrayList<Title> getunlockedTitles()
    {
        return unlockedTitles;
    }

    /**
     * Method to set the user's points
     * @param points the current points XP value
     */
    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * Method to set the user's level 
     * @param level the new level 
     */
    public void setLevel(int level) {
        this.level = level;
    }   

    /**
     * Method to set the current streak of the user
     * @param currentStreak the new current streak 
     */
    public void setCurrentStreak(int currentStreak) {
        this.currentStreak = currentStreak;
    }

    /**
     * Method to set the longest streak of the user
     * @param longestStreak the new longest streak
     */
    public void setlongestStreak(int longestStreak) {
        this.longestStreak = longestStreak;
    }

    /**
     * Method to set the equipped title of the user
     * @param equippedTitle the new equipped title
     */
    public void setEquippedTitle(Title equippedTitle) {
        this.equippedTitle = equippedTitle;
    }

    /**
     * Method to set the list of unlocked titles 
     * @param unlockedTitles the list of unlocked titles
     */
=======
 /**
 * Returns the list of titles that have been unlocked.
 *
 * @return an {@link ArrayList} of unlocked {@link Title} objects
 */   
    public ArrayList<Title> unlockedTitles()
    {
        return unlockedTitles;
    }
/**
 * Sets the current points.
 *
 * @param points the points value to set
 */
    public void setPoints(int points) {
        this.points = points;
    }
/**
 * Sets the current level.
 *
 * @param level the level value to set
 */
    public void setLevel(int level) {
        this.level = level;
    }   
/**
 * Sets the current streak.
 *
 * @param currentStreak the current streak value to set
 */
    public void setCurrentStreak(int currentStreak) {
        this.currentStreak = currentStreak;
    }
/**
 * Sets the longest streak.
 *
 * @param longestStreak the longest streak value to set
 */
    public void setLongestStreak(int longestStreak) {
        this.longestStreak = longestStreak;
    }
/**
 * Sets the currently equipped title.
 *
 * @param equippedTitle the {@link Title} to equip
 */
    public void setEquippedTitle(Title equippedTitle) {
        this.equippedTitle = equippedTitle;
    }
/**
 * Sets the list of unlocked titles.
 *
 * @param unlockedTitles the {@link ArrayList} of {@link Title} objects to set
 */
>>>>>>> 03256e56598440fddbf6a74bcb2855a829f4e7c2
    public void setUnlockedTitles(ArrayList<Title> unlockedTitles) {
        this.unlockedTitles = unlockedTitles;
    }
}
