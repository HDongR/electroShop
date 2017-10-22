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

import ryu.park.shop.vo.CategoryHighVO;
import ryu.park.shop.vo.GoodsVO;

@Repository
@Qualifier("goodsDao")
public class GoodsDAOImpl implements GoodsDAO {

	private static final Logger logger = LoggerFactory.getLogger(GoodsDAOImpl.class);

	private final String NAMESPACE = "GoodsMapper.";

	@Autowired
	SqlSession session;

	@Override
	public int goodsTotalCount(String searchOption, String keyword, int goodsCatHighSeq, int goodsCatMidSeq) {
		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("searchOption", searchOption);
		map.put("keyword", keyword);
		map.put("goodsCatHighSeq", goodsCatHighSeq);
		map.put("goodsCatMidSeq", goodsCatMidSeq);
		return session.selectOne(NAMESPACE + "goodsTotalCount", map);
	}

	@Override
	public int addGoods(GoodsVO goodsVO) {
		try {
			return session.insert(NAMESPACE + "addGoods", goodsVO);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
			return -1;
		}
	}

	@Override
	public int updateGoodsOne(GoodsVO goodsVO) {
		try {
			return session.update(NAMESPACE + "updateGoodsOne", goodsVO);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
			return -1;
		}
	}

	@Override
	public int deleteGoodsList(List<Integer> goodsSeqList) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("goodsSeqList", goodsSeqList);
		try {
			return session.delete(NAMESPACE + "deleteGoodsList", map);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
			return -1;
		} 
	}

	@Override
	public GoodsVO getGoodsOne(int goodsSeq) {
		return session.selectOne(NAMESPACE + "getGoodsOne", goodsSeq);
	}

	@Override
	public List<GoodsVO> getGoodsList(int start, int end, String searchOption, String keyword, int goodsCatHighSeq,
			int goodsCatMidSeq) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("searchOption", searchOption);
		map.put("keyword", keyword);
		map.put("start", start);
		map.put("end", end);
		map.put("goodsCatHighSeq", goodsCatHighSeq);
		map.put("goodsCatMidSeq", goodsCatMidSeq);
		return session.selectList(NAMESPACE + "getGoodsList", map);
	}

	@Override
	public Map<Integer, CategoryHighVO> getGoodsCat() {
		return session.selectMap(NAMESPACE + "getGoodsCat", "catHighSeq");
	}

}
