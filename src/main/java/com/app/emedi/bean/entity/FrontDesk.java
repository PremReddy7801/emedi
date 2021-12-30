package com.app.emedi.bean.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "front_desk")
public class FrontDesk {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long userId;
	String name;
	@Column(unique = true)
	String username;
	String password;
	String phoneNumber;
	String email;
	@ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	private List<Role> roles;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	@Override
	public String toString() {
		return "FrontDesk [userId=" + userId + ", name=" + name + ", username=" + username + ", password=" + password
				+ ", phoneNumber=" + phoneNumber + ", email=" + email + ", roles=" + roles + "]";
	}
	public FrontDesk() {
		super();
	}
	public FrontDesk(Long userId, String name, String username, String password, String phoneNumber, String email,
			List<Role> roles) {
		super();
		this.userId = userId;
		
		this.name = name;
		this.username = username;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.roles = roles;
	}
	
	
}
