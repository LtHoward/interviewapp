package com.model;

import java.util.ArrayList;
import java.util.UUID;

public class QuestionPost extends Post {
    
    public class QuestionPost extends Post {

    private String title;
    private Difficulty difficulty;
    private String hint;

    public QuestionPost(UUID id,
                        User author,
                        Date createdAt,
                        ArrayList<Comment> comments,
                        ArrayList<String> tags,
                        ArrayList<PostContent> contentSections,
                        int score,
                        String title,
                        Difficulty difficulty,
                        String hint) {

        super(id, author, createdAt, comments, tags, contentSections, score);
    }
}

}
