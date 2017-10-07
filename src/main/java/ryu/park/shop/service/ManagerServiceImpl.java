package ryu.park.shop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ryu.park.shop.dao.ManagerDAOImpl; 
import ryu.park.shop.vo.UserVO;

@Service
public class ManagerServiceImpl implements ManagerService{
	
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

	@Transactional
	@Override
	public int addManager(UserVO userVO) { 
		return dao.addManager(userVO);
	}

	@Transactional
	@Override
	public void updateManager(UserVO userVO) {
		dao.updateManager(userVO);
	}

	@Transactional
	@Override
	public void deleteManager(UserVO userVO) {
		dao.deleteManager(userVO);
	}

	@Transactional
	@Override
	public void deleteUsers(List<UserVO> users) {
		dao.deleteUsers(users);
	}

}
