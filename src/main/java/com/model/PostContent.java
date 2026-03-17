package com.model;

public class PostContent {
    private ContentType type;
    private String content;

    public PostContent(ContentType type, String content) {
        this.type = type;
        this.content = content;
    }

    public ContentType getType() {
        return type;
    }

    public String getContent() {
        return content;
    }
}