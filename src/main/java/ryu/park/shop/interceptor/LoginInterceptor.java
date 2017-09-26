package ryu.park.shop.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import ryu.park.shop.vo.UserVO;
 

public class LoginInterceptor extends HandlerInterceptorAdapter{
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession(false);
		if(session == null) {
			response.sendRedirect(request.getContextPath()+"/user/login.jsp");
			return false;
		}
		
		UserVO user = (UserVO)session.getAttribute("userVO");
		
		if(user == null) {
			response.sendRedirect(request.getContextPath()+"/user/login.jsp");
			return false;
		}
		
		return true;
	}

}
