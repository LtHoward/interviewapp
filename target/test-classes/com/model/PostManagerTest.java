package com.model;  

import java.util.ArrayList;
import java.util.UUID;


import java.util.*;

import org.junit.Test;



public class PostManagerTest{

    private PostManager postManager;
    private User authorUser;
    private User otherUser;
    private QuestionPost questionPost;
    private SolutionPost solutionPost;

    @BeforeEach
    void setUp() {
        // Reset singleton so each test starts with a clean state
        postManager = PostManager.getInstance();
        postManager.clearPosts();

        //Create test users
        authorUser = new User("author_user", "password123", "author@email.com");
        otherUser  = new User("other_user",  "password456", "other@email.com");
 QuestionPost questionPost = new QuestionPost(
                authorUser,
                "How do I reverse a linked list?",
                new ArrayList<>(Arrays.asList(new PostContent("Sample content"))),
                new ArrayList<>(Arrays.asList("java", "linked-list"))
        );

        solutionPost = new SolutionPost(
                authorUser,
                new ArrayList<>(Arrays.asList(new PostContent("Use a while loop and swap pointers.")))
        );
 
    }

}