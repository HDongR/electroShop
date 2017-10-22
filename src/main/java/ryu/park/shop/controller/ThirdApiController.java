package ryu.park.shop.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ryu.park.shop.service.ThirdApiService;

@RequestMapping("/api/*")
@Controller
public class ThirdApiController {

	private static final Logger logger = LoggerFactory.getLogger(ThirdApiController.class);

	@Autowired
	ThirdApiService thirdApiService;

	@ResponseBody
	@RequestMapping(value = "postal", method = RequestMethod.GET)
	public Map<String, Object> postalQuery(@RequestParam String query, @RequestParam(defaultValue = "1") int currentPage){
		logger.info("api/postar query:"+query + " page:"+currentPage);
		return thirdApiService.getPostAndAddress(query, currentPage); 
	}
}
