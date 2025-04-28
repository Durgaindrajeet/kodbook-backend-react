package com.kodbook.Controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kodbook.Entity.LoginData;
import com.kodbook.Entity.User;
import com.kodbook.Entity.UserProfileDTO;
import com.kodbook.Service.PostService;
import com.kodbook.Service.UserService;

import jakarta.servlet.http.HttpSession;

@CrossOrigin(
	    origins = "http://localhost:3000",
	    methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS},
	    allowedHeaders = "*",
	    allowCredentials = "true"
	)
@RestController
public class UserController {
    
    @Autowired
    UserService service;
    
    @Autowired
    PostService postservice;
    
    @PostMapping("/signUp")
    public String addUser(@RequestBody User user) {
        if (!service.userExists(user.getUsername(), user.getEmail())) {
            service.addUser(user);
            return "sign up done";
        }
        return "user already exists";
    }
    
    @PostMapping("/login")
    public String login(@RequestBody LoginData user, HttpSession session) {
        if (service.validateUser(user.getUsername(), user.getPassword())) {
            session.setAttribute("username", user.getUsername());
            return "valid";
        }
        return "invalid";
    }
    
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/resetPassword")
    public String resetPassword(@RequestBody LoginData loginDTO) {
        boolean success = service.resetPassword(loginDTO.getUsername(), loginDTO.getPassword());
        System.out.println("Reset password called for " + loginDTO.getUsername() + ", new password: " + loginDTO.getPassword());
        return success ? "success" : "fail";
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUser(@PathVariable String username) {
        User user = service.getUser(username);
        return ResponseEntity.ok(user);
    }
    
    @GetMapping("/getProfile")
    public ResponseEntity<UserProfileDTO> getProfile(@RequestParam String username) {
        User user = service.getUser(username);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        UserProfileDTO profile = new UserProfileDTO(user);
        System.out.println("Sending profile: " + profile); // Debugging
        return ResponseEntity.ok(profile);
    }

    @PostMapping(value = "/updateProfile", consumes = "multipart/form-data")
    public ResponseEntity<User> updateProfile(
        @RequestParam String username,
        @RequestParam(required = false) String dob,
        @RequestParam(required = false) String gender,
        @RequestParam(required = false) String city,
        @RequestParam(required = false) String bio,
        @RequestParam(required = false) String college,
        @RequestParam(required = false) String github,
        @RequestParam(required = false) String linkedin,
        @RequestParam(required = false) MultipartFile profilePic) {
        
        try {
            User user = service.getUser(username);
            if (user == null) {
                return ResponseEntity.status(404).build();
            }

            if (dob != null) user.setDob(dob);
            if (gender != null) user.setGender(gender);
            if (city != null) user.setCity(city);
            if (bio != null) user.setBio(bio);
            if (college != null) user.setCollege(college);
            if (github != null) user.setGithub(github);
            if (linkedin != null) user.setLinkdin(linkedin);
            if (profilePic != null) {
                user.setProfilePic(profilePic.getBytes());
            }

            User updatedUser = service.updateUser(user);
            return ResponseEntity.ok(updatedUser);
            
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}
