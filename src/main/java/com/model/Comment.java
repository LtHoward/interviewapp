package com.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class Comment {
    private User author;
    private LocalDate createdAt;
    private String content;
    private UUID commentId;
    private UUID postId;
    private ArrayList<Comment> reply;

    public Comment (User author, String content, UUID postId){
        this.author = author;
        this.createdAt = LocalDate.now();
        this.content = content;
        this.commentId = UUID.randomUUID();
        this.postId = postId;
        this.reply = new ArrayList<>();
    }

    public User getAuthor() {
        return author;
    }
    
    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public String getContent() {
        return content;
    }

    public UUID getCommentId() {
        return commentId;
    }

    public UUID getPostId() {
        return postId;
    }

    public ArrayList<Comment> getReply(){
        return reply;
    }
}
