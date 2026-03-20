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
    public static final int XP_POST = 20;
    public static final int XP_COMMENT = 10;
    public static final int XP_VOTE = 5;
    public static final int XP_GET_VOTE = 15;
    public static final int XP_MAX_VOTING = 50;
    public static final int XP_MAX_GET_VOTE = 100;
    public static final int XP_MAX_COMMENT = 100;
    public static final int XP_MAX_POST = 200;
    public static final int XP_EASY = 10;
    public static final int XP_MEDIUM = 20;
    public static final int XP_HARD = 30;

    private ArrayList<Title> unlockedTitles;

    public void awardXP(int amount) {
        
    }

    public boolean checkLevelUp() {
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
        return 0;
    }

    public void updateStreak(LocalDate solvedOn){
        
    }
    
    public ArrayList<Title> unlockTitles()
    {
        return unlockedTitles;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setLevel(int level) {
        this.level = level;
    }   

    public void setCurrentStreak(int currentStreak) {
        this.currentStreak = currentStreak;
    }

    public void setLongestStreak(int longestStreak) {
        this.longestStreak = longestStreak;
    }

    public void setEquippedTitle(Title equippedTitle) {
        this.equippedTitle = equippedTitle;
    }

    public void setUnlockedTitles(ArrayList<Title> unlockedTitles) {
        this.unlockedTitles = unlockedTitles;
    }
}
