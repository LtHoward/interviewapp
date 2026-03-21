package com.model;

public class PostContent {
    private ContentType type;
    private Object content;

    public PostContent(ContentType type, String content) {
        this.type = type;
        this.content = content;
    }

    public ContentType getType() {
        return type;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content)
    {
        this.content = content;

    }
}