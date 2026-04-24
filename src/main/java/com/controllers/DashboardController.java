package com.controllers;

import java.util.ArrayList;

import com.interviewapp.App;
import com.model.InterviewApp;

import com.model.Reward;
import com.model.RewardType;
import com.model.Student;
import com.model.Title;
import com.model.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

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

    @FXML
    private StackPane welcomeBanner;

    @FXML
    private ImageView profileImageView;

    @FXML 
    private Button homeButton;

    @FXML 
    private Button searchButton;

    @FXML 
    private Button solutionsButton;

    @FXML 
    private Button profileButton;

    @FXML private Button settingsButton;

   @FXML
    private VBox sidebarContainer;

    private boolean sidebarVisible = false;
    private static final double SIDEBAR_OPEN_WIDTH = 90;

    @FXML
    private Button sidebarToggleButton;

    @FXML
    public void initialize() {
        profileImageView.setImage(
            new Image(getClass().getResource("/com/interviewapp/images/DefaultIcon.png").toExternalForm())
        );

        // Rounded clipping for the banner
        Rectangle bannerClip = new Rectangle();
        bannerClip.setArcWidth(56);
        bannerClip.setArcHeight(56);
        bannerClip.widthProperty().bind(welcomeBanner.widthProperty());
        bannerClip.heightProperty().bind(welcomeBanner.heightProperty());
        welcomeBanner.setClip(bannerClip);

        // Rounded clipping for the profile image
        Rectangle profileClip = new Rectangle();
        profileClip.setArcWidth(35);
        profileClip.setArcHeight(35);
        profileClip.widthProperty().bind(profileImageView.fitWidthProperty());
        profileClip.heightProperty().bind(profileImageView.fitHeightProperty());
        profileImageView.setClip(profileClip);

        sidebarContainer.setPrefWidth(0);
        sidebarContainer.setMinWidth(0);
        sidebarContainer.setMaxWidth(0);
        setSidebarChildrenVisible(false);

        setButtonIcon(homeButton, "/com/interviewapp/images/icons/home.png", 32);
        setButtonIcon(searchButton, "/com/interviewapp/images/icons/search.png", 32);
        setButtonIcon(solutionsButton, "/com/interviewapp/images/icons/solutions.png", 32);
        setButtonIcon(profileButton, "/com/interviewapp/images/icons/profile.png", 32);
        setButtonIcon(settingsButton, "/com/interviewapp/images/icons/settings.png", 32);
    }

    @FXML
    private void toggleSidebar() {
        double targetWidth = sidebarVisible ? 0 : SIDEBAR_OPEN_WIDTH;

        if (!sidebarVisible) {
            setSidebarChildrenVisible(true);
        }

        Timeline timeline = new Timeline(
            new KeyFrame(Duration.millis(250),
                new KeyValue(sidebarContainer.prefWidthProperty(), targetWidth),
                new KeyValue(sidebarContainer.minWidthProperty(), targetWidth),
                new KeyValue(sidebarContainer.maxWidthProperty(), targetWidth)
            )
        );

        if (sidebarVisible) {
            timeline.setOnFinished(e -> setSidebarChildrenVisible(false));
        }

        timeline.play();
        sidebarVisible = !sidebarVisible;
    }

    @FXML
    private void setSidebarChildrenVisible(boolean visible) {
        sidebarContainer.getChildren().forEach(node -> {
            node.setVisible(visible);
            node.setManaged(visible);
        });
    }

    @FXML
    public void setUser(User user) {
        this.currentUser = user;
        populateDashboard();
    }

    @FXML
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
                    "   " + formatEnum(equippedTitle)
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
                    "A " + formatEnum(student.getSkillLevel()) + " " + 
                    formatEnum(student.getMajor()) + " major who's solved " +
                    student.getSolvedQuestions() + " questions."
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
                boostLabel.setText("|  XP Boost: x" + xpBoostCount);
            }

            if (streakSaverLabel != null) {
                streakSaverLabel.setText("|  Streak Saver: x" + streakSaverCount);
            }

            ArrayList<Title> unlockedTitles = student.getProgression().getUnlockedTitles();
            if (titlesUnlockedLabel != null) {
                if (unlockedTitles == null || unlockedTitles.isEmpty()) {
                    titlesUnlockedLabel.setText("No titles unlocked");
                } else {
                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < unlockedTitles.size(); i++) {
                        builder.append(formatEnum(unlockedTitles.get(i)));
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
    // Helper method to format enumerations for display
    private String formatEnum(Enum<?> value) {
        if (value == null) {
            return "None";
        }

        String[] words = value.name().toLowerCase().split("_");
        StringBuilder formatted = new StringBuilder();

        for (int i = 0; i < words.length; i++) {
            if (words[i].isEmpty()) continue;

            formatted.append(Character.toUpperCase(words[i].charAt(0)))
                    .append(words[i].substring(1));

            if (i < words.length - 1) {
                formatted.append(" ");
            }
        }

        return formatted.toString();
    }
     // helper method to set button icons
     private void setButtonIcon(Button button, String resourcePath, double size) {
        Image image = new Image(getClass().getResource(resourcePath).toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(size);
        imageView.setFitHeight(size);
        imageView.setPreserveRatio(true);
        button.setText("");
        button.setGraphic(imageView);
    }

    @FXML
    private void switchToSearch(ActionEvent event) {
        try {
            FXMLLoader loader = App.setRootWithLoader("search");
            SearchController controller = loader.getController();
            controller.setUser(currentUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void switchToProfile(ActionEvent event) {
        try {
            FXMLLoader loader = App.setRootWithLoader("profile");
            ProfileController controller = loader.getController();
            controller.setUser(currentUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void switchToSettings(ActionEvent event) {
        try {
            FXMLLoader loader = App.setRootWithLoader("settings");
            SettingsController controller = loader.getController();
            controller.setUser(currentUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     

}