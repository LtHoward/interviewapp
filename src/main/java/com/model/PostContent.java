package com.model;

/**
 * Class to represent a the content of a post. Manages to the type, content, and objects of the content
 */
public class PostContent {
    private ContentType type;
    private Object content;

    /**
     * Construtor for the postContent class with the given parameters 
     * @param type the type of the post
     * @param content the content of the post
     */
    public PostContent(ContentType type, String content) {
        this.type = type;
        this.content = content;
    }

    /**
     * Method to get the type of the post
     * @return the type
     */
    public ContentType getType() {
        return type;
    }

    /**
     * Method to get the content of the post
     * @return the content
     */
    public Object getContent() {
        return content;
    }

    /**
     * Method to set the content
     * @param content the content of the post to be set
     */
    public void setContent(Object content)
    {
        this.content = content;

    }
}