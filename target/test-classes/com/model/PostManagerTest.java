package com.model;  
import java.util.ArrayList;
import java.util.UUID;
import java.util.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


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
        
    //getInstance
    @Test
    void testGetInstance_returnsSameInstance() {
        PostManager instance1 = PostManager.getInstance();
        PostManager instance2 = PostManager.getInstance();
        assertSame(instance1 , instance2);
    }

    // addQuestion
    @Test
    void testAddQuestion_validPost_returnsTrue() {
        assertTrue(postManager.addQuestion(questionPost));
    }

    @Test
    void testAddQuestion_postAppearsInList() {
        postManager.addQuestion(questionPost);
        assertTrue(postManager.getAllQuestions().contains(questionPost));
    }


    }

}