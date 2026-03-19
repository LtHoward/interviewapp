package com.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Comment {
    private User author;
    private LocalDateTime createdAt;
    private String content;
    private UUID commentId;
    private UUID postId;

    public Comment (User author, String content, UUID postId){
        this.author = author;
        this.createdAt = LocalDateTime.now();
        this.content = content;
        this.commentId = UUID.randomUUID();
        this.postId = postId;
    }


    public UUID getpostId() {
        return postId;
    }

    public Comment getReply() {
        return null;
    }

    public String getContent() {
        return content;
    } 

    public UUID getCommentId() {
        return commentId;
    }

    public LocalDateTime getCreatedAt() {
        LocalDateTime today = LocalDateTime.now();

        return createdAt;
    }

    public User getAuthor() {
        return author;
    }

}

