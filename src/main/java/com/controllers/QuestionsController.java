package com.controllers;

import java.time.LocalDate;
import java.util.UUID;

import com.interviewapp.App;
import com.model.Comment;
import com.model.ContentType;
import com.model.PostContent;
import com.model.QuestionPost;
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

public class QuestionsController {

    private User currentUser;
    private QuestionPost currentQuestion;

    @FXML
    private Label titleLabel;

    @FXML
    private Label authorLabel;

    @FXML
    private Label difficultyLabel;

    @FXML
    private Label tagsLabel;

    @FXML
    private Label scoreLabel;

    @FXML
    private Label commentsCountLabel;

    @FXML
    private VBox contentContainer;

    @FXML
    private VBox commentsContainer;

    @FXML
    private TextField commentField;

    public void setData(User user, QuestionPost question) {
        this.currentUser = user;
        this.currentQuestion = question;
        populateQuestion();
    }

    private void populateQuestion() {
        if (currentQuestion == null) {
            return;
        }

        titleLabel.setText(currentQuestion.getTitle());

        if (currentQuestion.getAuthor() != null) {
            authorLabel.setText("By " + currentQuestion.getAuthor().getUsername());
        } else {
            authorLabel.setText("By Unknown");
        }

        difficultyLabel.setText(formatEnum(currentQuestion.getDifficulty()));
        tagsLabel.setText(String.join(", ", currentQuestion.getTags()));
        scoreLabel.setText(String.valueOf(currentQuestion.getScore()));

        populateContent();
        populateComments();
    }

    private void populateContent() {
        contentContainer.getChildren().clear();

        for (PostContent section : currentQuestion.getContentSections()) {
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
                    codeBlock.getStyleClass().add("question-code-block");

                    Label codeLabel = new Label(value);
                    codeLabel.setWrapText(true);
                    codeLabel.getStyleClass().add("question-code-text");

                    codeBlock.getChildren().add(codeLabel);
                    contentContainer.getChildren().add(codeBlock);
                    break;

                case IMAGE:
                    ImageView imageView = new ImageView();
                    imageView.setFitWidth(350);
                    imageView.setFitHeight(200);
                    imageView.setPreserveRatio(true);
                    imageView.getStyleClass().add("question-image-block");

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
                    videoBlock.getStyleClass().add("question-video-block");

                    Label videoLabel = new Label("Video: " + value);
                    videoLabel.getStyleClass().add("comment-text");

                    videoBlock.getChildren().add(videoLabel);
                    contentContainer.getChildren().add(videoBlock);
                    break;
            }
        }

        Label hintTitle = new Label("Hint:");
        hintTitle.getStyleClass().add("comment-username");

        Label hintText = new Label(currentQuestion.getHint());
        hintText.setWrapText(true);
        hintText.getStyleClass().add("comment-text");

        contentContainer.getChildren().add(hintTitle);
        contentContainer.getChildren().add(hintText);
    }

    private void populateComments() {
        commentsContainer.getChildren().clear();

        int count = currentQuestion.getComments() == null ? 0 : currentQuestion.getComments().size();

        commentsCountLabel.setText(count + " Comments");

        if (count == 0) {
            Label empty = new Label("No comments yet.");
            empty.getStyleClass().add("comment-text");
            commentsContainer.getChildren().add(empty);
            return;
        }

        for (Comment comment : currentQuestion.getComments()) {
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
    private void handleAddComment(ActionEvent event) {
        if (currentQuestion == null || currentUser == null) {
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
            currentQuestion.getPostId(),
            LocalDate.now()
        );

        currentQuestion.addComment(newComment);
        commentField.clear();
        populateComments();

        // Later we can call DataWriter.savePosts() here if you want it to persist.
    }

    @FXML
    private void handleBack(ActionEvent event) {
        try {
            FXMLLoader loader = App.setRootWithLoader("search");
            SearchController controller = loader.getController();
            controller.setUser(currentUser);
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