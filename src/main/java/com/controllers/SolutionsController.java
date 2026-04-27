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
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class SolutionsController {

    private int userVote = 0;
    private User currentUser;
    private SolutionPost currentSolution;
    private QuestionPost parentQuestion;

    @FXML private Label titleLabel;
    @FXML private Label authorLabel;
    @FXML private Label tagsLabel;
    @FXML private Label scoreLabel;
    @FXML private Label commentsCountLabel;

    @FXML private Button upvoteButton;
    @FXML private Button downvoteButton;

    @FXML private VBox contentContainer;
    @FXML private VBox commentsContainer;
    @FXML private TextField commentField;

    public void setData(User user, SolutionPost solution, QuestionPost question) {
        this.currentUser = user;
        this.currentSolution = solution;
        this.parentQuestion = question;
        populateSolution();
    }

    private void populateSolution() {
        if (currentSolution == null) {
            return;
        }

        titleLabel.setText(currentSolution.getTitle());

        if (currentSolution.getAuthor() != null) {
            authorLabel.setText("By " + currentSolution.getAuthor().getUsername());
        } else {
            authorLabel.setText("By Unknown");
        }

        if (currentSolution.getTags() == null || currentSolution.getTags().isEmpty()) {
            tagsLabel.setText("No tags");
        } else {
            tagsLabel.setText(String.join(", ", currentSolution.getTags()));
        }

        updateScoreDisplay();
        populateContent();
        populateComments();
    }

    private void populateContent() {
        contentContainer.getChildren().clear();

        if (currentSolution.getContentSections() == null) {
            return;
        }

        for (PostContent section : currentSolution.getContentSections()) {
            if (section == null || section.getType() == null || section.getContent() == null) {
                continue;
            }

            ContentType type = section.getType();
            String value = section.getContent().toString();

            switch (type) {
                case TEXT:
                    TextFlow formattedText = createFormattedText(value);
                    formattedText.setMaxWidth(740);
                    contentContainer.getChildren().add(formattedText);
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
                    contentContainer.getChildren().add(imageLabel);
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

    private void populateComments() {
        commentsContainer.getChildren().clear();

        int count = countCommentsAndReplies(currentSolution.getComments());
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
        thread.setSpacing(6);
        thread.getStyleClass().add("comment-thread");

        HBox row = new HBox();
        row.setSpacing(14);
        row.getStyleClass().add("comment-row");

        StackPane avatar = createAvatar();

        VBox textBox = new VBox();
        textBox.setSpacing(4);
        textBox.setPrefWidth(470);
        textBox.setMaxWidth(470);

        String username = comment.getAuthor() != null ? comment.getAuthor().getUsername() : "Unknown";

        Label usernameLabel = new Label(username);
        usernameLabel.getStyleClass().add("comment-username");

        Label dateLabel = new Label(comment.getCommentDate() != null ? comment.getCommentDate().toString() : "");
        dateLabel.getStyleClass().add("comment-date");

        HBox header = new HBox(10, usernameLabel, dateLabel);

        Label contentLabel = new Label(comment.getContent());
        contentLabel.setWrapText(true);
        contentLabel.getStyleClass().add("comment-text");

        int replyCount = comment.getReply() == null ? 0 : comment.getReply().size();

        Button repliesButton = new Button("View " + replyCount + " Replies");
        repliesButton.getStyleClass().add("comment-action-button");

        Button replyButton = new Button("Reply");
        replyButton.getStyleClass().add("comment-action-button");

        HBox actions = new HBox();
        actions.setSpacing(18);
        actions.getChildren().addAll(repliesButton, replyButton);

        if (comment.getAuthor() != null
                && currentUser != null
                && comment.getAuthor().getId().equals(currentUser.getId())) {

            Button deleteButton = new Button("Delete");
            deleteButton.getStyleClass().add("comment-action-button");

            deleteButton.setOnAction(e -> {
                deleteComment(currentSolution.getComments(), comment);
                populateComments();
                PostManager.getInstance().save();
            });

            actions.getChildren().add(deleteButton);
        }

        textBox.getChildren().addAll(header, contentLabel, actions);
        row.getChildren().addAll(avatar, textBox);

        VBox replyContainer = new VBox();
        replyContainer.setSpacing(10);
        replyContainer.getStyleClass().add("reply-container");
        replyContainer.setVisible(false);
        replyContainer.setManaged(false);

        if (comment.getReply() != null) {
            for (Comment reply : comment.getReply()) {
                VBox replyNode = createCommentNode(reply);
                replyNode.setStyle("-fx-padding: 0 0 0 65;");
                replyContainer.getChildren().add(replyNode);
            }
        }

        repliesButton.setOnAction(e -> {
            boolean showing = replyContainer.isVisible();
            replyContainer.setVisible(!showing);
            replyContainer.setManaged(!showing);
            repliesButton.setText((showing ? "View " : "Hide ") + replyCount + " Replies");
        });

        replyButton.setOnAction(e -> {
            if (textBox.lookup(".reply-input") != null) {
                return;
            }

            TextField replyField = new TextField();
            replyField.getStyleClass().addAll("pill-field", "reply-input");
            replyField.setPromptText("Write a reply...");

            replyField.setOnAction(replyEvent -> {
                String replyText = replyField.getText().trim();

                if (replyText.isEmpty() || currentUser == null || currentSolution == null) {
                    return;
                }

                Comment reply = new Comment(
                    UUID.randomUUID(),
                    currentUser,
                    replyText,
                    currentSolution.getPostId(),
                    LocalDate.now()
                );

                comment.addReply(reply);
                populateComments();
                PostManager.getInstance().save();
            });

            textBox.getChildren().add(replyField);
            replyField.requestFocus();
        });

        thread.getChildren().addAll(row, replyContainer);
        return thread;
    }

    private StackPane createAvatar() {
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
        return avatar;
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
        PostManager.getInstance().save();
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
    private void handleBack(ActionEvent event) {
        try {
            FXMLLoader loader = App.setRootWithLoader("questionPost");
            QuestionsController controller = loader.getController();
            controller.setData(currentUser, parentQuestion);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    private boolean deleteComment(ArrayList<Comment> comments, Comment target) {
        if (comments == null || target == null) {
            return false;
        }

        if (comments.remove(target)) {
            return true;
        }

        for (Comment comment : comments) {
            if (deleteComment(comment.getReply(), target)) {
                return true;
            }
        }

        return false;
    }

    private TextFlow createFormattedText(String raw) {
        TextFlow flow = new TextFlow();
        flow.getStyleClass().add("formatted-text");

        boolean bold = false;
        boolean italic = false;
        boolean underline = false;

        StringBuilder buffer = new StringBuilder();

        for (int i = 0; i < raw.length(); i++) {
            if (i + 1 < raw.length() && raw.substring(i, i + 2).equals("**")) {
                addStyledText(flow, buffer.toString(), bold, italic, underline);
                buffer.setLength(0);
                bold = !bold;
                i++;
            } else if (raw.charAt(i) == '*') {
                addStyledText(flow, buffer.toString(), bold, italic, underline);
                buffer.setLength(0);
                italic = !italic;
            } else if (raw.startsWith("<u>", i)) {
                addStyledText(flow, buffer.toString(), bold, italic, underline);
                buffer.setLength(0);
                underline = true;
                i += 2;
            } else if (raw.startsWith("</u>", i)) {
                addStyledText(flow, buffer.toString(), bold, italic, underline);
                buffer.setLength(0);
                underline = false;
                i += 3;
            } else {
                buffer.append(raw.charAt(i));
            }
        }

        addStyledText(flow, buffer.toString(), bold, italic, underline);
        return flow;
    }

    private void addStyledText(TextFlow flow, String textValue, boolean bold, boolean italic, boolean underline) {
        if (textValue == null || textValue.isEmpty()) {
            return;
        }

        Text text = new Text(textValue);
        text.setUnderline(underline);

        String style = "-fx-fill: white; -fx-font-size: 16px;";

        if (bold) {
            style += "-fx-font-weight: bold;";
        }

        if (italic) {
            style += "-fx-font-style: italic;";
        }

        text.setStyle(style);
        flow.getChildren().add(text);
    }
}