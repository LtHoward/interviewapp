package com.model;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class DataLoader extends DataConstants {

    public static ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();

        try (FileReader reader = new FileReader(USERS_FILE)) {

            JSONArray usersJSON = (JSONArray) new JSONParser().parse(reader);

            for (int i = 0; i < usersJSON.size(); i++) {
                JSONObject userJSON = (JSONObject) usersJSON.get(i);

                UUID id = UUID.fromString((String) userJSON.get(USER_ID));
                String username = (String) userJSON.get(USERNAME);
                String email = (String) userJSON.get(EMAIL);
                String password = (String) userJSON.get(PASSWORD);
                String firstName = (String) userJSON.get(FIRST_NAME);
                String lastName = (String) userJSON.get(LAST_NAME);

                Role role = Role.valueOf(((String) userJSON.get(ROLE)).toUpperCase());

                if (role == Role.STUDENT) {
            
                    String currentClasses = (String) userJSON.getOrDefault(CURRENT_CLASSES, "");
                    String classesTaken = (String) userJSON.getOrDefault(CLASSES_TAKEN, "");

                    Major major = Major.valueOf(((String) userJSON.getOrDefault(MAJOR, "COMPUTER_SCIENCE")).toUpperCase());
                    Year year = Year.valueOf(((String) userJSON.getOrDefault(YEAR, "FRESHMAN")).toUpperCase());
                    SkillLevel skillLevel = SkillLevel.valueOf(((String) userJSON.getOrDefault(SKILL_LEVEL, "BEGINNER")).toUpperCase());

                    int solvedQuestions = ((Long) userJSON.getOrDefault(SOLVED_QUESTIONS, 0L)).intValue();

                    // For now, build empty lists/objects unless you're loading them too
                    ArrayList<SolutionPost> postedSolutions = new ArrayList<>();
                    Progression progression = new Progression(); // make sure you have a default constructor
                    ArrayList<Reward> rewards = new ArrayList<>();

                    // If you store lastActivityDate as epoch millis (recommended):
                    long lastMs = (Long) userJSON.getOrDefault(LAST_ACTIVITY_DATE, System.currentTimeMillis());
                    Date lastActivityDate = new Date(lastMs);

                    users.add(new Student(id, username, email, password, firstName, 
                    lastName, major, year, currentClasses, classesTaken, 
                    skillLevel, solvedQuestions, postedSolutions, 
                    progression, rewards, lastActivityDate));

                } else if (role == Role.CONTRIBUTOR) {
                    String contributorExp = (String) userJSON.getOrDefault(EXPERIENCE, "");

                    users.add(new Contributor(
                            id, username, email, password, firstName, lastName,
                            contributorExp
                    ));

                } else if (role == Role.ADMINISTRATOR) {
                    // You said you don't have Administrator implemented
                    throw new IllegalStateException("Found ADMIN in users.json but no Administrator class exists.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }
}