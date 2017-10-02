package ryu.park.shop.interceptor;

import java.util.Date;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpSessionChecker implements HttpSessionListener {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void sessionCreated(HttpSessionEvent event) {

		logger.info("Session ID".concat(event.getSession().getId()).concat(" created at ").concat(new Date().toString()));

	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {

		logger.info("Session ID".concat(event.getSession().getId()).concat(" destroyed at ").concat(new Date().toString()));

	}
}
