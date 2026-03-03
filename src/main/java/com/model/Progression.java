package com.model;
<<<<<<< HEAD
import java.util.ArrayList;
import java.util.Date;
=======

import java.util.ArrayList;
import java.util.Date;

>>>>>>> fdb99d3959b5bb708c3f38cbe349b043eb400c9d
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
