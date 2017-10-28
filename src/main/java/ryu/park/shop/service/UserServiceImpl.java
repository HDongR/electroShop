package ryu.park.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import ryu.park.shop.dao.UserDAOImpl;
import ryu.park.shop.vo.UserVO;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	@Qualifier("userDao")
	private UserDAOImpl dao;

	@Override
	public UserVO findUser(String email) {
		return dao.findUser(email);
	}

	@Override
	public int addUser(UserVO userVO) {
		return dao.addUser(userVO);
	}

	@Override
	public UserVO loginUser(UserVO userVO) {
		return dao.loginUser(userVO);
	}

	@Override
	public int updateUser(UserVO userVO) {
		return dao.updateUser(userVO);
	}

	@Override
	public int deleteUser(UserVO userVO) {
		return dao.deleteUser(userVO);
	}

}
