package com.kodbook.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.kodbook.Entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
    // Correct method name for querying posts by user's username
    List<Post> findByUser_Username(String username);
}