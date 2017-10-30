package ryu.park.shop.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.transaction.annotation.Transactional;

import ryu.park.shop.vo.CartVO;

public interface CartService {
	public int totalCount(String userEmail);
	@Transactional
	public int addCart(HttpSession session, CartVO cartVO);
	@Transactional
	public int updateCart(CartVO cartVO);
	@Transactional
	public int deleteCartList(HttpSession session, List<Integer> cartSeqList, List<Integer> goodsSeqList);
	public Map<Integer, CartVO> getCartList(HttpSession session); 
}
