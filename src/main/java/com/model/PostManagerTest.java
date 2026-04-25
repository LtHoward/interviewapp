/*
Created by Michelle Ihetu
 */
package com.model;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;
import java.util.Date;

public class PostManagerTest {

    private PostManager postManager;
    private User authorUser;
    private User otherUser;
    private QuestionPost questionPost;
    private SolutionPost solutionPost;

    @Before
    public void setUp() {
        postManager = PostManager.getInstance();
        postManager.clearPosts();

        authorUser = new Student(
                UUID.randomUUID(),
                "author_user",
                "author@email.com",
                "password123",
                "Author",
                "User",
                Major.COMPUTER_SCIENCE,
                Year.FRESHMAN,
                "CSCE247",
                "CSCE146",
                SkillLevel.BEGINNER,
                0,
                null,
                new ArrayList<Reward>(),
                LocalDate.now(),
                Role.STUDENT
        );

        otherUser = new Student(
                UUID.randomUUID(),
                "other_user",
                "other@email.com",
                "password456",
                "Other",
                "User",
                Major.COMPUTER_SCIENCE,
                Year.FRESHMAN,
                "CSCE247",
                "CSCE146",
                SkillLevel.BEGINNER,
                0,
                null,
                new ArrayList<Reward>(),
                LocalDate.now(),
                Role.STUDENT
        );

        ArrayList<Comment> comments = new ArrayList<Comment>();
        ArrayList<String> tags = new ArrayList<String>();
        tags.add("java");
        tags.add("linked-list");

        ArrayList<PostContent> contentSections = new ArrayList<PostContent>();
        contentSections.add(new PostContent(ContentType.TEXT, "Sample content"));

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

        ArrayList<PostContent> solutionContent = new ArrayList<PostContent>();
        solutionContent.add(new PostContent(ContentType.TEXT, "Use a while loop and swap pointers."));

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

    // getInstance
    @Test
    public void testGetInstance_returnsSameInstance() {
        PostManager instance1 = PostManager.getInstance();
        PostManager instance2 = PostManager.getInstance();
        assertSame(instance1, instance2);
    }

    // addQuestion
    @Test
    public void testAddQuestion_validPost_returnsTrue() {
        assertTrue(postManager.addQuestion(questionPost));
    }

    @Test
    public void testAddQuestion_postAppearsInList() {
        postManager.addQuestion(questionPost);
        assertTrue(postManager.getAllQuestions().contains(questionPost));
    }

    // addSolution(question, user, solution)
    @Test
    public void testAddSolution_questionNotInSystem_returnsFalse() {
        assertFalse(postManager.addSolution(questionPost, authorUser, solutionPost));
    }

    @Test
    public void testAddSolution_nullSolution_returnsFalse() {
        postManager.addQuestion(questionPost);
        assertFalse(postManager.addSolution(questionPost, authorUser, null));
    }

    @Test
    public void testAddSolution_nullQuestion_returnsFalse() {
        assertFalse(postManager.addSolution(null, authorUser, solutionPost));
    }

    @Test
    public void testAddSolution_validInputs_returnsTrue() {
        postManager.addQuestion(questionPost);
        assertTrue(postManager.addSolution(questionPost, authorUser, solutionPost));
    }

    // addSolution(user, solution)
    @Test
    public void testAddSolutionUserOnly_nullSolution_returnsFalse() {
        assertFalse(postManager.addSolution(authorUser, null));
    }

    @Test
    public void testAddSolutionUserOnly_valid_returnsTrue() {
        assertTrue(postManager.addSolution(authorUser, solutionPost));
    }

    // getQuestion
    @Test
    public void testGetQuestion_matchingTitle_returnsPost() {
        postManager.addQuestion(questionPost);
        ArrayList<QuestionPost> result = postManager.getQuestion("HOW DO I REVERSE A LINKED LIST?");
        assertFalse(result.isEmpty());
        assertTrue(result.contains(questionPost));
    }

    @Test
    public void testGetQuestion_noMatch_returnsEmptyList() {
        postManager.addQuestion(questionPost);
        ArrayList<QuestionPost> result = postManager.getQuestion("Nonexistent question");
        assertTrue(result.isEmpty());
    }

    // getSolution
    @Test
    public void testGetSolution_validCount_returnsSublist() {
        postManager.addSolution(authorUser, solutionPost);
        ArrayList<SolutionPost> result = postManager.getSolution(1);
        assertEquals(1, result.size());
    }

    @Test
    public void testGetSolution_countExceedsSize_returnsEmptyList() {
        ArrayList<SolutionPost> result = postManager.getSolution(5);
        assertTrue(result.isEmpty());
    }

    // editPost
    @Test
    public void testEditPost_authorValidIndex_returnsTrue() {
        postManager.addQuestion(questionPost);
        PostContent newContent = new PostContent(ContentType.TEXT, "Updated content here.");
        assertTrue(postManager.editPost(authorUser, questionPost, newContent, 0));
    }

    @Test
    public void testEditPost_nonAuthor_returnsFalse() {
        postManager.addQuestion(questionPost);
        PostContent newContent = new PostContent(ContentType.TEXT, "Sneaky edit.");
        assertFalse(postManager.editPost(otherUser, questionPost, newContent, 0));
    }

    @Test
    public void testEditPost_postNotInSystem_returnsFalse() {
        PostContent newContent = new PostContent(ContentType.TEXT, "Edit attempt.");
        assertFalse(postManager.editPost(authorUser, questionPost, newContent, 0));
    }

    @Test
    public void testEditPost_outOfBoundsIndex_returnsFalse() {
        postManager.addQuestion(questionPost);
        PostContent newContent = new PostContent(ContentType.TEXT, "Out of bounds edit.");
        assertFalse(postManager.editPost(authorUser, questionPost, newContent, 99));
    }

    // deletePost
    @Test
    public void testDeletePost_authorDeletesQuestion_returnsTrue() {
        postManager.addQuestion(questionPost);
        assertTrue(postManager.deletePost(authorUser, questionPost));
    }

    @Test
    public void testDeletePost_nonAuthor_returnsFalse() {
        postManager.addQuestion(questionPost);
        assertFalse(postManager.deletePost(otherUser, questionPost));
    }

    @Test
    public void testDeletePost_postNotInSystem_returnsFalse() {
        assertFalse(postManager.deletePost(authorUser, questionPost));
    }

    @Test
    public void testDeletePost_postRemovedFromList() {
        postManager.addQuestion(questionPost);
        postManager.deletePost(authorUser, questionPost);
        assertFalse(postManager.getAllQuestions().contains(questionPost));
    }

    // searchQuestions
    @Test
    public void testSearchQuestions_matchesByTitle() {
        postManager.addQuestion(questionPost);
        ArrayList<QuestionPost> results = postManager.searchQuestions("reverse");
        assertTrue(results.contains(questionPost));
    }

    @Test
    public void testSearchQuestions_matchesByTag() {
        postManager.addQuestion(questionPost);
        ArrayList<QuestionPost> results = postManager.searchQuestions("java");
        assertTrue(results.contains(questionPost));
    }

    @Test
    public void testSearchQuestions_noMatch_returnsEmptyList() {
        postManager.addQuestion(questionPost);
        ArrayList<QuestionPost> results = postManager.searchQuestions("python");
        assertTrue(results.isEmpty());
    }

    @Test
    public void testSearchQuestions_nullKeyword_returnsEmptyList() {
        postManager.addQuestion(questionPost);
        ArrayList<QuestionPost> results = postManager.searchQuestions(null);
        assertTrue(results.isEmpty());
    }

    @Test
    public void testSearchQuestions_emptyKeyword_returnsEmptyList() {
        postManager.addQuestion(questionPost);
        ArrayList<QuestionPost> results = postManager.searchQuestions("");
        assertTrue(results.isEmpty());
    }

    // getQuestionById
    @Test
    public void testGetQuestionById_validId_returnsPost() {
        postManager.addQuestion(questionPost);
        UUID id = questionPost.getPostId();
        assertEquals(questionPost, postManager.getQuestionById(id));
    }

    @Test
    public void testGetQuestionById_unknownId_returnsNull() {
        assertNull(postManager.getQuestionById(UUID.randomUUID()));
    }

    // addComment
    @Test
    public void testAddComment_validPostAndComment_returnsTrue() {
        postManager.addQuestion(questionPost);
        Comment comment = new Comment(
                UUID.randomUUID(),
                authorUser,
                "Great question!",
                questionPost.getPostId(),
                LocalDate.now()
        );
        assertTrue(postManager.addComment(questionPost, comment));
    }

    @Test
    public void testAddComment_nullPost_returnsFalse() {
        Comment comment = new Comment(
                UUID.randomUUID(),
                authorUser,
                "Comment on nothing.",
                questionPost.getPostId(),
                LocalDate.now()
        );
        assertFalse(postManager.addComment(null, comment));
    }

    @Test
    public void testAddComment_nullComment_returnsFalse() {
        postManager.addQuestion(questionPost);
        assertFalse(postManager.addComment(questionPost, null));
    }

    @Test
    public void testAddComment_postNotInSystem_returnsFalse() {
        Comment comment = new Comment(
                UUID.randomUUID(),
                authorUser,
                "Post not registered.",
                questionPost.getPostId(),
                LocalDate.now()
        );
        assertFalse(postManager.addComment(questionPost, comment));
    }

    // getComments
    @Test
    public void testGetComments_returnsAddedComment() {
        postManager.addQuestion(questionPost);
        Comment comment = new Comment(
                UUID.randomUUID(),
                authorUser,
                "Nice question!",
                questionPost.getPostId(),
                LocalDate.now()
        );
        postManager.addComment(questionPost, comment);
        assertTrue(postManager.getComments(questionPost).contains(comment));
    }

    @Test
    public void testGetComments_nullPost_returnsEmptyList() {
        assertTrue(postManager.getComments(null).isEmpty());
    }

    // save
    @Test
    public void testSave_returnsTrue() {
        assertTrue(postManager.save());
    }

    // clearPosts
    @Test
    public void testClearPosts_removesAllPosts() {
        postManager.addQuestion(questionPost);
        postManager.addSolution(authorUser, solutionPost);
        postManager.clearPosts();
        assertTrue(postManager.getAllQuestions().isEmpty());
        assertTrue(postManager.getAllSolutions().isEmpty());
    }
}