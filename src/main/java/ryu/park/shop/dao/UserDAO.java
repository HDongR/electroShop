package ryu.park.shop.dao;

import ryu.park.shop.vo.UserVO;

public interface UserDAO {
	public UserVO findUser(String email);
	public UserVO loginUser(UserVO userVO);
	public int addUser(UserVO userVO);
	public void updateUser(UserVO userVO);
	public void deleteUser(UserVO userVO); 
}
