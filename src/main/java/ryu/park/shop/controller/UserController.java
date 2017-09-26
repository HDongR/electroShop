package ryu.park.shop.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ryu.park.shop.service.UserService;
import ryu.park.shop.vo.UserVO;

/**
 * Handles requests for the application home page.
 */
@RequestMapping("/user/*")
@Controller
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
 
	@Autowired
	private UserService service;
	
	@RequestMapping(value = "find/{userId}", method = RequestMethod.GET)
	public String findUser(@PathVariable("userId") String userId, Model model) {
 
		UserVO uservo  = service.findUser(userId); 
		model.addAttribute("userInfo", uservo);
		//
		return "user/find_user";
	}
	
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login() {
		
		
		return "user/user_login";
	}
	
	
}
