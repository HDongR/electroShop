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
 * @Class		UserController.java
 * @packagename	ryu.park.shop.controller
 * @author		hodongryu
 * @since		2017.10.30.
 * @version		1.0
 * @see			유저 로그인 관련 컨트롤러
 * <pre>
 * << 개정이력(Modification Information) >>
 *    수정일       수정자          수정내용
 *    -------      -------     -------------------
 *    2017.10.30.  hodongryu      최초작성
 * </pre>
 */ 
@RequestMapping("/user/*")
@Controller
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService service;

	@Autowired
	private SecurityUtils securityUtils;
 
	/**
	 * @method		checkEmail : 이메일 중복체크
	 * @param email
	 * @param res
	 * @throws IOException
	 * @author		hodongryu
	 * @since		2017.10.30.
	 * @version		1.0
	 * @see
	 * <pre>
	 * << 개정이력(Modification Information) >>
	 *    수정일       수정자          수정내용
	 *    -------      -------     -------------------
	 *    2017.10.30.  hodongryu      최초작성
	 * </pre>
	 */
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
 
	/**
	 * @method		loginPage : 로그인페이지
	 * @return
	 * @author		hodongryu
	 * @since		2017.10.30.
	 * @version		1.0
	 * @see
	 * <pre>
	 * << 개정이력(Modification Information) >>
	 *    수정일       수정자          수정내용
	 *    -------      -------     -------------------
	 *    2017.10.30.  hodongryu      최초작성
	 * </pre>
	 */
	@RequestMapping(value = "login_page", method = RequestMethod.GET)
	public String loginPage() {
		return "user/user_login";
	}
 
	/**
	 * @method		esLogin : 사이트 로그인
	 * @param user : 유저모델
	 * @param bindingResult : 유저모델 바인딩 결과
	 * @param req
	 * @param res
	 * @throws IOException
	 * @author		hodongryu
	 * @since		2017.10.30.
	 * @version		1.0
	 * @see
	 * <pre>
	 * << 개정이력(Modification Information) >>
	 *    수정일       수정자          수정내용
	 *    -------      -------     -------------------
	 *    2017.10.30.  hodongryu      최초작성
	 * </pre>
	 */
	@RequestMapping(value = "eslogin", method = RequestMethod.POST)
	public void esLogin(@Valid UserVO user, BindingResult bindingResult, HttpServletRequest req,
			HttpServletResponse res) throws IOException {
		logger.info("esLogin");
		if (bindingResult.hasErrors()) {
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
				HttpSession session = req.getSession(true);
				session.setAttribute("user", userVO);
				res.getWriter().print("loginComplete");
			}

		}
	}

	 
	/**
	 * @method		logout : 로그아웃
	 * @param req
	 * @return
	 * @author		hodongryu
	 * @since		2017.10.30.
	 * @version		1.0
	 * @see
	 * <pre>
	 * << 개정이력(Modification Information) >>
	 *    수정일       수정자          수정내용
	 *    -------      -------     -------------------
	 *    2017.10.30.  hodongryu      최초작성
	 * </pre>
	 */
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest req) {
		logger.info("user is logout");
		HttpSession session = req.getSession();
		session.removeAttribute("user");

		return "redirect:/";
	}

	 
	/**
	 * @method		loginnaver_callback : 네이버 로그인 콜백페이지
	 * @return
	 * @author		hodongryu
	 * @since		2017.10.30.
	 * @version		1.0
	 * @see
	 * <pre>
	 * << 개정이력(Modification Information) >>
	 *    수정일       수정자          수정내용
	 *    -------      -------     -------------------
	 *    2017.10.30.  hodongryu      최초작성
	 * </pre>
	 */
	@RequestMapping(value = "login/naver_callback", method = RequestMethod.GET)
	public String loginnaver_callback() {
		return "user/naver_callback";
	}

	 
	/**
	 * @method		joinPage : 사이트 회원가입 페이지
	 * @return
	 * @author		hodongryu
	 * @since		2017.10.30.
	 * @version		1.0
	 * @see
	 * <pre>
	 * << 개정이력(Modification Information) >>
	 *    수정일       수정자          수정내용
	 *    -------      -------     -------------------
	 *    2017.10.30.  hodongryu      최초작성
	 * </pre>
	 */
	@RequestMapping(value = "join_page", method = RequestMethod.GET)
	public String joinPage() {
		return "user/user_join";
	} 
	 
	/**
	 * @method		joinUser : 사이트 내 회원가입
	 * @param user : 유저모델
	 * @param bindingResult : 유저모델 바인딩 결과 
	 * @param req
	 * @param res
	 * @throws IOException
	 * @author		hodongryu
	 * @since		2017.10.30.
	 * @version		1.0
	 * @see
	 * <pre>
	 * << 개정이력(Modification Information) >>
	 *    수정일       수정자          수정내용
	 *    -------      -------     -------------------
	 *    2017.10.30.  hodongryu      최초작성
	 * </pre>
	 */
	@RequestMapping(value = "esjoin", method = RequestMethod.POST)
	public void joinUser(@Valid UserVO user, BindingResult bindingResult, HttpServletRequest req,
			HttpServletResponse res) throws IOException {
		logger.info("es join");
		if (bindingResult.hasErrors()) {
			logger.info("valid error");
			res.getWriter().print("validError");
		} else {
			user.setUserPassword(securityUtils.getHash(user.getUserPassword()));
			logger.info(user.getUserPassword());
			int r = service.addUser(user);
			logger.info("result:" + r);
			if (r > 0) {
				res.getWriter().print("joinUserComplete");
				HttpSession session = req.getSession();
				session.setAttribute("user", user);
			} else {
				res.getWriter().print("databaseError");
			}
		}
	}

 
	/**
	 * @method		snsJoin : sns회원가입
	 * @param user : 유저모델 
	 * @param bindingResult : 유저모델 바인딩 결과
	 * @param req
	 * @param res
	 * @throws IOException
	 * @author		hodongryu
	 * @since		2017.10.30.
	 * @version		1.0
	 * @see
	 * <pre>
	 * << 개정이력(Modification Information) >>
	 *    수정일       수정자          수정내용
	 *    -------      -------     -------------------
	 *    2017.10.30.  hodongryu      최초작성
	 * </pre>
	 */
	@RequestMapping(value = "snsjoin", method = RequestMethod.POST)
	public void snsJoin(@Valid UserVO user, BindingResult bindingResult, HttpServletRequest req,
			HttpServletResponse res) throws IOException {
		logger.info("snsJoin");
		if (bindingResult.hasErrors()) {
			logger.info("valid error");
			res.getWriter().print("validError");
		} else {
			user.setUserPassword(securityUtils.getHash(user.getUserEmail()));
			logger.info(user.getUserEmail());
			int r = service.addUser(user);
			logger.info("result:" + r);
			if (r > 0) {
				res.getWriter().print("joinUserComplete");
				HttpSession session = req.getSession();
				session.setAttribute("user", user);
			} else {
				res.getWriter().print("databaseError");
			}
		}
	}

	 
	/**
	 * @method		snsLogin : sns로그인
	 * @param user : 유저모델 
	 * @param bindingResult : 유저모델 바인딩 결과
	 * @param req
	 * @param res
	 * @throws IOException
	 * @author		hodongryu
	 * @since		2017.10.30.
	 * @version		1.0
	 * @see
	 * <pre>
	 * << 개정이력(Modification Information) >>
	 *    수정일       수정자          수정내용
	 *    -------      -------     -------------------
	 *    2017.10.30.  hodongryu      최초작성
	 * </pre>
	 */
	@RequestMapping(value = "snslogin", method = RequestMethod.POST)
	public void snsLogin(@Valid UserVO user, BindingResult bindingResult, HttpServletRequest req,
			HttpServletResponse res) throws IOException {
		logger.info("snsLogin");
		if (bindingResult.hasErrors()) {
			logger.info("valid error:" + bindingResult.getFieldError());
			res.getWriter().print("validError");
		} else {
			user.setUserPassword(securityUtils.getHash(user.getUserEmail()));
			UserVO userVO = service.loginUser(user);

			if (userVO == null) {
				res.getWriter().print("invalid Email or Pwd");
			} else {
				HttpSession session = req.getSession(true);
				session.setAttribute("user", userVO);

				res.getWriter().print("loginComplete");
			}
		}
	}
	
	/**
	 * @method		userModifyPage : 유저정보 수정 페이지
	 * @param email
	 * @param model
	 * @return
	 * @author		hodongryu
	 * @since		2017.10.30.
	 * @version		1.0
	 * @see
	 * <pre>
	 * << 개정이력(Modification Information) >>
	 *    수정일       수정자          수정내용
	 *    -------      -------     -------------------
	 *    2017.10.30.  hodongryu      최초작성
	 * </pre>
	 */
	@RequestMapping(value = "modify_user_page", method = RequestMethod.POST)
	public String userModifyPage(@RequestParam("userEmail") String email, Model model) {
		logger.info("user_modify_page");  
		UserVO userVO = service.findUser(email); 
		model.addAttribute("userVO", userVO);
		return "user/user_modify";
	}
	
	/**
	 * @method		modifyUser : 유저정보 수정
	 * @param userVO : 유저모델
	 * @param bindingResult : 유저모델 바인딩 결과
	 * @param req 
	 * @param res
	 * @throws Exception
	 * @throws IOException
	 * @author		hodongryu
	 * @since		2017.10.30.
	 * @version		1.0
	 * @see
	 * <pre>
	 * << 개정이력(Modification Information) >>
	 *    수정일       수정자          수정내용
	 *    -------      -------     -------------------
	 *    2017.10.30.  hodongryu      최초작성
	 * </pre>
	 */
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
