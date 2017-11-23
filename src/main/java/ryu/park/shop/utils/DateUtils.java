package ryu.park.shop.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Class		DateUtils.java
 * @packagename	ryu.park.shop.utils
 * @author		hodongryu
 * @since		2017.10.30.
 * @version		1.0
 * @see			날짜 유틸리티
 * <pre>
 * << 개정이력(Modification Information) >>
 *    수정일       수정자          수정내용
 *    -------      -------     -------------------
 *    2017.10.30.  hodongryu      최초작성
 * </pre>
 */
public class DateUtils { 
	
	public static String now() {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_HHmmss");
	 
		return format.format(date);
	}
	
	public static String now_short() {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월 dd일");
		return format.format(date);
	}
}
