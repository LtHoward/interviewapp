package com.model;
 
/**
 * Enum representing titles a student can unlock based on their progression level.
 * Each title has a required level that must be reached before it can be unlocked or equipped.
 */
public enum Title {
    SYNTAX_SCOUT(1),
    LOGIC_LEARNER(2),
    BOOLEAN_BRAWLER(3),
    ALGORITH_APPRENTICE(4),
    HASHMAP_HUNTER(5),
    RECURSION_RENEGADE(6),
    CODE_WARLORD(7),
    GARNET_GURU(8),
    LORD_OF_LOGIC(9);
 
    private final int requiredLevel;
 
    Title(int requiredLevel) {
        this.requiredLevel = requiredLevel;
    }
 
    /**
     * Returns the level required to unlock this title.
     *
     * @return the required level
     */
    public int getRequiredLevel() {
        return requiredLevel;
    }
}
 