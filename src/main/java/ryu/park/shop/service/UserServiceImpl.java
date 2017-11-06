package ryu.park.shop.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import ryu.park.shop.dao.UserDAO;
import ryu.park.shop.type.JoinType;
import ryu.park.shop.utils.SecurityUtils;
import ryu.park.shop.vo.UserVO;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	@Qualifier("userDao")
	private UserDAO dao; 
	 
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
	public UserVO loginUser(UserVO userVO, HttpSession session) {
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
 
	/**
	 * @method		setUserPassword
	 * @param userVO 
	 * @author		hodongryu
	 * @since		2017.10.30.
	 * @version		1.0
	 * @see			일반 로그인일 경우 비밀번호 해시, sns 로그인일 경우 비밀번호를 이메일로 해시
	 * <pre>
	 * << 개정이력(Modification Information) >>
	 *    수정일       수정자          수정내용
	 *    -------      -------     -------------------
	 *    2017.10.30.  hodongryu      최초작성
	 * </pre>
	 */
	private void setUserPassword(UserVO userVO) {
		if(userVO.getUserJoinType() == JoinType.COMMON)
			userVO.setUserPassword(securityUtils.getHash(userVO.getUserPassword()));
		else if(userVO.getUserJoinType() == JoinType.KAKAO || userVO.getUserJoinType() == JoinType.NAVER)
			userVO.setUserPassword(securityUtils.getHash(userVO.getUserEmail()));
	}

}
