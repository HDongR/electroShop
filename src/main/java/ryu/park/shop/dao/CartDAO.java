package ryu.park.shop.dao;

import java.util.List;

import ryu.park.shop.vo.CartVO;

public interface CartDAO {
	public int totalCount(String userEmail);
	public int addCart(CartVO cartVO);
	public int updateCart(CartVO cartVO);
	public int deleteCartList(List<Integer> cartSeqList);
	public List<CartVO> getCartList(String userEmail); 
}
