package ryu.park.shop.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import ryu.park.shop.vo.CartVO;

@Repository
@Qualifier("cartDao")
public class CartDAOImpl implements CartDAO{

	private static final Logger logger = LoggerFactory.getLogger(CartDAOImpl.class);
	
	private final String NAMESPACE = "CartMapper.";
	
	@Autowired
	SqlSession session;
	
	@Override
	public int totalCount(String userEmail) { 
		return session.selectOne(NAMESPACE + "totalCount", userEmail);
	}
	
	@Override
	public int upsertCart(CartVO cartVO) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("result", 0);
			map.put("cartUserEmail", cartVO.getCartUserEmail());
			map.put("cartGoodsSeq", cartVO.getCartGoodsSeq());
			map.put("cartGoodsCnt", cartVO.getCartGoodsCnt());
			
			session.selectOne(NAMESPACE + "upsertCart", map); 
			return (Integer) map.get("result");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
			return -1;
		}
	}
 
	@Override
	public int deleteCartList(List<Integer> cartGoodsSeqList, String cartUserEmail) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cartGoodsSeqList", cartGoodsSeqList);
		map.put("cartUserEmail", cartUserEmail);
		try {
			return session.delete(NAMESPACE + "deleteCartList", map);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
			return -1;
		}
	} 

	@Override
	public List<CartVO> getCartList(String userEmail) {
		return session.selectList(NAMESPACE + "getCartList", userEmail);
	} 
 
}
