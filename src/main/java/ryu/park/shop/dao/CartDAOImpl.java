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
			return session.update(NAMESPACE + "upsertCart", cartVO);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
			return -1;
		}
	}

	@Override
	public int updateCart(CartVO cartVO) {
		try {
			return session.update(NAMESPACE + "updateCart", cartVO);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
			return -1;
		}
	}
 
	@Override
	public int deleteCartList(List<Integer> cartSeqList) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cartSeqList", cartSeqList);
		try {
			return session.delete(NAMESPACE + "deleteCartList", map);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
			return -1;
		}
	} 

	@Override
	public Map<Integer, CartVO> getCartList(String userEmail) {
		return session.selectMap(NAMESPACE + "getCartList", userEmail, "cartSeq");
	} 
 
}
