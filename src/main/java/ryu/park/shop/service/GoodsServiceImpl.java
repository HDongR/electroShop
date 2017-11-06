package ryu.park.shop.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import ryu.park.shop.dao.GoodsDAO;
import ryu.park.shop.type.OrderType;
import ryu.park.shop.vo.CategoryHighVO;
import ryu.park.shop.vo.CategoryHighVO.CategoryMidVO;
import ryu.park.shop.vo.GoodsVO; 
 
@Service
public class GoodsServiceImpl implements GoodsService{
	
	private static final Logger logger = LoggerFactory.getLogger(GoodsServiceImpl.class);
	
	@Autowired 
	@Qualifier("goodsDao")
	private GoodsDAO dao;

	@Override
	public int goodsTotalCount(String searchOption, String keyword, int goodsCatHighSeq, int goodsCatMidSeq) {
		return dao.goodsTotalCount(searchOption, keyword, goodsCatHighSeq, goodsCatMidSeq);
	}

	@Override
	public int addGoods(GoodsVO goodsVO) {
		return dao.addGoods(goodsVO);
	}

	@Override
	public int updateGoodsOne(GoodsVO goodsVO) {
		return dao.updateGoodsOne(goodsVO);
	}

	@Override
	public int deleteGoodsList(List<Integer> goodsSeqList) {
		return dao.deleteGoodsList(goodsSeqList);
	}

	@Override
	public GoodsVO getGoodsOne(int goodsSeq) {
		return dao.getGoodsOne(goodsSeq);
	}

	@Override
	public List<GoodsVO> getGoodsList(int start, int end, String searchOption, String keyword, int goodsCatHighSeq,
			int goodsCatMidSeq, OrderType orderType, String order) {
		return dao.getGoodsList(start, end, searchOption, keyword, goodsCatHighSeq, goodsCatMidSeq, orderType, order);
	}  
	 
	/*  
	 * @param seachMode : 카테고리로 검색조건을 할 경우 전체선택카테고리를 추가해준다. 
	 */
	@Override
	public Map<Integer, CategoryHighVO> getGoodsCat(boolean seachMode) {
		if(!seachMode)
			return dao.getGoodsCat(); 
		else {
			Map<Integer, CategoryHighVO> map = dao.getGoodsCat();
			
			CategoryHighVO allCat = new CategoryHighVO();
			allCat.setCatHighName("전체카테고리");
			allCat.setCatHighSeq(0);
			allCat.setCategoryMidList(new ArrayList<CategoryHighVO.CategoryMidVO>());
			map.put(0, allCat);
			for( Integer key : map.keySet()) {
				CategoryHighVO.CategoryMidVO midVO = new CategoryMidVO();
				midVO.setCatHighSeq(map.get(key).getCatHighSeq());
				midVO.setCatMidName("전체카테고리");
				midVO.setCatMidSeq(0);
				map.get(key).getCategoryMidList().add(0, midVO);
			}
			
			return map;
		}
	} 
	
}
