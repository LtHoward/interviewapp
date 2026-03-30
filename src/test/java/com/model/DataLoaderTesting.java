package com.model;
import java.util.ArrayList;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * DataLoader Class tested by:
 * @author Myila Howard 
 * 
 */
class DataLoaderTesting {
    private UserManager userManager = UserManager.getInstance();
    private ArrayList<User> users = userManager.getUsers();

    @BeforeEach
    public void setup() {
        users.clear();
        UUID user1Id = UUID.fromString("ff461495-7827-4d76-bf2e-52d52e72e0fb");
        UUID user2Id = UUID.fromString("15f3ae3e-2759-44c3-9d00-c9cf0b4ce5ef");
        users.add(new Student(user1Id, "Jjhons", "jjhons@email.sc.edu", "J0hn$2026", "Jimmy", "Jhon", Major.COMPUTER_SCIENCE, Year.SOPHOMORE, "CSCE 145", "CSCE 240", SkillLevel.BEGINNER, 3, null, null, null, Role.STUDENT));
        users.add(new Contributor(user2Id, "bwhite", "whitebw@email.sc.edu", "whitebw123@", "Brian", "White", null));
        DataWriter.saveUsers();
    }

    @AfterEach
    public void tearDown() {
        UserManager.getInstance().getUsers().clear();
        DataWriter.saveUsers();
    }

    @Test
    void testGetUsersSize() {
        users = DataLoader.getUsers();
        assertEquals(2, users.size());
    }

    @Test
    void testGetUsersSizeZero() {
        UserManager.getInstance().getUsers().clear();
        DataWriter.saveUsers();
        assertEquals(0, users.size());
    }

    @Test
    void testGetUserFirstUsername() {
        users = DataLoader.getUsers();
        assertEquals("Jjhons", users.get(0).getUsername());
    }

    @Test
    void testGetUserSecondUsername() {
        users = DataLoader.getUsers();
        assertEquals("bwhite", users.get(1).getUsername());
    }

    @Test
    void testGetUserFirstEmail() {
        users = DataLoader.getUsers();
        assertEquals("jjhons@email.sc.edu", users.get(0).getEmail());
    }

    @Test
    void testGetFirstUserStatus() {
        users = DataLoader.getUsers();
        assertEquals(Role.STUDENT, users.get(0).getStatus());
    }

    @Test 
    void testGetSecondUserStatus() {
        users = DataLoader.getUsers();
        assertEquals(Role.CONTRIBUTOR, users.get(1).getStatus());    
    }

    @Test
    void testGetUserMajor() {
        users = DataLoader.getUsers();
        assertEquals(Major.COMPUTER_SCIENCE, ((Student) users.get(0)).getMajor());
    }


}
