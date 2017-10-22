package ryu.park.shop.dao;

import java.util.List;
import java.util.Map;

import ryu.park.shop.vo.CategoryHighVO;
import ryu.park.shop.vo.GoodsVO;

public interface GoodsDAO {
	public int goodsTotalCount(String searchOption, String keyword, int goodsCatHighSeq, int goodsCatMidSeq);
	public int addGoods(GoodsVO goodsVO);
	public int updateGoodsOne(GoodsVO goodsVO);
	public int deleteGoodsList(List<Integer> goodsSeqList);
	public GoodsVO getGoodsOne(int goodsSeq);
	public List<GoodsVO> getGoodsList(int start, int end, String searchOption, String keyword, int goodsCatHighSeq, int goodsCatMidSeq);
	public Map<Integer, CategoryHighVO> getGoodsCat();
}
