package com.model;

<<<<<<< HEAD
import java.time.LocalDateTime;
import java.util.UUID;

public class Comment {
    private User author;
    private LocalDateTime createdAt;
    private String content;
=======
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class Comment {
    private User commentAuthor;
    private LocalDate commentDate;
    private String commentContent;
>>>>>>> 19aad968de75e7f99a2270d9606f1777b054abb6
    private UUID commentId;
    private UUID postId;
    private ArrayList<Comment> reply;

<<<<<<< HEAD
    public Comment (User author, String content, UUID postId){
        this.author = author;
        this.createdAt = LocalDateTime.now();
        this.content = content;
        this.commentId = UUID.randomUUID();
=======
    public Comment (UUID commentId, User commentAuthor, String commentContent, UUID postId, LocalDate commentDate) {
        this.commentAuthor = commentAuthor;
        this.commentDate = commentDate;
        this.commentContent = commentContent;
        this.commentId = commentId;
>>>>>>> 19aad968de75e7f99a2270d9606f1777b054abb6
        this.postId = postId;
        this.reply = new ArrayList<>();
    }

<<<<<<< HEAD

    public UUID getpostId() {
        return postId;
    }

    public Comment getReply() {
        return null;
    }

    public String getContent() {
        return content;
    } 
=======
    public User getAuthor() {
        return commentAuthor;
    }
    
    public LocalDate getCommentDate() {
        return commentDate;
    }

    public String getContent() {
        return commentContent;
    }
>>>>>>> 19aad968de75e7f99a2270d9606f1777b054abb6

    public UUID getCommentId() {
        return commentId;
    }

    public LocalDateTime getCreatedAt() {
        LocalDateTime today = LocalDateTime.now();

        return createdAt;
    }

<<<<<<< HEAD
    public User getAuthor() {
        return author;
=======
    public ArrayList<Comment> getReply(){
        return reply;
>>>>>>> 19aad968de75e7f99a2270d9606f1777b054abb6
    }

}

