package ryu.park.shop.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ryu.park.shop.service.CartService;
import ryu.park.shop.service.UserService;
import ryu.park.shop.type.JoinType;
import ryu.park.shop.vo.CartVO;
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
	private CartService cartService;
 
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
	@RequestMapping(value = "login_page", method = RequestMethod.GET, produces = {"text/html"})
	public String loginPage() {
		return "user/user_login";
	}
 
	/**
	 * @method		login : 로그인
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
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public void login(@Valid UserVO user, BindingResult bindingResult, HttpSession session, HttpServletRequest req,
			HttpServletResponse res) throws IOException {
		logger.info("login");
		if (bindingResult.hasErrors()) {
			logger.info("valid error:" + bindingResult.getFieldError());
			res.getWriter().print("validError");
		} else {
			UserVO userVO = service.loginUser(user, session);
			if (userVO == null) {
				res.getWriter().print("invalid Email or Pwd");
			} else if (userVO.getUserJoinType() == JoinType.MANAGER) {
				res.getWriter().print("validManager");
			} else {
				req.getSession(true).setAttribute("user", userVO);
				cartService.translateCartList(userVO, session);
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
	@RequestMapping(value = "login/naver_callback", method = RequestMethod.GET, produces = {"text/html"})
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
	@RequestMapping(value = "join_page", method = RequestMethod.GET, produces = {"text/html"})
	public String joinPage() {
		return "user/user_join";
	} 
	 
	/**
	 * @method		joinUser : 회원가입
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
	@RequestMapping(value = "join", method = RequestMethod.POST)
	public void joinUser(@Valid UserVO user, BindingResult bindingResult, HttpServletRequest req,
			HttpServletResponse res) throws IOException {
		logger.info("es join");
		if (bindingResult.hasErrors()) {
			logger.info("valid error");
			res.getWriter().print("validError");
		} else { 
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
	@RequestMapping(value = "modify_user_page", method = RequestMethod.POST, produces = {"text/html"})
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

	/**
	 * @method		cartList : 기본적으로 모든 페이지에 장바구니 리스트 포함
	 * @param session : 현재세션
	 * @return
	 * @author		hodongryu
	 * @since		2017.10.30.
	 * @version		1.0
	 * @see			모든 페이지에 장바구니 리스트를 포함시킴
	 * <pre>
	 * << 개정이력(Modification Information) >>
	 *    수정일       수정자          수정내용
	 *    -------      -------     -------------------
	 *    2017.10.30.  hodongryu      최초작성
	 * </pre>
	 */
	@ModelAttribute("cartList")
	public List<CartVO> cartList(HttpSession session) {
		return cartService.getCartList(session);
	} 
}
