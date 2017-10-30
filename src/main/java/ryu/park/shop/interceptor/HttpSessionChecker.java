package ryu.park.shop.interceptor;

import java.util.Date;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Class		HttpSessionChecker.java
 * @packagename	ryu.park.shop.interceptor
 * @author		hodongryu
 * @since		2017.10.30.
 * @version		1.0
 * @see 			세션 생성 종료 체크리스너
 * <pre>
 * << 개정이력(Modification Information) >>
 *    수정일       수정자          수정내용
 *    -------      -------     -------------------
 *    2017.10.30.  hodongryu      최초작성
 * </pre>
 */
public class HttpSessionChecker implements HttpSessionListener {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http.HttpSessionEvent)
	 * 세션이 새로 생성되면 호출
	 */
	@Override
	public void sessionCreated(HttpSessionEvent event) {

		logger.info("Session ID".concat(event.getSession().getId()).concat(" created at ").concat(new Date().toString()));

	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.http.HttpSessionEvent)
	 * 세션이 종료되면 호출
	 */
	@Override
	public void sessionDestroyed(HttpSessionEvent event) {

		logger.info("Session ID".concat(event.getSession().getId()).concat(" destroyed at ").concat(new Date().toString()));

	}
}
