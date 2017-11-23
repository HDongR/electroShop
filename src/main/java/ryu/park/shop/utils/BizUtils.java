package ryu.park.shop.utils;

import java.util.List;
import java.util.Map;
 
import ryu.park.shop.vo.CartVO;

public class BizUtils {
	private static BizUtils Instance = new BizUtils();

	public static BizUtils getInstance() {
		return Instance == null ? new BizUtils() : Instance;
	}
	
	/**
	 * @method calculCostCartList
	 * @param returnType
	 *            0:공급가액, 1:부가세, 2:견적총액
	 * @param model
	 * @return 0:공급가액, 1:부가세, 2:견적총액
	 * @author hodongryu
	 * @since 2017.11.18.
	 * @version 1.0
	 * @see 장바구니 가격 계산 후 출력(총가격 포함)
	 * 
	 *      <pre>
	 * << 개정이력(Modification Information) >>
	 *    수정일       수정자          수정내용
	 *    -------      -------     -------------------
	 *    2017.11.18.  hodongryu      최초작성
	 *      </pre>
	 */
	public String calculCostCartList(int returnType, Map<String, Object> model) {
		@SuppressWarnings("unchecked")
		List<CartVO> cartList = (List<CartVO>) model.get("cartList");

		long allSum = 0;
		long allGoodsSum = 0;
		int tax = 0;

		for (CartVO cart : cartList) {

			int goodsCnt = cart.getCartGoodsCnt();
			long goodsCost = cart.getGoodsVO().getGoodsCost();
			long sum = goodsCnt * goodsCost;
			allGoodsSum += sum;
		}

		tax = Math.round(allGoodsSum / 10);
		allSum = allGoodsSum + tax;

		if (returnType == 0) {
			return CurrencyUtils.toNumFormat(allGoodsSum);
		} else if (returnType == 1) {
			return CurrencyUtils.toNumFormat(tax);
		} else {
			return CurrencyUtils.toNumFormat(allSum);
		}
	}
}
