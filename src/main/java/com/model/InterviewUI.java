package com.model;

import java.lang.reflect.Array;
import java.util.Scanner;
import java.util.ArrayList;

public class InterviewUI {
    private InterviewApp app;

    public InterviewUI() {
        app = new InterviewApp();
    }

    	public void run() {
        System.out.println("\nWelcome to the Interview App!\n\nChoose a scenario to run:\n\n1. Login Scenario\n2. Create Account Scenario\n3. logout Scenario");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                scenario1(); // Login scenario
                break;
            case 2:
                scenario2(); // Create account scenario
                break;
            case 3:
                scenario3(); // logout scenario
                break;
            default:
                System.out.println("Invalid choice.");
        }
	}

    private void scenario1() 
    {
        Scanner scanner = new Scanner(System.in);

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

            User currentUser = app.getUser(username, password);
            System.out.println("Welcome to the Interview App, " + username + "!\nStatus: " + currentUser.getStatus() + "\n");

            boolean validRole = false;
            while(!validRole)
            {
                switch (currentUser.getStatus()) 
                {
                    case STUDENT:
                        validRole = true;
                        System.out.println("You have the Student role.\n 1. Questions\n 2. Solutions\n 3. Logout");
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
                    case CONTRIBUTOR:
                        validRole = true;
                        System.out.println("You have the Contributor role.\n 1. Create Question\n 2. Create Solution\n 3. Logout");
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
                    case ADMINISTRATOR:
                        validRole = true;
                        System.out.println("You have the Administrator role.\n 1. Edit Post\n 2. Delete Post\n 3. Remove User\n 4. Logout");
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
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            }
    }

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

    private void scenario2() 
    {
        Scanner scanner = new Scanner(System.in);

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
        

            if (!app.createUser(username, email, password, firstName, lastName)) 
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
        int role = scanner.nextInt();
        switch (role) 
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
    

    private void scenario3() 
    {
       if(!app.logout()) {
        System.out.println("Logout failed :(");
       } else {
        System.out.println("Logout successful!");
       }
    }

    public static void main(String[] args) {
		InterviewUI libraryInterface = new InterviewUI();
		libraryInterface.run();

	}
}
