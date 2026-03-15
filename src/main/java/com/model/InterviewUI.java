package com.model;

import java.util.Scanner;

public class InterviewUI {
    private InterviewApp app;

    public InterviewUI() {
        app = new InterviewApp();
    }

    	public void run() {
        System.out.println("Welcome to the Interview App!\nChoose a scenario to run:\n1. Login Scenario\n2. Create Account Scenario\n3. logout Scenario");
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

    private void scenario1() {
        System.out.println();

        if (!app.login("sSteven", "ssteven@email.sc.edu", "daR33l*5T3ven!")){
            System.out.println("Login failed :(");
        } else {
            System.out.println("Login successful!");
        }
    }

    private void scenario2() 
    {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter username:");
        String username = scanner.nextLine();

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
        
        System.out.println("Welcome, " + username + "!\nWhat is your role? \n1. Student\n2. Contributor\n3. Administrator");
        int role = scanner.nextInt();
        switch (role) 
        {
            case 1:
                System.out.println("You have chosen the Student role.\n 1. Questions\n 2. Solutions\n 3. Logout");
                break;
            case 2:
                System.out.println("You have chosen the Contributor role.\n 1. Create Question\n 2. Create Solution\n 3. Logout");
                break;
            case 3:
                System.out.println("You have chosen the Administrator role.\n 1. Edit Post\n 2. Delete Post\n 3. Remove User\n 4. Logout");
                break;
            default:
                System.out.println("Invalid choice.");
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
