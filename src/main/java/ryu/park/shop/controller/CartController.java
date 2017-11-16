package ryu.park.shop.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import ryu.park.shop.service.CartService;
import ryu.park.shop.vo.CartVO;

/**
 * @Class CartController.java
 * @packagename ryu.park.shop.controller
 * @author hodongryu
 * @since 2017.10.30.
 * @version 1.0
 * @see 장바구니 관련 컨트롤러
 * 
 *      <pre>
 * << 개정이력(Modification Information) >>
 *    수정일       수정자          수정내용
 *    -------      -------     -------------------
 *    2017.10.30.  hodongryu      최초작성
 *      </pre>
 */
@RequestMapping("/cart/*")
@Controller
public class CartController {

	private static final Logger logger = LoggerFactory.getLogger(CartController.class);

	@Autowired
	private CartService cartService;

	@RequestMapping(value = "cart_page", method = RequestMethod.GET, produces = {"text/html"})
	public String cartPage(Model model) {
		return "cart/cart_page";
	}
	
	@RequestMapping(value = "cart_estimate_page", method = RequestMethod.GET, produces = {"text/html","application/pdf","application/vnd.ms-excel"})
	public String cartEstimate(@ModelAttribute String cartList, Model model) {
		return "doc/cart_estimate_page";
	} 

	@RequestMapping(value = "totalCount", method = RequestMethod.GET)
	public void totalCartCount(@RequestParam String userEmail) {

	}

	/**
	 * @method addCart : 장바구니에 집어넣음
	 * @param session
	 *            : 현재세션
	 * @param cartVO
	 *            : 카트모델
	 * @param res
	 *            : response
	 * @throws IOException
	 * @author hodongryu
	 * @since 2017.10.30.
	 * @version 1.0
	 * @see
	 * 
	 *      <pre>
	 * << 개정이력(Modification Information) >>
	 *    수정일       수정자          수정내용
	 *    -------      -------     -------------------
	 *    2017.10.30.  hodongryu      최초작성
	 *      </pre>
	 */
	@RequestMapping(value = "upsertCart", method = RequestMethod.POST)
	public void upsertCart(HttpSession session, @Valid CartVO cartVO, BindingResult bindingResult, HttpServletResponse res) throws IOException {
		logger.info("upsertCart");
		if (bindingResult.hasErrors()) {
			logger.info("valid error : " + bindingResult.getAllErrors());
			res.getWriter().print("validError");
		}else{
			int r = cartService.upsertCart(session, cartVO);
	
			logger.info("result:"+r);
			if (r > 0) {
				res.getWriter().print(r);
			}else{
				res.getWriter().print("error");
			} 
		}
	}

	
	/**
	 * @method deleteCartList : 장바구니내 상품 삭제
	 * @param session
	 *            : 세션
	 * @param cartSeqList
	 *            : 장바구니 번호 리스트 (로그인 했을 경우)
	 * @param goodsSeqList
	 *            : 장바구니 상품번호 리스트 (로그인 안했을 경우)
	 * @param res
	 *            : response
	 * @throws IOException
	 * @author hodongryu
	 * @since 2017.10.30.
	 * @version 1.0
	 * @see
	 * 
	 *      <pre>
	 * << 개정이력(Modification Information) >>
	 *    수정일       수정자          수정내용
	 *    -------      -------     -------------------
	 *    2017.10.30.  hodongryu      최초작성
	 *      </pre>
	 */
	@RequestMapping(value = "deleteCart", method = RequestMethod.POST)
	public void deleteCartList(HttpSession session, @RequestParam(value = "cartGoodsSeqList[]") List<Integer> cartGoodsSeqList,
			HttpServletResponse res) throws IOException {
		logger.info("deleteCart");

		int result = cartService.deleteCartList(session, cartGoodsSeqList);

		if (result < 0) {
			// error
			res.getWriter().print("Error");
		} else {
			// success
			res.getWriter().print("completeDeleteCart");
		}
	}

	/**
	 * @method cartList : 기본적으로 모든 페이지에 장바구니 리스트 포함
	 * @param session
	 *            : 현재세션
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
		List<CartVO> v = cartService.getCartList(session);
		for(CartVO c : v) {
			logger.info("get cartSeq:"+ c.getCartSeq());
			logger.info("get cartGoodsSeq:"+ c.getCartGoodsSeq());
			logger.info("get godosSubject:"+ c.getGoodsVO().getGoodsSubject());
			logger.info("get goodsCnt:"+ c.getCartGoodsCnt()); 
			logger.info("get cartEmail:"+ c.getCartUserEmail()); 
		}
		return v;
	}
}
