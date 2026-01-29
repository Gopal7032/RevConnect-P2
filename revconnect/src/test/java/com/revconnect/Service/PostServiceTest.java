package com.revconnect.Service;

import com.revconnect.entity.*;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Test
    void testCreatePost() {

        User user = new User();

        user.setUsername("poster");
        user.setEmail("poster@mail.com");
        user.setPassword("123");
        user.setRole(Role.PERSONAL);

        user = userService.register(user);

        Post post =
                postService.createPost(
                        "Hello World",
                        "#test",
                        user
                );

        assertNotNull(post);
        assertNotNull(post.getId());
        assertEquals("Hello World", post.getContent());
    }

    @Test
    void testGetMyPosts() {

        User user = new User();

        user.setUsername("myposts");
        user.setEmail("myposts@mail.com");
        user.setPassword("123");
        user.setRole(Role.PERSONAL);

        user = userService.register(user);

        postService.createPost("Post 1", "#one", user);
        postService.createPost("Post 2", "#two", user);

        var posts =
                postService.getMyPosts(user.getId());

        assertEquals(2, posts.size());
    }
}
