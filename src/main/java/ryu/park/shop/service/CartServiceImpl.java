package ryu.park.shop.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

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

	public int upsertCart(HttpSession session, CartVO cartVO) {
		// 로그인이 안되어있을 경우 세션에 장바구니 수정또는 저장
		if (!isLoginUser(session)) {
			if (getCartFromSession(session) == null) {
				Map<Integer, CartVO> cartList = new HashMap<Integer, CartVO>(); // 세션당 하나의 맵을 생성 key:상품번호
				cartVO.setGoodsVO(goodsDao.getGoodsOne(cartVO.getCartGoodsSeq()));
				cartList.put(cartVO.getCartGoodsSeq(), cartVO);
				session.setAttribute("cartList", cartList);
			} else {
				Map<Integer, CartVO> cartList = getCartFromSession(session);
				cartVO.setGoodsVO(goodsDao.getGoodsOne(cartVO.getCartGoodsSeq()));
				if (cartList.containsKey(cartVO.getCartGoodsSeq())) { // 중복되는 상품이 있는지 검사 후 중복시 더해서 저장
					cartVO.setCartGoodsCnt(
							cartVO.getCartGoodsCnt() + cartList.get(cartVO.getCartGoodsSeq()).getCartGoodsCnt());
				}
				cartList.put(cartVO.getCartGoodsSeq(), cartVO);
			}
			return 0;
		} else {
			// 로그인이 되어있을 경우
			cartVO.setCartUserEmail(((UserVO) session.getAttribute("user")).getUserEmail());
			return dao.upsertCart(cartVO);
		}

	}

	@Override
	public int updateCart(HttpSession session, CartVO cartVO) {
		// 로그인이 안되어있을 경우 세션에 장바구니 수정
		if (!isLoginUser(session)) {  
			Map<Integer, CartVO> cartList = getCartFromSession(session);
			if (cartList.containsKey(cartVO.getCartGoodsSeq())) {
				CartVO updatedCartVO = cartList.get( cartVO.getCartGoodsSeq() );
				updatedCartVO.setCartGoodsCnt(cartVO.getCartGoodsCnt());
				cartList.put(cartVO.getCartGoodsSeq(), updatedCartVO); 
			} 
			return 0;
		} else {
			// 로그인이 되어있을 경우
			cartVO.setCartUserEmail(((UserVO) session.getAttribute("user")).getUserEmail());
			return dao.updateCart(cartVO);
		}
	}

	@Override
	public int deleteCartList(HttpSession session, List<Integer> cartSeqList) {
		// 로그인이 안되었을 경우 세션에서 삭제
		if (!isLoginUser(session)) {
			logger.info("deleteCart user is null");

			Map<Integer, CartVO> cartList = getCartFromSession(session);
			logger.info("deleteCart list size: " + cartList.size());

			for (Integer goodsSeq : cartSeqList) {
				cartList.remove(goodsSeq);
			}

			session.setAttribute("cartList", cartList);

			return 0;
		} else {
			int result = dao.deleteCartList(cartSeqList);
			return cartSeqList.size() <= result ? 1 : -1;
		}
	}

	@Override
	public Map<Integer, CartVO> getCartList(HttpSession session) {
		// 로그인이 안되었을 경우 세션에서 가져옴
		if (!isLoginUser(session)) {
			Map<Integer, CartVO> sessionCart = getCartFromSession(session);
			if (sessionCart == null) {
				Map<Integer, CartVO> cartList = new HashMap<Integer, CartVO>(); // 세션당 하나의 맵을 생성 key:상품번호
				session.setAttribute("cartList", cartList);
			}
			return sessionCart;

		} else {
			logger.info("isUser");
			// 로그인이 되었을경우 디비에서 가져옴
			return dao.getCartList(((UserVO) session.getAttribute("user")).getUserEmail());
		}
	}

	/**
	 * @method isLoginUser
	 * @param session
	 * @return true : 로그인되어있음, false : 로그인 안되어있음
	 * @author hodongryu
	 * @since 2017.10.30.
	 * @version 1.0
	 * @see 로그인이 되었나 확인
	 * 
	 *      <pre>
	 * << 개정이력(Modification Information) >>
	 *    수정일       수정자          수정내용
	 *    -------      -------     -------------------
	 *    2017.10.30.  hodongryu      최초작성
	 *      </pre>
	 */
	private boolean isLoginUser(HttpSession session) {
		return session.getAttribute("user") != null ? true : false;
	}

	/**
	 * @method getCartFromSession
	 * @param session
	 * @return Map(key:상품번호, value:장바구니모델)
	 * @author hodongryu
	 * @since 2017.10.30.
	 * @version 1.0
	 * @see 세션에서 카트리스트를 가져옴
	 * 
	 *      <pre>
	 * << 개정이력(Modification Information) >>
	 *    수정일       수정자          수정내용
	 *    -------      -------     -------------------
	 *    2017.10.30.  hodongryu      최초작성
	 *      </pre>
	 */
	private HashMap<Integer, CartVO> getCartFromSession(HttpSession session) {
		return (HashMap<Integer, CartVO>) session.getAttribute("cartList");
	}

}
