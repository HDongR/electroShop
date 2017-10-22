package ryu.park.shop.service;

import org.springframework.transaction.annotation.Transactional;

import ryu.park.shop.vo.UserVO;

public interface UserService {
	public UserVO findUser(String email);
	public UserVO loginUser(UserVO userVO);
	@Transactional
	public int addUser(UserVO userVO);
	@Transactional
	public int updateUser(UserVO userVO);
	@Transactional
	public int deleteUser(UserVO userVO);
}
