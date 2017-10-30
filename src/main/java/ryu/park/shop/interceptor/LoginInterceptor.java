package ryu.park.shop.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import ryu.park.shop.vo.UserVO;
 

/**
 * @Class		LoginInterceptor.java
 * @packagename	ryu.park.shop.interceptor
 * @author		hodongryu
 * @since		2017.10.30.
 * @version		1.0
 * @see			사용자 로그인에 따른 페이지 분기 처리
 * <pre>
 * << 개정이력(Modification Information) >>
 *    수정일       수정자          수정내용
 *    -------      -------     -------------------
 *    2017.10.30.  hodongryu      최초작성
 * </pre>
 */
public class LoginInterceptor extends HandlerInterceptorAdapter{
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession(false);
		if(session == null) {
			logger.info("session is null");
			response.sendRedirect(request.getContextPath()+"/");
			return false;
		}
		
		UserVO user = (UserVO)session.getAttribute("user");
		
		//로그인 되었을 경우 /user/** 페이지 접근 제한
		if(user != null) {
			logger.info("user email is " + user.getUserEmail());
			response.sendRedirect(request.getContextPath()+"/");
			return false;
		}
		
		//로그아웃되면 /user/** 페이지 접근가능
		logger.info("user is null");
		return super.preHandle(request, response, handler);
	}

}
