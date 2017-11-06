package ryu.park.shop.service;

import java.util.List;
import java.util.Map;

import ryu.park.shop.type.OrderType;
import ryu.park.shop.vo.CategoryHighVO;
import ryu.park.shop.vo.GoodsVO;

public interface GoodsService { 
	public int goodsTotalCount(String searchOption, String keyword, int goodsCatHighSeq, int goodsCatMidSeq); 
	public int addGoods(GoodsVO goodsVO); 
	public int updateGoodsOne(GoodsVO goodsVO);
	public int deleteGoodsList(List<Integer> goodsSeqList);
	public GoodsVO getGoodsOne(int goodsSeq);
	public List<GoodsVO> getGoodsList(int start, int end, String searchOption, String keyword, int goodsCatHighSeq, int goodsCatMidSeq, OrderType orderType, String order);
	public Map<Integer, CategoryHighVO> getGoodsCat(boolean seachMode);
}
