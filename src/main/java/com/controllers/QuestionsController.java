package com.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
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
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class QuestionsController {

    private int userVote = 0;
    private User currentUser;
    private QuestionPost currentQuestion;

    @FXML private Label titleLabel;
    @FXML private Button upvoteButton;
    @FXML private Button downvoteButton;
    @FXML private Label authorLabel;
    @FXML private Label difficultyLabel;
    @FXML private Label tagsLabel;
    @FXML private Label scoreLabel;
    @FXML private Label commentsCountLabel;
    @FXML private VBox contentContainer;
    @FXML private VBox commentsContainer;
    @FXML private TextField commentField;

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

        if (currentQuestion.getTags() == null || currentQuestion.getTags().isEmpty()) {
            tagsLabel.setText("No tags");
        } else {
            tagsLabel.setText(String.join(", ", currentQuestion.getTags()));
        }

        updateScoreDisplay();
        populateContent();
        populateComments();
    }

    private void populateContent() {
        contentContainer.getChildren().clear();

        if (currentQuestion.getContentSections() != null) {
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
                        codeBlock.setAlignment(Pos.CENTER_LEFT);

                        Label codeLabel = new Label(value);
                        codeLabel.setWrapText(true);
                        codeLabel.getStyleClass().add("question-code-text");
                        codeLabel.setAlignment(Pos.CENTER_LEFT);
                        codeLabel.setMaxWidth(Double.MAX_VALUE);

                        codeBlock.getChildren().add(codeLabel);
                        contentContainer.getChildren().add(codeBlock);
                        break;

                    case IMAGE:
                        Label imageLabel = new Label("Image: " + value);
                        imageLabel.getStyleClass().add("comment-text");

                        ImageView imageView = new ImageView();
                        imageView.setFitWidth(350);
                        imageView.setFitHeight(200);
                        imageView.setPreserveRatio(true);
                        imageView.getStyleClass().add("question-image-block");

                        contentContainer.getChildren().addAll(imageLabel, imageView);
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

                    default:
                        break;
                }
            }
        }

        Label hintTitle = new Label("Hint:");
        hintTitle.getStyleClass().add("comment-username");

        Label hintText = new Label(currentQuestion.getHint() == null ? "No hint available." : currentQuestion.getHint());
        hintText.setWrapText(true);
        hintText.getStyleClass().add("comment-text");

        contentContainer.getChildren().addAll(hintTitle, hintText);
    }

    private void populateComments() {
        commentsContainer.getChildren().clear();

        int count = countCommentsAndReplies(currentQuestion.getComments());
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
        avatar.setMinWidth(45);
        avatar.setMinHeight(45);
        avatar.setMaxWidth(45);
        avatar.setMaxHeight(45);
        avatar.getStyleClass().add("comments-user-avatar");

        ImageView avatarImage = new ImageView(
            new Image(getClass().getResource("/com/interviewapp/images/DefaultIcon.png").toExternalForm())
        );
        avatarImage.setFitWidth(45);
        avatarImage.setFitHeight(45);
        avatarImage.setPreserveRatio(true);
        avatar.getChildren().add(avatarImage);

        VBox textBox = new VBox();
        textBox.setSpacing(6);

        HBox header = new HBox();
        header.setSpacing(10);

        String username = comment.getAuthor() != null
            ? comment.getAuthor().getUsername()
            : "Unknown";

        Label usernameLabel = new Label(username);
        usernameLabel.getStyleClass().add("comment-username");

        Label dateLabel = new Label(comment.getCommentDate() != null ? comment.getCommentDate().toString() : "");
        dateLabel.getStyleClass().add("comment-date");

        header.getChildren().addAll(usernameLabel, dateLabel);

        Label contentLabel = new Label(comment.getContent());
        contentLabel.setWrapText(true);
        contentLabel.getStyleClass().add("comment-text");

        int replyCount = comment.getReply() == null ? 0 : comment.getReply().size();

        Button repliesButton = new Button("View " + replyCount + " Replies");
        repliesButton.getStyleClass().add("comment-action-button");

        Button replyButton = new Button("Reply");
        replyButton.getStyleClass().add("comment-action-button");

        replyButton.setOnAction(e -> {
            if (textBox.lookup(".reply-input") != null) {
                return;
            }

            TextField replyField = new TextField();
            replyField.getStyleClass().addAll("pill-field", "reply-input");
            replyField.setPromptText("Write a reply...");

            replyField.setOnAction(replyEvent -> {
                String replyText = replyField.getText().trim();

                if (replyText.isEmpty() || currentUser == null || currentQuestion == null) {
                    return;
                }

                Comment reply = new Comment(
                    UUID.randomUUID(),
                    currentUser,
                    replyText,
                    currentQuestion.getPostId(),
                    LocalDate.now()
                );

                comment.addReply(reply);
                populateComments();
                PostManager.getInstance().save();
            });

            textBox.getChildren().add(replyField);
            replyField.requestFocus();
        });

        HBox actions = new HBox();
        actions.setSpacing(18);
        actions.getChildren().addAll(repliesButton, replyButton);

        textBox.getChildren().addAll(header, contentLabel, actions);
        row.getChildren().addAll(avatar, textBox);

        VBox replyContainer = new VBox();
        replyContainer.setSpacing(14);
        replyContainer.getStyleClass().add("reply-container");
        replyContainer.setVisible(false);
        replyContainer.setManaged(false);

        if (comment.getReply() != null) {
            for (Comment reply : comment.getReply()) {
                VBox replyNode = createCommentNode(reply);
                replyNode.setStyle("-fx-padding: 0 0 0 55;");
                replyContainer.getChildren().add(replyNode);
            }
        }

        repliesButton.setOnAction(e -> {
            boolean showing = replyContainer.isVisible();
            replyContainer.setVisible(!showing);
            replyContainer.setManaged(!showing);
            repliesButton.setText((showing ? "View " : "Hide ") + replyCount + " Replies");
        });

        thread.getChildren().addAll(row, replyContainer);
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
        PostManager.getInstance().save();
    }

    @FXML
    private void handleUpvote(ActionEvent event) {
        if (currentQuestion == null || currentUser == null) {
            return;
        }

        if (userVote == 1) {
            currentQuestion.downvote(currentUser);
            userVote = 0;
        } else if (userVote == -1) {
            currentQuestion.upvote(currentUser);
            currentQuestion.upvote(currentUser);
            userVote = 1;
        } else {
            currentQuestion.upvote(currentUser);
            userVote = 1;
        }

        updateScoreDisplay();
        PostManager.getInstance().save();
    }

    @FXML
    private void handleDownvote(ActionEvent event) {
        if (currentQuestion == null || currentUser == null) {
            return;
        }

        if (userVote == -1) {
            currentQuestion.upvote(currentUser);
            userVote = 0;
        } else if (userVote == 1) {
            currentQuestion.downvote(currentUser);
            currentQuestion.downvote(currentUser);
            userVote = -1;
        } else {
            currentQuestion.downvote(currentUser);
            userVote = -1;
        }

        updateScoreDisplay();
        PostManager.getInstance().save();
    }

    private void updateScoreDisplay() {
        if (currentQuestion == null) {
            return;
        }

        scoreLabel.setText(String.valueOf(currentQuestion.getScore()));

        upvoteButton.getStyleClass().remove("vote-selected");
        downvoteButton.getStyleClass().remove("vote-selected");

        if (userVote == 1) {
            upvoteButton.getStyleClass().add("vote-selected");
        } else if (userVote == -1) {
            downvoteButton.getStyleClass().add("vote-selected");
        }
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

    private int countCommentsAndReplies(ArrayList<Comment> comments) {
        if (comments == null) {
            return 0;
        }

        int count = 0;

        for (Comment comment : comments) {
            count++;

            if (comment.getReply() != null) {
                count += countCommentsAndReplies(comment.getReply());
            }
        }

        return count;
    }

    /**
     * Event handler for the "View Solutions" button. 
     * It finds the solution associated with the current question
     * @param event The ActionEvent triggered by clicking the "View Solutions" button
     */
    @FXML
    private void handleViewSolutions(ActionEvent event) {
        if (currentQuestion == null || currentUser == null) return;
        
        SolutionPost matchingSolution = findSolutionForQuestion(currentQuestion.getPostId());

        if (matchingSolution == null) {
            System.out.println("No solution found for question: " + currentQuestion.getTitle());
            return;
        }

        try {
            FXMLLoader loader = App.setRootWithLoader("solutionPost");
            SolutionsController controller = loader.getController();
            controller.setData(currentUser, matchingSolution, currentQuestion);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Helper method to find the solution post associated with a given question ID.
     * @param questionId The UUID of the question for which we want to find the solution.
     * @return The SolutionPost associated with the question, or null if not found.
     */
    private SolutionPost findSolutionForQuestion(UUID questionId) {
        for (SolutionPost solution : PostManager.getInstance().getAllSolutions()) {
            if (solution.getQuestionId() != null && solution.getQuestionId().equals(questionId)) {
                return solution;
            }
        }
        return null;
    }
}