package com.revconnect.console;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import com.revconnect.service.UserService;
import com.revconnect.Service.PostService;
import com.revconnect.Service.CommentService;

import com.revconnect.entity.*;

import java.util.List;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class ConsoleUI implements CommandLineRunner {

    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;

    private User loggedUser;

    private final Scanner sc = new Scanner(System.in);

    @Override
    public void run(String... args) {
        mainMenu(); // Start console
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
                    System.out.println("Thank you for using RevConnect!");
                    System.exit(0);
                }

                default -> System.out.println("Invalid choice!");
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

        System.out.println("Registered Successfully!");
    }

    private void login() {

        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Password: ");
        String pass = sc.nextLine();

        loggedUser = userService.login(email, pass);

        if (loggedUser == null) {

            System.out.println("Invalid Login!");

        } else {

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
                    loggedUser = null;
                    return;
                }

                default -> System.out.println("Invalid choice!");
            }
        }
    }

    private void createPost() {

        System.out.print("Content: ");
        String c = sc.nextLine();

        System.out.print("Hashtags: ");
        String h = sc.nextLine();

        postService.createPost(c, h, loggedUser);

        System.out.println("Post Created!");
    }

    private void viewFeed() {

        List<Post> posts = postService.getAllPosts();

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

            System.out.println("Post not found!");

        } else {

            System.out.println("Comment Added!");
        }
    }
}
