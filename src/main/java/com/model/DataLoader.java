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

                    String currentClasses = (String) userJSON.getOrDefault(CURRENT_CLASSES, "");
                    String classesTaken = (String) userJSON.getOrDefault(CLASSES_TAKEN, "");

                    Major major = Major.valueOf(((String) userJSON.getOrDefault(MAJOR, "COMPUTER_SCIENCE")).toUpperCase());
                    Year year = Year.valueOf(((String) userJSON.getOrDefault(YEAR, "FRESHMAN")).toUpperCase());
                    SkillLevel skillLevel = SkillLevel.valueOf(((String) userJSON.getOrDefault(SKILL_LEVEL, "BEGINNER")).toUpperCase());

                    int solvedQuestions = ((Long) userJSON.getOrDefault(SOLVED_QUESTIONS, 0L)).intValue();

                    ArrayList<SolutionPost> postedSolutions = new ArrayList<>();
                    Progression progression = new Progression();
                    ArrayList<Reward> rewards = new ArrayList<>();

                    long lastMs = (Long) userJSON.getOrDefault(LAST_ACTIVITY_DATE, System.currentTimeMillis());
                    Date lastActivityDate = new Date(lastMs);

                    users.add(new Student(id, username, email, password, firstName,
                            lastName, major, year, currentClasses, classesTaken,
                            skillLevel, solvedQuestions, postedSolutions,
                            progression, rewards, lastActivityDate));

                } else if (role == Role.CONTRIBUTOR) {
                    String experience = (String) userJSON.getOrDefault(EXPERIENCE, "");

                    users.add(new Contributor(id, username, email, password, firstName,
                            lastName, experience));

                } else if (role == Role.ADMINISTRATOR) {
                    String experience = (String) userJSON.getOrDefault(EXPERIENCE, "");
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

                    String title = (String) postJSON.getOrDefault(TITLE, "");
                    Difficulty difficulty = Difficulty.valueOf(((String) postJSON.getOrDefault(DIFFICULTY, "EASY")).toUpperCase());
                    String hint = (String) postJSON.getOrDefault(HINT, "");

                    posts.add(new QuestionPost(postId, author, createdAt, comments, tags, contentSections,
                            score, title, difficulty, hint));

                } else if (type.equals("SOLUTION")) {

                    int solutionNumber = ((Number) postJSON.getOrDefault(SOLUTION_NUMBER, 0L)).intValue();
                    UUID questionId = UUID.fromString((String) postJSON.getOrDefault(QUESTION_ID, postId.toString()));

                    posts.add(new SolutionPost(postId, author, createdAt, comments, tags, contentSections,
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