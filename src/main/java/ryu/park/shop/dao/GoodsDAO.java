package ryu.park.shop.dao;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import ryu.park.shop.type.OrderType;
import ryu.park.shop.vo.CategoryHighVO;
import ryu.park.shop.vo.GoodsVO;

public interface GoodsDAO {
	public int goodsTotalCount(String searchOption, String keyword, int goodsCatHighSeq, int goodsCatMidSeq);
	@Transactional
	public int addGoods(GoodsVO goodsVO);
	@Transactional
	public int updateGoodsOne(GoodsVO goodsVO);
	@Transactional
	public int deleteGoodsList(List<Integer> goodsSeqList);
	public GoodsVO getGoodsOne(int goodsSeq);
	public List<GoodsVO> getGoodsList(int start, int end, String searchOption, String keyword, int goodsCatHighSeq, int goodsCatMidSeq, OrderType orderType, String order);
	public Map<Integer, CategoryHighVO> getGoodsCat();
}
