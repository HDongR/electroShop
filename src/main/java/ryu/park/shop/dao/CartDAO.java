package ryu.park.shop.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ryu.park.shop.vo.CartVO;

public interface CartDAO {
	public int totalCount(String userEmail);
	@Transactional
	public int upsertCart(CartVO cartVO);
	@Transactional
	public int deleteCartList(List<Integer> cartGoodsSeqList, String cartUserEmail);
	public List<CartVO> getCartList(String userEmail); 
}
