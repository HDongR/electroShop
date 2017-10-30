package ryu.park.shop.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ryu.park.shop.service.CartService;
import ryu.park.shop.vo.CartVO;

@RequestMapping("/cart/*")
@Controller
public class CartController {

	private static final Logger logger = LoggerFactory.getLogger(CartController.class);

	@Autowired
	private CartService cartService;

	@RequestMapping(value = "totalCount", method = RequestMethod.GET)
	public void totalCartCount(@RequestParam String userEmail) {

	}

	@RequestMapping(value = "addCart", method = RequestMethod.POST)
	public void addCart(HttpSession session, @Valid CartVO cartVO, HttpServletResponse res) throws IOException {
		logger.info("addCart");
		int r = cartService.addCart(session, cartVO);
		if(r < 0) {
			//error
			res.getWriter().print("error");
		}else {
			res.getWriter().print("success");
		}
	}

	@RequestMapping(value = "deleteCart", method = RequestMethod.POST)
	public void deleteCartList(HttpSession session, @RequestParam(value="cartSeqList[]", defaultValue="null") List<Integer> cartSeqList,
			@RequestParam(value = "goodsSeqList[]", defaultValue = "null") List<Integer> goodsSeqList,
			HttpServletResponse res) throws IOException {
		logger.info("deleteCart");
 
		int result = cartService.deleteCartList(session, cartSeqList, goodsSeqList);
		
		if(result < 0) {
			//error
		}else {
			//success
		} 
	}

	@RequestMapping(value = "cartList", method = RequestMethod.POST)
	public void getCartList(@RequestParam("userEmail") String userEmail) {
		
	}

}
