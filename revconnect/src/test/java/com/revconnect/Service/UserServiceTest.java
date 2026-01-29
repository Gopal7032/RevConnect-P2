package com.revconnect.Service;

import com.revconnect.entity.Role;
import com.revconnect.entity.User;

import org.junit.jupiter.api.Test;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional   // Rollback after each test
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void testRegisterUser() {

        User user = new User();

        user.setUsername("testuser");
        user.setEmail("testuser@mail.com");
        user.setPassword("12345");
        user.setRole(Role.PERSONAL);

        User savedUser = userService.register(user);

        assertNotNull(savedUser);
        assertNotNull(savedUser.getId());
        assertEquals("testuser", savedUser.getUsername());
    }

    @Test
    void testLoginSuccess() {

        User user = new User();

        user.setUsername("loginuser");
        user.setEmail("login@mail.com");
        user.setPassword("pass");
        user.setRole(Role.PERSONAL);

        userService.register(user);

        User loggedUser =
                userService.login("login@mail.com", "pass");

        assertNotNull(loggedUser);
        assertEquals("loginuser", loggedUser.getUsername());
    }

    @Test
    void testLoginFailure() {

        User loggedUser =
                userService.login("wrong@mail.com", "123");

        assertNull(loggedUser);
    }
}
