package ryu.park.shop.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import ryu.park.shop.dao.ManagerDAOImpl;
import ryu.park.shop.vo.UserVO; 

@Service
public class ManagerServiceImpl implements ManagerService{
	private static final Logger logger = LoggerFactory.getLogger(ManagerServiceImpl.class);
	
	@Autowired 
	@Qualifier("managerDao")
	private ManagerDAOImpl dao;
	
	@Override
	public UserVO findManager(String managerEmail) { 
		return dao.findManager(managerEmail);
	}

	@Override
	public UserVO loginManager(UserVO userVO) { 
		return dao.loginManager(userVO);
	}
 
	@Override
	public int addManager(UserVO userVO) { 
		return dao.addManager(userVO);
	}
 
	@Override
	public int updateManager(UserVO userVO) {
		return dao.updateManager(userVO);
	}
 
	@Override
	public int deleteManager(UserVO userVO) {
		return dao.deleteManager(userVO);
	}
 
	@Override
	public int deleteUsers(List<UserVO> users) {
		return dao.deleteUsers(users);
	}
 

	@Override
	public List<UserVO> getUserList(int start, int end, String searchOption, String keyword) {
		return dao.getUserList(start, end, searchOption, keyword);
	}

	@Override
	public int userTotalCount(String searchOption, String keyword) { 
		return dao.userTotalCount(searchOption, keyword);
	}

	@Override
	public UserVO getUserOne(String email) {
		return dao.getUserOne(email);
	}
 
	@Override
	public int updateUserOne(UserVO userVO) {
		return dao.updateUserOne(userVO);
	}
  
}
