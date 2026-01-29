package com.revconnect.console;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import com.revconnect.Service.UserService;
import com.revconnect.Service.PostService;
import com.revconnect.Service.CommentService;

import com.revconnect.entity.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
@Profile("!test")
public class ConsoleUI implements CommandLineRunner {

    private static final Logger logger =
            LogManager.getLogger(ConsoleUI.class);

    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;

    private User loggedUser;

    private final Scanner sc = new Scanner(System.in);

    @Override
    public void run(String... args) {

        logger.info("RevConnect Console Started");

        mainMenu();
    }

    private void mainMenu() {

        while (true) {

            System.out.println("\n==== REVCONNECT ====");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");

            int ch = sc.nextInt();
            sc.nextLine();

            switch (ch) {

                case 1 -> register();
                case 2 -> login();

                case 3 -> {
                    logger.info("Application closed by user");
                    System.out.println("Thank you for using RevConnect!");
                    System.exit(0);
                }

                default -> {
                    logger.warn("Invalid main menu choice: " + ch);
                    System.out.println("Invalid choice!");
                }
            }
        }
    }

    private void register() {

        User u = new User();

        System.out.print("Username: ");
        u.setUsername(sc.nextLine());

        System.out.print("Email: ");
        u.setEmail(sc.nextLine());

        System.out.print("Password: ");
        u.setPassword(sc.nextLine());

        System.out.print("Role (1-PERSONAL, 2-CREATOR, 3-BUSINESS): ");
        int r = sc.nextInt();
        sc.nextLine();

        u.setRole(
                r == 1 ? Role.PERSONAL :
                        r == 2 ? Role.CREATOR :
                                Role.BUSINESS
        );

        userService.register(u);

        logger.info("New user registered: " + u.getEmail());

        System.out.println("Registered Successfully!");
    }

    private void login() {

        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Password: ");
        String pass = sc.nextLine();

        loggedUser = userService.login(email, pass);

        if (loggedUser == null) {

            logger.warn("Failed login attempt: " + email);

            System.out.println("Invalid Login!");

        } else {

            logger.info("User logged in: " + loggedUser.getUsername());

            System.out.println("Welcome " + loggedUser.getUsername());

            userMenu();
        }
    }

    private void userMenu() {

        while (true) {

            System.out.println("\n==== USER MENU ====");
            System.out.println("1. Create Post");
            System.out.println("2. View Feed");
            System.out.println("3. My Posts");
            System.out.println("4. Comment on Post");
            System.out.println("5. Logout");

            int ch = sc.nextInt();
            sc.nextLine();

            switch (ch) {

                case 1 -> createPost();
                case 2 -> viewFeed();
                case 3 -> myPosts();
                case 4 -> commentPost();

                case 5 -> {
                    logger.info("User logged out: " +
                            loggedUser.getUsername());

                    loggedUser = null;
                    return;
                }

                default -> {
                    logger.warn("Invalid user menu choice: " + ch);
                    System.out.println("Invalid choice!");
                }
            }
        }
    }

    private void createPost() {

        System.out.print("Content: ");
        String c = sc.nextLine();

        System.out.print("Hashtags: ");
        String h = sc.nextLine();

        postService.createPost(c, h, loggedUser);

        logger.info("Post created by: " +
                loggedUser.getUsername());

        System.out.println("Post Created!");
    }

    private void viewFeed() {

        List<Post> posts = postService.getAllPosts();

        logger.info("Feed viewed by: " +
                loggedUser.getUsername());

        System.out.println("\n---- FEED ----");

        for (Post p : posts) {

            System.out.println("ID: " + p.getId());
            System.out.println("By: " + p.getUser().getUsername());
            System.out.println(p.getContent());
            System.out.println("#" + p.getHashtags());
            System.out.println("--------------");
        }
    }

    private void myPosts() {

        List<Post> posts =
                postService.getMyPosts(loggedUser.getId());

        logger.info("My posts viewed by: " +
                loggedUser.getUsername());

        for (Post p : posts) {

            System.out.println(p.getId() + " : " + p.getContent());
        }
    }

    private void commentPost() {

        System.out.print("Post ID: ");
        Long id = sc.nextLong();
        sc.nextLine();

        System.out.print("Comment: ");
        String text = sc.nextLine();

        Comment c =
                commentService.addComment(id, text, loggedUser);

        if (c == null) {

            logger.warn("Comment failed. Post not found: " + id);

            System.out.println("Post not found!");

        } else {

            logger.info("Comment added by: " +
                    loggedUser.getUsername());

            System.out.println("Comment Added!");
        }
    }
}
