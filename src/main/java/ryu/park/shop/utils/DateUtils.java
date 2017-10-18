package ryu.park.shop.utils;

import java.util.Date;

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
