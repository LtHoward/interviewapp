package com.model;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class InterviewUI {

    public static void main(String[] args) {

        InterviewApp app = new InterviewApp();
        PostManager postManager = PostManager.getInstance();
        UserManager userManager = UserManager.getInstance();

        System.out.println("========== BEFORE DEMO ==========");
        printUsers(app);
        printPosts(app);

        System.out.println("\n========== CREATE ACCOUNT - DUPLICATE ==========");

        boolean duplicate = app.createUser(
                "sSparrow", // already exists in your JSON
                "sally@email.com",
                "Password123!",
                "Sally",
                "Sparrow",
                Role.STUDENT,
                Major.COMPUTER_SCIENCE,
                Year.SOPHOMORE
        );

        if (!duplicate) {
            System.out.println("Duplicate user detected. Account creation rejected.");
        }

        System.out.println("\n========== CREATE ACCOUNT - SUCCESS ==========");

        app.createUser(
                "SallySparrow",
                "sally@email.com",
                "ValidPass1!",
                "Sally",
                "Sparrow",
                Role.CONTRIBUTOR,
                Major.COMPUTER_SCIENCE,
                Year.SOPHOMORE
        );

        User sally = app.getUser("SallySparrow", "ValidPass1!");
        System.out.println("Sally logged in: " + (sally != null));

        System.out.println("\n========== SALLY CREATES QUESTION ==========");

        ArrayList<PostContent> questionContent = new ArrayList<>();
        questionContent.add(new PostContent(ContentType.TEXT, "Find the longest subarray with sum K."));
        questionContent.add(new PostContent(ContentType.CODE, "LongestSubArraySolution.java"));

        QuestionPost question = new QuestionPost(
                UUID.randomUUID(),
                "Longest SubArray with Sum K",
                sally,
                new Date(),
                new ArrayList<>(),
                new ArrayList<>(),
                questionContent,
                0,
                Difficulty.MEDIUM,
                "Use prefix sums or hashmap"
        );

        app.addQuestion(question);

        System.out.println("Question created: " + question.getTitle());

        System.out.println("\n========== SALLY ADDS SOLUTIONS ==========");

        SolutionPost sol1 = new SolutionPost(
                UUID.randomUUID(),
                "Brute Force Solution",
                sally,
                new Date(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                0,
                1,
                question.getPostId()
        );

        SolutionPost sol2 = new SolutionPost(
                UUID.randomUUID(),
                "Optimized HashMap Solution",
                sally,
                new Date(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                0,
                2,
                question.getPostId()
        );

        postManager.addSolution(question, sally, sol1);
        postManager.addSolution(question, sally, sol2);

        System.out.println("2 solutions added.");

        System.out.println("\nSally logs out...");
        app.logout();

        // ----------------------------------------------------

        System.out.println("\n========== JIMMY BAUER DAILY TASK ==========");

        User jimmy = app.getUser("jJohns", "J0hn$2026");
        Student jimmyStudent = (Student) jimmy;

        System.out.println("Jimmy logged in.");
        System.out.println("Current streak: " + jimmyStudent.getCurrentStreak());

        System.out.println("\nDaily Challenge Presented:");
        System.out.println("-> " + question.getTitle());

        System.out.println("\nJimmy reviews solutions:");
        for (Post post : app.getAllPosts()) {
            if (post instanceof SolutionPost &&
                ((SolutionPost) post).getQuestionId().equals(question.getPostId())) {

                System.out.println("- " + post.getTitle());
            }
        }

        System.out.println("\nJimmy is confused and comments on Solution 2...");

        Comment comment = new Comment(
                UUID.randomUUID(),
                jimmy,
                "Can someone explain why this works?",
                sol2.getPostId(),
                LocalDate.now()
        );

        app.addComment(sol2, comment);

        System.out.println("Comment added:");
        System.out.println(jimmy.getUsername() + " (" + LocalDate.now() + "): " + comment.getContent());

        System.out.println("\n========== PRINT TO FILE ==========");

        try {
            PrintWriter writer = new PrintWriter(new FileWriter("JimmyReview.txt"));
            writer.println("Question: " + question.getTitle());
            writer.println("Comment: " + comment.getContent());
            writer.close();
            System.out.println("Printed to JimmyReview.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("\n========== SEARCH: Binary Search Tree ==========");

        ArrayList<QuestionPost> results = postManager.searchQuestions("Binary Search Tree");

        System.out.println("Found " + results.size() + " questions:");

        for (QuestionPost q : results) {
            System.out.println("- " + q.getTitle());
        }

        System.out.println("\n========== STREAK UPDATE ==========");
        jimmyStudent.getProgression().setCurrentStreak(jimmyStudent.getCurrentStreak() + 1);
        System.out.println("New streak: " + jimmyStudent.getCurrentStreak());

        System.out.println("\nJimmy logs out...");
        app.logout();

        // ----------------------------------------------------

        System.out.println("\n========== AFTER DEMO ==========");
        DataWriter.saveUsers();
        DataWriter.savePosts();

        printUsers(app);
        printPosts(app);
    }

    // ---------------- HELPERS ----------------

    private static void printUsers(InterviewApp app) {
        System.out.println("\nUsers:");
        for (User user : app.getAllUsers()) {
            System.out.println("- " + user.getUsername() + " (" + user.getStatus() + ")");
        }
    }

    private static void printPosts(InterviewApp app) {
        System.out.println("\nPosts:");
        for (Post post : app.getAllPosts()) {
            System.out.println("- " + post.getTitle() + " [" + post.getType() + "]");
        }
    }
}