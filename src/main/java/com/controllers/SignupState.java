package com.controllers;

import com.model.Major;
import com.model.Role;
import com.model.Year;

/**
 * A utility class to hold the selected role, major, and year during the signup process.
 * This class provides static methods to set and get the selected values, 
 * as well as a method to clear the state after the signup process is complete. 
 * It is used to maintain the state across different scenes during the signup flow.
 */
public class SignupState {
    private static Role selectedRole;
    private static Major selectedMajor;
    private static Year selectedYear;

    public static void setSelectedRole(Role role) {
        selectedRole = role;
    }

    public static Role getSelectedRole() {
        return selectedRole;
    }

    public static void setSelectedMajor(Major major) {
        selectedMajor = major;
    }

    public static Major getSelectedMajor() {
        return selectedMajor;
    }

    public static void setSelectedYear(Year year) {
        selectedYear = year;
    }

    public static Year getSelectedYear() {
        return selectedYear;
    }

    public static void clear() {
        selectedRole = null;
        selectedMajor = null;
        selectedYear = null;
    }
}