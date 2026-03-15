package com.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public abstract class Post {

    private UUID postId;
    private String title;
    private User author;
    private Date createdAt;
    private ArrayList<Comment> comments;
    private ArrayList<String> tags;
    private ArrayList<PostContent> contentSections;
    private int score;

    public Post(UUID postId, String title, User author, Date createdAt,
                ArrayList<Comment> comments, ArrayList<String> tags,
                ArrayList<PostContent> contentSections, int score) {

        this.postId = postId;
        this.title = title;
        this.author = author;
        this.createdAt = createdAt;
        this.comments = comments;
        this.tags = tags;
        this.contentSections = contentSections;
        this.score = score;
    }

    public Post(User author) {
        this(UUID.randomUUID(),
            "Untitled",
            author,
             new Date(),
             new ArrayList<>(),
             new ArrayList<>(),
             new ArrayList<>(),
             0);
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public UUID getPostId() {
        return postId;
    }

    public String getTitle() {
        return title;
    }


    public ArrayList<Comment> getComments() {
        return comments;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public String getType() {
        return this instanceof QuestionPost ? "QUESTION" : "SOLUTION";
    }

    public User getAuthor() {
        return author;
    }

    public ArrayList<PostContent> getContentSections() {
        return contentSections;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public int getScore() {
        return score;
    }

    public int upvote(User user) {
        return ++score;
    }

    public int downvote(User user) {
        return --score;
    }

    public int getScore(int upVote, int downVote) {
        return upVote - downVote;
    }
}