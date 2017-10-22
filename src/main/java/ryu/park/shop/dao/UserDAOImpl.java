package ryu.park.shop.dao;
 
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import ryu.park.shop.vo.UserVO;

@Repository 
@Qualifier("userDao")
public class UserDAOImpl implements UserDAO{
	private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);
	
	@Autowired SqlSession session;
	
	private final String NAMESPACE="UserMapper.";
	
	@Override
	public UserVO findUser(String email) {
		return session.selectOne(NAMESPACE+"findUser", email);
	}
	

	@Override
	public UserVO loginUser(UserVO userVO) { 
		return session.selectOne(NAMESPACE+"loginUser", userVO);
	}

 
	@Override
	public int addUser(UserVO userVO) { 
		try {
			return session.insert(NAMESPACE + "addUser", userVO);
		} catch(Exception e) {
			logger.error(e.getLocalizedMessage(), e);
			return -1;
		}
	}

	@Override
	public int updateUser(UserVO userVO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteUser(UserVO userVO) {
		// TODO Auto-generated method stub
		return 0;
	}

}
