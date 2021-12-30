package com.app.emedi.service;

import java.util.List;

import com.app.emedi.bean.entity.FrontDesk;

public interface FrontDeskUserService {

	public FrontDesk saveUser(FrontDesk frontDesk);

	public List<FrontDesk> allFrontDeskUsers();

	public FrontDesk frontDeskUser(String username);

	public FrontDesk deleteFrontDeskUser(String username);

	public FrontDesk updateFrontDeskUser(FrontDesk frontDesk);

}
