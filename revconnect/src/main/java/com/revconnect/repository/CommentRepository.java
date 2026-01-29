package com.revconnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.revconnect.entity.Comment;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(Long postId);
}