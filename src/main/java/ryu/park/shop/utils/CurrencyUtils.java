package ryu.park.shop.utils;

import java.text.DecimalFormat;

public class CurrencyUtils { 
 
	/**
	 * @method		toNumFormat
	 * @param 		num
	 * @return		포맷된 값
	 * @author		hodongryu
	 * @since		2017.11.23.
	 * @version		1.0
	 * @see 			숫자에 천단위마다 콤마 넣기
	 * <pre>
	 * << 개정이력(Modification Information) >>
	 *    수정일       수정자          수정내용
	 *    -------      -------     -------------------
	 *    2017.11.23.  hodongryu      최초작성
	 * </pre>
	 */
	public static String toNumFormat(long num) { 
		DecimalFormat df = new DecimalFormat("#,###");
		String nu = df.format(num); 
		return nu;
	}
}
