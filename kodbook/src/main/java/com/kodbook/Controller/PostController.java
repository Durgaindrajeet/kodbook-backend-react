package com.kodbook.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kodbook.Entity.Post;
import com.kodbook.Entity.PostResponse;
import com.kodbook.Entity.User;
import com.kodbook.Entity.UserPosted;
import com.kodbook.Service.PostService;
import com.kodbook.Service.UserService;

import jakarta.servlet.http.HttpSession;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")

public class PostController {

    @Autowired
    PostService service;
    
    @Autowired
    UserService userService;
    
    @PostMapping("/createPost")
    public ResponseEntity<String> createPost(
            @RequestParam("caption") String caption,
            @RequestParam("photo") MultipartFile photo,
            @RequestParam("username") String username) {

        try {
            // 1. Validate inputs
            if (caption == null || caption.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Caption cannot be empty");
            }
            
            if (photo == null || photo.isEmpty()) {
                return ResponseEntity.badRequest().body("Photo cannot be empty");
            }

            // 2. Get user
            User user = userService.getUser(username);
            if (user == null) {
                return ResponseEntity.badRequest().body("User not found");
            }

            // 3. Create post
            Post post = new Post();
            post.setUser(user);
            post.setCaption(caption);
            post.setPhoto(photo.getBytes());
            
            // 4. Save post
            service.createPost(post);

            // 5. Update user's posts
            List<Post> posts = user.getPost() != null ? user.getPost() : new ArrayList<>();
            posts.add(post);
            user.setPost(posts);
            userService.updateUser(user);

            return ResponseEntity.ok("Post created successfully");
            
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing image: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Server error: " + e.getMessage());
        }
    }
    @GetMapping("/getUserProfile")
    public ResponseEntity<Map<String, Object>> getUserProfile(@RequestParam String username) {
        User user = userService.getUser(username);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        Map<String, Object> userData = new HashMap<>();
        userData.put("username", user.getUsername());
        userData.put("dob", user.getDob());
        userData.put("gender", user.getGender());
        userData.put("city", user.getCity());
        userData.put("college", user.getCollege());
        userData.put("bio", user.getBio());
        userData.put("github", user.getGithub());
        userData.put("linkdin", user.getLinkdin());

        if (user.getProfilePic() != null) {
            String base64Image = Base64.getEncoder().encodeToString(user.getProfilePic());
            userData.put("photoBase64", base64Image);
        }

        return ResponseEntity.ok(userData);
    }


    @GetMapping("/posts/user")
    public ResponseEntity<List<PostResponse>> getUserPosts(
            @RequestParam String username) {
        List<Post> posts = service.findByUsername(username);
        
        List<PostResponse> responses = posts.stream()
            .map(p -> new PostResponse(
                p.getId(), 
                p.getCaption(), 
                p.getLikes(), 
                p.getComments(), 
                p.getPhoto(), 
                new UserPosted(p.getUser().getUsername(), p.getUser().getProfilePic())
            ))
            .collect(Collectors.toList());
            
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/getAllPosts")
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        List<Post> posts = service.fetchAllPosts();
        List<PostResponse> responses = new ArrayList<>();
        
        for (Post p : posts) {
            if (p.getUser() == null) {
                continue; // Skip posts with null users
            }
            
            responses.add(new PostResponse(
                p.getId(), 
                p.getCaption(), 
                p.getLikes(), 
                p.getComments(), 
                p.getPhoto(), 
                new UserPosted(p.getUser().getUsername(), p.getUser().getProfilePic())

            ));
        }
        
        return ResponseEntity.ok(responses);
    }

    
    @GetMapping("/getUserPosts")
    public ResponseEntity<List<PostResponse>> getUserPosts1(
            @RequestParam String username) {
        
        try {
            List<Post> posts = service.findByUsername(username);
            
            // Convert Posts to PostResponse with user information
            List<PostResponse> responses = posts.stream()
                .map(post -> {
                    UserPosted userPosted = new UserPosted(
                        post.getUser().getUsername(),
                        post.getUser().getProfilePic()
                    );
                    return new PostResponse(
                        post.getId(),
                        post.getCaption(),
                        post.getLikes(),
                        post.getComments(),
                        post.getPhoto(),
                        userPosted
                    );
                })
                .collect(Collectors.toList());
            
            return ResponseEntity.ok(responses);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PostMapping("/likePost")
    public ResponseEntity<PostResponse> likePost(@RequestBody Map<String, Long> requestBody) {
        Long id = requestBody.get("id");
        
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }

        Post post = service.getPost(id);
        if (post == null) {
            return ResponseEntity.notFound().build();
        }
        
        post.setLikes(post.getLikes() + 1);
        service.updatePost(post);
        
        PostResponse response = new PostResponse(
            post.getId(),
            post.getCaption(),
            post.getLikes(),
            post.getComments(),
            post.getPhoto(),
            new UserPosted(post.getUser().getUsername(), post.getUser().getProfilePic())
        );
        
        return ResponseEntity.ok(response);
    }

    @PostMapping("/addComment")
    public ResponseEntity<PostResponse> addComment(@RequestBody Map<String, String> requestBody) {
        try {
            Long id = Long.parseLong(requestBody.get("id"));
            String comment = requestBody.get("comment");

            if (comment == null || comment.trim().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            Post post = service.getPost(id);
            if (post == null) {
                return ResponseEntity.notFound().build();
            }

            if (post.getComments() == null) {
                post.setComments(new ArrayList<>());
            }
            post.getComments().add(comment);

            service.updatePost(post);

            PostResponse response = new PostResponse(
                post.getId(),
                post.getCaption(),
                post.getLikes(),
                post.getComments(),
                post.getPhoto(),
                new UserPosted(post.getUser().getUsername(), post.getUser().getProfilePic())
            );

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}