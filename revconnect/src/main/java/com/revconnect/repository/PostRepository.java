package com.revconnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.revconnect.entity.Post;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUserId(Long userId);
}
