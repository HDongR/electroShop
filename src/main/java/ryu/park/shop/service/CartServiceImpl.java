package ryu.park.shop.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ryu.park.shop.dao.CartDAO;
import ryu.park.shop.dao.GoodsDAO;
import ryu.park.shop.vo.CartVO;
import ryu.park.shop.vo.UserVO;

@Service
public class CartServiceImpl implements CartService {

	private static final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

	@Autowired
	@Qualifier("cartDao")
	private CartDAO dao;

	@Autowired
	@Qualifier("goodsDao")
	private GoodsDAO goodsDao;

	@Override
	public int totalCount(String userEmail) {
		return dao.totalCount(userEmail);
	}
	
	@Override
	public int upsertCart(HttpSession session, CartVO cartVO) {
		if (session.getAttribute("user") == null) { // 로그인이 안되어있을 경우 세션에 장바구니 수정또는 저장 
			return upsertCartInSession(session, cartVO);
		} else { // 로그인이 되어있을 경우
			cartVO.setCartUserEmail(((UserVO) session.getAttribute("user")).getUserEmail());
			return dao.upsertCart(cartVO);
		}
	}
	
	/**
	 * @method		upsertCartInSession
	 * @param session
	 * @param cartVO
	 * @return		장바구니 안의 상품 개수
	 * @author		hodongryu
	 * @since		2017.11.14.
	 * @version		1.0
	 * @see			세션안에서 장바구니 리스트를 update 나 insert 를 한다. 
	 * <pre>
	 * << 개정이력(Modification Information) >>
	 *    수정일       수정자          수정내용
	 *    -------      -------     -------------------
	 *    2017.11.14.  hodongryu      최초작성
	 * </pre>
	 */
	private int upsertCartInSession(HttpSession session, CartVO cartVO) {
		List<CartVO> cartList = getCartInSession(session);

		boolean isDuplicateCart = false;
		
		for (CartVO c : cartList) { // 중복되는 상품이 있는지 검사 후 중복시 update(세션)
			if (c.getCartGoodsSeq().intValue() == cartVO.getCartGoodsSeq().intValue()) {
				isDuplicateCart = true;
				validGoodsStock(cartVO);
				c.setCartGoodsCnt(cartVO.getCartGoodsCnt());
			}
		} 
		
		if(!isDuplicateCart) { //중복되는 상품이 없을경우 insert(세션)
			validGoodsStock(cartVO);
			cartList.add(cartVO);
		}
		
		return cartVO.getCartGoodsCnt();
	}


	@Override
	public int deleteCartList(HttpSession session, List<Integer> cartGoodsSeqList) {
		UserVO userVO = (UserVO)session.getAttribute("user");
		if (userVO == null) { // 로그인이 안되었을 경우 세션에서 삭제
			return deleteCartInSession(session, cartGoodsSeqList);
		} else {
			int result = dao.deleteCartList(cartGoodsSeqList, userVO.getUserEmail());
			return cartGoodsSeqList.size() == result ? 1 : -1;
		}
	}
	
	/**
	 * @method		deleteCartInSession
	 * @param session
	 * @param cartGoodsSeqList
	 * @return
	 * @author		hodongryu
	 * @since		2017.11.14.
	 * @version		1.0
	 * @see			세션안의 장바구니리스트에서 상품번호에 맞는 상품을 삭제한다.
	 * <pre>	
	 * << 개정이력(Modification Information) >>
	 *    수정일       수정자          수정내용
	 *    -------      -------     -------------------
	 *    2017.11.14.  hodongryu      최초작성
	 * </pre>
	 */
	private int deleteCartInSession(HttpSession session, List<Integer> cartGoodsSeqList) {
		List<CartVO> cartList = getCartInSession(session);

		for (Integer goodsSeq : cartGoodsSeqList) {
			for (int i = 0; i < cartList.size(); i++) {
				if (cartList.get(i).getCartGoodsSeq().intValue() == goodsSeq.intValue()) {
					cartList.remove(i);
				}
			}
		}

		return 1;
	}

	/**
	 * 장바구니 리스트를 가져온다
	 */
	@Override
	public List<CartVO> getCartList(HttpSession session) {
		return session.getAttribute("user") == null ? getCartInSession(session)
				: dao.getCartList(((UserVO) session.getAttribute("user")).getUserEmail());
	}
 
	/**
	 * 세션에 있는 장바구니 리스트를 데이터베이스로 옮긴다.
	 */
	@Transactional
	@Override
	public void translateCartList(UserVO userVO, HttpSession session) {
		List<CartVO> cartList = getCartInSession(session);
		for (CartVO cart : cartList) {
			upsertCart(session, cart);
		} 
		cartList.clear();
	}

	 
	/**
	 * @method		validGoodsStock
	 * @param cartVO : 장바구니 모델
	 * @return
	 * @author		hodongryu
	 * @since		2017.11.10.
	 * @version		1.0
	 * @see			장바구니 상품개수와 재고량 검증을 위함. 상품개수가 재고량을 넘길시 상품개수를 재고량수로 변경
	 * <pre>
	 * << 개정이력(Modification Information) >>
	 *    수정일       수정자          수정내용
	 *    -------      -------     -------------------
	 *    2017.11.10.  hodongryu      최초작성
	 * </pre>
	 */
	private void validGoodsStock(CartVO cartVO) {
		if (cartVO.getGoodsVO() == null) {
			cartVO.setGoodsVO(goodsDao.getGoodsOne(cartVO.getCartGoodsSeq())); // 장바구니도메인에 상품도메인 추가
		}
		int goodsStock = cartVO.getGoodsVO().getGoodsStock();
		 
		if(cartVO.getCartGoodsCnt() > goodsStock) { //장바구니개수가 재고량보다 클경우 재고량만큼만 입력
			cartVO.setCartGoodsCnt(goodsStock);
		}
	} 
	
	/**
	 * @method		getCartInSession
	 * @param session
	 * @return
	 * @author		hodongryu
	 * @since		2017.11.14.
	 * @version		1.0
	 * @see			세션에서 장바구니 리스트를 가져옴. 없으면 생성
	 * <pre>
	 * << 개정이력(Modification Information) >>
	 *    수정일       수정자          수정내용
	 *    -------      -------     -------------------
	 *    2017.11.14.  hodongryu      최초작성
	 * </pre>
	 */
	private List<CartVO> getCartInSession(HttpSession session) {
		@SuppressWarnings("unchecked")
		List<CartVO> cartList = (ArrayList<CartVO>) session.getAttribute("cartList");
		if (cartList == null) {
			cartList = new ArrayList<CartVO>();
			session.setAttribute("cartList", cartList);
		}
		return cartList;
	}
}
