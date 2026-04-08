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
 
    /**
     * Creates a new Progression instance with default starting values,
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
     * Method to update the user's activity streak based on the last activity date.
     *
     * @param solvedOn         the date of the current activity
     * @param lastActivityDate the previous activity date
     */
    public void updateStreak(LocalDate solvedOn, LocalDate lastActivityDate) {
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
    public ArrayList<Title> getUnlockedTitles() {
        return unlockedTitles;
    }
 
    // -------------------------------------------------------------------------
    // BUG FIX: Title access control
    // Previously, setEquippedTitle() accepted any Title with no validation,
    // allowing a student to equip titles they had not yet earned.
    // The three methods below close that gap:
    //   1. isTitleUnlocked()        — checks if a title is earned
    //   2. setEquippedTitle()       — only equips if unlocked (returns boolean)
    //   3. unlockTitleIfEligible()  — unlocks a title when level requirement is met
    // -------------------------------------------------------------------------
 
    /**
     * Checks whether the given title has been unlocked by the student.
     *
     * @param title the {@link Title} to check
     * @return {@code true} if the title is in the unlocked titles list, {@code false} otherwise
     */
    public boolean isTitleUnlocked(Title title) {
        if (title == null) return false;
        return unlockedTitles.contains(title);
    }
 
    /**
     * Unlocks a title if the student's current level meets or exceeds the title's
     * required level, and the title has not already been unlocked.
     *
     * @param title the {@link Title} to potentially unlock
     * @return {@code true} if the title was newly unlocked, {@code false} otherwise
     */
    public boolean unlockTitleIfEligible(Title title) {
        if (title == null) return false;
        if (isTitleUnlocked(title)) return false;
        if (this.level >= title.getRequiredLevel()) {
            unlockedTitles.add(title);
            return true;
        }
        return false;
    }
 
    /**
     * Equips the given title only if it has already been unlocked.
     * If the title has not been unlocked, the equipped title remains unchanged.
     *
     * @param equippedTitle the {@link Title} to equip
     * @return {@code true} if the title was successfully equipped,
     *         {@code false} if it is not yet unlocked
     */
    public boolean setEquippedTitle(Title equippedTitle) {
        if (!isTitleUnlocked(equippedTitle)) {
            return false;
        }
        this.equippedTitle = equippedTitle;
        return true;
    }
 
    // -------------------------------------------------------------------------
    // Remaining setters (for deserialization / data loading only)
    // -------------------------------------------------------------------------
 
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
     * Sets the list of unlocked titles (for data loading only).
     * To unlock titles during gameplay, use {@link #unlockTitleIfEligible(Title)}.
     *
     * @param unlockedTitles the {@link ArrayList} of {@link Title} objects to set
     */
    public void setUnlockedTitles(ArrayList<Title> unlockedTitles) {
        this.unlockedTitles = unlockedTitles;
    }
}
