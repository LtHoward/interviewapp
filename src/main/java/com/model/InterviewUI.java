package com.model;
import java.util.Scanner;
public class InterviewUI {

    public static void main(String[] args) {
        InterviewUI interviewUI = new InterviewUI();
        interviewUI.run();
    }
    
    public void run() {
        //Select scenario//
        Scanner scanner = new Scanner(System.in);
        
        System.out.println(" Scenario 1");
        System.out.println(" Scenario 2");
       
        int intchoice = scanner.nextInt();

        scanner.nextLine(); // Consume the newline character
        int choice = 0;
        switch (choice) {
            case 1:
                runScenario1(scanner);
                break;
            case 2:
                runScenario2(scanner);
                break;
            default:
                System.out.println("Invalid choice. Please select a valid scenario.");
        }

//She isnt going to be able to log in to the app without creating an account first, so we will just run scenario 1 for now. We can add more scenarios later if we have time. 
    }

private void runScenario2(Scanner scanner) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'runScenario2'");
    }

private void runScenario1(Scanner scanner) { 
         System.out.println("Welcome to the Interview App!"); 
       
         System.out.println("Create an account"); 
         app.createUser(String username = "Sparrow";
       String email = "sparow@email.com";
       String password = "Sparrow33*";
       String firstName = "Sally";           
         String lastName = "Sparrow";
            String role = "CONTRIBUTOR";
            final User user = new User(username,email,password,firstName,lastName,role);
            System.out.println("Exit");)

System.out.println("Create an account");
app.createUser(String username1 = "Sparrow330";
       String email1 = "sparow@email.com";
       String password1 = "Sparrow33*";
       String firstName1 = "Sally";           
         String lastName1 = "Sparrow";
            String role1 = "CONTRIBUTOR";
            final User user1 = new addUser(username1,email1,password1,firstName1,lastName1,role1);
System.out.println("Account created successfully! Please log in to your account.");)
       
    }
    
}
