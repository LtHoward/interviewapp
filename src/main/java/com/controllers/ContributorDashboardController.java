package com.controllers;

import com.interviewapp.App;
import com.model.User;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class ContributorDashboardController {

    private User currentUser;

    @FXML private Label welcomeLabel;
    @FXML private Label statsLabel;

    @FXML private StackPane welcomeBanner;
    @FXML private ImageView profileImageView;

    @FXML private Button homeButton;
    @FXML private Button searchButton;
    @FXML private Button searchButtonTwo;
    @FXML private Button solutionsButton;
    @FXML private Button profileButton;
    @FXML private Button settingsButton;
    @FXML private Button sidebarToggleButton;

    @FXML private VBox sidebarContainer;

    private boolean sidebarVisible = false;
    private static final double SIDEBAR_OPEN_WIDTH = 90;

    @FXML
    public void initialize() {
        profileImageView.setImage(
            new Image(getClass().getResource("/com/interviewapp/images/DefaultIcon.png").toExternalForm())
        );

        Rectangle bannerClip = new Rectangle();
        bannerClip.setArcWidth(56);
        bannerClip.setArcHeight(56);
        bannerClip.widthProperty().bind(welcomeBanner.widthProperty());
        bannerClip.heightProperty().bind(welcomeBanner.heightProperty());
        welcomeBanner.setClip(bannerClip);

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

    public void setUser(User user) {
        this.currentUser = user;
        populateDashboard();
    }

    private void populateDashboard() {
        if (currentUser == null) {
            return;
        }

        welcomeLabel.setText("Welcome " + currentUser.getUsername());

        // Placeholder until you wire real contributor stats.
        statsLabel.setText("Created Questions: ~  |  Solved Questions: ~");
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

    private void setSidebarChildrenVisible(boolean visible) {
        sidebarContainer.getChildren().forEach(node -> {
            node.setVisible(visible);
            node.setManaged(visible);
        });
    }

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

    @FXML
    private void switchToCreateQuestionPost(ActionEvent event) {
        try {
            FXMLLoader loader = App.setRootWithLoader("createQuestionPost");
            CreateQuestionPostController controller = loader.getController();
            controller.setUser(currentUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}