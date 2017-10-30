package ryu.park.shop.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ryu.park.shop.service.ThirdApiService;

/**
 * @Class		ThirdApiController.java
 * @packagename	ryu.park.shop.controller
 * @author		hodongryu
 * @since		2017.10.30.
 * @version		1.0
 * @see			서드파티 api 컨트롤러
 * <pre>
 * << 개정이력(Modification Information) >>
 *    수정일       수정자          수정내용
 *    -------      -------     -------------------
 *    2017.10.30.  hodongryu      최초작성
 * </pre>
 */
@RequestMapping("/api/*")
@Controller
public class ThirdApiController {

	private static final Logger logger = LoggerFactory.getLogger(ThirdApiController.class);

	@Autowired
	private ThirdApiService thirdApiService;

	/**
	 * @method		postalQuery : 우체국 주소검색 
	 * @param query : 주소검색 내용
	 * @param currentPage : 요청페이지번호
	 * @return
	 * @author		hodongryu
	 * @since		2017.10.30.
	 * @version		1.0
	 * @see
	 * <pre>
	 * << 개정이력(Modification Information) >>
	 *    수정일       수정자          수정내용
	 *    -------      -------     -------------------
	 *    2017.10.30.  hodongryu      최초작성
	 * </pre>
	 */
	@ResponseBody
	@RequestMapping(value = "postal", method = RequestMethod.GET)
	public Map<String, Object> postalQuery(@RequestParam String query, @RequestParam(defaultValue = "1") int currentPage){
		logger.info("api/postar query:"+query + " page:"+currentPage);
		return thirdApiService.getPostAndAddress(query, currentPage); 
	}
}
