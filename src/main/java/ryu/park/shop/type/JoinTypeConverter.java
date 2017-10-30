package ryu.park.shop.type;

import org.springframework.core.convert.converter.Converter; 


/**
 * @Class		JoinTypeConverter.java
 * @packagename	ryu.park.shop.utils
 * @author		hodongryu
 * @since		2017.10.30.
 * @version		1.0
 * @see			JoinType 컨버터 : HttpRequest <-> Controller 에서 파라미터 처리
 * <pre>
 * << 개정이력(Modification Information) >>
 *    수정일       수정자          수정내용
 *    -------      -------     -------------------
 *    2017.10.30.  hodongryu      최초작성
 * </pre>
 */
public class JoinTypeConverter implements Converter<String, JoinType>{

	@Override
	public JoinType convert(String source) {
		 try {
	          return JoinType.valueOf(source);
	       } catch(Exception e) {
	          return JoinType.COMMON;
	       }
	}
}
