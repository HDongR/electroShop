package ryu.park.shop.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ryu.park.shop.service.CartService;
import ryu.park.shop.vo.CartVO;

@RequestMapping("/doc/*")
@Controller
public class DocController {
	
	private static final Logger logger = LoggerFactory.getLogger(DocController.class);

	@Autowired
	private CartService cartService;

	/**
	 * @method cartEstimateDownload
	 * @param name 이름
	 * @param phoneNum 전화번호
	 * @param faxNum 팩스번호
	 * @param model 모델
	 * @return
	 * @author hodongryu
	 * @since 2017.11.16.
	 * @version 1.0
	 * @see 견적서 다운로드 메소드(이름, 전화번호, 팩스번호를 파라미터로 받음)
	 * 
	 *      <pre>
	 * << 개정이력(Modification Information) >>
	 *    수정일       수정자          수정내용
	 *    -------      -------     -------------------
	 *    2017.11.16.  hodongryu      최초작성
	 *      </pre>
	 */
	@RequestMapping(value = "cart_estimate_download", method = RequestMethod.GET, produces = { "application/pdf",
			"application/vnd.ms-excel" })
	public String cartEstimateDownload(@RequestParam("name") String name,
			@RequestParam(value = "phoneNum") String phoneNum,
			@RequestParam(value = "faxNum", required = false) String faxNum, Model model) {
		
		logger.info("name:" + name );
		
		logger.info("phone:" + phoneNum );
		logger.info("fax:" + faxNum );
		
		model.addAttribute("name", name);
		model.addAttribute("phoneNum", phoneNum);
		model.addAttribute("faxNum", faxNum);
		
		return "doc/cart_estimate_download";
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
		return cartService.getCartList(session);
	}
}
