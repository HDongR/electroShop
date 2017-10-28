package ryu.park.shop.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;

import ryu.park.shop.service.GoodsService;
import ryu.park.shop.type.OrderType;
import ryu.park.shop.utils.BoardPager;
import ryu.park.shop.utils.JsonFormatter;
import ryu.park.shop.vo.CategoryHighVO;
import ryu.park.shop.vo.GoodsVO;

@RequestMapping("/board/*")
@Controller
public class BoardController {

	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

	@Autowired
	private GoodsService goodsService;

	@RequestMapping(value = "goods", method = { RequestMethod.GET, RequestMethod.POST })
	public String goodsBoardPage(@RequestParam(defaultValue = "allGoods") String searchOption,
			@RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "1") int curPage,
			@RequestParam(defaultValue = "0") int goodsCatHighSeq, @RequestParam(defaultValue = "0") int goodsCatMidSeq,
			@RequestParam(defaultValue = "SEQ") OrderType orderType,
			@RequestParam(defaultValue = "DESC") String order, Model model) throws JsonProcessingException {
		logger.info("goodsBoardPage: " + orderType + " order:" +order);

		int count = goodsService.goodsTotalCount(searchOption, keyword, goodsCatHighSeq, goodsCatMidSeq);
		// 페이지 나누기 관련 처리
		BoardPager boardPager = new BoardPager(count, curPage, 9);
		int start = boardPager.getPageBegin();
		int end = boardPager.getPageEnd();

		List<GoodsVO> list = goodsService.getGoodsList(start, end, searchOption, keyword, goodsCatHighSeq,
				goodsCatMidSeq, orderType, order);

		model.addAttribute("list", list); // list
		model.addAttribute("count", count); // 레코드의 갯수
		model.addAttribute("searchOption", searchOption); // 검색옵션
		model.addAttribute("keyword", keyword); // 검색키워드
		model.addAttribute("boardPager", boardPager);
		model.addAttribute("goodsCatHighSeq", goodsCatHighSeq);
		model.addAttribute("goodsCatMidSeq", goodsCatMidSeq);
		model.addAttribute("orderType", orderType);
		model.addAttribute("order", order);
		

		Map<Integer, CategoryHighVO> category = goodsService.getGoodsCat(false);
		String jsonCategory = JsonFormatter.INSTANCE.getObjectMapper().writeValueAsString(category);
		model.addAttribute("categoryJson", jsonCategory);

		return "board/goods_list";
	}

}
