package com.app.emedi.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.emedi.bean.entity.FrontDesk;
import com.app.emedi.repository.FrontDesUserkRepo;
@Service
@Transactional
public class FrontDeskUserServiceImpl implements FrontDeskUserService, UserDetailsService {
	@Autowired
	FrontDesUserkRepo frontDesUserkRepo;
	
	@Autowired
	PasswordEncoder encoder;
	
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		FrontDesk frontDesk = frontDesUserkRepo.findByUsername(username);
		if (frontDesk!=null) {
			Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
			frontDesk.getRoles().forEach(role->authorities.add(new SimpleGrantedAuthority(role.getName())));
			return new User(frontDesk.getUsername(), frontDesk.getPassword(), authorities);
		} else {
			throw new RuntimeException("user not found");
		}
	}

	@Override
	public FrontDesk saveUser(FrontDesk frontDesk) {
		frontDesk.setUsername(frontDesk.getUsername().toLowerCase());
		frontDesk.setPassword(encoder.encode(frontDesk.getPassword()));
		return frontDesUserkRepo.save(frontDesk);
	}

	@Override
	public List<FrontDesk> allFrontDeskUsers() {
		return frontDesUserkRepo.findAll();
	}

	@Override
	public FrontDesk frontDeskUser(String username) {
		return frontDesUserkRepo.findByUsername(username);
	}

	@Override
	public FrontDesk deleteFrontDeskUser(String username) {
		return frontDesUserkRepo.deleteByUsername(username);
	}

	@Override
	public FrontDesk updateFrontDeskUser(FrontDesk frontDesk) {
		return frontDesUserkRepo.save(frontDesk);
	}

	@Override
	public FrontDesk getPassword(String email) {
		return frontDesUserkRepo.findByEmail(email);
	}

	@Override
	public FrontDesk updatePassword(String email, String password) {
		String encodedPassword = encoder.encode(password);
		frontDesUserkRepo.updatePassword(email,encodedPassword);
		return null;
	}

}
