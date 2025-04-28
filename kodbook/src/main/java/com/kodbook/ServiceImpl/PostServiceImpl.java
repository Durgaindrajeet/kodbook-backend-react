package com.kodbook.ServiceImpl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kodbook.Entity.Post;
import com.kodbook.Repository.PostRepository;
import com.kodbook.Service.PostService;

@Service
public class PostServiceImpl implements PostService {
    
    @Autowired
    private PostRepository postRepository;

    @Override
    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public List<Post> fetchAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Post updatePost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post getPost(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    @Override
    public List<Post> getPostsByUser(String username) {
        return postRepository.findByUser_Username(username);
    }

	@Override
	public List<Post> findByUsername(String username) {
		// TODO Auto-generated method stub
		return postRepository.findByUser_Username(username);
	}
}