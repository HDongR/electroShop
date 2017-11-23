package ryu.park.shop.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import ryu.park.shop.service.CartService;
import ryu.park.shop.service.GoodsService;
import ryu.park.shop.vo.CartVO;

/**
 * @Class		BizController.java
 * @packagename	ryu.park.shop.controller
 * @author		hodongryu
 * @since		2017.11.23.
 * @version		1.0
 * @see			상품의 주문, 결제를 담당
 * <pre>
 * << 개정이력(Modification Information) >>
 *    수정일       수정자          수정내용
 *    -------      -------     -------------------
 *    2017.11.23.  hodongryu      최초작성
 * </pre>
 */
@RequestMapping("/biz/*")
@Controller
public class BizController {
	private static final Logger logger = LoggerFactory.getLogger(BizController.class);

	@Autowired
	private GoodsService goodsService;
	
	@Autowired
	private CartService cartService;
	
	
	
	/**
	 * @method cartList : 기본적으로 모든 페이지에 장바구니 리스트 포함
	 * @param session  : 현재세션
	 * @return
	 * @author hodongryu
	 * @since 2017.10.30.
	 * @version 1.0
	 * @see 모든 페이지에 장바구니 리스트를 포함시킴
	 * 
	 *      <pre>
	 * << 개정이력(Modification Information) >>
	 *    수정일       수정자          수정내용
	 *    -------      -------     -------------------
	 *    2017.10.30.  hodongryu      최초작성
	 *      </pre>
	 */
	@ModelAttribute("cartList")
	public List<CartVO> cartList(HttpSession session) { 
		return cartService.getCartList(session);
	}
}
