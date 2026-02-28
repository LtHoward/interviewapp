package com.model;

import java.util.Date;
import java.util.UUID;

public class Comment {
    private User author;
    private Date createdAt;
    private String content;
    private UUID id;
    private UUID postId;

    public Comment (User author, String content){

    }

    public Comment getReply(){
        return null;
    }
}
