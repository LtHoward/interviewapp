package com.model;

import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.time.OffsetDateTime;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * DataLoader is responsible for loading user and post data from JSON files.
 * It reads the data, parses it, and constructs the appropriate User and Post
 * objects based on the JSON structure.
 * 
 * @author Tyrel Hamilton
 */
public class DataLoader extends DataConstants {

    /**
     * Loads users from the USERS_FILE JSON file and constructs User objects based
     * on the data.
     * 
     * @return An ArrayList of User objects.
     */
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
                    LocalDate lastActivityDate = LocalDate.now();

                    Progression progression = new Progression();
                    ArrayList<Reward> rewards = new ArrayList<>();

                    if (studentData != null) {
                        currentClasses = (String) studentData.getOrDefault(CURRENT_CLASSES, "");
                        classesTaken = (String) studentData.getOrDefault(CLASSES_TAKEN, "");
                        major = Major
                                .valueOf(((String) studentData.getOrDefault(MAJOR, "COMPUTER_SCIENCE")).toUpperCase());
                        year = Year.valueOf(((String) studentData.getOrDefault(YEAR, "FRESHMAN")).toUpperCase());
                        skillLevel = SkillLevel
                                .valueOf(((String) studentData.getOrDefault(SKILL_LEVEL, "BEGINNER")).toUpperCase());
                        solvedQuestions = ((Long) studentData.getOrDefault(SOLVED_QUESTIONS, 0L)).intValue();

                        String lastActivityDateStr = (String) studentData.get(LAST_ACTIVITY_DATE);
                        if (lastActivityDateStr != null) {
                            try {
                                lastActivityDate = LocalDate
                                        .from(OffsetDateTime.parse(lastActivityDateStr).toInstant());
                            } catch (Exception e) {
                                lastActivityDate = LocalDate.now();
                            }
                        }
                    }

                    JSONObject progressionJSON = (JSONObject) studentData.get(PROGRESSION);
                    if (progressionJSON != null) {
                        progression.setPoints(((Long) progressionJSON.getOrDefault(POINTS, 0L)).intValue());
                        progression.setLevel(((Long) progressionJSON.getOrDefault(LEVEL, 1L)).intValue());
                        progression
                                .setCurrentStreak(((Long) progressionJSON.getOrDefault(CURRENT_STREAK, 0L)).intValue());
                        progression
                                .setLongestStreak(((Long) progressionJSON.getOrDefault(LONGEST_STREAK, 0L)).intValue());

                        JSONArray unlockedTitlesJSON = (JSONArray) progressionJSON.get(UNLOCKED_TITLES);
                        ArrayList<Title> unlockedTitles = new ArrayList<>();
                        if (unlockedTitlesJSON != null) {
                            for (Object obj : unlockedTitlesJSON) {
                                unlockedTitles.add(Title.valueOf(((String) obj).toUpperCase()));
                            }
                        }
                        progression.setUnlockedTitles(unlockedTitles);

                        String equippedTitleStr = (String) progressionJSON.get(EQUIPPED_TITLE);
                        if (equippedTitleStr != null) {
                            progression.setEquippedTitle(Title.valueOf(equippedTitleStr.toUpperCase()));
                        }
                    }

                    JSONArray rewardsJSON = (JSONArray) userJSON.get(REWARDS);
                    if (rewardsJSON != null) {
                        for (Object rewardObj : rewardsJSON) {
                            JSONObject rewardJSON = (JSONObject) rewardObj;

                            RewardType type = RewardType.valueOf(((String) rewardJSON.get(TYPE)).toUpperCase());
                            int amount = ((Long) rewardJSON.getOrDefault(AMOUNT, 0L)).intValue();
                            boolean redeemed = (Boolean) rewardJSON.getOrDefault(REDEEMED, false);

                            rewards.add(new Reward(type, amount, redeemed));
                        }
                    }

                    users.add(new Student(id, username, email, password, firstName,
                            lastName, major, year, currentClasses, classesTaken,
                            skillLevel, solvedQuestions,
                            progression, rewards, lastActivityDate, role));

                } else if (role == Role.CONTRIBUTOR) {
                    JSONObject contributorData = (JSONObject) userJSON.get(CONTRIBUTOR_DATA);
                    String experience = "";
                    if (contributorData != null) {
                        experience = (String) contributorData.getOrDefault(EXPERIENCE, "");
                    }

                    users.add(new Contributor(id, username, email, password, firstName,
                            lastName, experience));

                } else if (role == Role.ADMINISTRATOR) {
                    JSONObject contributorData = (JSONObject) userJSON.get(CONTRIBUTOR_DATA);
                    String experience = "";
                    if (contributorData != null) {
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

    /**
     * Loads posts from the POSTS_FILE JSON file and constructs Post objects based
     * on the data.
     * 
     * @param users the list of users used to map author IDs to User objects
     * @return an ArrayList of Post objects loaded from the JSON file
     */
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
                Date createdAt = new Date();
                if (createdAtStr != null) {
                    try {
                        createdAt = Date.from(OffsetDateTime.parse(createdAtStr).toInstant());
                    } catch (Exception e) {
                        createdAt = new Date();
                    }
                }

                Number scoreNum = (Number) postJSON.getOrDefault(SCORE, 0L);
                int score = scoreNum.intValue();

                String type = ((String) postJSON.getOrDefault(POST_TYPE, "")).toUpperCase();

                ArrayList<Comment> comments = new ArrayList<>();
                ArrayList<String> tags = new ArrayList<>();
                ArrayList<PostContent> contentSections = new ArrayList<>();

                JSONArray tagsJSON = (JSONArray) postJSON.get(TAGS);
                if (tagsJSON != null) {
                    for (Object tagObj : tagsJSON) {
                        tags.add((String) tagObj);
                    }
                }

                JSONArray contentSectionsJSON = (JSONArray) postJSON.get(CONTENT_SECTIONS);
                if (contentSectionsJSON != null) {
                    for (Object sectionObj : contentSectionsJSON) {
                        JSONObject sectionJSON = (JSONObject) sectionObj;

                        String sectionTypeStr = (String) sectionJSON.get(TYPE);
                        String sectionValue = (String) sectionJSON.get(VALUE);

                        if (sectionTypeStr != null && sectionValue != null) {
                            try {
                                ContentType contentType = ContentType.valueOf(sectionTypeStr.toUpperCase());
                                contentSections.add(new PostContent(contentType, sectionValue));
                            } catch (IllegalArgumentException e) {
                                System.out.println("Invalid content type: " + sectionTypeStr);
                            }
                        }
                    }
                }

                JSONArray commentsJSON = (JSONArray) postJSON.get(COMMENTS);
                if (commentsJSON != null) {
                    for (Object commentObj : commentsJSON) {
                        Comment comment = loadComment((JSONObject) commentObj, userMap, postId);
                        if (comment != null) {
                            comments.add(comment);
                        }
                    }
                }

                if (type.equals("QUESTION")) {

                    Difficulty difficulty = Difficulty
                            .valueOf(((String) postJSON.getOrDefault(DIFFICULTY, "EASY")).toUpperCase());
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

    /**
     * Loads a single comment from JSON and recursively loads all
     * replies attached to the comment.
     * 
     * @param commentJSON  the JSON object representing one comment
     * @param userMap      map of user IDs to User objects
     * @param parentPostId the ID of the post this comment belongs to
     * @return a Comment object with nested replies loaded, or null if invalid
     */
    private static Comment loadComment(JSONObject commentJSON, Map<UUID, User> userMap, UUID parentPostId) {
        String commentIdString = (String) commentJSON.get(COMMENT_ID);
        String commentAuthorIdString = (String) commentJSON.get(AUTHOR_ID);
        String commentContent = (String) commentJSON.getOrDefault(CONTENT, "");
        String commentCreatedAtStr = (String) commentJSON.get(CREATED_AT);

        if (commentIdString == null || commentAuthorIdString == null) {
            return null;
        }

        UUID commentId = UUID.fromString(commentIdString);
        UUID commentAuthorId = UUID.fromString(commentAuthorIdString);

        User commentAuthor = userMap.get(commentAuthorId);
        if (commentAuthor == null) {
            return null;
        }

        LocalDate commentDate = LocalDate.now();
        if (commentCreatedAtStr != null) {
            try {
                commentDate = OffsetDateTime.parse(commentCreatedAtStr).toLocalDate();
            } catch (Exception e) {
                commentDate = LocalDate.now();
            }
        }

        Comment comment = new Comment(commentId, commentAuthor, commentContent, parentPostId, commentDate);

        JSONArray repliesJSON = (JSONArray) commentJSON.get(REPLIES);
        if (repliesJSON != null) {
            for (Object replyObj : repliesJSON) {
                Comment reply = loadComment((JSONObject) replyObj, userMap, parentPostId);
                if (reply != null) {
                    comment.addReply(reply);
                }
            }
        }

        return comment;
    }

    public static void main(String[] args) {
        ArrayList<User> users = DataLoader.getUsers();
        System.out.println("Users loaded: " + users.size());

        ArrayList<Post> posts = DataLoader.getPosts(users);
        System.out.println("Posts loaded: " + posts.size());
    }
}