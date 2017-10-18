package ryu.park.shop.service;

import java.util.List;

import ryu.park.shop.vo.GoodsVO;
import ryu.park.shop.vo.UserVO;

public interface ManagerService {
	public UserVO findManager(String managerEmail);
	public UserVO loginManager(UserVO userVO);
	public int addManager(UserVO userVO);
	public void updateManager(UserVO userVO);
	public void deleteManager(UserVO userVO);
	public void deleteUsers(List<UserVO> users);
	public int userTotalCount(String searchOption, String keyword);
	public List<UserVO> getUserList(int start, int end, String searchOption, String keyword); 
	public UserVO getUserOne(String email);
	public void updateUserOne(UserVO userVO);
	public int goodsTotalCount(String searchOption, String keyword);
	public int addGoods(GoodsVO goodsVO); 
	public int deleteGoodsList(List<Integer> goodsSeqList);
	public List<GoodsVO> getGoodsList(int start, int end, String searchOption, String keyword);
	public GoodsVO getGoodsOne(int goodsSeq);
	public void updateGoodsOne(GoodsVO goodsVO);
}
