package ryu.park.shop.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import ryu.park.shop.type.JoinType;
import ryu.park.shop.vo.UserVO;

/**
 * @Class		ManagerInterceptor.java
 * @packagename	ryu.park.shop.interceptor
 * @author		hodongryu
 * @since		2017.10.30.
 * @version		1.0
 * @see			매니저 페이지를 일반유저가 접근하지 못하게 처리
 * <pre>
 * << 개정이력(Modification Information) >>
 *    수정일       수정자          수정내용
 *    -------      -------     -------------------
 *    2017.10.30.  hodongryu      최초작성
 * </pre>
 */
public class ManagerInterceptor extends HandlerInterceptorAdapter {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	public static boolean isDEBUG = true;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession(false);
		if (session == null) {
			logger.info("session is null");
			response.sendRedirect(request.getContextPath() + "/manager/login_page");
			return false;
		}

		UserVO user = (UserVO) session.getAttribute("user"); 
		
		// 관리자가 아닌 로그인 되었을 경우 /manager/** 페이지 접근 제한
		if (user != null) {
			if (user.getUserJoinType() != JoinType.MANAGER) {
				logger.info("user'email is " + user.getUserEmail() + " not authorization");
				response.sendRedirect(request.getContextPath() + "/");
				return false;
			} 
		} else {
			logger.info("user is null");
			if(isDEBUG) { 
				UserVO userVO = new UserVO();
				userVO.setUserEmail("master@master.com");
				userVO.setUserJoinType(JoinType.MANAGER);
				session.setAttribute("user", userVO); 
			}
			response.sendRedirect(request.getContextPath() + "/manager/login_page");
			return false;
		}

		return super.preHandle(request, response, handler);
	}

}
