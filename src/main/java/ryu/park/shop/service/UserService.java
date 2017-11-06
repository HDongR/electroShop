package ryu.park.shop.service;

import javax.servlet.http.HttpSession;

import ryu.park.shop.vo.UserVO;

public interface UserService {
	public UserVO findUser(String email);
	public UserVO loginUser(UserVO userVO, HttpSession session); 
	public int addUser(UserVO userVO); 
	public int updateUser(UserVO userVO); 
	public int deleteUser(UserVO userVO);
}
