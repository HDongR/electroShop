package ryu.park.shop.dao;

import ryu.park.shop.vo.UserVO;

public interface UserDAO {
	public UserVO findUser(String userId);
	public void addUser(UserVO userVo);
	public void updateUser(UserVO userVO);
	public void deleteUser(UserVO userVO);
}
