package ryu.park.shop.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ryu.park.shop.vo.UserVO;

public interface ManagerService {
	public UserVO findManager(String managerEmail);
	public UserVO loginManager(UserVO userVO);
	@Transactional
	public int addManager(UserVO userVO);
	@Transactional
	public int updateManager(UserVO userVO);
	@Transactional
	public int deleteManager(UserVO userVO);
	@Transactional
	public int deleteUsers(List<UserVO> users);
	public int userTotalCount(String searchOption, String keyword);
	public List<UserVO> getUserList(int start, int end, String searchOption, String keyword); 
	public UserVO getUserOne(String email);
	@Transactional
	public int updateUserOne(UserVO userVO);

}
