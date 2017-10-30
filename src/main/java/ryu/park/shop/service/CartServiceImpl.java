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

import ryu.park.shop.dao.CartDAOImpl;
import ryu.park.shop.vo.CartVO;
import ryu.park.shop.vo.UserVO;

@Service
public class CartServiceImpl implements CartService {
	
	private static final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

	@Autowired
	@Qualifier("cartDao")
	private CartDAOImpl dao;

	@Override
	public int totalCount(String userEmail) {
		return dao.totalCount(userEmail);
	}

	@Override
	public int addCart(HttpSession session, CartVO cartVO) {
		// 로그인이 안되어있을 경우 세션에 장바구니 저장
		if (!isLoginUser(session)) {
			logger.info("addCart user is null");
			if (getCartFromSession(session) == null) {
				logger.info("addCart list is null");
				Map<Integer, CartVO> cartList = new HashMap<Integer, CartVO>(); // 세션당 하나의 맵을 생성 key:상품번호
				session.setAttribute("cartList", cartList);
			} else {
				Map<Integer, CartVO> cartList = getCartFromSession(session);
				logger.info("addCart list size: " + cartList.size());
				cartList.put(cartVO.getCartGoodsSeq(), cartVO);
			}
			return 0;
		} else {
			// 로그인이 되어있을 경우
			cartVO.setCartUserEmail(((UserVO)session.getAttribute("user")).getUserEmail());
			return dao.addCart(cartVO);
		}
	}

	@Override
	public int updateCart(CartVO cartVO) {
		return dao.updateCart(cartVO);
	}

	@Override
	public int deleteCartList(HttpSession session, List<Integer> cartSeqList, List<Integer> goodsSeqList) {
		// 로그인이 안되었을 경우 세션에서 삭제
		if (!isLoginUser(session)) {
			logger.info("deleteCart user is null");

			Map<Integer, CartVO> cartList = getCartFromSession(session);
			logger.info("deleteCart list size: " + cartList.size());
			if (goodsSeqList != null) {
				for (Integer goodsSeq : goodsSeqList) {
					cartList.remove(goodsSeq);
				}

				session.setAttribute("cartList", cartList);

				return 0;
			} else {
				return -1;
			}
		} else {
			// 로그인이 되어있을 경우
			if (cartSeqList != null) {
				int result = dao.deleteCartList(cartSeqList);
				return cartSeqList.size() == result ? 1 : -1;
			} else {
				return -1;
			}
		}
	}

	@Override
	public Map<Integer, CartVO> getCartList(HttpSession session) {
		// 로그인이 안되었을 경우 세션에서 가져옴
		if (!isLoginUser(session)) {
			logger.info("getCartList user is null");
			Map<Integer, CartVO> sessionCart = getCartFromSession(session);
			if (sessionCart == null) {
				Map<Integer, CartVO> cartList = new HashMap<Integer, CartVO>(); // 세션당 하나의 맵을 생성 key:상품번호
				session.setAttribute("cartList", cartList);
			} 
			return sessionCart;
		
			
		} else {
			logger.info("isUser");
			// 로그인이 되었을경우 디비에서 가져옴
			List<CartVO> cartList = dao.getCartList(((UserVO)session.getAttribute("user")).getUserEmail());
			logger.info("isUser size"+ cartList.size());
			Map<Integer, CartVO> ret = new HashMap<Integer, CartVO>();
			
			for(CartVO cart : cartList) {
				if(cart instanceof CartVO)
				logger.info("cart is null?" + (cart == null ? "null" : "not null"));
				//ret.put(cart.getCartSeq(), cart);
			}
			return ret; 
		}
	}
 
	private boolean isLoginUser(HttpSession session) {
		return session.getAttribute("user") != null ? true : false;
	}

	private HashMap<Integer, CartVO> getCartFromSession(HttpSession session) {
		return (HashMap<Integer, CartVO>) session.getAttribute("cartList");
	}

}
