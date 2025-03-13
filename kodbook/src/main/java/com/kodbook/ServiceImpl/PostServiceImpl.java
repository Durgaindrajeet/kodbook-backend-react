package com.kodbook.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodbook.Entity.Post;
import com.kodbook.Repository.PostRepository;
import com.kodbook.Service.PostService;

@Service
public class PostServiceImpl  implements PostService{
	
	@Autowired
	PostRepository postrepo;

	@Override
	public void createPost(Post post) {
		
		postrepo.save(post);
	}

	@Override
	public List<Post> fetchAllPosts() {
		
		return postrepo.findAll();
	}

	@Override
	public void updatePost(Post post) {
		postrepo.save(post);
		
	}

	@Override
	public Post getPost(Long id) {
		// TODO Auto-generated method stub
		return postrepo.findById(id).get();
	}

}
