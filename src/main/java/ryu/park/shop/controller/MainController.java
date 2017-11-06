package ryu.park.shop.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.core.JsonProcessingException;

import ryu.park.shop.service.CartService;
import ryu.park.shop.service.GoodsService;
import ryu.park.shop.type.OrderType;
import ryu.park.shop.utils.BoardPager;
import ryu.park.shop.utils.JsonFormatter;
import ryu.park.shop.vo.CartVO;
import ryu.park.shop.vo.CategoryHighVO;
import ryu.park.shop.vo.GoodsVO;

/**
 * @Class		MainController.java
 * @packagename	ryu.park.shop.controller
 * @author		hodongryu
 * @since		2017.10.30.
 * @version		1.0
 * @see			메인페이지 컨트롤러
 * <pre>
 * << 개정이력(Modification Information) >>
 *    수정일       수정자          수정내용
 *    -------      -------     -------------------
 *    2017.10.30.  hodongryu      최초작성
 * </pre>
 */
@Controller
public class MainController {

	private static final Logger logger = LoggerFactory.getLogger(MainController.class);

	@Autowired
	private GoodsService goodsService;
	
	@Autowired
	private CartService cartService;

	/**
	 * @method		home : 메인페이지
	 * @param searchOption
	 * @param keyword
	 * @param curPage
	 * @param goodsCatHighSeq
	 * @param goodsCatMidSeq
	 * @param orderType
	 * @param order
	 * @param model
	 * @return
	 * @throws JsonProcessingException
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
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/", method = { RequestMethod.GET, RequestMethod.POST }, produces = {"text/html"})
	public String home(@RequestParam(defaultValue = "allGoods") String searchOption,
			@RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "1") int curPage,
			@RequestParam(defaultValue = "0") int goodsCatHighSeq, @RequestParam(defaultValue = "0") int goodsCatMidSeq,
			@RequestParam(defaultValue = "SEQ") OrderType orderType, @RequestParam(defaultValue = "DESC") String order,
			Model model) throws JsonProcessingException {

//		int count = goodsService.goodsTotalCount(searchOption, keyword, goodsCatHighSeq, goodsCatMidSeq);
//		
//		BoardPager boardPager = new BoardPager(count, curPage, 10);
//		int start = boardPager.getPageBegin();
//		int end = boardPager.getPageEnd();
//
//		List<GoodsVO> list = goodsService.getGoodsList(start, end, searchOption, keyword, goodsCatHighSeq,
//				goodsCatMidSeq, orderType, order);
//
//		model.addAttribute("list", list);
//		model.addAttribute("count", count);
//		model.addAttribute("searchOption", searchOption);
//		model.addAttribute("keyword", keyword);
//		model.addAttribute("boardPager", boardPager);
//		model.addAttribute("goodsCatHighSeq", goodsCatHighSeq);
//		model.addAttribute("goodsCatMidSeq", goodsCatMidSeq);
//
//		Map<Integer, CategoryHighVO> category = goodsService.getGoodsCat(true);
//
//		String jsonCategory = JsonFormatter.INSTANCE.getObjectMapper().writeValueAsString(category);
//		model.addAttribute("categoryJson", jsonCategory); 
		model.addAttribute("cartList");
		
		return "main";
	}
	
	/**
	 * @method		cartList : 기본적으로 모든 페이지에 장바구니 리스트 포함
	 * @param session : 현재세션
	 * @return
	 * @author		hodongryu
	 * @since		2017.10.30.
	 * @version		1.0
	 * @see			모든 페이지에 장바구니 리스트를 포함시킴
	 * <pre>
	 * << 개정이력(Modification Information) >>
	 *    수정일       수정자          수정내용
	 *    -------      -------     -------------------
	 *    2017.10.30.  hodongryu      최초작성
	 * </pre>
	 */
	@ModelAttribute("cartList")
	public Map<Integer, CartVO> cartList(HttpSession session) {
		return cartService.getCartList(session);
	}
}
