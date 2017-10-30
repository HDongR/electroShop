package ryu.park.shop.utils;

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
	private static DateUtils Instance = new DateUtils();
	
	public static DateUtils getInstance() {
		return Instance == null ? new DateUtils() : Instance;
	}
	
	public String now() {
		Date date = new Date();
		int year = date.getYear() + 1900;
		int month = date.getMonth();
		String monthStr = "";

		if (month < 10)
			monthStr = "0" + month;
		else
			monthStr = "" + month;
		
		return year + monthStr;
	}
}
