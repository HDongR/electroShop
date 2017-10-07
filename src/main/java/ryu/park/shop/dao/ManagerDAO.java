package ryu.park.shop.dao;

import java.util.List;

import ryu.park.shop.vo.UserVO;

public interface ManagerDAO {
	public UserVO findManager(String managerEmail);
	public UserVO loginManager(UserVO userVO);
	public int addManager(UserVO userVO);
	public void updateManager(UserVO userVO);
	public void deleteManager(UserVO userVO);
	
	public void deleteUsers(List<UserVO> users);
}
