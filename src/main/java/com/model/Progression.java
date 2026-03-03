package com.model;
<<<<<<< HEAD
import java.util.ArrayList;
import java.util.Date;
=======

import java.util.ArrayList;
import java.util.Date;

>>>>>>> 32b7bbd57b11d024f9b9eee8711f348e08b8b2ce
public class Progression 
{
    private int points;
    private int level;
    private int currentStreak;
    private int longestStreak;
    private Title equippedTitle;
    private ArrayList<Title> unlockedTitles;

    public void awardXP(int amount) {
        return;
    }

    public boolean checkLevelUp() {
        return false;
    }

    public int xpToNextLevel() {
        return 0;
    }

    public void updateStreak(Date solvedOn)
    {
        return;
    }
    
    public ArrayList<Title> unlockTitles()
    {
        return null;
    }
}
