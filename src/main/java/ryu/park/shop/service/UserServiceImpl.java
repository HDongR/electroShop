package ryu.park.shop.service;
 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
import ryu.park.shop.dao.UserDAOImpl;
import ryu.park.shop.vo.UserVO;

@Service
public class UserServiceImpl implements UserService{

	@Autowired 
	@Qualifier("userDao")
	private UserDAOImpl dao;
	
	@Override
	public UserVO findUser(String email) { 
		return dao.findUser(email);
	}

	
	@Override
	public UserVO loginUser(UserVO userVO) { 
		return dao.loginUser(userVO);
	} 
	
	@Transactional
	@Override
	public void updateUser(UserVO userVO) {
		dao.updateUser(userVO);
		
	}

	@Transactional
	@Override
	public void deleteUser(UserVO userVO) {
		dao.deleteUser(userVO);
		
	}

	@Transactional
	@Override
	public int addUser(UserVO userVO) {
		return dao.addUser(userVO); 
	}

}
