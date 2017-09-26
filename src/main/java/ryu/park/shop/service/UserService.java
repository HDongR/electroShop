package ryu.park.shop.service;

import ryu.park.shop.vo.UserVO;

public interface UserService {
	public UserVO findUser(String userId);
	public void addUser(UserVO userVo);
	public void updateUser(UserVO userVO);
	public void deleteUser(UserVO userVO);
}
