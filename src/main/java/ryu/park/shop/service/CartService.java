package ryu.park.shop.service;
 
import java.util.List;

import javax.servlet.http.HttpSession;
 
import ryu.park.shop.vo.CartVO;
import ryu.park.shop.vo.UserVO;

public interface CartService {
	public int totalCount(String userEmail); 
	public int upsertCart(HttpSession session, CartVO cartVO); 
	public int deleteCartList(HttpSession session, List<Integer> cartGoodsSeqList);
	public List<CartVO> getCartList(HttpSession session); 
	public void translateCartList(UserVO userVO, HttpSession session);
}
