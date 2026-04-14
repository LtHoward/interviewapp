package com.controllers;

import java.util.ArrayList;

import com.model.Reward;
import com.model.RewardType;
import com.model.Student;
import com.model.Title;
import com.model.User;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class DashboardController {

    private User currentUser;

    @FXML
    private Label welcomeLabel;

    @FXML
    private Label statsLabel;

    @FXML
    private Label levelLabel;

    @FXML
    private Label xpLabel;

    @FXML
    private Label xpToNextLabel;

    @FXML
    private Label pointsLabel;

    @FXML
    private Label boostLabel;

    @FXML
    private Label streakRewardLabel;

    @FXML
    private Label streakSaverLabel;

    @FXML
    private Label titlesUnlockedLabel;

    @FXML
    private Label detailsLabel;

    @FXML
    private ProgressBar xpBar;

    public void setUser(User user) {
        this.currentUser = user;
        populateDashboard();
    }

    private void populateDashboard() {
        if (currentUser == null) {
            return;
        }

        if (welcomeLabel != null) {
            welcomeLabel.setText("Welcome " + currentUser.getUsername());
        }

        if (currentUser instanceof Student) {
            Student student = (Student) currentUser;

            int level = student.getProgression().getLevel();
            int streak = student.getCurrentStreak();
            int points = student.getProgression().getPoints();
            int xpNeeded = student.getProgression().xpToNextLevel();
            Title equippedTitle = student.getProgression().getEquippedTitle();

            if (statsLabel != null) {
                statsLabel.setText(
                    "Level: " + level +
                    "   Streak: " + streak +
                    "   Title: " + (equippedTitle != null ? equippedTitle : "None")
                );
            }

            if (levelLabel != null) {
                levelLabel.setText("Level " + level);
            }

            if (xpLabel != null) {
                xpLabel.setText(points + " XP");
            }

            if (xpToNextLabel != null) {
                xpToNextLabel.setText(xpNeeded + " XP to next level");
            }

            if (pointsLabel != null) {
                pointsLabel.setText("XP: " + points);
            }

            if (streakRewardLabel != null) {
                streakRewardLabel.setText("Streak: " + streak);
            }

            double progress = (double) points / (level * 100.0);
            if (xpBar != null) {
                xpBar.setProgress(Math.min(progress, 1.0));
            }

            if (detailsLabel != null) {
                detailsLabel.setText(
                    student.getMajor() + " | " +
                    student.getSkillLevel() + " | Solved: " +
                    student.getSolvedQuestions()
                );
            }

            ArrayList<Reward> rewards = student.getRewards();
            int xpBoostCount = 0;
            int streakSaverCount = 0;

            for (Reward reward : rewards) {
                if (reward.getType() == RewardType.XP_BOOST && !reward.isRedeemed()) {
                    xpBoostCount += reward.getAmount();
                }

                if (reward.getType().name().equals("STREAK_SAVER") && !reward.isRedeemed()) {
                    streakSaverCount += reward.getAmount();
                }
            }

            if (boostLabel != null) {
                boostLabel.setText("XP Boost: x" + xpBoostCount);
            }

            if (streakSaverLabel != null) {
                streakSaverLabel.setText("Streak Saver: x" + streakSaverCount);
            }

            ArrayList<Title> unlockedTitles = student.getProgression().getUnlockedTitles();
            if (titlesUnlockedLabel != null) {
                if (unlockedTitles == null || unlockedTitles.isEmpty()) {
                    titlesUnlockedLabel.setText("No titles unlocked");
                } else {
                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < unlockedTitles.size(); i++) {
                        builder.append(unlockedTitles.get(i));
                        if (i < unlockedTitles.size() - 1) {
                            builder.append(", ");
                        }
                    }
                    titlesUnlockedLabel.setText(builder.toString());
                }
            }
        } else {
            if (statsLabel != null) {
                statsLabel.setText("Contributor/Admin dashboard");
            }
            if (levelLabel != null) {
                levelLabel.setText("N/A");
            }
            if (xpLabel != null) {
                xpLabel.setText("N/A");
            }
            if (xpToNextLabel != null) {
                xpToNextLabel.setText("No student progression");
            }
            if (pointsLabel != null) {
                pointsLabel.setText("XP: N/A");
            }
            if (boostLabel != null) {
                boostLabel.setText("XP Boost: N/A");
            }
            if (streakRewardLabel != null) {
                streakRewardLabel.setText("Streak: N/A");
            }
            if (streakSaverLabel != null) {
                streakSaverLabel.setText("Streak Saver: N/A");
            }
            if (titlesUnlockedLabel != null) {
                titlesUnlockedLabel.setText("No student titles");
            }
            if (detailsLabel != null) {
                detailsLabel.setText(currentUser.getStatus().toString());
            }
            if (xpBar != null) {
                xpBar.setProgress(0.0);
            }
        }
    }
}