package com.revconnect.Service;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import com.revconnect.repository.PostRepository;
import com.revconnect.entity.*;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepo;

    public Post createPost(String content, String hashtags, User user) {
        Post post = new Post();
        post.setContent(content);
        post.setHashtags(hashtags);
        post.setUser(user);
        post.setCreatedAt(LocalDateTime.now());
        return postRepo.save(post);
    }

    public List<Post> getMyPosts(Long userId) {
        return postRepo.findByUserId(userId);
    }

    public List<Post> getAllPosts() {
        return postRepo.findAll();
    }
}