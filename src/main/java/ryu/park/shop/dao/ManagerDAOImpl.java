package ryu.park.shop.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import ryu.park.shop.vo.UserVO;

@Repository 
@Qualifier("managerDao")
public class ManagerDAOImpl implements ManagerDAO{

	private static final Logger logger = LoggerFactory.getLogger(ManagerDAOImpl.class);
	
	@Autowired SqlSession session;
	
	private final String NAMESPACE="ManagerMapper.";
	
	@Override
	public UserVO findManager(String managerEmail) { 
		return session.selectOne(NAMESPACE + "findManager", managerEmail);
	}

	@Override
	public UserVO loginManager(UserVO userVO) { 
		return session.selectOne(NAMESPACE + "loginManager", userVO);
	}

	@Override
	public int addManager(UserVO userVO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void updateManager(UserVO userVO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteManager(UserVO userVO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteUsers(List<UserVO> users) {
		// TODO Auto-generated method stub
		
	}

}
