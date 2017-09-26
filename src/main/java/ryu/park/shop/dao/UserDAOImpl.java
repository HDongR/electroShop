package ryu.park.shop.dao;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import ryu.park.shop.vo.UserVO;

@Repository 
@Qualifier("userDao")
public class UserDAOImpl implements UserDAO{
 
	@Autowired SqlSession session;
	
	private final String NAMESPACE="UserMapper.";
	
	@Override
	public UserVO findUser(String userId) {
		// TODO Auto-generated method stub
		return session.selectOne(NAMESPACE+"findUser", userId);
	}

	@Override
	public void addUser(UserVO userVo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateUser(UserVO userVO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteUser(UserVO userVO) {
		// TODO Auto-generated method stub
		
	}

}
