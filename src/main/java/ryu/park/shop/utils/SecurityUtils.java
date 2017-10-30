package ryu.park.shop.utils;

import org.apache.commons.codec.digest.DigestUtils;  
import org.springframework.stereotype.Component; 
 
/**
 * @Class		SecurityUtils.java
 * @packagename	ryu.park.shop.utils
 * @author		hodongryu
 * @since		2017.10.30.
 * @version		1.0
 * @see			비밀번호 해싱 처리 유틸리티 (자바빈으로 등록)
 * <pre>
 * << 개정이력(Modification Information) >>
 *    수정일       수정자          수정내용
 *    -------      -------     -------------------
 *    2017.10.30.  hodongryu      최초작성
 * </pre>
 */
@Component
public class SecurityUtils { 
	
	public String getHash(String originalString) { 
		return DigestUtils.sha256Hex(originalString + "ryu.park.shop");
	}
}
