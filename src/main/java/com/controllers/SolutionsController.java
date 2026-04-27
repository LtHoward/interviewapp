package com.controllers;

import java.time.LocalDate;
import java.util.UUID;

import com.interviewapp.App;
import com.model.Comment;
import com.model.ContentType;
import com.model.PostContent;
import com.model.PostManager;
import com.model.QuestionPost;
import com.model.SolutionPost;
import com.model.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class SolutionsController {
    
    private User currentUser;
    private int userVote = 0;
    private SolutionPost currentSolution;
    private QuestionPost selectedQuestion;

    @FXML private Label titleLabel;
    @FXML private Label authorLabel;
    @FXML private Label difficultyLabel;
    @FXML private Label tagsLabel;
    @FXML private Label scoreLabel;
    @FXML private Label commentsCountLabel;
    @FXML private VBox contentContainer;
    @FXML private VBox commentsContainer;
    @FXML private TextField commentField;
    @FXML private Button upvoteButton;
    @FXML private Button downvoteButton;

    
    public void setData(User user, SolutionPost solution, QuestionPost question) {
        this.currentUser = user;
        this.currentSolution = solution;
        this.selectedQuestion = question;
        populateSolution();
    }

    private void populateSolution() {
        if (currentSolution == null) {
            return;
        }

        titleLabel.setText(currentSolution.getTitle());
        authorLabel.setText(
            currentSolution.getAuthor() != null
                ? currentSolution.getAuthor().getUsername()
                : "Unknown"
        );
        difficultyLabel.setText(formatEnum(currentSolution.getDifficulty()));
        tagsLabel.setText(String.join(", ", currentSolution.getTags()));
        scoreLabel.setText(String.valueOf(currentSolution.getScore()));

        populateContent();
        populateComments();
    }

    private void populateContent() {
        contentContainer.getChildren().clear();

        for (PostContent section : currentSolution.getContentSections()) {
            if (section == null || section.getType() == null || section.getContent() == null) {
                continue;
            }

            ContentType type = section.getType();
            String value = section.getContent().toString();

            switch (type) {
                case TEXT:
                    Label textLabel = new Label(value);
                    textLabel.setWrapText(true);
                    textLabel.getStyleClass().add("comment-text");
                    contentContainer.getChildren().add(textLabel);
                    break;

                case CODE:
                    StackPane codeBlock = new StackPane();
                    codeBlock.getStyleClass().add("solution-code-block");

                    Label codeLabel = new Label(value);
                    codeLabel.setWrapText(true);
                    codeLabel.getStyleClass().add("solution-code-text");

                    codeBlock.getChildren().add(codeLabel);
                    contentContainer.getChildren().add(codeBlock);
                    break;

                case IMAGE:
                    ImageView imageView = new ImageView();
                    imageView.setFitWidth(350);
                    imageView.setFitHeight(200);
                    imageView.setPreserveRatio(true);
                    imageView.getStyleClass().add("solution-image-block");

                    // For now, display filename as text too.
                    // If the image exists in resources later, we can load it here.
                    Label imageLabel = new Label("Image: " + value);
                    imageLabel.getStyleClass().add("comment-text");

                    contentContainer.getChildren().add(imageLabel);
                    contentContainer.getChildren().add(imageView);
                    break;

                case VIDEO:
                    StackPane videoBlock = new StackPane();
                    videoBlock.setPrefWidth(350);
                    videoBlock.setPrefHeight(245);
                    videoBlock.getStyleClass().add("solution-video-block");

                    Label videoLabel = new Label("Video: " + value);
                    videoLabel.getStyleClass().add("comment-text");

                    videoBlock.getChildren().add(videoLabel);
                    contentContainer.getChildren().add(videoBlock);
                    break;
            }
        }
    }

        private void populateComments() {
        commentsContainer.getChildren().clear();

        int count = currentSolution.getComments() == null ? 0 : currentSolution.getComments().size();

        commentsCountLabel.setText(count + " Comments");

        if (count == 0) {
            Label empty = new Label("No comments yet.");
            empty.getStyleClass().add("comment-text");
            commentsContainer.getChildren().add(empty);
            return;
        }

        for (Comment comment : currentSolution.getComments()) {
            commentsContainer.getChildren().add(createCommentNode(comment));
        }
    }

    private VBox createCommentNode(Comment comment) {
        VBox thread = new VBox();
        thread.setSpacing(10);
        thread.getStyleClass().add("comment-thread");

        HBox row = new HBox();
        row.setSpacing(12);
        row.getStyleClass().add("comment-row");

        StackPane avatar = new StackPane();
        avatar.setPrefWidth(45);
        avatar.setPrefHeight(45);
        avatar.getStyleClass().add("comments-user-avatar");

        VBox textBox = new VBox();
        textBox.setSpacing(6);

        HBox header = new HBox();
        header.setSpacing(10);

        String username = comment.getAuthor() != null
            ? comment.getAuthor().getUsername()
            : "Unknown";

        Label usernameLabel = new Label(username);
        usernameLabel.getStyleClass().add("comment-username");

        Label dateLabel = new Label(
            comment.getCommentDate() != null
                ? comment.getCommentDate().toString()
                : ""
        );
        dateLabel.getStyleClass().add("comment-date");

        header.getChildren().addAll(usernameLabel, dateLabel);

        Label contentLabel = new Label(comment.getContent());
        contentLabel.setWrapText(true);
        contentLabel.getStyleClass().add("comment-text");

        HBox actions = new HBox();
        actions.setSpacing(18);

        int replyCount = comment.getReply() == null ? 0 : comment.getReply().size();

        Button repliesButton = new Button("View " + replyCount + " Replies");
        repliesButton.getStyleClass().add("comment-action-button");

        Button replyButton = new Button("Reply");
        replyButton.getStyleClass().add("comment-action-button");

        actions.getChildren().addAll(repliesButton, replyButton);

        textBox.getChildren().addAll(header, contentLabel, actions);
        row.getChildren().addAll(avatar, textBox);
        thread.getChildren().add(row);

        if (comment.getReply() != null) {
            for (Comment reply : comment.getReply()) {
                VBox replyNode = createCommentNode(reply);
                replyNode.setStyle("-fx-padding: 0 0 0 55;");
                thread.getChildren().add(replyNode);
            }
        }

        return thread;
    }

    @FXML
    private void handleUpvote(ActionEvent event) {
        if (currentSolution == null || currentUser == null) {
            return;
        }

        if (userVote == 1) {
            currentSolution.downvote(currentUser);
            userVote = 0;
        } else if (userVote == -1) {
            currentSolution.upvote(currentUser);
            currentSolution.upvote(currentUser);
            userVote = 1;
        } else {
            currentSolution.upvote(currentUser);
            userVote = 1;
        }

        updateScoreDisplay();
        PostManager.getInstance().save();
    }

    @FXML
    private void handleDownvote(ActionEvent event) {
        if (currentSolution == null || currentUser == null) {
            return;
        }

        if (userVote == -1) {
            currentSolution.upvote(currentUser);
            userVote = 0;
        } else if (userVote == 1) {
            currentSolution.downvote(currentUser);
            currentSolution.downvote(currentUser);
            userVote = -1;
        } else {
            currentSolution.downvote(currentUser);
            userVote = -1;
        }

        updateScoreDisplay();
        PostManager.getInstance().save();
    }

    private void updateScoreDisplay() {
        if (currentSolution == null) {
            return;
        }

        scoreLabel.setText(String.valueOf(currentSolution.getScore()));

        upvoteButton.getStyleClass().remove("vote-selected");
        downvoteButton.getStyleClass().remove("vote-selected");

        if (userVote == 1) {
            upvoteButton.getStyleClass().add("vote-selected");
        } else if (userVote == -1) {
            downvoteButton.getStyleClass().add("vote-selected");
        }
    }

    @FXML
    private void handleAddComment(ActionEvent event) {
        if (currentSolution == null || currentUser == null) {
            return;
        }

        String text = commentField.getText().trim();

        if (text.isEmpty()) {
            return;
        }

        Comment newComment = new Comment(
            UUID.randomUUID(),
            currentUser,
            text,
            currentSolution.getPostId(),
            LocalDate.now()
        );

        currentSolution.addComment(newComment);
        commentField.clear();
        populateComments();

        // Later we can call DataWriter.savePosts() here if you want it to persist.
    }


    @FXML
    private void handleBack(ActionEvent event) {
        try {
            FXMLLoader loader = App.setRootWithLoader("questionPost");
            QuestionsController controller = loader.getController();
            controller.setData(currentUser, selectedQuestion);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }   

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

}

