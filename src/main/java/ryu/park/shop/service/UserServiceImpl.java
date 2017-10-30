package ryu.park.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import ryu.park.shop.dao.UserDAOImpl;
import ryu.park.shop.type.JoinType;
import ryu.park.shop.utils.SecurityUtils;
import ryu.park.shop.vo.UserVO;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	@Qualifier("userDao")
	private UserDAOImpl dao;
	 
	@Autowired
	private SecurityUtils securityUtils;

	@Override
	public UserVO findUser(String email) {
		return dao.findUser(email);
	}

	@Override
	public int addUser(UserVO userVO) {
		setUserPassword(userVO);
		return dao.addUser(userVO);
	}

	@Override
	public UserVO loginUser(UserVO userVO) {
		setUserPassword(userVO);
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
 
	private void setUserPassword(UserVO userVO) {
		if(userVO.getUserJoinType() == JoinType.COMMON)
			userVO.setUserPassword(securityUtils.getHash(userVO.getUserPassword()));
		else if(userVO.getUserJoinType() == JoinType.KAKAO || userVO.getUserJoinType() == JoinType.NAVER)
			userVO.setUserPassword(securityUtils.getHash(userVO.getUserEmail()));
	}
}
