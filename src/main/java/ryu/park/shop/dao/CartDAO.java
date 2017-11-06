package ryu.park.shop.dao;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import ryu.park.shop.vo.CartVO;

public interface CartDAO {
	public int totalCount(String userEmail);
	@Transactional
	public int updateCart(CartVO cartVO);
	@Transactional
	public int upsertCart(CartVO cartVO);
	@Transactional
	public int deleteCartList(List<Integer> cartSeqList);
	public Map<Integer, CartVO> getCartList(String userEmail); 
}
