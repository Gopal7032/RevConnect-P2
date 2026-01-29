package com.revconnect.Service;

import com.revconnect.entity.*;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CommentServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @Test
    void testAddComment() {

        User user = new User();

        user.setUsername("commenter");
        user.setEmail("comment@mail.com");
        user.setPassword("123");
        user.setRole(Role.PERSONAL);

        user = userService.register(user);

        Post post =
                postService.createPost(
                        "Test Post",
                        "#test",
                        user
                );

        Comment comment =
                commentService.addComment(
                        post.getId(),
                        "Nice post!",
                        user
                );

        assertNotNull(comment);
        assertNotNull(comment.getId());
        assertEquals("Nice post!", comment.getText());
    }

    @Test
    void testAddCommentInvalidPost() {

        User user = new User();

        user.setUsername("user2");
        user.setEmail("user2@mail.com");
        user.setPassword("123");
        user.setRole(Role.PERSONAL);

        user = userService.register(user);

        Comment comment =
                commentService.addComment(
                        9999L,
                        "Invalid",
                        user
                );

        assertNull(comment);
    }
}
