package com.model;
import java.time.LocalDate;
import java.util.ArrayList;
<<<<<<< HEAD
/**
 * Represents a user's progression, tracking experience points, level,
 * streaks, and unlocked titles.
=======

/**
 * Class to represents the user progression in the application. It manages the points, level, streaks, and unlocked titles.
 * It provides functionality to award Xp, check for level ups, track streaks, and manage titles.
>>>>>>> 839b35d13dab2b39a211bafef58772451497e41a
 */
public class Progression 
{
    private int points;
    private int level;
    private int currentStreak;
    private int longestStreak;
    private Title equippedTitle;
    private ArrayList<Title> unlockedTitles;
/**
 * Constructs a new Progression instance with default starting values,
 * initializing points, level, and streaks to zero and an empty list of unlocked titles.
 */
    public Progression() {
    this.points = 0;
    this.level = 1;
    this.currentStreak = 0;
    this.longestStreak = 0;
    this.unlockedTitles = new ArrayList<>();
}
/**
 * Awards the specified number of experience points to the progression.
 *
 * @param amount the number of points to add
 */
    public void awardXP(int amount) {
        if (amount > 0 ) {
            this.points += amount;
            checkLevelUp();
        }
    }
/**
 * Checks whether the current points are sufficient to level up.
 * If so, increments the level and deducts the required experience points.
 * The experience points needed to level up is the current level multiplied by 100.
 *
 * @return {@code true} if a level up occurred, {@code false} otherwise
 */
    public boolean checkLevelUp() {
    int xpNeeded = level * 100; // e.g. level 1 needs 100xp, level 2 needs 200xp, etc.
    if (points >= xpNeeded) {
        level++;
        points -= xpNeeded; // reset points after leveling up
        return true;
    }
    return false;
}
/**
 * Returns the current points, ensuring the value is never negative.
 *
 * @return the current points, or {@code 0} if points have fallen below zero
 */  
    public int getPoints() {
       return Math.max(points, 0);
    }
/**
 * Returns the current level.
 *
 * @return the current level
 */
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
/**
 * Returns the longest streak ever achieved.
 *
 * @return the longest streak
 */
    public int getLongestStreak() {

        return longestStreak;
    }
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
 /**
 * Returns the list of titles that have been unlocked.
 *
 * @return an {@link ArrayList} of unlocked {@link Title} objects
 */   
    public ArrayList<Title> getUnlockedTitles()
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
    public void setUnlockedTitles(ArrayList<Title> unlockedTitles) {
        this.unlockedTitles = unlockedTitles;
    }
}
