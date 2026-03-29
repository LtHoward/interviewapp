package com.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Tyrel Hamilton
 *
 * Classes tested by me:
 * - UserManager
 */
public class UserManagerTest {

    private UserManager userManager;

    @BeforeEach
    public void setUp() {
        userManager = new UserManager();
        userManager.getUsers().clear();
    }

    @Test
    public void isPasswordValid_nullPassword() {
        assertFalse(userManager.isPasswordValid(null));
    }

    @Test
    public void isPasswordValid_validPassword() {
        assertTrue(userManager.isPasswordValid("Valid123!"));
    }

    @Test
    public void isPasswordValid_missingSpecialChar() {
        assertFalse(userManager.isPasswordValid("Valid123"));
    }

    @Test
    public void getUser_existingUsername() {
        userManager.addUser(
                "student1",
                "student1@email.com",
                "Valid123!",
                "Jimmy",
                "Bauer",
                Role.STUDENT,
                Major.COMPUTER_SCIENCE,
                Year.SOPHOMORE
        );

        User foundUser = userManager.getUser("student1");

        assertNotNull(foundUser);
        assertEquals("student1", foundUser.getUsername());
    }

    @Test
    public void getUser_nullUsername() {
        assertNull(userManager.getUser(null));
    }

    @Test
    public void userExist_existingUsername() {
        userManager.addUser(
                "student1",
                "student1@email.com",
                "Valid123!",
                "Jimmy",
                "Bauer",
                Role.STUDENT,
                Major.COMPUTER_SCIENCE,
                Year.SOPHOMORE
        );

        assertTrue(userManager.userExist("student1"));
    }

    @Test
    public void addUser_studentSuccess() {
        boolean added = userManager.addUser(
                "student1",
                "student1@email.com",
                "Valid123!",
                "Jimmy",
                "Bauer",
                Role.STUDENT,
                Major.COMPUTER_SCIENCE,
                Year.SOPHOMORE
        );

        assertTrue(added);
        assertEquals(1, userManager.getUsers().size());
        assertTrue(userManager.getUsers().get(0) instanceof Student);
        assertEquals(Role.STUDENT, userManager.getUsers().get(0).getStatus());
    }

    @Test
    public void addUser_contributorSuccess() {
        boolean added = userManager.addUser(
                "professor1",
                "prof@email.com",
                "Valid123!",
                "Peter",
                "Professor",
                Role.CONTRIBUTOR,
                Major.COMPUTER_SCIENCE,
                Year.SENIOR
        );

        assertTrue(added);
        assertEquals(1, userManager.getUsers().size());
        assertTrue(userManager.getUsers().get(0) instanceof Contributor);
        assertEquals(Role.CONTRIBUTOR, userManager.getUsers().get(0).getStatus());
    }

    @Test
    public void addUser_adminSuccess() {
        boolean added = userManager.addUser(
                "admin1",
                "admin@email.com",
                "Valid123!",
                "Ava",
                "Admin",
                Role.ADMINISTRATOR,
                Major.COMPUTER_SCIENCE,
                Year.SENIOR
        );

        assertTrue(added);
        assertEquals(1, userManager.getUsers().size());
        assertTrue(userManager.getUsers().get(0) instanceof Contributor);
        assertEquals(Role.ADMINISTRATOR, userManager.getUsers().get(0).getStatus());
    }

    @Test
    public void addUser_duplicateUsernameFails() {
        userManager.addUser(
                "student1",
                "student1@email.com",
                "Valid123!",
                "Jimmy",
                "Bauer",
                Role.STUDENT,
                Major.COMPUTER_SCIENCE,
                Year.SOPHOMORE
        );

        boolean addedDuplicate = userManager.addUser(
                "student1",
                "other@email.com",
                "Valid123!",
                "Jim",
                "Bauer",
                Role.STUDENT,
                Major.COMPUTER_SCIENCE,
                Year.SOPHOMORE
        );

        assertFalse(addedDuplicate);
        assertEquals(1, userManager.getUsers().size());
    }

    @Test
    public void addUser_nullFieldFails() {
        boolean added = userManager.addUser(
                null,
                "student1@email.com",
                "Valid123!",
                "Jimmy",
                "Bauer",
                Role.STUDENT,
                Major.COMPUTER_SCIENCE,
                Year.SOPHOMORE
        );

        assertFalse(added);
        assertEquals(0, userManager.getUsers().size());
    }

    @Test
    public void resetPassword_existingUserSuccess() {
        userManager.addUser(
                "student1",
                "student1@email.com",
                "Valid123!",
                "Jimmy",
                "Bauer",
                Role.STUDENT,
                Major.COMPUTER_SCIENCE,
                Year.SOPHOMORE
        );

        boolean changed = userManager.resetPassword("student1", "NewPass456!");

        assertTrue(changed);
        assertEquals("NewPass456!", userManager.getUser("student1").getPassword());
    }

    @Test
    public void resetPassword_samePasswordFails() {
        userManager.addUser(
                "student1",
                "student1@email.com",
                "Valid123!",
                "Jimmy",
                "Bauer",
                Role.STUDENT,
                Major.COMPUTER_SCIENCE,
                Year.SOPHOMORE
        );

        boolean changed = userManager.resetPassword("student1", "Valid123!");

        assertFalse(changed);
        assertEquals("Valid123!", userManager.getUser("student1").getPassword());
    }

    @Test
    public void resetPassword_unknownUserFails() {
        assertFalse(userManager.resetPassword("ghostUser", "NewPass456!"));
    }

    @Test
    public void removeAccount_existingUser() {
        userManager.addUser(
                "student1",
                "student1@email.com",
                "Valid123!",
                "Jimmy",
                "Bauer",
                Role.STUDENT,
                Major.COMPUTER_SCIENCE,
                Year.SOPHOMORE
        );

        User user = userManager.getUser("student1");
        boolean removed = userManager.removeAccount(user);

        assertTrue(removed);
        assertFalse(userManager.userExist("student1"));
        assertEquals(0, userManager.getUsers().size());
    }

    @Test
    public void removeOtherAccount_nonAdminFails() {
        userManager.addUser(
                "student1",
                "student1@email.com",
                "Valid123!",
                "Jimmy",
                "Bauer",
                Role.STUDENT,
                Major.COMPUTER_SCIENCE,
                Year.SOPHOMORE
        );

        User user = userManager.getUser("student1");
        boolean removed = userManager.removeOtherAccount(user);

        assertFalse(removed);
        assertTrue(userManager.userExist("student1"));
    }

    @Test
    public void logout_returnsTrue() {
        assertTrue(userManager.logout());
    }
}