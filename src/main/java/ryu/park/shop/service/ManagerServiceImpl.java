package ryu.park.shop.service;

import java.util.ArrayList; 
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ryu.park.shop.controller.ManagerController;
import ryu.park.shop.dao.ManagerDAOImpl;
import ryu.park.shop.vo.CategoryHighVO;
import ryu.park.shop.vo.GoodsVO;
import ryu.park.shop.vo.UserVO;
import ryu.park.shop.vo.CategoryHighVO.CategoryMidVO;

@Service
public class ManagerServiceImpl implements ManagerService{
	private static final Logger logger = LoggerFactory.getLogger(ManagerServiceImpl.class);
	
	@Autowired 
	@Qualifier("managerDao")
	private ManagerDAOImpl dao;
	
	@Override
	public UserVO findManager(String managerEmail) { 
		return dao.findManager(managerEmail);
	}

	@Override
	public UserVO loginManager(UserVO userVO) { 
		return dao.loginManager(userVO);
	}

	@Transactional
	@Override
	public int addManager(UserVO userVO) { 
		return dao.addManager(userVO);
	}

	@Transactional
	@Override
	public void updateManager(UserVO userVO) {
		dao.updateManager(userVO);
	}

	@Transactional
	@Override
	public void deleteManager(UserVO userVO) {
		dao.deleteManager(userVO);
	}

	@Transactional
	@Override
	public void deleteUsers(List<UserVO> users) {
		dao.deleteUsers(users);
	}

	@Transactional
	@Override
	public int addGoods(GoodsVO goodsVO) { 
		return dao.addGoods(goodsVO);
	}

	@Override
	public List<GoodsVO> getGoodsList(int start, int end, String searchOption, String keyword, int goodsCatHighSeq, int goodsCatMidSeq) { 
		return dao.getGoodsList(start, end, searchOption, keyword, goodsCatHighSeq, goodsCatMidSeq);
	}

	@Override
	public GoodsVO getGoodsOne(int goodsSeq) { 
		return dao.getGoodsOne(goodsSeq);
	}

	@Override
	public void updateGoodsOne(GoodsVO goodsVO) {
		dao.updateGoodsOne(goodsVO);
	}

	@Override
	public int goodsTotalCount(String searchOption, String keyword, int goodsCatHighSeq, int goodsCatMidSeq) { 
		return dao.goodsTotalCount(searchOption, keyword, goodsCatHighSeq, goodsCatMidSeq);
	}
  
	@Transactional
	@Override
	public int deleteGoodsList(List<Integer> goodsSeqList) { 
		return dao.deleteGoodsList(goodsSeqList);
	}

	@Override
	public List<UserVO> getUserList(int start, int end, String searchOption, String keyword) {
		return dao.getUserList(start, end, searchOption, keyword);
	}

	@Override
	public int userTotalCount(String searchOption, String keyword) { 
		return dao.userTotalCount(searchOption, keyword);
	}

	@Override
	public UserVO getUserOne(String email) {
		return dao.getUserOne(email);
	}

	@Transactional
	@Override
	public void updateUserOne(UserVO userVO) {
		dao.updateUserOne(userVO);
	}

	//searchMode : 카테고리로 검색조건을 할 경우 전체선택카테고리를 추가해준다. 
	@Override
	public Map<Integer, CategoryHighVO> getGoodsCat(boolean seachMode){
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
