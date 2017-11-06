package ryu.park.shop.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import ryu.park.shop.vo.CartVO;

public interface CartService {
	public int totalCount(String userEmail); 
	public int upsertCart(HttpSession session, CartVO cartVO); 
	public int updateCart(HttpSession session, CartVO cartVO);
	public int deleteCartList(HttpSession session, List<Integer> cartSeqList);
	public Map<Integer, CartVO> getCartList(HttpSession session); 
}
