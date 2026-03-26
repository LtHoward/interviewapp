package com.model;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;


/**
 * Class to demonstrate the functionality of the application by creating users, posts, comments, and interactions between them. 
 * It also demonstrates saving data to files and searching for posts.
 * 
 * @author Overnight Opperators
 */
public class InterviewUI {
<<<<<<< HEAD
    /** The main application instance handling business logic. */
    private InterviewApp app;
    /** The currently logged-in user, or {@code null} if no user is logged in. */
    private User currentUser;
    /** Scanner for reading user input from the console. */
    private Scanner scanner;
/**
     * Constructs a new InterviewUI instance, initializing the application,
     * scanner, and setting the current user to {@code null}.
*/
    public InterviewUI() {
        app = new InterviewApp();
        scanner = new Scanner(System.in);
        currentUser = null;
    }
/**
     * Starts the main application loop, displaying the menu and routing
     * the user to the appropriate scenario based on their input.
     */
    public void run() {
        boolean running = true;
        while (running) {
            System.out.println("\nWelcome to the Interview App!\n" +
            "\nChoose a scenario to run:\n" +
            "\n1. Login Scenario" +
            "\n2. Create Account Scenario" +
            "\n3. logout Scenario" +
            "\n4. Create Question Post Scenario" +
            "\n5. View Question Post" +
            "\n6. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    scenario1(); // Login scenario
                    break;
                case 2:
                   /* scenario2();*/ // Create account scenario
                    break;
                case 3:
                    scenario3(); // logout scenario
                    break;
                case 4: 
                    scenario4(); //question post creation scenario
                    break;
                case 5:
                    scenario5(); //view question post scenario
                    break;
                case 6:
                    scenario6(); //comment on question post scenario
                    break;
                case 7:
                    System.out.println("Exiting the app...");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
	}
 /**
     * Handles the login scenario, prompting the user for credentials
     * and retrying until a successful login is achieved.
     */
    private void scenario1() 
    {
        String username = " ";
        String email = " ";
        String password = " "; 
        boolean loggedIn = false;

        do
        {
            System.out.println("Enter username:");
            username = scanner.nextLine().trim();

            System.out.println("Enter email:");
            email = scanner.nextLine();

            System.out.println("Enter password:");
            password = scanner.nextLine();

            loggedIn = app.login(username, email, password);
            if (!loggedIn) 
            {
                System.out.println("Login failed. Please try again.");
            } 
            else 
            {
                System.out.println("\nLogin successful!\n");
            }
        }
        while(!loggedIn);

        currentUser = app.getUser(username, password);
        System.out.println("Welcome to the Interview App, " + username + "!\nStatus: " + currentUser.getStatus() + "\n");
    }
/**
     * Validates the given username by checking that it is non-null,
     * non-empty, and not already taken.
     *
     * @param username the username to validate
     * @return {@code true} if the username is valid, {@code false} otherwise
     */
    private boolean validateUsername(String username) 
    {
        if (username == null || username.isEmpty()) 
        {
            System.out.println("Invalid Username Try Again");
            return false;
        }
        if(app.haveUser(username)) 
        {
            System.out.println("Username already exists! Try Again");
            return false;
        }
        return true;
    }
/**
     * Validates whether the given email belongs to an existing Administrator.
     *
     * @param email the email address to validate
     * @return {@code true} if the email belongs to an Administrator, {@code false} otherwise
     */
    private boolean validateAdminEmail(String email) 
    {
        ArrayList<User> users = app.getAllUsers();
        for (User user : users) 
        {
            if (user.getEmail().equals(email) && user.getStatus() == Role.ADMINISTRATOR) 
            {
                return true;
            }
        }
        return false;
    }
/**
     * Handles the account creation scenario, prompting the user for all
     * required details including username, email, password, role, major, and year.
     * Also routes the user to role-specific options after account creation.
     */
    private void scenario2() 
    {

        String username = "";
        do 
        {
            System.out.println("Enter username:");
            username = scanner.nextLine().trim();
        } 
        while (!validateUsername(username));
        System.out.println("Username accepted!");
        

        System.out.println("Enter email:");
        String email = scanner.nextLine();

        System.out.println("Enter password:");
        String password = scanner.nextLine();

        System.out.println("Enter first name:");
        String firstName = scanner.nextLine();

        System.out.println("Enter last name:");
        String lastName = scanner.nextLine();

        System.out.println("Select role:\n1. Student\n2. Contributor\n3. Administrator");
        int roleChoice = scanner.nextInt();
        Role role = null;
        switch (roleChoice) {
            case 1:
                role = Role.STUDENT;
                break;
            case 2:
                role = Role.CONTRIBUTOR;
                break;
            case 3:
                role = Role.ADMINISTRATOR;
                break;
            default:
                System.out.println("Invalid role choice.");
                return;
        }

        System.out.println("Select major:\n1. Computer Science\n2. Software Engineering\n3. Information Technology");
        int majorChoice = scanner.nextInt();
        Major major = null;
        switch (majorChoice) {
            case 1:
                major = Major.COMPUTER_SCIENCE;
                break;
            case 2:
                major = Major.COMPUTER_ENGINEERING;
                break;
            case 3:
                major = Major.COMPUTER_INFORMATION_SYSTEMS;
                break;
            default:
                System.out.println("Invalid major choice.");
                return;
        }

        System.out.println("Select year:\n1. Freshman\n2. Sophomore\n3. Junior\n4. Senior");
        int yearChoice = scanner.nextInt();
        Year year = null;
        switch (yearChoice) {
            case 1:
                year = Year.FRESHMAN;
                break;
            case 2:
                year = Year.SOPHOMORE;
                break;
            case 3:
                year = Year.JUNIOR;
                break;
            case 4:
                year = Year.SENIOR;
                break;
            default:
                System.out.println("Invalid year choice.");
                return;
        }

            if (!app.createUser(username, email, password, firstName, lastName, role, major, year)) 
            {
                System.out.println("User creation failed :(");
            }
            else 
            {
                System.out.println("User created successfully!");
            }
        
        boolean validRole = false;
        while(!validRole)
    {
        System.out.println("Welcome, " + username + "!\nWhat is your role? \n1. Student\n2. Contributor\n3. Administrator");
        switch (roleChoice) 
        {
            case 1:
                validRole = true;
                System.out.println("You have chosen the Student role.\n 1. Questions\n 2. Solutions\n 3. Logout");
                int studentChoice = scanner.nextInt();
                if(studentChoice == 1) 
                {
                    System.out.println("You have chosen to view Questions.");
                } 
                else if (studentChoice == 2) 
                {
                    System.out.println("You have chosen to view Solutions.");
                } 
                else if (studentChoice == 3) scenario3();
                break;
            case 2:
                validRole = true;
                System.out.println("You have chosen the Contributor role.\n 1. Create Question\n 2. Create Solution\n 3. Logout");
                    int contributorChoice = scanner.nextInt();
                if(contributorChoice == 1) 
                {
                    System.out.println("You have chosen to Create a Question.");
                } 
                else if (contributorChoice == 2) 
                {
                    System.out.println("You have chosen to Create a Solution.");
                }
                else if (contributorChoice == 3) scenario3();
                break;
            case 3:
                if(!validateAdminEmail(email)) 
                {
                    System.out.println("Invalid email for Administrator role. Access denied.");
                    System.out.println("Please choose a valid role to continue.");
                }
                else
                {
                
                validRole = true;
                System.out.println("Email Verified!\nYou have chosen the Administrator role.\n 1. Edit Post\n 2. Delete Post\n 3. Remove User\n 4. Logout");
                    int adminChoice = scanner.nextInt();
                if(adminChoice == 1) 
                {
                    System.out.println("You have chosen to Edit a Post.");
                } 
                else if (adminChoice == 2) 
                {
                    System.out.println("You have chosen to Delete a Post.");
                } 
                else if (adminChoice == 3)
                {
                    System.out.println("You have chosen to Remove a User.");
                } 
                else if (adminChoice == 4) scenario3();
                }
                break;
            default:
                System.out.println("Invalid choice.");
            }
        }
    }
    
=======

>>>>>>> 22475bd39ee84bd15d0ebc895d90a533c601b926
    /**
     * Main method to run the demonstration of the application functionality.
     * @param args the command line arguments (not used)
      * @author Overnight Operators
      * @see InterviewApp
      * @see UserManager
      * @see PostManager
      * @see Student
      * @see Comment
      * @see DataWriter
      */    
    public static void main(String[] args) {

        InterviewApp app = new InterviewApp();
        PostManager postManager = PostManager.getInstance();
        UserManager userManager = UserManager.getInstance();

        System.out.println("========== BEFORE DEMO ==========");
        printUsers(app);
        printPosts(app);

        System.out.println("\n========== CREATE ACCOUNT - DUPLICATE ==========");

        boolean duplicate = app.createUser(
                "sSparrow", 
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

    /**
     * prints all users in the application
     * @param app the InterviewApp instance to get users from
     */
    private static void printUsers(InterviewApp app) {
        System.out.println("\nUsers:");
        for (User user : app.getAllUsers()) {
            System.out.println("- " + user.getUsername() + " (" + user.getStatus() + ")");
        }
    }

    /**
     * prints all posts in the application
     * @param app the InterviewApp instance to get posts from
     */
    private static void printPosts(InterviewApp app) {
        System.out.println("\nPosts:");
        for (Post post : app.getAllPosts()) {
            System.out.println("- " + post.getTitle() + " [" + post.getType() + "]");
        }
    }
}