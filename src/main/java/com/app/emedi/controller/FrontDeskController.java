package com.app.emedi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.emedi.bean.entity.FrontDesk;
import com.app.emedi.service.FrontDeskUserService;

@RestController
@RequestMapping("/emedi/frontdesk/")
@CrossOrigin
public class FrontDeskController {
	@Autowired
	FrontDeskUserService frontDeskUserService;
	
	@PostMapping("signup")
	public FrontDesk saveUser(@RequestBody FrontDesk frontDesk) {
		System.out.println("xxxxxxxxxxxxxxxxxxxxx");
		return frontDeskUserService.saveUser(frontDesk);
		
	}
	@GetMapping("user/getallntdesk")
	public List<FrontDesk> allFrontDeskUseres() {
		return frontDeskUserService.allFrontDeskUsers();
	}
	@GetMapping("user/getfrontdesk")
	public FrontDesk frontDeskUser(@RequestParam String username) {
		return frontDeskUserService.frontDeskUser(username);
	}
	@DeleteMapping("user/deletefrontdesk")
	public FrontDesk deleteFrontDeskUser(@RequestParam String username) {
		return frontDeskUserService.deleteFrontDeskUser(username);
	}
	@PutMapping("user/updatefrontdesk")
	public FrontDesk updateFrontDeskUser(@RequestBody FrontDesk frontDesk) {
		return frontDeskUserService.updateFrontDeskUser(frontDesk);
	}
	@GetMapping("user/password")
	public String getPassword(@RequestParam String email) {
		sendPasswordTomail(email);
		return frontDeskUserService.getPassword(email);
	}
	
	@PutMapping("user/updatepassword")
	public FrontDesk updatePassword(@RequestParam String email,@RequestParam String password) {
		return frontDeskUserService.updatePassword(email, password);
	}
	private void sendPasswordTomail(String email) {
		
	}
	@GetMapping("signup/sample")
	public String sampleEndPoint() {
		return "running fine";
		}
}
