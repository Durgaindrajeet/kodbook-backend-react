package com.kodbook.Service;

import java.util.List;

import com.kodbook.Entity.Post;

public interface PostService {

	void createPost(Post post);

	List<Post> fetchAllPosts();

	void updatePost(Post post);

	Post getPost(Long id);

}
