package com.model;

import java.util.Scanner;

public class InterviewUI {
    private InterviewApp app;

    public InterviewUI() {
        app = new InterviewApp();
    }

    	public void run() {
        System.out.println("Welcome to the Interview App!\nChoose a scenario to run:\n1. Login Scenario\n2. Create Account Scenario");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                scenario1(); // Login scenario
                break;
            case 2:
                // scenario2();
                break;
            case 3:
                // scenario3();
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

    private void scenario2() {
        // Implement scenario 2
    }

    public static void main(String[] args) {
		InterviewUI libraryInterface = new InterviewUI();
		libraryInterface.run();

	}
}
