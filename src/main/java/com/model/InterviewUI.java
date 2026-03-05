package com.model;

import java.util.Scanner;

public class InterviewUI {
    private InterviewApp app;

    public InterviewUI() {
        app = new InterviewApp();
    }

    	public void run() {
		scenario1();
	}

    private void scenario1() {
        System.out.println();

        if (!app.login("sSteven", "ssteven@email.sc.edu", "daR33l*5T3ven!")){
            System.out.println("Login failed for Steven.");
        } else {
            System.out.println("Login successful for Steven.");
        }
    }

    public static void main(String[] args) {
		InterviewUI libraryInterface = new InterviewUI();
		libraryInterface.run();

	}
}
