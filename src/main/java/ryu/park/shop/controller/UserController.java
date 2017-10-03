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
import org.springframework.web.bind.annotation.PathVariable;
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
	public void checkEmail(@RequestParam("email") String email, HttpSession session, HttpServletResponse response)
			throws IOException {
		logger.info("checkEmail: " + email);
		UserVO user = service.findUser(email);

		if (user == null) {
			logger.info("checkEmail: null");
			response.getWriter().print("isNotDuplicateEmail");
		} else if( user.getJoinType() == JoinType.MANAGER) {
			logger.info("checkEmail: MANAGER");
			response.getWriter().print("isManagerJoined");
		} else if (user.getJoinType() == JoinType.COMMON) {
			logger.info("checkEmail: COMMON");
			response.getWriter().print("isCommonJoined");
		} else if (user.getJoinType() == JoinType.KAKAO) {
			logger.info("checkEmail: KAKAO");
			response.getWriter().print("isKakaoJoined");
		} else if (user.getJoinType() == JoinType.NAVER) {
			logger.info("checkEmail: NAVER");
			response.getWriter().print("isNaverJoined");
		}
	}

	// login page
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login() {
		return "user/user_login";
	}

	// es login
	@RequestMapping(value = "eslogin", method = RequestMethod.POST)
	public void esLogin(@Valid UserVO user, BindingResult bindingResult, HttpServletRequest request,
			HttpServletResponse res) throws IOException {
		if (bindingResult.hasErrors()) { // 검증에 실패한 빈은 BindingResult에 담겨 뷰에 전달된다.
			logger.info("valid error");
			res.getWriter().print("validError");
		} else {
			user.setPassword(securityUtils.getHash(user.getPassword()));
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
	@RequestMapping(value = "join", method = RequestMethod.GET)
	public String join() {
		return "user/join_user";
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
			user.setPassword(securityUtils.getHash(user.getPassword()));
			logger.info(user.getPassword());
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
			user.setPassword(securityUtils.getHash(user.getEmail()));
			logger.info(user.getPassword());
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
			logger.info("valid error");
			res.getWriter().print("validError");
		} else {
			user.setPassword(securityUtils.getHash(user.getEmail()));
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
 
}
