package ryu.park.shop.utils;

import org.apache.commons.codec.digest.DigestUtils;  
import org.springframework.stereotype.Component; 
 
@Component
public class SecurityUtils { 
	
	public String getHash(String originalString) { 
		return DigestUtils.sha256Hex(originalString + "ryu.park.shop");
	}
}
