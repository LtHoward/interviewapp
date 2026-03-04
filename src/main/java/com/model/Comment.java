package com.model;

import java.util.Date;
import java.util.UUID;

public class Comment {
    private User author;
    private Date createdAt;
    private String content;
    private UUID commentId;
    private UUID postId;

    public Comment (User author, String content, UUID postId){

    }

    public Comment getReply(){
        return null;
    }
}
