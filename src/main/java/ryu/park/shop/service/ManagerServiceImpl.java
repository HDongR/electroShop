package ryu.park.shop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ryu.park.shop.dao.ManagerDAOImpl;
import ryu.park.shop.vo.GoodsVO;
import ryu.park.shop.vo.UserVO;

@Service
public class ManagerServiceImpl implements ManagerService{
	
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
	public List<GoodsVO> getGoodsList(int start, int end, String searchOption, String keyword) { 
		return dao.getGoodsList(start, end, searchOption, keyword);
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
	public int goodsTotalCount(String searchOption, String keyword) { 
		return dao.goodsTotalCount(searchOption, keyword);
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
	
	

}
