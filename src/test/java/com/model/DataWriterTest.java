package com.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import java.util.ArrayList;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Dorian Rhone
 * Test class for DataWriter
 */

/*
 * AI was used to generate test method bodies.
 *
 * +--------------------------------+-----------------------------------------------------------+
 * | Test                           | Reasoning                                                 |
 * +--------------------------------+-----------------------------------------------------------+
 * |testSaveUserWithExistingUsers.  | Exisiting users should be saved correctly, while adding   |
 * |                                | a new user to the JSON file.                              |
 * |testSaveQuestionPost            | Question Post should be able to be made and saved.        |
 * |testStudentMajorIsSavedCorrectly| Student major should be saved correctly.                  |
 * |testSaveSolutionPostWithComment | Solution Post with Comment should be saved correctly.     |
 * |testSaveCommentWithNestedReplies| Comments should have recursive replies saved.             |
 * |testSaveSolutionPostWithComment | Solutions can have comments as well.                      |
 * |testAdminIsSavedCorrectly       | test if a admin is saved correctly.                       |
 * |testSaveQuestionPostWithTag     | Question Post with Tag should be saved correctly.         |
 * |testContributorExpIsSaved       | Contributor experience should be saved correctly.         |
 * |testSolutionNotAQuestion        | Solution post should not be saved as a question post      |
 * |                                | or share the same details                                 |
 * |testSaveStudentWithReward       | Student rewards should be saved correctly.                |
 * |testSavePostContentSections     | Post content sections should be saved correctly.          |
 * |testSaveStudLastActDate         | The latest activity date for a student should be saved.   |
 * |testEquiTitleMustBeUnlocked     | Only unlocked titles can be equipped. (bug found)         |
 * |testSaveStudentSolvedQuestions  | Questions students have solved should be saved correctly. |
 * |testSaveAllPostsFromJson        | All posts loaded from JSON should still be present after  |
 * |                                | saving posts. (bug found)                                             |
 * +--------------------------------+-----------------------------------------------------------+
 */

public class DataWriterTest {

       @BeforeEach
        public void setUp() {
        UserManager.getInstance().getUsers().clear();
        PostManager.getInstance().clearPosts();
        }

        @AfterEach
        public void tearDown() {
        UserManager.getInstance().getUsers().clear();
        PostManager.getInstance().clearPosts();
        }

        @Test
        public void testSaveUserWithExistingUsers() {
        UserManager.getInstance().addUser(
            "ljames",          
            "ljames@email.sc.edu",
            "password1@A",    
            "Lebron",           
            "James",            
            Role.STUDENT,      
            Major.COMPUTER_SCIENCE, 
            Year.FRESHMAN       
        );

        UserManager.getInstance().addUser(
            "jdoe12",          
            "jdoe@email.sc.edu",
            "password1@A",    
            "John",           
            "Doe",            
            Role.STUDENT,      
            Major.COMPUTER_SCIENCE, 
            Year.FRESHMAN       
        );

        DataWriter.saveUsers();

        ArrayList<User> reloaded = DataLoader.getUsers();
        assertEquals(2, reloaded.size());
        }


        @Test
        public void testSaveQuestionPost() {
        UserManager.getInstance().addUser(
            "ljames",          
            "ljames@email.sc.edu",
            "password1@A",    
            "Lebron",           
            "James",            
            Role.STUDENT,      
            Major.COMPUTER_SCIENCE, 
            Year.FRESHMAN       
        );

        User author = UserManager.getInstance().getUser("ljames");

        QuestionPost question = new QuestionPost(
            UUID.randomUUID(),
            "Two Sum",
            author,
            new java.util.Date(),
            new ArrayList<>(),
            new ArrayList<>(),
            new ArrayList<>(),
            0,
            Difficulty.EASY,
            "Try using a hashmap."
        );

        PostManager.getInstance().addQuestion(question);
        DataWriter.savePosts();

        ArrayList<Post> reloaded = DataLoader.getPosts(UserManager.getInstance().getUsers());
        assertFalse(reloaded.isEmpty());
        assertEquals("Two Sum", reloaded.get(0).getTitle());
        }


        @Test
        public void testStudentMajorIsSavedCorrectly() {
        UserManager.getInstance().addUser(
            "ljames",          
            "ljames@email.sc.edu",
            "password1@A",    
            "Lebron",           
            "James",            
            Role.STUDENT,      
            Major.COMPUTER_SCIENCE, 
            Year.FRESHMAN       
        );

        DataWriter.saveUsers();

        ArrayList<User> reloaded = DataLoader.getUsers();
        Student student = (Student) reloaded.get(0);
        assertEquals(Major.COMPUTER_SCIENCE, student.getMajor(), "Student major was not saved correctly");
        }
    

        @Test
        public void testSaveQuestionPostWithComment() {
        UserManager.getInstance().addUser(
            "ljames",          
            "ljames@email.sc.edu",
            "password1@A",    
            "Lebron",           
            "James",            
            Role.STUDENT,      
            Major.COMPUTER_SCIENCE, 
            Year.FRESHMAN       
        );

        User author = UserManager.getInstance().getUser("ljames");

        QuestionPost question = new QuestionPost(
            UUID.randomUUID(),
            "Two Sum",
            author,
            new java.util.Date(),
            new ArrayList<>(),
            new ArrayList<>(),
            new ArrayList<>(),
            0,
            Difficulty.EASY,
            "Try using a hashmap."
        );

        Comment comment = new Comment(
            UUID.randomUUID(),
            author,
            "This is a test comment",
            question.getPostId(),
            java.time.LocalDate.now()
        );

        question.addComment(comment);
        PostManager.getInstance().addQuestion(question);
        DataWriter.savePosts();

        ArrayList<Post> reloaded = DataLoader.getPosts(UserManager.getInstance().getUsers());
        assertFalse(reloaded.get(0).getComments().isEmpty(), "Comment was not saved correctly");
        }

        @Test
        public void testSaveSolutionPostWithComment() {
        UserManager.getInstance().addUser(
            "ljames",          
            "ljames@email.sc.edu",
            "password1@A",    
            "Lebron",           
            "James",            
            Role.STUDENT,      
            Major.COMPUTER_SCIENCE, 
            Year.FRESHMAN       
        );

        User author = UserManager.getInstance().getUser("ljames");

        QuestionPost question = new QuestionPost(
            UUID.randomUUID(),
            "Two Sum",
            author,
            new java.util.Date(),
            new ArrayList<>(),
            new ArrayList<>(),
            new ArrayList<>(),
            0,
            Difficulty.EASY,
            "Try using a hashmap."
        );

        SolutionPost solution = new SolutionPost(
            UUID.randomUUID(),
            "Two Sum Solution",
            author,
            new java.util.Date(),
            new ArrayList<>(),
            new ArrayList<>(),
            new ArrayList<>(),
            0,
            1,
            question.getPostId()
        );

        Comment comment = new Comment(
            UUID.randomUUID(),
            author,
            "Great solution!",
            solution.getPostId(),
            java.time.LocalDate.now()
        );

        solution.addComment(comment);
        PostManager.getInstance().addQuestion(question);
        PostManager.getInstance().addSolution(author, solution);
        DataWriter.savePosts();

        ArrayList<Post> reloaded = DataLoader.getPosts(UserManager.getInstance().getUsers());
        assertFalse(reloaded.get(1).getComments().isEmpty(), "Solution post comment was not saved correctly");
        }


        @Test
        public void testSaveCommentWithNestedReplies() {
        UserManager.getInstance().addUser(
            "ljames",          
            "ljames@email.sc.edu",
            "password1@A",    
            "Lebron",           
            "James",            
            Role.STUDENT,      
            Major.COMPUTER_SCIENCE, 
            Year.FRESHMAN       
        );

        User author = UserManager.getInstance().getUser("ljames");

        QuestionPost question = new QuestionPost(
            UUID.randomUUID(),
            "Two Sum",
            author,
            new java.util.Date(),
            new ArrayList<>(),
            new ArrayList<>(),
            new ArrayList<>(),
            0,
            Difficulty.EASY,
            "Try using a hashmap."
        );

        Comment comment = new Comment(
            UUID.randomUUID(),
            author,
            "This is a test comment",
            question.getPostId(),
            java.time.LocalDate.now()
        );

        Comment reply1 = new Comment(
            UUID.randomUUID(),
            author,
            "This is reply 1",
            question.getPostId(),
            java.time.LocalDate.now()
        );

        Comment reply2 = new Comment(
            UUID.randomUUID(),
            author,
            "This is reply 2",
            question.getPostId(),
            java.time.LocalDate.now()
        );

        comment.addReply(reply1);
        comment.addReply(reply2);
        question.addComment(comment);
        PostManager.getInstance().addQuestion(question);
        DataWriter.savePosts();

        ArrayList<Post> reloaded = DataLoader.getPosts(UserManager.getInstance().getUsers());
        assertEquals(2, reloaded.get(0).getComments().get(0).getReply().size(), 
            "Nested replies were not saved correctly");
        }
            
        @Test
        public void testContributorExpIsSaved() {
        Contributor contributor = new Contributor(
            UUID.randomUUID(),
            "pprofessor",
            "pprofessor@cse.sc.edu",
            "password1@A",
            "Peter",
            "Professor",
            "Professor of Computer Science with 15 years experience."
        );

        UserManager.getInstance().getUsers().add(contributor);
        DataWriter.saveUsers();

        ArrayList<User> reloaded = DataLoader.getUsers();
        Contributor reloadedContributor = (Contributor) reloaded.get(0);
        assertEquals("Professor of Computer Science with 15 years experience.", 
            reloadedContributor.getExperience(), 
            "Contributor experience was not saved correctly");
        }


        @Test
        public void testAdminIsSavedCorrectly() {
        Contributor admin = new Contributor(
            UUID.randomUUID(),
            "adminA",
            "adminA@email.sc.edu",
            "password1@A",
            "Ava",
            "Admin",
            null,
            Role.ADMINISTRATOR
        );

        UserManager.getInstance().getUsers().add(admin);
        DataWriter.saveUsers();

        ArrayList<User> reloaded = DataLoader.getUsers();
        assertFalse(reloaded.isEmpty(), "Admin was not saved");
        assertEquals("adminA", reloaded.get(0).getUsername(), "Admin username was not saved correctly");
        assertEquals(Role.ADMINISTRATOR, reloaded.get(0).getStatus(), "Admin role was not saved correctly");
        }


        @Test
        public void testSaveQuestionPostWithTag() {
        UserManager.getInstance().addUser(
            "ljames",          
            "ljames@email.sc.edu",
            "password1@A",    
            "Lebron",           
            "James",            
            Role.STUDENT,      
            Major.COMPUTER_SCIENCE, 
            Year.FRESHMAN       
        );

        User author = UserManager.getInstance().getUser("ljames");

        ArrayList<String> tags = new ArrayList<>();
        tags.add("arrays");

        QuestionPost question = new QuestionPost(
            UUID.randomUUID(),
            "Two Sum",
            author,
            new java.util.Date(),
            new ArrayList<>(),
            tags,
            new ArrayList<>(),
            0,
            Difficulty.EASY,
            "Try using a hashmap."
        );

        PostManager.getInstance().addQuestion(question);
        DataWriter.savePosts();

        ArrayList<Post> reloaded = DataLoader.getPosts(UserManager.getInstance().getUsers());
        assertFalse(reloaded.get(0).getTags().isEmpty(), "Tags were not saved correctly");
        assertEquals("arrays", reloaded.get(0).getTags().get(0), "Tag value was not saved correctly");
        }

        @Test
        public void testSolutionNotAQuestion() {
        UserManager.getInstance().addUser(
            "ljames",          
            "ljames@email.sc.edu",
            "password1@A",    
            "Lebron",           
            "James",            
            Role.STUDENT,      
            Major.COMPUTER_SCIENCE, 
            Year.FRESHMAN       
        );

        User author = UserManager.getInstance().getUser("ljames");

        QuestionPost question = new QuestionPost(
            UUID.randomUUID(),
            "Two Sum",
            author,
            new java.util.Date(),
            new ArrayList<>(),
            new ArrayList<>(),
            new ArrayList<>(),
            0,
            Difficulty.EASY,
            "Try using a hashmap."
        );

        SolutionPost solution = new SolutionPost(
            UUID.randomUUID(),
            "Two Sum Solution",
            author,
            new java.util.Date(),
            new ArrayList<>(),
            new ArrayList<>(),
            new ArrayList<>(),
            0,
            1,
            question.getPostId()
        );

        PostManager.getInstance().addQuestion(question);
        PostManager.getInstance().addSolution(author, solution);
        DataWriter.savePosts();

        ArrayList<Post> reloaded = DataLoader.getPosts(UserManager.getInstance().getUsers());
        Post reloadedPost = reloaded.get(1);

        assertFalse(reloadedPost instanceof QuestionPost, "Solution post should not be a question post");
        SolutionPost reloadedSolution = (SolutionPost) reloadedPost;
        assertEquals(question.getPostId(), reloadedSolution.getQuestionId(), "Solution should only be linked to question via questionId");   
        }

        @Test
        public void testSaveStudentWithReward() {
        UserManager.getInstance().addUser(
            "ljames",
            "ljames@email.sc.edu",
            "password1@A",
            "Lebron",
            "James",
            Role.STUDENT,
            Major.COMPUTER_SCIENCE,
            Year.FRESHMAN
        );

        Student student = (Student) UserManager.getInstance().getUser("ljames");

        Reward reward = new Reward(RewardType.XP_BOOST, 20, false);
        student.getRewards().add(reward);

        DataWriter.saveUsers();

        ArrayList<User> reloaded = DataLoader.getUsers();
        Student reloadedStudent = (Student) reloaded.get(0);

        assertFalse(reloadedStudent.getRewards().isEmpty(), "Student reward was not saved");
        assertEquals(1, reloadedStudent.getRewards().size(), "Student should have exactly one saved reward");

        Reward reloadedReward = reloadedStudent.getRewards().get(0);
        assertEquals(RewardType.XP_BOOST, reloadedReward.getType(), "Reward type was not saved correctly");
        assertEquals(20, reloadedReward.getAmount(), "Reward amount was not saved correctly");
        assertFalse(reloadedReward.isRedeemed(), "Reward redeemed status was not saved correctly");
        }

        @Test
        public void testSavePostContentSections() {
        UserManager.getInstance().addUser(
            "ljames",
            "ljames@email.sc.edu",
            "password1@A",
            "Lebron",
            "James",
            Role.STUDENT,
            Major.COMPUTER_SCIENCE,
            Year.FRESHMAN
        );

        User author = UserManager.getInstance().getUser("ljames");

        ArrayList<PostContent> contentSections = new ArrayList<>();
        contentSections.add(new PostContent(ContentType.TEXT, "Solve Two Sum efficiently."));
        contentSections.add(new PostContent(ContentType.CODE, "for(...) { ... }"));

        QuestionPost question = new QuestionPost(
            UUID.randomUUID(),
            "Two Sum",
            author,
            new java.util.Date(),
            new ArrayList<>(),
            new ArrayList<>(),
            contentSections,
            0,
            Difficulty.EASY,
            "Try using a hashmap."
        );

        PostManager.getInstance().addQuestion(question);
        DataWriter.savePosts();

        ArrayList<Post> reloaded = DataLoader.getPosts(UserManager.getInstance().getUsers());
        QuestionPost reloadedQuestion = (QuestionPost) reloaded.get(0);

        assertFalse(reloadedQuestion.getContentSections().isEmpty(),
            "Content sections were not saved");
        assertEquals(2, reloadedQuestion.getContentSections().size(),
            "Post should have exactly two saved content sections");

        assertEquals(ContentType.TEXT, reloadedQuestion.getContentSections().get(0).getType(),
            "First content section type was not saved correctly");
        assertEquals("Solve Two Sum efficiently.",
            reloadedQuestion.getContentSections().get(0).getContent(),
            "First content section value was not saved correctly");

        assertEquals(ContentType.CODE, reloadedQuestion.getContentSections().get(1).getType(),
            "Second content section type was not saved correctly");
        assertEquals("for(...) { ... }",
            reloadedQuestion.getContentSections().get(1).getContent(),
            "Second content section value was not saved correctly");
        }

        @Test
        public void testSaveStudLastActDate() {
        Progression progression = new Progression();
        ArrayList<Reward> rewards = new ArrayList<>();
        java.time.LocalDate activityDate = java.time.LocalDate.of(2026, 4, 7);

        Student student = new Student(
            UUID.randomUUID(),
            "ljames",
            "ljames@email.sc.edu",
            "password1@A",
            "Lebron",
            "James",
            Major.COMPUTER_SCIENCE,
            Year.FRESHMAN,
            "CSCE 247, MATH 374",
            "CSCE 145, CSCE 146",
            SkillLevel.BEGINNER,
            0,
            progression,
            rewards,
            activityDate,
            Role.STUDENT
        );

        UserManager.getInstance().getUsers().add(student);

        DataWriter.saveUsers();

        ArrayList<User> reloaded = DataLoader.getUsers();
        Student reloadedStudent = (Student) reloaded.get(0);

        assertNotNull(reloadedStudent.getLastActivityDate(), "Last activity date was not saved");
        assertEquals(activityDate, reloadedStudent.getLastActivityDate(),
            "Last activity date was not saved correctly");
        }

        @Test
        public void testEquiTitleMustBeUnlocked() {
            Progression progression = new Progression();
            ArrayList<Title> unlockedTitles = new ArrayList<>();
            unlockedTitles.add(Title.SYNTAX_SCOUT);
            unlockedTitles.add(Title.LOGIC_LEARNER);

            progression.setUnlockedTitles(unlockedTitles);
            progression.setEquippedTitle(Title.BOOLEAN_BRAWLER);

            ArrayList<Reward> rewards = new ArrayList<>();

            Student student = new Student(
                UUID.randomUUID(),
                "ljames",
                "ljames@email.sc.edu",
                "password1@A",
                "Lebron",
                "James",
                Major.COMPUTER_SCIENCE,
                Year.FRESHMAN,
                "CSCE 247, MATH 374",
                "CSCE 145, CSCE 146",
                SkillLevel.BEGINNER,
                0,
                progression,
                rewards,
                java.time.LocalDate.of(2026, 3, 29),
                Role.STUDENT
            );

            UserManager.getInstance().getUsers().add(student);

            DataWriter.saveUsers();

            ArrayList<User> reloaded = DataLoader.getUsers();
            Student reloadedStudent = (Student) reloaded.get(0);

            assertNull(
                reloadedStudent.getProgression().getEquippedTitle(),
                "Equipped title should be null if it was not unlocked"
            );

            assertEquals(
                2,
                reloadedStudent.getProgression().getUnlockedTitles().size(),
                "Unlocked titles should still be saved correctly"
            );

            assertTrue(
                reloadedStudent.getProgression().getUnlockedTitles().contains(Title.SYNTAX_SCOUT),
                "SYNTAX_SCOUT should still be unlocked"
            );

            assertTrue(
                reloadedStudent.getProgression().getUnlockedTitles().contains(Title.LOGIC_LEARNER),
                "LOGIC_LEARNER should still be unlocked"
            );
        }

        @Test
        public void testSaveStudentSolvedQuestions() {
        Progression progression = new Progression();
        ArrayList<Reward> rewards = new ArrayList<>();

        Student student = new Student(
            UUID.randomUUID(),
            "ljames",
            "ljames@email.sc.edu",
            "password1@A",
            "Lebron",
            "James",
            Major.COMPUTER_SCIENCE,
            Year.FRESHMAN,
            "CSCE 247, MATH 374",
            "CSCE 145, CSCE 146",
            SkillLevel.BEGINNER,
            0,
            progression,
            rewards,
            java.time.LocalDate.of(2026, 3, 29),
            Role.STUDENT
        );

        student.recordSolvedQuestions();
        student.recordSolvedQuestions();

        UserManager.getInstance().getUsers().add(student);

        DataWriter.saveUsers();

        ArrayList<User> reloaded = DataLoader.getUsers();
        Student reloadedStudent = (Student) reloaded.get(0);

        assertEquals(2, reloadedStudent.getSolvedQuestions(),
            "Solved questions were not saved correctly");
        }

       @Test
        public void testSaveAllPostsFromJson() {
            UserManager.getInstance().getUsers().clear();
            PostManager.getInstance().clearPosts();

            UserManager.getInstance().addUser(
                "ljames",
                "ljames@email.sc.edu",
                "password1@A",
                "Lebron",
                "James",
                Role.STUDENT,
                Major.COMPUTER_SCIENCE,
                Year.FRESHMAN
            );

            User author = UserManager.getInstance().getUser("ljames");

            QuestionPost question = new QuestionPost(
                UUID.randomUUID(),
                "Two Sum",
                author,
                new java.util.Date(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                0,
                Difficulty.EASY,
                "Try using a hashmap."
            );

            SolutionPost solution = new SolutionPost(
                UUID.randomUUID(),
                "Two Sum Solution",
                author,
                new java.util.Date(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                0,
                1,
                question.getPostId()
            );

            PostManager.getInstance().addQuestion(question);
            PostManager.getInstance().addSolution(author, solution);

            DataWriter.saveUsers();
            DataWriter.savePosts();

            ArrayList<User> users = DataLoader.getUsers();
            assertFalse(users.isEmpty(), "Users loaded from JSON should not be empty");

            ArrayList<Post> originalPosts = DataLoader.getPosts(users);
            assertFalse(originalPosts.isEmpty(), "Original posts loaded from JSON should not be empty");

            UserManager.getInstance().getUsers().clear();
            PostManager.getInstance().clearPosts();
            UserManager.getInstance().getUsers().addAll(users);

            for (Post post : originalPosts) {
                if (post.getType().equals("QUESTION")) {
                    PostManager.getInstance().addQuestion((QuestionPost) post);
                }
            }

            for (Post post : originalPosts) {
                if (post.getType().equals("SOLUTION")) {
                    SolutionPost currentSolution = (SolutionPost) post;
                    QuestionPost parent = PostManager.getInstance().getQuestionById(currentSolution.getQuestionId());
                    assertNotNull(parent, "Parent question should exist for each solution post");
                    PostManager.getInstance().addSolution(parent, currentSolution.getAuthor(), currentSolution);
                }
            }

            DataWriter.savePosts();

            ArrayList<Post> reloadedPosts = DataLoader.getPosts(UserManager.getInstance().getUsers());
            assertFalse(reloadedPosts.isEmpty(), "Reloaded posts should not be empty after saving");
            assertEquals(originalPosts.size(), reloadedPosts.size(),
                "All posts from JSON should still be present after saving");

            assertEquals(originalPosts.get(0).getTitle(), reloadedPosts.get(0).getTitle(),
                "First post title was not saved correctly");
            assertEquals(originalPosts.get(0).getType(), reloadedPosts.get(0).getType(),
                "First post type was not saved correctly");
        }
}