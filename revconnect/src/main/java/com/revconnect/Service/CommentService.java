package com.revconnect.Service;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import com.revconnect.repository.*;
import com.revconnect.entity.*;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepo;
    private final PostRepository postRepo;

    public Comment addComment(Long postId, String text, User user) {
        Post post = postRepo.findById(postId).orElse(null);

        if (post == null) return null;

        Comment c = new Comment();
        c.setText(text);
        c.setUser(user);
        c.setPost(post);
        c.setCreatedAt(LocalDateTime.now());

        return commentRepo.save(c);
    }

    public List<Comment> getComments(Long postId) {
        return commentRepo.findByPostId(postId);
    }
}
