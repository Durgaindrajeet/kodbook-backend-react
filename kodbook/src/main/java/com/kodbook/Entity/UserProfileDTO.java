package com.kodbook.Entity;

import java.util.Base64;

public class UserProfileDTO {
    private String username;
    private String dob;
    private String gender;
    private String city;
    private String bio;
    private String college;
    private String github;
    private String linkdin;
    private String photoBase64;

    public UserProfileDTO() {}

    public UserProfileDTO(com.kodbook.Entity.User user) {
        this.username = user.getUsername();
        this.dob = user.getDob();
        this.gender = user.getGender();
        this.city = user.getCity();
        this.bio = user.getBio();
        this.college = user.getCollege();
        this.github = user.getGithub();
        this.linkdin = user.getLinkdin();
        if (user.getProfilePic() != null) {
            this.photoBase64 = Base64.getEncoder().encodeToString(user.getProfilePic());
        }
    }

    // Getters and setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getDob() { return dob; }
    public void setDob(String dob) { this.dob = dob; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public String getCollege() { return college; }
    public void setCollege(String college) { this.college = college; }

    public String getGithub() { return github; }
    public void setGithub(String github) { this.github = github; }

    public String getLinkdin() { return linkdin; }
    public void setLinkdin(String linkdin) { this.linkdin = linkdin; }

    public String getPhotoBase64() { return photoBase64; }
    public void setPhotoBase64(String photoBase64) { this.photoBase64 = photoBase64; }

	@Override
	public String toString() {
		return "UserProfileDTO [username=" + username + ", dob=" + dob + ", gender=" + gender + ", city=" + city
				+ ", bio=" + bio + ", college=" + college + ", github=" + github + ", linkdin=" + linkdin
				+ ", photoBase64=" + photoBase64 + "]";
	}
    
    
}
