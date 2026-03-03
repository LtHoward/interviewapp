package com.model;
import java.util.ArrayList;
import java.util.Date;
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

    public int xpToNextLevel() {
        return 0;
    }

    public void updateStreak(Date solvedOn)
    {
       
    }
    
    public ArrayList<Title> unlockTitles()
    {
        return null;
    }
}
