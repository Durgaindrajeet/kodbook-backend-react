package com.kodbook.Service;

import java.util.List;
import com.kodbook.Entity.Post;

public interface PostService {
    Post createPost(Post post);
    List<Post> fetchAllPosts();
    Post updatePost(Post post);
    Post getPost(Long id);
    List<Post> getPostsByUser(String username);
    List<Post> findByUsername(String username);
}