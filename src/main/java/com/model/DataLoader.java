package com.model;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.time.OffsetDateTime;

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
                    JSONObject studentData = (JSONObject) userJSON.get(STUDENT_DATA);

                    String currentClasses = "";
                    String classesTaken = "";
                    Major major = Major.COMPUTER_SCIENCE;
                    Year year = Year.FRESHMAN;
                    SkillLevel skillLevel = SkillLevel.BEGINNER;
                    int solvedQuestions = 0;
                    Date lastActivityDate = new Date();

                    if(studentData != null) {
                        currentClasses = (String) studentData.getOrDefault(CURRENT_CLASSES, "");
                        classesTaken = (String) studentData.getOrDefault(CLASSES_TAKEN, "");
                        major = Major.valueOf(((String) studentData.getOrDefault(MAJOR, "COMPUTER_SCIENCE")).toUpperCase());
                        year = Year.valueOf(((String) studentData.getOrDefault(YEAR, "FRESHMAN")).toUpperCase());
                        skillLevel = SkillLevel.valueOf(((String) studentData.getOrDefault(SKILL_LEVEL, "BEGINNER")).toUpperCase());
                        solvedQuestions = ((Long) studentData.getOrDefault(SOLVED_QUESTIONS, 0L)).intValue();
                        
                        String lastActivityDateStr = (String) studentData.get(LAST_ACTIVITY_DATE);
                        if (lastActivityDateStr != null) {
                            try{
                                lastActivityDate = Date.from(OffsetDateTime.parse(lastActivityDateStr).toInstant());
                            } catch (Exception e) {
                                lastActivityDate = new Date();
                            }
                        }   
                    }

                    ArrayList<SolutionPost> postedSolutions = new ArrayList<>();
                    Progression progression = new Progression();
                    ArrayList<Reward> rewards = new ArrayList<>();

                    users.add(new Student(id, username, email, password, firstName,
                        lastName, major, year, currentClasses, classesTaken,
                        skillLevel, solvedQuestions, postedSolutions,
                        progression, rewards, lastActivityDate, role));

                } else if (role == Role.CONTRIBUTOR) {
                    JSONObject contributorData = (JSONObject) userJSON.get(CONTRIBUTOR_DATA);
                    String experience = "";
                    if(contributorData != null) {
                        experience = (String) contributorData.getOrDefault(EXPERIENCE, "");
                    }

                    users.add(new Contributor(id, username, email, password, firstName,
                            lastName, experience));

                } else if (role == Role.ADMINISTRATOR) {
                    JSONObject contributorData = (JSONObject) userJSON.get(CONTRIBUTOR_DATA);
                    String experience = "";
                    if(contributorData != null) {
                        experience = (String) contributorData.getOrDefault(EXPERIENCE, "");
                    }
                    users.add(new Contributor(id, username, email, password, firstName,
                            lastName, experience, Role.ADMINISTRATOR));
                }
                
            } 
        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }

    public static ArrayList<Post> getPosts(ArrayList<User> users) {
        ArrayList<Post> posts = new ArrayList<>();

        Map<UUID, User> userMap = new HashMap<>();
        for (User user : users) {
            userMap.put(user.getId(), user);
        }

        try (FileReader reader = new FileReader(POSTS_FILE)) {

            JSONArray postsJSON = (JSONArray) new JSONParser().parse(reader);

            for (int i = 0; i < postsJSON.size(); i++) {
                JSONObject postJSON = (JSONObject) postsJSON.get(i);

                String postIdString = (String) postJSON.get(POST_ID);
                String authorIdString = (String) postJSON.get(AUTHOR_ID);
                String title = (String) postJSON.getOrDefault(TITLE, "");

                if (postIdString == null || authorIdString == null) {
                    System.out.println("Bad post JSON entry (missing id keys): " + postJSON.toJSONString());
                    continue;
                }

                UUID postId = UUID.fromString(postIdString);
                UUID authorId = UUID.fromString(authorIdString);

                User author = userMap.get(authorId);
                if (author == null) {
                    System.out.println("Post has unknown authorId: " + authorId + " | post=" + postJSON.toJSONString());
                    continue;
                }

                String createdAtStr = (String) postJSON.get(CREATED_AT);
                Date createdAt = (createdAtStr == null)
                        ? new Date()
                        : Date.from(OffsetDateTime.parse(createdAtStr).toInstant());

                Number scoreNum = (Number) postJSON.getOrDefault(SCORE, 0L);
                int score = scoreNum.intValue();

                String type = ((String) postJSON.getOrDefault(POST_TYPE, "")).toUpperCase();

                ArrayList<Comment> comments = new ArrayList<>();
                ArrayList<String> tags = new ArrayList<>();
                ArrayList<PostContent> contentSections = new ArrayList<>();

                if (type.equals("QUESTION")) {

                    Difficulty difficulty = Difficulty.valueOf(((String) postJSON.getOrDefault(DIFFICULTY, "EASY")).toUpperCase());
                    String hint = (String) postJSON.getOrDefault(HINT, "");

                    posts.add(new QuestionPost(postId, title, author, createdAt, comments, tags, contentSections,
                            score, difficulty, hint));

                } else if (type.equals("SOLUTION")) {

                    int solutionNumber = ((Number) postJSON.getOrDefault(SOLUTION_NUMBER, 0L)).intValue();
                    UUID questionId = UUID.fromString((String) postJSON.getOrDefault(QUESTION_ID, postId.toString()));

                    posts.add(new SolutionPost(postId, title, author, createdAt, comments, tags, contentSections,
                            score, solutionNumber, questionId));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return posts;
    }

    public static void main(String[] args) {
        ArrayList<User> users = DataLoader.getUsers();
        System.out.println("Users loaded: " + users.size());

        ArrayList<Post> posts = DataLoader.getPosts(users);
        System.out.println("Posts loaded: " + posts.size());
    }
}