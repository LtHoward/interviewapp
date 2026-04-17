package com.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Progression class.
 *
 * AI was used to help generate this test suite.
 *
 * +-------------------------------------------------------+--------------------------------------------------------------+
 * | Test                                                  | Reasoning                                                    |
 * +-------------------------------------------------------+--------------------------------------------------------------+
 * | initialProgressionStartsAtLevelOne                    | New users must always begin at level 1                       |
 * | initialProgressionHasZeroPoints                       | New users start with no XP accumulated                       |
 * | initialProgressionHasZeroStreaks                      | No activity has occurred yet so streaks must be 0            |
 * | awardXP_positiveAmount_increasesPoints                | Valid XP awards should accumulate points correctly           |
 * | awardXP_zeroAmount_doesNotChangePoints                | Zero is not a valid XP gain and should be ignored            |
 * | awardXP_negativeAmount_doesNotChangePoints            | Negative XP would corrupt progression; must be rejected      |
 * | awardXP_exactLevelThreshold_triggersLevelUp           | Awarding exactly enough XP must trigger a level up           |
 * | awardXP_causeMultipleLevelUps_accumulatesCorrectly    | Large XP awards can skip multiple levels; all must process   |
 * | checkLevelUp_sufficientPoints_returnsTrue             | Method must return true when a level-up occurs               |
 * | checkLevelUp_insufficientPoints_returnsFalse          | Method must return false when not enough XP to level up      |
 * | checkLevelUp_exactThreshold_levelsUpAndResetsPoints   | Points should be reduced by the XP cost after leveling up    |
 * | xpToNextLevel_returnsCorrectRemainder                 | Players need accurate feedback on XP remaining               |
 * | xpToNextLevel_atZeroPoints_returnsFullLevelCost       | At zero XP the full cost of the next level must be shown     |
 * | updateStreak_nullLastActivity_setsStreakToOne         | First-ever activity should initialize the streak to 1        |
 * | updateStreak_consecutiveDay_incrementsStreak          | Solving on consecutive days must grow the streak             |
 * | updateStreak_sameDay_doesNotChangeStreak              | Solving twice on same day must not double-count the streak   |
 * | updateStreak_missedDay_resetsStreakToOne               | Breaking a streak by skipping a day must reset it to 1       |
 * | updateStreak_updatesLongestStreak                     | The longest streak record must be updated when surpassed     |
 * | updateStreak_longestStreakNotDowngraded                | Longest streak must never decrease when current drops        |
 * | equippedTitle_defaultIsNull                           | No title should be equipped on a brand-new progression       |
 * | setEquippedTitle_storesAndReturnsTitle                | Equipping a title must make it retrievable                   |
 * | unlockedTitles_defaultIsEmpty                         | A new progression should have no unlocked titles             |
 * | setUnlockedTitles_replacesTheList                     | Setting a new title list must replace the previous one       |
 * +-------------------------------------------------------+--------------------------------------------------------------+
 */
class ProgressionTest {

    private Progression progression;

    @BeforeEach
    void setUp() {
        progression = new Progression();
    }

    // ----------------------------------------------------------------
    // Constructor / initial state
    // ----------------------------------------------------------------

    @Test
    void initialProgressionStartsAtLevelOne() {
        assertEquals(1, progression.getLevel());
    }

    @Test
    void initialProgressionHasZeroPoints() {
        assertEquals(0, progression.getPoints());
    }

    @Test
    void initialProgressionHasZeroStreaks() {
        assertEquals(0, progression.getCurrentStreak());
        assertEquals(0, progression.getLongestStreak());
    }

    // ----------------------------------------------------------------
    // awardXP
    // ----------------------------------------------------------------

    @Test
    void awardXP_positiveAmount_increasesPoints() {
        progression.awardXP(50);
        assertEquals(50, progression.getPoints());
    }

    @Test
    void awardXP_zeroAmount_doesNotChangePoints() {
        progression.awardXP(0);
        assertEquals(0, progression.getPoints());
    }

    @Test
    void awardXP_negativeAmount_doesNotChangePoints() {
        progression.awardXP(-30);
        assertEquals(0, progression.getPoints());
    }

    @Test
    void awardXP_exactLevelThreshold_triggersLevelUp() {
        // Level 1 needs 100 XP to level up
        progression.awardXP(100);
        assertEquals(2, progression.getLevel());
        assertEquals(0, progression.getPoints());
    }

    @Test
    void awardXP_causeMultipleLevelUps_accumulatesCorrectly() {
        // Level 1 costs 100, level 2 costs 200 -> 300 XP total should reach level 3 with 0 remaining
        progression.awardXP(300);
        assertEquals(3, progression.getLevel());
        assertEquals(0, progression.getPoints());
    }

    // ----------------------------------------------------------------
    // checkLevelUp
    // ----------------------------------------------------------------

    @Test
    void checkLevelUp_sufficientPoints_returnsTrue() {
        progression.setPoints(100); // level 1 needs 100
        assertTrue(progression.checkLevelUp());
    }

    @Test
    void checkLevelUp_insufficientPoints_returnsFalse() {
        progression.setPoints(50);
        assertFalse(progression.checkLevelUp());
    }

    @Test
    void checkLevelUp_exactThreshold_levelsUpAndResetsPoints() {
        progression.setPoints(100);
        progression.checkLevelUp();
        assertEquals(2, progression.getLevel());
        assertEquals(0, progression.getPoints());
    }

    // ----------------------------------------------------------------
    // xpToNextLevel
    // ----------------------------------------------------------------

    @Test
    void xpToNextLevel_returnsCorrectRemainder() {
        progression.awardXP(60); // level 1 still, 40 XP remaining to level
        assertEquals(40, progression.xpToNextLevel());
    }

    @Test
    void xpToNextLevel_atZeroPoints_returnsFullLevelCost() {
        // Level 1: 1 * 100 = 100 needed
        assertEquals(100, progression.xpToNextLevel());
    }

    // ----------------------------------------------------------------
    // updateStreak
    // ----------------------------------------------------------------

    @Test
    void updateStreak_nullLastActivity_setsStreakToOne() {
        progression.updateStreak(LocalDate.of(2024, 1, 1), null);
        assertEquals(1, progression.getCurrentStreak());
    }

    @Test
    void updateStreak_consecutiveDay_incrementsStreak() {
        LocalDate yesterday = LocalDate.of(2024, 6, 10);
        LocalDate today     = LocalDate.of(2024, 6, 11);
        progression.setCurrentStreak(3);
        progression.updateStreak(today, yesterday);
        assertEquals(4, progression.getCurrentStreak());
    }

    @Test
    void updateStreak_sameDay_doesNotChangeStreak() {
        LocalDate today = LocalDate.of(2024, 6, 11);
        progression.setCurrentStreak(5);
        progression.updateStreak(today, today);
        assertEquals(5, progression.getCurrentStreak());
    }

    @Test
    void updateStreak_missedDay_resetsStreakToOne() {
        LocalDate twoDaysAgo = LocalDate.of(2024, 6, 9);
        LocalDate today      = LocalDate.of(2024, 6, 11);
        progression.setCurrentStreak(7);
        progression.updateStreak(today, twoDaysAgo);
        assertEquals(1, progression.getCurrentStreak());
    }

    @Test
    void updateStreak_updatesLongestStreak() {
        LocalDate yesterday = LocalDate.of(2024, 6, 10);
        LocalDate today     = LocalDate.of(2024, 6, 11);
        progression.setCurrentStreak(4);
        progression.setLongestStreak(4);
        progression.updateStreak(today, yesterday);
        assertEquals(5, progression.getLongestStreak());
    }

    @Test
    void updateStreak_longestStreakNotDowngraded() {
        // Break a streak; longest should remain at its previous high
        LocalDate twoDaysAgo = LocalDate.of(2024, 6, 9);
        LocalDate today      = LocalDate.of(2024, 6, 11);
        progression.setCurrentStreak(10);
        progression.setLongestStreak(10);
        progression.updateStreak(today, twoDaysAgo);
        assertEquals(10, progression.getLongestStreak()); // must NOT drop
        assertEquals(1,  progression.getCurrentStreak()); // reset to 1
    }

    // ----------------------------------------------------------------
    // Equipped title
    // ----------------------------------------------------------------

    @Test
    void equippedTitle_defaultIsNull() {
        assertNull(progression.getEquippedTitle());
    }

    @Test
    void setEquippedTitle_storesAndReturnsTitle() {
        progression.setEquippedTitle(Title.SYNTAX_SCOUT);
        assertEquals(Title.SYNTAX_SCOUT, progression.getEquippedTitle());
    }

    // ----------------------------------------------------------------
    // Unlocked titles list
    // ----------------------------------------------------------------

    @Test
    void unlockedTitles_defaultIsEmpty() {
        assertTrue(progression.getUnlockedTitles().isEmpty());
    }

    @Test
    void setUnlockedTitles_replacesTheList() {
        ArrayList<Title> titles = new ArrayList<>();
        titles.add(Title.SYNTAX_SCOUT);
        titles.add(Title.LOGIC_LEARNER);
        progression.setUnlockedTitles(titles);
        assertEquals(2, progression.getUnlockedTitles().size());
        assertEquals(Title.SYNTAX_SCOUT, progression.getUnlockedTitles().get(0));
    }
}