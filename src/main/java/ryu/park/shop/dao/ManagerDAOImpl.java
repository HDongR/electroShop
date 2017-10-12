package ryu.park.shop.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import ryu.park.shop.vo.GoodsVO;
import ryu.park.shop.vo.UserVO;

@Repository
@Qualifier("managerDao")
public class ManagerDAOImpl implements ManagerDAO {

	private static final Logger logger = LoggerFactory.getLogger(ManagerDAOImpl.class);

	@Autowired
	SqlSession session;

	private final String NAMESPACE = "ManagerMapper.";

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

	@Override
	public int addGoods(GoodsVO goodsVO) {
		try {
			return session.insert(NAMESPACE + "addGoods", goodsVO);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
			return -1;
		}
	}

	@Override
	public List<GoodsVO> getGoodsList(int start, int end, String searchOption, String keyword) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("searchOption", searchOption);
		map.put("keyword", keyword); 
		map.put("start", start);
		map.put("end", end);

		return session.selectList(NAMESPACE + "getGoodsList", map);
	}

	@Override
	public GoodsVO getGoodsOne(int goodsSeq) {
		return session.selectOne(NAMESPACE + "getGoodsOne", goodsSeq);
	}

	@Override
	public void updateGoodsOne(GoodsVO goodsVO) {
		session.update(NAMESPACE + "updateGoodsOne", goodsVO);
	}

	@Override
	public int goodsTotalCount() {
		return session.selectOne(NAMESPACE + "goodsTotalCount");
	}

}
