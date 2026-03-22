package com.model;
import java.time.LocalDate;
import java.util.ArrayList;
public class Progression 
{
    private int points;
    private int level;
    private int currentStreak;
    private int longestStreak;
    private Title equippedTitle;
    private ArrayList<Title> unlockedTitles;

    public Progression() {
    this.points = 0;
    this.level = 1;
    this.currentStreak = 0;
    this.longestStreak = 0;
    this.unlockedTitles = new ArrayList<>();
}
    public void awardXP(int amount) {
        this.points += amount;
    }

    public boolean checkLevelUp() {
    int xpNeeded = level * 100; // e.g. level 1 needs 100xp, level 2 needs 200xp, etc.
    if (points >= xpNeeded) {
        level++;
        points -= xpNeeded; // reset points after leveling up
        return true;
    }
    return false;
}
    

    public int getPoints() {
        return points;
    }

    public int getLevel() {
        return level;
    }

    /**
     * Method to get the current streak of the user
     * @return the current streak of the user
     */
    public int getCurrentStreak() {
        return currentStreak;
    }

    public int getLongestStreak() {

        return longestStreak;
    }

    public Title getEquippedTitle() {
        return equippedTitle;
    }

    public int xpToNextLevel() {
    return (level * 100) - points;
}
    

    public void updateStreak(LocalDate solvedOn){ 
        LocalDate.now();                
    }
    
    public ArrayList<Title> unlockedTitles()
    {
        return unlockedTitles;
    }

    public void setpoints(int points) {
        this.points = points;
    }

    public void setlevel(int level) {
        this.level = level;
    }   

    public void setcurrentStreak(int currentStreak) {
        this.currentStreak = currentStreak;
    }

    public void setlongestStreak(int longestStreak) {
        this.longestStreak = longestStreak;
    }

    public void setequippedTitle(Title equippedTitle) {
        this.equippedTitle = equippedTitle;
    }

    public void setunlockedTitles(ArrayList<Title> unlockedTitles) {
        this.unlockedTitles = unlockedTitles;
    }
}
