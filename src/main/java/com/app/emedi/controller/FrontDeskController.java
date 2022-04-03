package com.app.emedi.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.emedi.bean.Mail;
import com.app.emedi.bean.entity.FrontDesk;
import com.app.emedi.service.FrontDeskUserService;

@RestController
@RequestMapping("/emedi/frontdesk/")
@CrossOrigin
public class FrontDeskController {
	@Autowired
	FrontDeskUserService frontDeskUserService;
	
	@Autowired
	 JavaMailSender mailSender;
	
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
	@GetMapping("password")
	public String getPassword(@RequestParam String email) {
		System.out.println("email :"+email);
		Mail mail = new Mail();
		FrontDesk frontDesk = frontDeskUserService.getPassword(email);
		mail.setMailTo(frontDesk.getEmail());
		mail.setMailFrom("softwareengineerprem@gmail.com");
		mail.setMailSubject("Update password");
		mail.setMailContent("hello "+frontDesk.getUsername()+"/n click on below link to update new password //n http://localhost:4200/updatePassword");
		sendEmail(mail);
		return "sent succefully";
	}
	
	@GetMapping("user/updatepassword")
	public FrontDesk updatePassword(@RequestParam String email,@RequestParam String password) {
		System.out.println("111111");
		FrontDesk frontDesk = frontDeskUserService.getPassword(email);
		System.out.println("222222");
		
		if(frontDesk!=null) {
			System.out.println("33333"+frontDesk);
			FrontDesk frontDesks = frontDeskUserService.updatePassword(email, password);
			System.out.println("44444");
		}
		Mail mail = new Mail();
		mail.setMailTo(frontDesk.getEmail());
		mail.setMailFrom("softwareengineerprem@gmail.com");
		mail.setMailSubject("Update password sucessfully");
		mail.setMailContent("<h3>hello "+frontDesk.getUsername()+" /n your password is :"+password);
		sendEmail(mail);
		return null;
	}
	public void sendEmail(Mail mail) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
 
        try {
 
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
 
            mimeMessageHelper.setSubject(mail.getMailSubject());
            mimeMessageHelper.setFrom(new InternetAddress(mail.getMailFrom(), "developerqueries.com"));
            mimeMessageHelper.setTo(mail.getMailTo());
            mimeMessageHelper.setText(mail.getMailContent());
 
            mailSender.send(mimeMessageHelper.getMimeMessage());
 
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
	@GetMapping("signup/sample")
	public String sampleEndPoint() {
		return "running fine";
		}
}
