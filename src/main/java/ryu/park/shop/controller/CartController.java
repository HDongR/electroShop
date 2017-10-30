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

/**
 * @Class		CartController.java
 * @packagename	ryu.park.shop.controller
 * @author		hodongryu
 * @since		2017.10.30.
 * @version		1.0
 * @see		 	장바구니 관련 컨트롤러
 * <pre>
 * << 개정이력(Modification Information) >>
 *    수정일       수정자          수정내용
 *    -------      -------     -------------------
 *    2017.10.30.  hodongryu      최초작성
 * </pre>
 */
@RequestMapping("/cart/*")
@Controller
public class CartController {

	private static final Logger logger = LoggerFactory.getLogger(CartController.class);

	@Autowired
	private CartService cartService;

	@RequestMapping(value = "totalCount", method = RequestMethod.GET)
	public void totalCartCount(@RequestParam String userEmail) {

	}

	/**
	 * @method		addCart : 장바구니에 집어넣음
	 * @param session : 현재세션
	 * @param cartVO : 카트모델
	 * @param res : response
	 * @throws IOException
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

	/**
	 * @method		deleteCartList : 장바구니내 상품 삭제
	 * @param session : 세션
	 * @param cartSeqList : 장바구니 번호 리스트 (로그인 했을 경우) 
	 * @param goodsSeqList : 장바구니 상품번호 리스트 (로그인 안했을 경우)
	 * @param res : response
	 * @throws IOException
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
