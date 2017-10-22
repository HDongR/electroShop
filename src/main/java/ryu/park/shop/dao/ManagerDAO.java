package ryu.park.shop.dao;
 
import java.util.List; 
import ryu.park.shop.vo.UserVO;

public interface ManagerDAO {
	public UserVO findManager(String managerEmail);
	public UserVO loginManager(UserVO userVO);
	public int addManager(UserVO userVO);
	public int updateManager(UserVO userVO);
	public int deleteManager(UserVO userVO); 
	public int deleteUsers(List<UserVO> users);
	public int userTotalCount(String searchOption, String keyword);
	public List<UserVO> getUserList(int start, int end, String searchOption, String keyword);
	public UserVO getUserOne(String email);
	public int updateUserOne(UserVO userVO);

}
