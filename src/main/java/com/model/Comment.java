package com.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class Comment {
    private User commentAuthor;
    private LocalDate commentDate;
    private String commentContent;
    private UUID commentId;
    private UUID postId;
    private ArrayList<Comment> reply;

    public Comment (UUID commentId, User commentAuthor, String commentContent, UUID postId, LocalDate commentDate) {
        this.commentAuthor = commentAuthor;
        this.commentDate = commentDate;
        this.commentContent = commentContent;
        this.commentId = commentId;
        this.postId = postId;
        this.reply = new ArrayList<>();
    }

    public User getAuthor() {
        return commentAuthor;
    }
    
    public LocalDate getCommentDate() {
        return commentDate;
    }

    public String getContent() {
        return commentContent;
    }

    public UUID getCommentId() {
        return commentId;
    }



    public ArrayList<Comment> getReply(){
        return reply;
    }

}

