package com.model;
import java.util.ArrayList;
import java.util.UUID;

class DataLoaderTesting {
    private UserManager userManager = UserManager.getInstance();
    private ArrayList<User> users = userManager.getUsers();

    public void setup() {
        users.clear();
        UUID user1Id = UUID.fromString("ff461495-7827-4d76-bf2e-52d52e72e0fb");
        UUID user2Id = UUID.fromString("15f3ae3e-2759-44c3-9d00-c9cf0b4ce5ef");
        users.add(new Student(user1Id, "Jjhons", "jjhons@email.sc.edu", "J0hn$2026", "Jimmy", "Jhon", Major.COMPUTER_SCIENCE, Year.SOPHOMORE, "CSCE 145", "CSCE 240", SkillLevel.BEGINNER, 3, null, null, null, Role.STUDENT));
        users.add(new Contributor(user2Id, "bwhite", "whitebw@email.sc.edu", "whitebw123@", "Brian", "White", null));
    }
}
