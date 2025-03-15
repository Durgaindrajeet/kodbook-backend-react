package com.kodbook.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kodbook.Entity.Post;
import com.kodbook.Entity.User;
import com.kodbook.Service.PostService;
import com.kodbook.Service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class NavController {
	@Autowired
	PostService postService;
	
	@Autowired
	UserService service;
	
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/openSignUp")
	public String opeSignUp() {
		return "signUp";
	}
	
	@GetMapping("/openCreatePost")
	public String openCreatePost() {
		return "createPost";
	}
	
	@GetMapping("/goHome")
	public String login(Model model) {
		List<Post> allPosts = postService.fetchAllPosts();
		model.addAttribute("allPosts", allPosts);
		return "home";
	}
	
	@GetMapping("/openMyProfile")
	public String openMyProfile(Model model, HttpSession session) {
		String username = (String) session.getAttribute("username");
		User user = service.getUser(username);
		model.addAttribute("user", user);
		List<Post> myPosts = user.getPost();
		model.addAttribute("myPosts", myPosts);
		
		return "myProfile";
	}
	@GetMapping("/openEditProfile")
	public String openEditProfile(HttpSession session) {
		if(session.getAttribute("username")!=null)
			return "editProfile";
		else
			return "index";
	}
	@PostMapping("/visitProfile")
	public String visitProfile(@RequestParam String profileName, Model model) {
		User user = service.getUser(profileName);
		model.addAttribute("user", user);
		List<Post> myPosts = user.getPost();
		model.addAttribute("myPosts", myPosts);
		
		return "showUserProfile";
	}

@GetMapping("/logout")
public String logout(HttpSession session) {
	session.invalidate();
	return "index";
}
}

