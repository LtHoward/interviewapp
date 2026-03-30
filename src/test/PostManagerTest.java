package com.model;  
import java.util.ArrayList;
import java.util.UUID;
import java.util.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Date;


public class PostManagerTest{

    private PostManager postManager;
    private User authorUser;
    private User otherUser;
    private QuestionPost questionPost;
    private SolutionPost solutionPost;

    @Before
public void setUp() {
    postManager = PostManager.getInstance();
    postManager.clearPosts();

    authorUser = new User("author_user", "password123", "author@email.com");
    otherUser  = new User("other_user",  "password456", "other@email.com");

    ArrayList<Comment> comments = new ArrayList<Comment>();
    ArrayList<String> tags = new ArrayList<String>();
    tags.add("java");
    tags.add("linked-list");

    ArrayList<PostContent> contentSections = new ArrayList<PostContent>();
contentSections.add(new PostContent(ContentType.TEXT, "Sample content"));

ArrayList<PostContent> solutionContent = new ArrayList<PostContent>();
solutionContent.add(new PostContent(ContentType.TEXT, "Use a while loop and swap pointers."));

    questionPost = new QuestionPost(
            UUID.randomUUID(),
            "How do I reverse a linked list?",
            authorUser,
            new Date(),
            comments,
            tags,
            contentSections,
            0,
            Difficulty.EASY,
            "Think about pointers"
    );

solutionPost = new SolutionPost(
            UUID.randomUUID(),                        
            "Reverse linked list solution",           
            authorUser,                              
            new Date(),                               
            new ArrayList<Comment>(),                
            new ArrayList<String>(),                  
            solutionContent,                          
            0,                                      
            1,                                        
            questionPost.getPostId()                  
    );
}
}
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