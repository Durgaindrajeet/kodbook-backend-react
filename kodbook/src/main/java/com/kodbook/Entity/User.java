package com.kodbook.Entity;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String username;
	private String email;
	private String password;
	private String dob;
	private String gender;
	private String city;
	private String bio;
	private String college;
	private String github;
	private String linkdin;
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Post> post; 

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(columnDefinition = "LONGBLOB")
		private byte[] profilePic;


	public String getPhotoBase64() {
		if(profilePic == null) {
			return null;
		}
		return Base64.getEncoder().encodeToString(profilePic);
	}
	public User() {
		super();
	}
	public User(Long id, String username, String email, String password, String dob, String gender, String city,
			String bio, String college, String github, String linkdin, List<Post> post, byte[] profilePic) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.dob = dob;
		this.gender = gender;
		this.city = city;
		this.bio = bio;
		this.college = college;
		this.github = github;
		this.linkdin = linkdin;
		this.post = post;
		this.profilePic = profilePic;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getBio() {
		return bio;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}
	public String getCollege() {
		return college;
	}
	public void setCollege(String college) {
		this.college = college;
	}
	public String getGithub() {
		return github;
	}
	public void setGithub(String github) {
		this.github = github;
	}
	public String getLinkdin() {
		return linkdin;
	}
	public void setLinkdin(String linkdin) {
		this.linkdin = linkdin;
	}
	public List<Post> getPost() {
		return post;
	}
	public void setPost(List<Post> post) {
		this.post = post;
	}
	public byte[] getProfilePic() {
		return profilePic;
	}
	public void setProfilePic(byte[] profilePic) {
		this.profilePic = profilePic;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", email=" + email + ", password=" + password + ", dob="
				+ dob + ", gender=" + gender + ", city=" + city + ", bio=" + bio + ", college=" + college + ", github="
				+ github + ", linkdin=" + linkdin + ", post=" + post + ", profilePic=" + Arrays.toString(profilePic)
				+ "]";
	}

}
