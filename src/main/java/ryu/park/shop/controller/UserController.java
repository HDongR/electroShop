package ryu.park.shop.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ryu.park.shop.service.UserService;
import ryu.park.shop.type.JoinType;
import ryu.park.shop.utils.SecurityUtils;
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

	@Autowired
	private SecurityUtils securityUtils;

	// check Email
	@RequestMapping(value = "check_email", method = RequestMethod.POST)
	public void checkEmail(@RequestParam("userEmail") String email, HttpServletResponse res)
			throws IOException {
		logger.info("checkEmail: " + email);
		UserVO user = service.findUser(email); 
		if (user == null) { 
			res.getWriter().print("isNotDuplicateEmail");
		} else if (user.getUserJoinType() == JoinType.MANAGER) { 
			res.getWriter().print("isManagerJoined");
		} else if (user.getUserJoinType() == JoinType.COMMON) { 
			res.getWriter().print("isCommonJoined");
		} else if (user.getUserJoinType() == JoinType.KAKAO) { 
			res.getWriter().print("isKakaoJoined");
		} else if (user.getUserJoinType() == JoinType.NAVER) { 
			res.getWriter().print("isNaverJoined");
		}
	}

	// login page
	@RequestMapping(value = "login_page", method = RequestMethod.GET)
	public String loginPage() {
		return "user/user_login";
	}

	// es login
	@RequestMapping(value = "eslogin", method = RequestMethod.POST)
	public void esLogin(@Valid UserVO user, BindingResult bindingResult, HttpServletRequest request,
			HttpServletResponse res) throws IOException {
		logger.info("esLogin");
		if (bindingResult.hasErrors()) { // 검증에 실패한 빈은 BindingResult에 담겨 뷰에 전달된다.
			logger.info("valid error");
			res.getWriter().print("validError");
		} else {
			user.setUserPassword(securityUtils.getHash(user.getUserPassword()));
			UserVO userVO = service.loginUser(user);
			if (userVO == null) {
				res.getWriter().print("invalid Email or Pwd");
			} else if (userVO.getUserJoinType() == JoinType.MANAGER) {
				res.getWriter().print("validManager");
			} else {
				HttpSession session = request.getSession(true);
				session.setAttribute("user", userVO);
				res.getWriter().print("loginComplete");
			}

		}
	}

	// logout
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest req) {
		logger.info("user is logout");
		HttpSession session = req.getSession();
		session.removeAttribute("user");

		return "redirect:/";
	}

	// join page
	@RequestMapping(value = "login/naver_callback", method = RequestMethod.GET)
	public String loginnaver_callback() {
		return "user/naver_callback";
	}

	// join page
	@RequestMapping(value = "join_page", method = RequestMethod.GET)
	public String joinPage() {
		return "user/user_join";
	}

	// es join
	@RequestMapping(value = "esjoin", method = RequestMethod.POST)
	public void joinUser(@Valid UserVO user, BindingResult bindingResult, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		logger.info("es join");
		if (bindingResult.hasErrors()) { // 검증에 실패한 빈은 BindingResult에 담겨 뷰에 전달된다.
			logger.info("valid error");
			response.getWriter().print("validError");
		} else {
			user.setUserPassword(securityUtils.getHash(user.getUserPassword()));
			logger.info(user.getUserPassword());
			int r = service.addUser(user);
			logger.info("result:" + r);
			if (r > 0) { // database 에러처리
				response.getWriter().print("joinUserComplete");
				HttpSession session = request.getSession();
				session.setAttribute("user", user);
			} else {
				response.getWriter().print("databaseError");
			}
		}
	}

	// sns join
	@RequestMapping(value = "snsjoin", method = RequestMethod.POST)
	public void kakaoJoin(@Valid UserVO user, BindingResult bindingResult, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		logger.info("snsJoin");
		if (bindingResult.hasErrors()) { // 검증에 실패한 빈은 BindingResult에 담겨 뷰에 전달된다.
			logger.info("valid error");
			response.getWriter().print("validError");
		} else {
			user.setUserPassword(securityUtils.getHash(user.getUserEmail()));
			logger.info(user.getUserEmail());
			int r = service.addUser(user);
			logger.info("result:" + r);
			if (r > 0) { // database 에러처리
				response.getWriter().print("joinUserComplete");
				HttpSession session = request.getSession();
				session.setAttribute("user", user);
			} else {
				response.getWriter().print("databaseError");
			}
		}
	}

	// sns login
	@RequestMapping(value = "snslogin", method = RequestMethod.POST)
	public void kakaoLogin(@Valid UserVO user, BindingResult bindingResult, HttpServletRequest request,
			HttpServletResponse res) throws IOException {
		logger.info("snsLogin");
		if (bindingResult.hasErrors()) { // 검증에 실패한 빈은 BindingResult에 담겨 뷰에 전달된다.
			logger.info("valid error:" + bindingResult.getFieldError());
			res.getWriter().print("validError");
		} else {
			user.setUserPassword(securityUtils.getHash(user.getUserEmail()));
			UserVO userVO = service.loginUser(user);

			if (userVO == null) {
				res.getWriter().print("invalid Email or Pwd");
			} else {
				HttpSession session = request.getSession(true);
				session.setAttribute("user", userVO);

				res.getWriter().print("loginComplete");
			}
		}
	}
	
	@RequestMapping(value = "modify_user_page", method = RequestMethod.POST)
	public String userModifyPage(@RequestParam("userEmail") String email, Model model) {
		logger.info("user_modify_page");  
		UserVO userVO = service.findUser(email); 
		model.addAttribute("userVO", userVO);
		return "user/user_modify";
	}
	
	@RequestMapping(value = "modify_user", method = RequestMethod.POST)
	public void modifyUser(@Valid UserVO userVO, BindingResult bindingResult, HttpServletRequest req,
			HttpServletResponse res) throws Exception, IOException {
		logger.info("modifyUser");
 
		if (bindingResult.hasErrors()) {
			logger.info("valid error" + bindingResult.getFieldError());
			res.getWriter().print("validError");
		} else { 
			int r = service.updateUser(userVO);
			if(r > 0)
				res.getWriter().print("completeUpdatedUser");
			else {
				res.getWriter().print("error");
			}
		}
	}

}
