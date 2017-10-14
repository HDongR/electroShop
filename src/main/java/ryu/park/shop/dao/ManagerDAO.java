package ryu.park.shop.dao;

import java.util.List;

import ryu.park.shop.vo.GoodsVO;
import ryu.park.shop.vo.UserVO;

public interface ManagerDAO {
	public UserVO findManager(String managerEmail);
	public UserVO loginManager(UserVO userVO);
	public int addManager(UserVO userVO);
	public void updateManager(UserVO userVO);
	public void deleteManager(UserVO userVO); 
	public void deleteUsers(List<UserVO> users); 
	public int goodsTotalCount(String searchOption, String keyword);
	public int addGoods(GoodsVO goodsVO);
	public int deleteGoods(int goodsSeq);
	public int deleteGoodsList(List<Integer> goodsSeqList);
	public List<GoodsVO> getGoodsList(int start, int end, String searchOption, String keyword);
	public GoodsVO getGoodsOne(int goodsSeq);
	public void updateGoodsOne(GoodsVO goodsVO);
}
