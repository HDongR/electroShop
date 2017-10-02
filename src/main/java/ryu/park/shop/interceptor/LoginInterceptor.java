package ryu.park.shop.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import ryu.park.shop.vo.UserVO;
 

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
			logger.info("user email is " + user.getEmail());
			response.sendRedirect(request.getContextPath()+"/");
			return false;
		}
		
		//로그아웃되면 /user/** 페이지 접근가능
		logger.info("user is null");
		return super.preHandle(request, response, handler);
	}

}
