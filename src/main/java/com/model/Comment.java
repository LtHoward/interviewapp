package com.model;

import java.time.LocalDate;
import java.util.UUID;

public class Comment {
    private User author;
    private LocalDate createdAt;
    private String content;
    private UUID commentId;
    private UUID postId;

    public Comment (User author, String content, UUID postId){
        this.author = author;
        this.createdAt = LocalDate.now();
        this.content = content;
        this.commentId = UUID.randomUUID();
        this.postId = postId;
    }

    public Comment getReply(){
        return null;
    }
}
