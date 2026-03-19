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
}
