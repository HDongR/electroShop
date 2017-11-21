package ryu.park.shop.utils;

import java.text.DecimalFormat;

public class CurrencyUtils {
	private static CurrencyUtils Instance = new CurrencyUtils();

	public static CurrencyUtils getInstance() {
		return Instance == null ? new CurrencyUtils() : Instance;
	}

	/**
	 * 숫자에 천단위마다 콤마 넣기
	 * @param int
	 * @return String
	 */
	public static String toNumFormat(long num) {
		DecimalFormat df = new DecimalFormat("#,###");
		return df.format(num);
	}
}
