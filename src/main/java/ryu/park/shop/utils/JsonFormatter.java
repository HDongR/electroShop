package ryu.park.shop.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @Class		JsonFormatter.java
 * @packagename	ryu.park.shop.utils
 * @author		hodongryu
 * @since		2017.10.30.
 * @version		1.0
 * @see			제이슨포매터 싱글톤 객체 리턴
 * <pre>
 * << 개정이력(Modification Information) >>
 *    수정일       수정자          수정내용
 *    -------      -------     -------------------
 *    2017.10.30.  hodongryu      최초작성
 * </pre>
 */
public enum JsonFormatter {
	  INSTANCE;
	  private final ObjectMapper mapper = new ObjectMapper();

	  private JsonFormatter(){
	    // Perform any configuration on the ObjectMapper here.
	  }

	  public ObjectMapper getObjectMapper() {
	    return mapper;
	  }
	}