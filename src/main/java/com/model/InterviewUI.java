package com.model;
import com.model.Year;

import java.lang.reflect.Array;
import java.time.LocalDate;
//import java.time.Year;
import java.util.Scanner;
import java.util.UUID;
import java.util.ArrayList;
import java.util.Date;
import java.time.LocalDate;
/**
 * Provides a command-line interface for interacting with the InterviewApp,
 * allowing users to log in, create accounts, manage posts, and add comments.
 */
public class InterviewUI {
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

    /**
     * Handles the logout scenario, logging out the current user
     * and clearing the session.
     */
    private void scenario3() 
    {
       if(!app.logout()) {
        System.out.println("Logout failed :(");
       } else {
        currentUser = null;
        System.out.println("Logout successful!");
       }
    }
 /**
     * Handles the question post creation scenario, prompting the current user
     * for a title, tags, content type, content, difficulty, and hint.
     * Only Contributors and Administrators are permitted to create question posts.
     */
    private void scenario4() {
        if (currentUser == null) {
            System.out.println("No user logged in. Please login first.");
            return;
        }

        Role role = currentUser.getStatus();

        if (role != Role.CONTRIBUTOR && role != Role.ADMINISTRATOR) {
            System.out.println("Access denied. Only Contributors or Admins can create QuestionPosts.");
            return;
        }

        System.out.println("\n--- Create Question Post ---");

        // TITLE
        System.out.println("Enter question title:");
        String title = scanner.nextLine();

        // TAGS
        System.out.println("Enter a tag:");
        String tag = scanner.nextLine();

        ArrayList<String> tags = new ArrayList<>();
        tags.add(tag);
        
        // CONTENT TYPE
        System.out.println("Select content type:");
        System.out.println("1. TEXT\n2. CODE\n3. IMAGE\n4. VIDEO");

        int typeChoice = scanner.nextInt();
        scanner.nextLine();

        ContentType type;

        switch(typeChoice) {
            case 1: type = ContentType.TEXT; break;
            case 2: type = ContentType.CODE; break;
            case 3: type = ContentType.IMAGE; break;
            case 4: type = ContentType.VIDEO; break;
            default:
                System.out.println("Invalid choice. Defaulting to TEXT.");
                type = ContentType.TEXT;
        }

         // CONTENT
        System.out.println("Enter content:");
        String content = scanner.nextLine();

        ArrayList<PostContent> contentSections = new ArrayList<>();
        contentSections.add(new PostContent(type, content));

        // DIFFICULTY
        System.out.println("Select difficulty:");
        System.out.println("1. EASY\n2. MEDIUM\n3. HARD");

        int diffChoice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        Difficulty difficulty;

        switch (diffChoice) {
            case 1:
                difficulty = Difficulty.EASY;
                break;
            case 2:
                difficulty = Difficulty.MEDIUM;
                break;
            case 3:
                difficulty = Difficulty.HARD;
                break;
            default:
                System.out.println("Invalid choice. Defaulting to EASY.");
                difficulty = Difficulty.EASY;
        }

        // HINT
        System.out.println("Enter a hint:");
        String hint = scanner.nextLine();

        // CREATE OBJECT
        QuestionPost questionPost = new QuestionPost(
            UUID.randomUUID(),
            title,
            currentUser,
            new Date(),
            new ArrayList<Comment>(),
            tags,
            contentSections,
            0,
            difficulty,
            hint
        );

        // SAVE
        boolean success = app.addQuestion(questionPost);

        if (success) {
            System.out.println("QuestionPost created successfully!");
        } else {
            System.out.println("Failed to create QuestionPost.");
        }
    }
/**
     * Handles the view question post scenario, displaying a list of all
     * available question posts and showing the details of the selected post.
     */
    private void scenario5() {
        ArrayList<Post> allPosts = app.getAllPosts();

        ArrayList<QuestionPost> questions = new ArrayList<>();
        
        for (Post post : allPosts) {
            if (post instanceof QuestionPost) {
                questions.add((QuestionPost) post);
            }
        }

        if (questions.isEmpty()) {
            System.out.println("No question posts available.");
            return;
        }

        System.out.println("\n--- Question Posts ---");
        for (int i = 0; i < questions.size(); i++) {
            System.out.println((i + 1) + ". " + questions.get(i).getTitle());
        }

        System.out.println("\nSelect a question by number:");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice < 1 || choice > questions.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        QuestionPost selected = questions.get(choice - 1);

        System.out.println("\n--- Question Details ---");
        System.out.println("Title: " + selected.getTitle());
        System.out.println("Author: " + selected.getAuthor().getUsername());
        System.out.println("Difficulty: " + selected.getDifficulty());
        System.out.println("Hint: " + selected.getHint());
        System.out.println("Score: " + selected.getScore());

        System.out.println("\nTags:");
        for (String tag : selected.getTags()) {
            System.out.println("- " + tag);
        }

        System.out.println("\nContent:");
        for (PostContent section : selected.getContentSections()) {
            System.out.println("Type: " + section.getType());
            System.out.println(section.getContent());
            System.out.println();
        }
    }
    /**
     * Handles the view question post scenario, displaying a list of all
     * available question posts and showing the details of the selected post.
     */
 private void scenario6() {
  
    if (currentUser == null) {
        System.out.println("No user logged in. Please login first.");
        return;
    }

    ArrayList<Post> allPosts = app.getAllPosts();
    ArrayList<QuestionPost> questions = new ArrayList<>();

    for (Post post : allPosts) {
        if (post instanceof QuestionPost) {
            questions.add((QuestionPost) post);
        }
    }
// Checks if there are any question posts to comment on
    if (questions.isEmpty()) {
        System.out.println("No question posts available to comment on.");
        return;
    }

    // Display posts 
    System.out.println("\n--- Select a Question Post to Comment On ---");
    for (int i = 0; i < questions.size(); i++) {
        System.out.println((i + 1) + ". " + questions.get(i).getTitle());
    }

    System.out.println("\nSelect a question by number:");
    int choice = scanner.nextInt();
    scanner.nextLine(); // consume newline

    if (choice < 1 || choice > questions.size()) {
        System.out.println("Invalid selection.");
        return;
    }

    QuestionPost selected = questions.get(choice - 1);

    // Show existing comments first
    System.out.println("\n--- Existing Comments on: " + selected.getTitle() + " ---");
    ArrayList<Comment> comments = selected.getComments();

    if (comments.isEmpty()) {
        System.out.println("They're no comments yet. Be the first!");
    } else {
        for (int i = 0; i < comments.size(); i++) {
            Comment c = comments.get(i);
            System.out.println((i + 1) + ". " + c.getAuthor().getUsername() + ": " + c.getContent());
        }
    }

    // Collect and post the new comment
    System.out.println("\nEnter your comment:");
    String commentText = scanner.nextLine();

    if (commentText == null || commentText.trim().isEmpty()) {
        System.out.println("Your comment cannot be empty.");
        return;
    }

   Comment newComment = new Comment(
    UUID.randomUUID(),
    currentUser,
    commentText.trim(),
    selected.getPostId(),   // postId from the selected QuestionPost
    LocalDate.now()
);

   boolean success = app.addComment(selected, newComment);

    if (success) {
        System.out.println("Comment posted successfully!");
    } else {
        System.out.println("Failed to post comment.");
    }
}
/**
     * The entry point of the application. Creates an {@link InterviewUI} instance and starts the run loop.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
		InterviewUI libraryInterface = new InterviewUI();
		libraryInterface.run();

	}
}
