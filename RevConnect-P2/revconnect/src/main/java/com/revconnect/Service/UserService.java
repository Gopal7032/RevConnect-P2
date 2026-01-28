package com.revconnect.service;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import com.revconnect.repository.UserRepository;
import com.revconnect.entity.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepo;

    public User register(User user) {
        return userRepo.save(user);
    }

    public User login(String email, String password) {
        return userRepo.findByEmail(email)
                .filter(u -> u.getPassword().equals(password))
                .orElse(null);
    }
}