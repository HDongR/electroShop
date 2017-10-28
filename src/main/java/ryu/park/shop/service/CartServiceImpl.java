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
			// 로그인이 되어있을 경우
		} else {
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
			}else {
				return -1;
			}

			// 로그인이 되어있을 경우
		} else {
			if(cartSeqList != null) {
				int result = dao.deleteCartList(cartSeqList);
				return cartSeqList.size() == result ? 1 : -1;
			}else {
				return -1;
			} 
		}
	}

	@Override
	public List<CartVO> getCartList(String userEmail) {
		return dao.getCartList(userEmail);
	}

	private boolean isLoginUser(HttpSession session) {
		return session.getAttribute("user") != null ? true : false;
	}

	private HashMap<Integer, CartVO> getCartFromSession(HttpSession session) {
		return (HashMap<Integer, CartVO>) session.getAttribute("cartList");
	}

}
