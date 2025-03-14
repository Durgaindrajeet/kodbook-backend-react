package com.kodbook.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.kodbook.Entity.Post;
import com.kodbook.Entity.User;
import com.kodbook.Service.PostService;
import com.kodbook.Service.UserService;

@Controller
public class UserController {
	
	@Autowired
	UserService service;
	@Autowired
	PostService postservice;
	
    @PostMapping("/signUp")
	public String addUser(@ModelAttribute User user) {
    	//user Exist?
    	String Username = user.getUsername();
    	String email = user.getEmail();
    	boolean status = service.userExists(Username,email);
    	if(status== false) {
    		service.addUser(user);
    	}
    	return "index";
		
	}
    
    @PostMapping("/login")
   	public String login(@RequestParam String username, @RequestParam String password, Model model) {
    	boolean status = service.validateUser(username,password);
    	if(status == true) {
    		List<Post> allPosts = postservice.fetchAllPosts();
    		model.addAttribute("allPosts", allPosts);
    		return"home";
    	}
    	else {
    		return "index";
    	}
    }
    
    @PostMapping("/updateProfile")
    public String updateProfile(@RequestParam String dob, @RequestParam String gender, @RequestParam String bio,@RequestParam String college,@RequestParam String github,@RequestParam String linkdin,@RequestParam MultipartFile profilePic) {
    	System.out.println(dob+ " "+bio);
    	
    	return "myProfile";
    }
}
