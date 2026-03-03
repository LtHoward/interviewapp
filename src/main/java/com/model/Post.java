package com.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public abstract class Post {

    private UUID postId;
    private User author;
    private Date createdAt;
    private ArrayList<Comment> comments;
    private ArrayList<String> tags;
    private ArrayList<PostContent> contentSections;
    private int score;

    public Post(UUID postId, User author, Date createdAt,
                ArrayList<Comment> comments, ArrayList<String> tags,
                ArrayList<PostContent> contentSections, int score) {

        this.postId = postId;
        this.author = author;
        this.createdAt = createdAt;
        this.comments = comments;
        this.tags = tags;
        this.contentSections = contentSections;
        this.score = score;
    }

    public Post(User author) {
        this(UUID.randomUUID(),
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

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public User getAuthor() {
        return author;
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